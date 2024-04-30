package menu.presentation

import customer.domain.model.CustomerModel
import menu.domain.model.MenuModel
import setting.domain.model.ItemModel

sealed class OrderEvent {
    data class MenuSelectEvent(val menu: MenuModel) : OrderEvent()
    data class SelectOrderEvent(val item: ItemModel) : OrderEvent()
    data class OnRemoveOrder(val item: ItemModel) : OrderEvent()
    object PlaceOrderEvent : OrderEvent()
    data class PrintLabelEvent(val items: List<ItemModel>) : OrderEvent()
    data class FindCustomerOrdersEvent(val cusId: Long) : OrderEvent()
    data class PreOrderEvent(val customerModel: CustomerModel) : OrderEvent()
    data class QuantityChangeEvent(val item: ItemModel) : OrderEvent()
    data class BookmarkItemEvent(val item: ItemModel): OrderEvent()
    object ClearEvent: OrderEvent()
    object GetItemsEvent: OrderEvent()
    object GetMenusEvent: OrderEvent()
}