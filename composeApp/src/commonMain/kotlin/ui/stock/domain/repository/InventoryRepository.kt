package ui.stock.domain.repository

import ui.stock.domain.model.Product
import ui.stock.domain.model.Stock

interface InventoryRepository {
    suspend fun addStock(stock: Stock)

    suspend fun addProduct(product: Product)
}