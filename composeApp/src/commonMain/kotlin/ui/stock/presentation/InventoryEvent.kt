package ui.stock.presentation

import ui.stock.domain.model.Product

sealed class InventoryEvent {
    class SearchProduct(val keyword: String): InventoryEvent()
    class AddProduct(val product: Product) : InventoryEvent()
    class UpdateProduct(val product: Product) : InventoryEvent()
    class GetMenu : InventoryEvent()
    class GetProductStock : InventoryEvent()
    class GetProduct(val menuId: Long) : InventoryEvent()
}