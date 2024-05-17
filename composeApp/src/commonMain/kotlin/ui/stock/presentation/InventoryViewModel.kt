package ui.stock.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.data.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.launch
import ui.stock.domain.model.Product
import ui.stock.domain.model.ProductStock
import ui.stock.domain.model.Stock
import ui.stock.domain.repository.InventoryRepository

data class InventoryState(
    var status: Status? = null,
    var isLoading: Boolean? = null,
    var data: List<ProductStock>? = null
)

class InventoryViewModel(
    private val repository: InventoryRepository
): ScreenModel {

    private val _state = MutableStateFlow(SearchEngineState())
    val state: StateFlow<SearchEngineState> = _state.asStateFlow()

    private val _stateStock = MutableStateFlow(InventoryState())
    val stateStock: StateFlow<InventoryState> = _stateStock.asStateFlow()

    fun onAddProduct(product: Product) {
        screenModelScope.launch {
            repository.addProduct(product)

            val stock = Stock(
                productId = product.product_id,
                stockIn = +1,
                stockOut = 0,
                stockBox = 0,
                stockTotal = +1,
                dateIn = "",
                dateOut = ""
            )
            repository.addStock(stock)
        }
    }

    fun onGetStock() {
        screenModelScope.launch {
            repository.getStock().collect { stock ->
                _stateStock.value = _stateStock.value.copy(
//                    data = stock.data
                )
            }
        }
    }

}