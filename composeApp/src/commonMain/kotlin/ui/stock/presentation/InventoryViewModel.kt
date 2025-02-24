package ui.stock.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.data.Resource
import core.data.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import menu.domain.model.MenuModel
import ui.stock.domain.model.Product
import ui.stock.domain.model.ProductMenu
import ui.stock.domain.model.ProductStock
import ui.stock.domain.repository.InventoryRepository

data class InventoryState(
    var status: Status? = null,
    var isLoading: Boolean? = null,
    var data: List<ProductStock>? = null,
    var menu: List<MenuModel>? = null
)

data class ProductState(
    var status: Status? = null,
    var isLoading: Boolean? = null,
    var data: List<ProductMenu>? = null
)

class InventoryViewModel(
    private val repository: InventoryRepository
): ScreenModel {

    private val _state = MutableStateFlow(SearchEngineState())
    val state: StateFlow<SearchEngineState> = _state.asStateFlow()

    private val _stateProductStock = MutableStateFlow(InventoryState())
    val stateProductStock: StateFlow<InventoryState> = _stateProductStock.asStateFlow()

    private val _stateProduct = MutableStateFlow(ProductState())
    val stateProduct: StateFlow<ProductState> = _stateProduct.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private var _product = MutableStateFlow(listOf<ProductMenu>())

    val product = searchText
        .onEach {
            _isSearching.update { true }
        }
        .combine(_product) { text, product ->
            if (text.isBlank()) {
                product
            } else {
                product.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _product.value
        )

    fun onEvent(event: InventoryEvent) {
        when(event) {
            is InventoryEvent.SearchProduct -> {
                onSearchTextChange(event.keyword)
            }
            is InventoryEvent.AddProduct -> {
                onAddProduct(event.product)
            }
            is InventoryEvent.UpdateStock -> {
                onUpdateStock(event.product)
            }
            is InventoryEvent.AdjustProduct -> {
                onAdjustProduct(event.product)
            }
            is InventoryEvent.GetMenu -> {
                onGetMenu()
            }
            is InventoryEvent.GetProductStock -> {
                onGetProductStock()
            }
            is InventoryEvent.GetProduct -> {
                onGetProduct(event.menuId)
            }
            is InventoryEvent.SearchProductByDate -> {
                onSearchProductByDate(event.startDate, event.endDate)
            }
        }
    }

    private fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    private fun onAddProduct(product: Product) {
        screenModelScope.launch {
            repository.addProduct(product)
        }
    }

    private fun onUpdateStock(product: Product) {
        screenModelScope.launch {
            repository.updateStock(product)
        }
    }

    private fun onAdjustProduct(product: Product) {
        screenModelScope.launch {
            repository.adjustProduct(product)
        }
    }

    private fun onGetProductStock() {
        screenModelScope.launch {
            repository.getStock().collect { stock ->
                _stateProductStock.value = _stateProductStock.value.copy(
                    data = stock.data
                )
            }
        }
    }

    private fun onGetMenu() {
        screenModelScope.launch {
            repository.getMenu().collect { stock ->
                _stateProductStock.value = _stateProductStock.value.copy(
                    menu = stock.data
                )
            }
        }
    }

    private fun onSearchProductByDate(startDate: String, endDate: String) {
        screenModelScope.launch {
            repository.getSearchProductByDate(startDate, endDate).collect { product ->
               when (product) {
                   is Resource.Success -> {
                       product.data?.let {
                           _product.value = it
                       }

                       _stateProduct.value = _stateProduct.value.copy(
                           data = product.data,
                           isLoading = false
                       )
                   }
                   is Resource.Error -> {
                       _stateProduct.value = _stateProduct.value.copy(
                           isLoading = false
                       )
                   }
                   is Resource.Loading -> {
                       _stateProduct.value = _stateProduct.value.copy(
                           isLoading = true
                       )
                   }
               }
            }
        }
    }

    private fun onGetProduct(menuId: Long) {
        screenModelScope.launch {
            if (menuId > 0) {
                repository.getProductByMenuId(menuId).collect { product ->
                    when (product) {
                        is Resource.Success -> {
                            product.data?.let {
                                _product.value = it
                            }

                            _stateProduct.value = _stateProduct.value.copy(
                                data = product.data,
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {
                            _stateProduct.value = _stateProduct.value.copy(
                                isLoading = false
                            )
                        }
                        is Resource.Loading -> {
                            _stateProduct.value = _stateProduct.value.copy(
                                isLoading = true
                            )
                        }
                    }
                }
            } else {
                repository.getAllProduct().collect { product ->
                    when (product) {
                        is Resource.Success -> {
                            product.data?.let {
                                _product.value = it
                            }
                            _stateProduct.value = _stateProduct.value.copy(
                                data = product.data,
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {
                            _stateProduct.value = _stateProduct.value.copy(
                                isLoading = false
                            )
                        }
                        is Resource.Loading -> {
                            _stateProduct.value = _stateProduct.value.copy(
                                isLoading = true
                            )
                        }
                    }
                }
            }

        }
    }


}