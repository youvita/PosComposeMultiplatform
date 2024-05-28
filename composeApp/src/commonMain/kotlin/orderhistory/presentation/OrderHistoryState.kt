package orderhistory.presentation

import core.data.Status
import org.topteam.pos.OrderEntity

data class OrderHistoryState(
    val status: Status? = null,
    val isLoading: Boolean = false,
    val searchText: String = "",
    val orderList: List<OrderEntity>? = arrayListOf(),
    val offset: Int = 0,
    val totalPage: Int = 1,
    val allRow: Int = 0
)