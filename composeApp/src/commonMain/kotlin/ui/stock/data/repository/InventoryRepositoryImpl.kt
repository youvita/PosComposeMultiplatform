package ui.stock.data.repository

import com.preat.peekaboo.image.picker.toImageBitmap
import core.data.Resource
import core.mapper.toMenu
import core.mapper.toStock
import core.utils.getCurrentDate
import core.utils.getCurrentDateTime
import core.utils.getCurrentTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import menu.domain.model.MenuModel
import org.topteam.pos.PosDatabase
import ui.stock.domain.model.Product
import ui.stock.domain.model.ProductMenu
import ui.stock.domain.model.ProductStock
import ui.stock.domain.repository.InventoryRepository

class InventoryRepositoryImpl(posDatabase: PosDatabase): InventoryRepository {

    private val db = posDatabase.appDatabaseQueries
    override suspend fun updateProduct(product: Product) {
        val result = db.getStockByProductId(product.productId).executeAsList()
        if (result.isNotEmpty()) {
            val stock = result.last().toStock()
            stock.stockId?.let { id ->
                db.updateStock(
                    product.productId,
                    stock.stockIn,
                    stock.stockOut,
                    stock.stockBox,
                    stock.stockTotal,
                    getCurrentDateTime(),
                    stock.dateOut,
                    stock.timeIn,
                    stock.timeOut,
                    id
                )
            }
        }
        db.updateProduct(
            menu_id = product.menuId,
            product_id = product.productId,
            name = product.name,
            image = product.image,
            imageUrl = product.imageUrl,
            qty = product.qty,
            price = product.price,
            discount = product.discount,
            product_id_ = product.productId
        )
    }

    override suspend fun addProduct(product: Product) {
        val result = db.getStockByProductId(product.productId).executeAsList()
        if (result.isNotEmpty()) {
            val stock = result.last().toStock()
            stock.stockId?.let { id ->
                db.updateStock(
                    product.productId,
                    stock.stockIn?.plus(1),
                    stock.stockOut,
                    stock.stockBox,
                    stock.stockTotal?.plus(1),
                    getCurrentDate(),
                    stock.dateOut,
                    getCurrentTime(),
                    stock.timeOut,
                    id
                )
            }
        } else {
            db.insertStock(
                product_id = product.productId,
                stock_in = 1,
                stock_out = 0,
                stock_box = 0,
                total = 1,
                date_in = getCurrentDate(),
                date_out = "",
                time_in = getCurrentTime(),
                time_out = ""
            )
        }

        db.insertProduct(
            menu_id = product.menuId,
            product_id = product.productId,
            name = product.name,
            image = product.image,
            imageUrl = product.imageUrl,
            qty = product.qty,
            price = product.price,
            discount = product.discount
        )
    }

    override suspend fun getProduct(id: Long): Flow<Resource<List<ProductMenu>>> = flow {
        emit(Resource.Loading())
        val result = db.getProductByMenuId(id).executeAsList()
        val productMenu = mutableListOf<ProductMenu>()
        for (item in result) {
            val match = ProductMenu(
                menuId = item.menu_id,
                menuName = item.menuName,
                menuImage = item.menuImage,
                productId = item.product_id,
                name = item.name,
                image = item.image,
                imageUrl = item.imageUrl,
                qty = item.qty,
                price = item.price,
                discount = item.discount,
                date = item.date_in + " " + item.time_in
            )
            productMenu.add(match)
        }
        emit(Resource.Success(productMenu))
    }

    override suspend fun getSearchProductByDate(
        startDate: String,
        endDate: String,
    ): Flow<Resource<List<ProductMenu>>> = flow {
        emit(Resource.Loading())
        val result = db.getProductByDate(startDate, endDate).executeAsList()
        val productMenu = mutableListOf<ProductMenu>()
        for (item in result) {
            val match = ProductMenu(
                menuId = item.menu_id,
                menuName = item.menuName,
                menuImage = item.menuImage,
                productId = item.product_id,
                name = item.name,
                image = item.image,
                imageUrl = item.imageUrl,
                qty = item.qty,
                price = item.price,
                discount = item.discount,
                date = item.date_in + " " + item.time_in
            )
            productMenu.add(match)
        }
        emit(Resource.Success(productMenu))
    }

    override suspend fun getAllProduct(): Flow<Resource<List<ProductMenu>>> = flow {
        emit(Resource.Loading())
        val result = db.getAllProduct().executeAsList()
        val productMenu = mutableListOf<ProductMenu>()
        for (item in result) {
            val match = ProductMenu(
                menuId = item.menu_id,
                menuName = item.menuName,
                menuImage = item.menuImage,
                productId = item.product_id,
                name = item.name,
                image = item.image,
                imageUrl = item.imageUrl,
                qty = item.qty,
                price = item.price,
                discount = item.discount,
                date = item.date_in + " " + item.time_in
            )
            productMenu.add(match)
        }
        emit(Resource.Success(productMenu))
    }

    override suspend fun getStock(): Flow<Resource<List<ProductStock>>> = flow {
        emit(Resource.Loading())
        val result = db.getProductStock().executeAsList()
        val productStock = mutableListOf<ProductStock>()
        for (item in result) {
            val match = ProductStock(
                stockId = item.stock_id,
                stockIn = item.stock_in,
                stockOut = item.stock_out,
                stockTotal = item.total,
                productId = item.product_id,
                productName = item.name,
                productImage = item.image?.toImageBitmap(),
                productImageUrl = item.imageUrl,
                categoryName = item.menuName,
                dateIn = item.date_in,
                timeIn = item.time_in
            )
            productStock.add(match)
        }
        emit(Resource.Success(productStock))
    }

    override suspend fun getMenu(): Flow<Resource<List<MenuModel>>> = flow {
        emit(Resource.Loading())
        val result = db.getAllMenu().executeAsList().map { it.toMenu() }
        emit(Resource.Success(result))
    }
}