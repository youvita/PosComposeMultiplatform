package ui.stock.presentation

import androidx.compose.foundation.interaction.DragInteraction
import ui.stock.domain.model.Product

sealed class InventoryEvent {
    class SearchProduct(val keyword: String): InventoryEvent()
    class SearchProductByDate(val startDate: String, val endDate: String): InventoryEvent()
    class AddProduct(val product: Product) : InventoryEvent()
    class UpdateProduct(val product: Product) : InventoryEvent()
    class GetMenu : InventoryEvent()
    class GetProductStock : InventoryEvent()
    class GetProduct(val menuId: Long) : InventoryEvent()
}