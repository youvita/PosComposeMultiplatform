package ui.stock.data.repository

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
            box = stock.box,
            total = stock.total,
            date_in = stock.date_in,
            date_out = stock.date_out)
    }

    override suspend fun addProduct(product: Product) {
        db.insertProduct(
            product_id = product.product_id,
            name = product.name,
            image = product.image,
            qty = product.qty,
            price = product.price,
            description = product.description
        )
    }
}