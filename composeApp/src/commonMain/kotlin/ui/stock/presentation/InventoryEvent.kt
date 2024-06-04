package ui.stock.presentation

sealed class InventoryEvent {
    class SearchProduct(val keyword: String): InventoryEvent()
}