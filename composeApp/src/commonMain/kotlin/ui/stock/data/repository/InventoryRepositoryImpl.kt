package ui.stock.data.repository

import com.preat.peekaboo.image.picker.toImageBitmap
import core.data.Resource
import core.mapper.toMenu
import core.mapper.toStock
import core.utils.Constants
import core.utils.SharePrefer
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
    override suspend fun updateStock(product: Product) {
        val result = db.getStockByProductId(product.productId).executeAsList()
        if (result.isNotEmpty()) {
            val stock = result.find {
                it.stock_id == product.id
            }?.toStock()
            db.updateStock(
                stock_in = product.qty?.toLong().takeIf { product.statusCode == Constants.StockType.STOCK_IN } ?: 0,
                stock_out = product.qty?.toLong().takeIf { product.statusCode == Constants.StockType.STOCK_OUT } ?: 0,
                total = product.qty?.toLong(),
                price = product.price?.toLong().takeIf { product.statusCode == Constants.StockType.STOCK_OUT } ?: stock?.unitPrice,
                status_name = product.statusName,
                date = getCurrentDate(),
                time = getCurrentTime(),
                stock_id = product.id
            )

//            db.updateProduct(
//                menu_id = product.menuId,
//                product_id = product.productId,
//                name = product.name,
//                image = product.image,
//                imageUrl = product.imageUrl,
//                uom = product.uom,
//                qty = product.qty,
//                price = product.price,
//                discount = product.discount,
//                product_id_ = product.productId
//            )

            // lasted update for date in
            SharePrefer.putPrefer("ADD", "add")
        }
    }

    override suspend fun adjustProduct(product: Product) {
        val result = db.getStockByProductId(product.productId).executeAsList()
        if (result.isNotEmpty()) {
            val stock = result.last().toStock()
            stock.stockId?.let { id ->
//                db.updateStock(
//                    product.productId,
//                    stock.stockIn,
//                    product.qty?.toLong(),
//                    stock.stockBox,
//                    stock.stockTotal?.minus(product.qty?.toLong() ?: 0),
//                    stock.dateIn,
//                    getCurrentDate(),
//                    stock.timeIn,
//                    getCurrentTime(),
//                    id
//                )
                db.updateProductQty(
                    qty = stock.stockTotal?.minus(product.qty?.toLong() ?: 0).toString(),
                    id = id
                )
            }

            db.insertStock(
                product_id = product.productId,
                stock_in = product.qty?.toLong().takeIf { product.statusCode == Constants.StockType.STOCK_IN } ?: 0,
                stock_out = product.qty?.toLong().takeIf { product.statusCode == Constants.StockType.STOCK_OUT } ?: 0,
                stock_box = 0,
                total = product.qty?.toLong(),
                price = product.price?.toLong(),
                status_code = Constants.StockType.STOCK_IN.takeIf { product.statusCode == Constants.StockType.STOCK_IN } ?: Constants.StockType.STOCK_OUT,
                status_name = product.statusName,
                date = getCurrentDate(),
                time = getCurrentTime()
            )

            // lasted update for date out
            SharePrefer.putPrefer("ADD", "")
        }


    }

    override suspend fun updateProductQty(id: Long, qty: String): Flow<Resource<Unit>> = flow{
        emit(Resource.Loading())
        emit(Resource.Success(db.updateProductQty(qty = qty, id = id)))
    }

    override suspend fun addProduct(product: Product) {
        val result = db.getStockByProductId(product.productId).executeAsList()
//        if (result.isNotEmpty()) {
//            // add existing stock
//            val stock = result.last().toStock()
//            stock.stockId?.let { id ->
//                db.updateStock(
//                    product.productId,
//                    product.qty?.toLong(),
//                    stock.stockOut,
//                    product.qty?.toLong(),
//                    stock.statusCode,
//                    getCurrentDate(),
//                    getCurrentTime(),
//                    id
//                )
//            }
//
//            db.updateProduct(
//                menu_id = product.menuId,
//                product_id = product.productId,
//                name = product.name,
//                image = product.image,
//                imageUrl = product.imageUrl,
//                uom = product.uom,
//                qty = stock.stockTotal?.plus(product.qty?.toLong() ?: 0).toString(),
//                price = product.price,
//                discount = product.discount,
//                product_id_ = product.productId
//            )
//
//        } else {
            db.insertStock(
                product_id = product.productId,
                stock_in = product.qty?.toLong(),
                stock_out = 0,
                stock_box = 0,
                total = product.qty?.toLong(),
                price = product.price?.toLong(),
                status_code = Constants.StockType.STOCK_IN,
                status_name = product.statusName,
                date = getCurrentDate(),
                time = getCurrentTime(),
            )

            db.insertProduct(
                menu_id = product.menuId,
                product_id = product.productId,
                name = product.name,
                image = product.image,
                imageUrl = product.imageUrl,
                uom = product.uom,
                qty = product.qty,
                price = product.price,
                discount = product.discount
            )
//        }

        // lasted update for date in
        SharePrefer.putPrefer("ADD", "add")
    }

    override suspend fun getProductByMenuId(id: Long): Flow<Resource<List<ProductMenu>>> = flow {
        emit(Resource.Loading())
        val result = db.getProductByMenuId(id).executeAsList()
        val productMenu = mutableListOf<ProductMenu>()
        for (item in result) {
            val match = ProductMenu(
                id = item.id,
                menuId = item.menu_id,
                menuName = item.menuName,
                menuImage = item.menuImage,
                productId = item.product_id,
                name = item.name,
                image = item.image,
                imageUrl = item.imageUrl,
                uom = item.uom,
                qty = item.qty,
                price = item.price,
                discount = item.discount,
                statusCode = item.status_code,
                statusName = item.status_name,
                date = item.date
//                date = (item.date_in + "" + item.time_in).takeIf { SharePrefer.getPrefer("ADD") == "add" } ?: (item.date_out + " " + item.time_out)
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
//        val result = db.getProductByDate(startDate, endDate).executeAsList()
        val productMenu = mutableListOf<ProductMenu>()
//        for (item in result) {
//            val match = ProductMenu(
//                id = item.id,
//                menuId = item.menu_id,
//                menuName = item.menuName,
//                menuImage = item.menuImage,
//                productId = item.product_id,
//                name = item.name,
//                image = item.image,
//                imageUrl = item.imageUrl,
//                uom = item.uom,
//                qty = item.qty,
//                price = item.price,
//                discount = item.discount,
//                date = item.date
////                date = (item.date_in + "" + item.time_in).takeIf { SharePrefer.getPrefer("ADD") == "add" } ?: (item.date_out + " " + item.time_out)
//            )
//            productMenu.add(match)
//        }
        emit(Resource.Success(productMenu))
    }

    override suspend fun getAllProduct(): Flow<Resource<List<ProductMenu>>> = flow {
        emit(Resource.Loading())
        val result = db.getAllProduct().executeAsList()
        val productMenu = mutableListOf<ProductMenu>()
        for (item in result) {
            val match = ProductMenu(
                id = item.id,
                menuId = item.menu_id,
                menuName = item.menuName,
                menuImage = item.menuImage,
                productId = item.product_id,
                name = item.name,
                image = item.image,
                imageUrl = item.imageUrl,
                uom = item.uom,
                qty = item.qty,
                price = item.price,
                discount = item.discount,
                statusCode = item.status_code,
                statusName = item.status_name,
                date = item.date
//                date = (item.date_in + "" + item.time_in).takeIf { SharePrefer.getPrefer("ADD") == "add" } ?: (item.date_out + " " + item.time_out)
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
                productPrice = item.price?.toString(),
                categoryName = item.menuName,
                statusName = item.status_name,
                statusCode = item.status_code,
                date = item.date,
                time = item.time
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