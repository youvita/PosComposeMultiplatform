package orderhistory.presentation

import core.data.Status
import menu.presentation.OrderState
import org.topteam.pos.OrderEntity
import org.topteam.pos.ProductOrderEntity
import setting.domain.model.ItemModel
import ui.stock.domain.model.Product

data class OrderHistoryState(
    val status: Status? = null,
    val isLoading: Boolean = false,
    val searchText: String = "",
    val orderList: List<OrderEntity>? = arrayListOf(),
    val offset: Int = 0,
    val totalPage: Int = 1,
    val allRow: Int = 0,
    val productList: List<ItemModel>? = null,
    val orderSelected: OrderEntity? = null,
    val selectedId: Long? = null
)