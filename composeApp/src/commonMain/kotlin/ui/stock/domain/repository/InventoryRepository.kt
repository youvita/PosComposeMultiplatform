package ui.stock.domain.repository

import core.data.Resource
import kotlinx.coroutines.flow.Flow
import menu.domain.model.MenuModel
import ui.stock.domain.model.Product
import ui.stock.domain.model.ProductStock

interface InventoryRepository {
    suspend fun addProduct(product: Product)
    suspend fun getProduct(id: Long): Flow<Resource<List<Product>>>
    suspend fun getStock(): Flow<Resource<List<ProductStock>>>
    suspend fun getMenu(): Flow<Resource<List<MenuModel>>>
}