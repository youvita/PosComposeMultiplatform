package menu.presentation

import customer.domain.model.CustomerModel
import customer.presentation.CustomerEvent
import menu.domain.model.BillModel
import menu.domain.model.MenuModel
import setting.domain.model.ItemModel

sealed class OrderEvent {
    data class MenuSelectEvent(val menu: MenuModel) : OrderEvent()
    data class SelectOrderEvent(val item: ItemModel) : OrderEvent()
    data class OnRemoveOrder(val item: ItemModel) : OrderEvent()
    data class PlaceOrderEvent(val bill: BillModel) : OrderEvent()
    data class PrintLabelEvent(val items: List<ItemModel>) : OrderEvent()
    data class FindCustomerOrdersEvent(val cusId: Long) : OrderEvent()
    data class PreOrderEvent(val customerModel: CustomerModel) : OrderEvent()
    data class QuantityChangeEvent(
        val item: ItemModel,
        val qtyChanged: Int
    ) : OrderEvent()
    data class BookmarkItemEvent(val item: ItemModel): OrderEvent()
    object ClearEvent: OrderEvent()
    data class SearchEvent(val searchText: String) : OrderEvent()
    data class GetItemsEvent(val id: Long): OrderEvent()
    object GetMenusEvent: OrderEvent()
}