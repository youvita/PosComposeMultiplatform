package ui.stock.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneNotNull
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.preat.peekaboo.image.picker.toImageBitmap
import core.data.Resource
import core.mapper.toProduct
import core.mapper.toStock
import io.ktor.client.request.forms.formData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.DateTimeUnit
import org.topteam.pos.PosDatabase
import ui.stock.domain.model.Product
import ui.stock.domain.model.ProductStock
import ui.stock.domain.model.Stock
import ui.stock.domain.repository.InventoryRepository

class InventoryRepositoryImpl(posDatabase: PosDatabase): InventoryRepository {

    private val db = posDatabase.appDatabaseQueries

    override suspend fun addStock(stock: Stock) {
        var item = stock
        val result = db.getStockByProductId(item.productId).executeAsList()
        if (result.isNotEmpty()) {
            item = result.last().toStock()
        } else {
            val lastId = db.getStockId().executeAsList()
            if (lastId.isNotEmpty()) {
                item = lastId.last().toStock()
            }
        }

        println(">>>> ${item.stockId}")
        db.insertStock(
            stock_id = item.stockId?.plus(1).takeIf { item.stockId?.let { it > 0 } == true } ?: 1,
            product_id = stock.productId,
            stock_in = item.stockIn?.plus(1),
            stock_out = item.stockOut,
            stock_box = item.stockBox,
            total = item.stockIn?.plus(1),
            date_in = item.dateIn,
            date_out = item.dateOut
        )
    }

    override suspend fun addProduct(product: Product) {
        db.insertProduct(
            menu_id = product.menu_id,
            product_id = product.product_id,
            name = product.name,
            image = product.image,
            qty = product.qty,
            price = product.price,
            discount = product.discount,
            category_name = product.category_name,
            cateogry_image = product.category_image
        )
    }

    override suspend fun getProduct(id: Long): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        val result = db.getAllProduct(id).executeAsList().map { it.toProduct() }
        emit(Resource.Success(result))
    }

    override suspend fun getStock(): Flow<Resource<List<ProductStock>>> = flow {
        emit(Resource.Loading())
        val result = db.getProductStock().executeAsList()
        val productStock = mutableListOf<ProductStock>()
        println(">>>> size::: ${result.size}")
        for (item in result) {
            println(">>>> item:::: ${item.product_id}")
            val match = ProductStock(
                stockId = item.stock_id,
                stockIn = item.stock_in,
                stockOut = item.stock_out,
                stockTotal = item.stock_box,
                productId = item.product_id,
                productName = item.name,
                productImage = item.image?.toImageBitmap(),
            )
            productStock.add(match)
        }
        emit(Resource.Success(productStock))
    }
}