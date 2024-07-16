package orderhistory.presentation

sealed class OrderHistoryEvent {
    data class SearchOrder(val searchText: String) : OrderHistoryEvent()
    data class NextPage(
        val offset: Int = 0,
        val totalPage: Int?,
        val currentPage: Int = 0,
    ) : OrderHistoryEvent()
    data class SetLimitRow(val limitRow: Int?) : OrderHistoryEvent()
    object GetOrderOrderHistoryEvent : OrderHistoryEvent()

    object ClearSearchOrder: OrderHistoryEvent()
    object ClearSearchOrderByDate: OrderHistoryEvent()
    data class GetOrderDetail(val id: Long) : OrderHistoryEvent()
    object ClearSelectItem : OrderHistoryEvent()
    data class SearchOrderByDate(val start: String, val end: String) : OrderHistoryEvent()

}