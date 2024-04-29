package history.presentation

sealed class HistoryEvent {
    data class SearchEvent(val searchText: String) : HistoryEvent()
    data class NextPage(
        val offset: Int = 0,
        val totalPage: Int?,
        val currentPage: Int = 0,
    ) : HistoryEvent()
    data class SetLimitRow(val limitRow: Int?) : HistoryEvent()
    object GetOrderHistoryEvent : HistoryEvent()

    object ClearEvent: HistoryEvent()
    data class GetOrderDetail(val id: Long) : HistoryEvent()

}