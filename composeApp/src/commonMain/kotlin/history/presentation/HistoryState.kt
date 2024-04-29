package history.presentation

import core.data.Status
import history.domain.model.OrderModel

data class HistoryState(
    val status: Status? = null,
    val isLoading: Boolean = false,
    val searchText: String = "",
//    val orderList: List<OrderModel>? = arrayListOf(),
    val orderList: List<OrderModel>? = arrayListOf(),
//    val orderDetailList: List<OrderDetail>? = arrayListOf(),
    val offset: Int = 0,
    val totalPage: Int = 1,
    val allRow: Int = 0
)