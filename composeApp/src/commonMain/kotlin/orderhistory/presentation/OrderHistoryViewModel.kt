package orderhistory.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.data.Resource
import orderhistory.domain.repository.OrderHistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import org.topteam.pos.OrderEntity
import setting.domain.model.ItemModel


class OrderHistoryViewModel (
    private val orderHistoryRepository: OrderHistoryRepository
): ScreenModel {

    private val _uiState = MutableStateFlow(OrderHistoryState())
    val uiState: StateFlow<OrderHistoryState> = _uiState.asStateFlow()

    private val _pagingState = MutableStateFlow(PagingState())
    val pagingState: StateFlow<PagingState> = _pagingState.asStateFlow()

    var ROW_LIMIT = 5

    val orderList = _uiState
        .onEach {

        }
        .combine(_uiState) { stateText, stateItem ->
            if (stateText.searchText.isBlank()) {
                stateItem.orderList
            } else {
                stateItem.orderList?.filter { orderEntity ->
                    val matchingCombinations = listOf(
                        "${orderEntity.order_no}",
                        "${orderEntity.total}"
                    )
                    matchingCombinations.any {
                        it.contains(stateText.searchText, ignoreCase = true)
                    }
                }
            }
        }
        .onEach {

        }
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _uiState.value.orderList
        )

    private fun getAllHistoryList() {
        orderHistoryRepository.getOrderHistory().onEach { result ->
            when(result) {
                is Resource.Success -> {

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

    private fun getHistoryByDate(start: String, end: String) {
        orderHistoryRepository.getOrderHistory().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _uiState.value = uiState.value.copy(
                        orderListByDate = filterOrderHistoryList(result.data?: emptyList(),start,end)
                    )
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
        orderHistoryRepository.getOrderHistoryPaging(pagingState.value.limitRow ,offset).onEach { result -> //limit is max row, offset is amount row that we skip

            when (result) {
                is Resource.Success -> {
                    //map object list History to OrderModel
                    val orderModelList = ArrayList<OrderEntity>()

                    result.data?.forEach { dataHistory->
                        orderModelList.add(dataHistory)
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


    private fun getProductOrderById(id: Long) {
        orderHistoryRepository.getProductByOrderId(id).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    val productList = ArrayList<ItemModel>()
                    result.data?.forEach {
                        productList.add(
                            ItemModel(
                                itemId = it.id,
                                product_id = it.product_id,
                                name = it.name,
                                image_product = it.image,
                                qty = it.qty?.toInt(),
                                price = it.price?.toDouble(),
                                discount = it.discount?.toInt()
                            )
                        )
                    }

                    _uiState.value = uiState.value.copy(
                        productList = productList,
                    )
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

    fun onEvent(event: OrderHistoryEvent){
        when(event){
            is OrderHistoryEvent.SearchOrder -> {
                _uiState.value = _uiState.value.copy(searchText = event.searchText)

            }

            is OrderHistoryEvent.ClearSearchOrder -> {
                _uiState.value = _uiState.value.copy(searchText = "")
            }

            is OrderHistoryEvent.ClearSearchOrderByDate -> {
                _uiState.value = _uiState.value.copy(
                    orderListByDate = _uiState.value.orderList
                )
            }

            is OrderHistoryEvent.GetOrderOrderHistoryEvent -> {
                clearSelected()
                getAllHistoryList()
            }

            is OrderHistoryEvent.NextPage -> {
                getHistoryListPaging(
                    currentPage = event.currentPage,
                    offset = event.offset
                )
            }

            is OrderHistoryEvent.SetLimitRow -> {
                ROW_LIMIT = event.limitRow?:ROW_LIMIT
                _pagingState.value = _pagingState.value.copy(limitRow = event.limitRow?:ROW_LIMIT)
                getHistoryListPaging(1,0)
            }

            is OrderHistoryEvent.GetOrderDetail -> {
                getProductOrderById(event.id)
                _uiState.value = _uiState.value.copy(
                    orderSelected = getOrderDetail(event.id),
                    selectedId = event.id
                )
            }
            is OrderHistoryEvent.ClearSelectItem -> {
                clearSelected()
            }
            is OrderHistoryEvent.SearchOrderByDate -> {
                getHistoryByDate(event.start, event.end)
            }

            else -> {}
        }
    }

    private fun clearSelected() {
        _uiState.value = _uiState.value.copy(
            productList = emptyList(),
            selectedId = 0L
        )
    }

    private fun getOrderDetail(id: Long): OrderEntity? {
        for (item in _uiState.value.orderList?: arrayListOf()){
            if (id == item.id){
               return item
            }
        }
        return null
    }

    private fun filterOrderHistoryList(orderEntityList: List<OrderEntity>, startDate: String, endDate: String): List<OrderEntity> {
        try {
            val dateFormat = LocalDate.Format {
                dayOfMonth()
                char(' ')
                monthName(MonthNames.ENGLISH_ABBREVIATED)
                char(' ')
                year()
            }

            val start = LocalDate.parse(startDate, dateFormat)
            val end = LocalDate.parse(endDate, dateFormat)

            return orderEntityList.filter { orderEntity ->
                val transactionDate: LocalDate = LocalDate.parse(orderEntity.date.toString(), dateFormat)
                transactionDate in start..end
            }
        } catch (e:Exception){
            e.message
            return emptyList()
        }
    }


}