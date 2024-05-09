package ui.stock.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.data.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ui.stock.domain.model.Product
import ui.stock.domain.model.Stock
import ui.stock.domain.repository.InventoryRepository

data class InventoryState(
    var status: Status? = null,
    var isLoading: Boolean? = null,
    var data: Unit? = null
)

class InventoryViewModel(
    private val repository: InventoryRepository
): ScreenModel {

    private val _state = MutableStateFlow(SearchEngineState())
    val state: StateFlow<SearchEngineState> = _state.asStateFlow()

    fun onAddProduct(product: Product) {
        screenModelScope.launch {
            repository.addProduct(product)

            val stock = Stock(
                product_id = product.product_id,
                stock_in = +1,
                stock_out = 0,
                box = 0,
                total = +1,
                date_in = "",
                date_out = ""
            )
            repository.addStock(stock)
        }
    }

}