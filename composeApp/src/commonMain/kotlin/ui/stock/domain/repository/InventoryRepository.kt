package ui.stock.domain.repository

import core.data.Resource
import kotlinx.coroutines.flow.Flow
import org.topteam.pos.Menu
import ui.stock.domain.model.Product
import ui.stock.domain.model.ProductStock
import ui.stock.domain.model.Stock

interface InventoryRepository {
    suspend fun addStock(stock: Stock)

    suspend fun addProduct(product: Product)
    suspend fun getProduct(id: Long): Flow<Resource<List<Product>>>
    suspend fun getStock(): Flow<Resource<List<ProductStock>>>
}