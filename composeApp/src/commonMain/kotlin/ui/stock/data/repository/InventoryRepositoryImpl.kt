package ui.stock.data.repository

import com.preat.peekaboo.image.picker.toImageBitmap
import core.data.Resource
import core.mapper.toMenu
import core.mapper.toProduct
import core.mapper.toStock
import core.utils.getCurrentDateTime
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
                    getCurrentDateTime(),
                    stock.dateOut,
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
                date_in = getCurrentDateTime(),
                date_out = ""
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
                menuId = item.id,
                menuName = item.menuName,
                menuImage = item.menuImage,
                productId = item.product_id,
                name = item.name,
                image = item.image,
                imageUrl = item.imageUrl,
                qty = item.qty,
                price = item.price,
                discount = item.discount
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
                menuId = item.id,
                menuName = item.menuName,
                menuImage = item.menuImage,
                productId = item.product_id,
                name = item.name,
                image = item.image,
                imageUrl = item.imageUrl,
                qty = item.qty,
                price = item.price,
                discount = item.discount
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
                stockTotal = item.stock_box,
                productId = item.product_id,
                productName = item.name,
                productImage = item.image?.toImageBitmap(),
                productImageUrl = item.imageUrl,
                dateIn = item.date_in
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