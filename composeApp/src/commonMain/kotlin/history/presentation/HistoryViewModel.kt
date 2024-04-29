package history.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.data.Resource
import history.domain.model.OrderModel
import history.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.topteam.pos.History


class HistoryViewModel (
    private val historyRepository: HistoryRepository
): ScreenModel {

    private val _uiState = MutableStateFlow(HistoryState())
    val uiState: StateFlow<HistoryState> = _uiState.asStateFlow()

    private val _pagingState = MutableStateFlow(PagingState())
    val pagingState: StateFlow<PagingState> = _pagingState.asStateFlow()


//    private val _pagingState = mutableStateOf(PagingState())
//    val pagingState: State<PagingState> get() = _pagingState

    var ROW_LIMIT = 5

    private fun getAllHistoryList() {
        historyRepository.getOrderHistory().onEach {result ->
            when(result) {
                is Resource.Success -> {
                    //map object list History to OrderModel
//                    val orderModelList = ArrayList<OrderModel>()
//                    result.data?.forEach {dataHistory->
//                        orderModelList.add(
//                            OrderModel(
//                                customer_id = dataHistory.customer_id,
//                                table_id = dataHistory.table_id,
//                                order_no = dataHistory.order_no,
//                                queue_no = dataHistory.queue_no,
//                                date = dataHistory.date,
//                                total_item = dataHistory.total_item,
//                                total_qty = dataHistory.total_qty,
//                                sub_total = dataHistory.sub_total,
//                                discount = dataHistory.discount,
//                                vat = dataHistory.vat,
//                                total = dataHistory.total,
//                                status = dataHistory.status
//                            )
//                        )
//                    }
//
//                    _uiState.value = uiState.value.copy(
//                        status = result.status,
//                        isLoading = false,
//                        orderList = orderModelList
//                    )

                    val limitRow = pagingState.value.limitRow
                    //get totalPage
                    val totalPage = if ((result.data?.size?:0) % limitRow > 0){
                        ((result.data?.size?:0) / limitRow) + 1
                    } else {
                        ((result.data?.size?:0) / limitRow)
                    }
                    _pagingState.value = pagingState.value.copy(
                        isLoading = false,
                        allRow = result.data?.size,
                        totalPage = totalPage,
                        offset = 0,
                        limitRow = limitRow
                    )
                    //fetch first page
                    getHistoryListPaging(1,0)

                }
                is Resource.Error -> {
                    _uiState.value = uiState.value.copy(
                        status = result.status,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _uiState.value = uiState.value.copy(isLoading = true)
                }
            }
        }.launchIn(screenModelScope)
    }

    private fun getHistoryListPaging(currentPage: Int = 0, offset: Int = 0){
        historyRepository.getOrderHistoryPaging(pagingState.value.limitRow ,offset).onEach {result -> //limit is max row, offset is amount row that we skip

            when (result) {
                is Resource.Success -> {
                    //map object list History to OrderModel
                    val orderModelList = ArrayList<OrderModel>()

                    result.data?.forEach {dataHistory->
                        orderModelList.add(
                            OrderModel(
                                customer_id = dataHistory.customer_id,
                                table_id = dataHistory.table_id,
                                order_no = dataHistory.order_no,
                                queue_no = dataHistory.queue_no,
                                date = dataHistory.date,
                                total_item = dataHistory.total_item,
                                total_qty = dataHistory.total_qty,
                                sub_total = dataHistory.sub_total,
                                discount = dataHistory.discount,
                                vat = dataHistory.vat,
                                total = dataHistory.total,
                                status = dataHistory.status
                            )
                        )
                    }

                    //get totalPage
                    val totalPage = if ((pagingState.value.allRow?:0) % pagingState.value.limitRow > 0){
                        ((pagingState.value.allRow?:0) / pagingState.value.limitRow) + 1
                    } else {
                        ((pagingState.value.allRow?:0) / pagingState.value.limitRow)
                    }

                    //update data for table
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        orderList = orderModelList
                    )

                    //update PagingState
                    _pagingState.value = pagingState.value.copy(
                        isLoading = false,
                        currentPage = currentPage,
                        totalPage = totalPage,
                        offset = offset
                    )

                }
                is Resource.Error -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                    )
                }
                is Resource.Loading -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = true,
                    )
                }
            }
        }.launchIn(screenModelScope)
    }

//    private fun getHistoryListPaging(currentPage: Int?, offset: Int?) {
//        historyUseCase.getOrderPaging(pagingState.value.limitRow,offset?:0).onEach { result -> //limit is max row, offset is amount row that we skip
//            when (result) {
//                is Resource.Success -> {
//                    //get totalPage
//                    val totalPage = if ((pagingState.value.allRow?:0) % pagingState.value.limitRow > 0){
//                        ((pagingState.value.allRow?:0) / pagingState.value.limitRow) + 1
//                    } else {
//                        ((pagingState.value.allRow?:0) / pagingState.value.limitRow)
//                    }
//
//                    //update data for table
//                    _state.value = state.value.copy(
//                        isLoading = false,
//                        orderList = result.data
//                    )
//
//                    //update PagingState
//                    _pagingState.value = pagingState.value.copy(
//                        isLoading = false,
//                        currentPage = currentPage,
//                        totalPage = totalPage,
//                        offset = offset
//                    )
//
//                }
//                is Resource.Error -> {
//                    _state.value = state.value.copy(
//                        isLoading = false,
//                    )
//                }
//                is Resource.Loading -> {
//                    _state.value = state.value.copy(
//                        isLoading = true,
//                    )
//                }
//            }
//        }.launchIn(viewModelScope)
//    }

//    private fun getOrderDetail(id: Long) {
//        historyUseCase.getOrderDetail(id).onEach { result -> //limit is max row, offset is amount row that we skip
//            when (result) {
//                is Resource.Success -> {
//                    _state.value = state.value.copy(
//                        orderDetailList = result.data
//                    )
//                    Log.d(">>>> OrderDetail", "${result.data}")
//                }
//                is Resource.Error -> {
//                    _state.value = state.value.copy(
//                        isLoading = false,
//                    )
//                }
//                is Resource.Loading -> {
//                    _state.value = state.value.copy(
//                        isLoading = true,
//                    )
//                }
//            }
//        }.launchIn(viewModelScope)
//    }

    fun onEvent(event: HistoryEvent){
        when(event){
            is HistoryEvent.SearchEvent -> {
                _uiState.value = _uiState.value.copy(searchText = event.searchText)

            }

            is HistoryEvent.ClearEvent -> {
                _uiState.value = _uiState.value.copy(searchText = "")
            }

            is HistoryEvent.GetOrderHistoryEvent -> {
                getAllHistoryList()
            }

            is HistoryEvent.NextPage -> {
                getHistoryListPaging(
                    currentPage = event.currentPage,
                    offset = event.offset
                )
            }

            is HistoryEvent.SetLimitRow -> {
                ROW_LIMIT = event.limitRow?:ROW_LIMIT
                _pagingState.value = _pagingState.value.copy(limitRow = event.limitRow?:ROW_LIMIT)
                getHistoryListPaging(1,0)
            }

//            is HistoryEvent.GetOrderDetail -> {
//                getOrderDetail(event.id)
//            }

            else -> {}
        }
    }

}