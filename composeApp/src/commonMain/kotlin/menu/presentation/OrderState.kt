package menu.presentation

import core.data.Status
import menu.domain.model.BillModel
import menu.domain.model.MenuModel
import setting.domain.model.ItemModel


data class OrderState(
    val status: Status? = null,
    val message: String? = null,
    val searchText: String? = null,
    val isSearching: Boolean = false,
    val queue_no: Long? = 0,
    val menus: List<MenuModel>? = null,
    val items: List<ItemModel>? = null,
    val orders: List<ItemModel>? = null,
    var bill: BillModel? = null
)