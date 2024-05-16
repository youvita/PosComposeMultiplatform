package ui.stock.data.repository

import core.data.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.topteam.pos.Menu
import org.topteam.pos.PosDatabase
import ui.stock.domain.model.Product
import ui.stock.domain.model.Stock
import ui.stock.domain.repository.InventoryRepository

class InventoryRepositoryImpl(posDatabase: PosDatabase): InventoryRepository {

    private val db = posDatabase.appDatabaseQueries

    override suspend fun addStock(stock: Stock) {
        db.insertStock(
            product_id = stock.product_id,
            stock_in = stock.stock_in,
            stock_out = stock.stock_out,
            stock_box = stock.box,
            total = stock.total,
            date_in = stock.date_in,
            date_out = stock.date_out)
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

    override suspend fun getProduct(id: Long): Flow<Resource<List<org.topteam.pos.Product>>> = flow {
        emit(Resource.Loading())
        emit(Resource.Success(db.getAllProduct(id).executeAsList()))
    }
}