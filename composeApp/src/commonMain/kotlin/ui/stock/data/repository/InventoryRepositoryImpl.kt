package ui.stock.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneNotNull
import app.cash.sqldelight.coroutines.mapToOneOrNull
import core.data.Resource
import core.mapper.toProduct
import core.mapper.toStock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.topteam.pos.PosDatabase
import ui.stock.domain.model.Product
import ui.stock.domain.model.Stock
import ui.stock.domain.repository.InventoryRepository

class InventoryRepositoryImpl(posDatabase: PosDatabase): InventoryRepository {

    private val db = posDatabase.appDatabaseQueries

    override suspend fun addStock(stock: Stock) {
        db.insertStock(
            product_id = stock.productId,
            stock_in = stock.stockIn,
            stock_out = stock.stockOut,
            stock_box = stock.stockBox,
            total = stock.stockTotal,
            date_in = stock.dateIn,
            date_out = stock.dateOut)
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

    override suspend fun getStock(): Flow<Resource<List<Stock>>> = flow {
        emit(Resource.Loading())
        val result = db.getStock().executeAsList().map { it.toStock() }
        emit(Resource.Success(result))
    }
}