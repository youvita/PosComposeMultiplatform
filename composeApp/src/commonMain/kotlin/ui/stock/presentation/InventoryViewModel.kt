package ui.stock.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.data.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ui.stock.domain.model.Product
import ui.stock.domain.model.ProductStock
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

    private val _stateProductStock = MutableStateFlow(InventoryState())
    val stateProductStock: StateFlow<InventoryState> = _stateProductStock.asStateFlow()

    fun onAddProduct(product: Product) {
        screenModelScope.launch {
            repository.addProduct(product)
        }
    }

    fun onGetProductStock() {
        screenModelScope.launch {
            repository.getStock().collect { stock ->
                _stateProductStock.value = _stateProductStock.value.copy(
                    data = stock.data
                )
            }
        }
    }

}