package menu.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.data.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import mario.presentation.MarioEvent
import menu.domain.model.MenuModel
import menu.domain.repository.MenuRepository
import org.topteam.pos.Menu

class OrderViewModel(
    private val repository: MenuRepository
): ScreenModel {

    private var totalAmount: Double = 0.0
    private var subTotal: Double = 0.0
    private var discountAmount: Double = 0.0
    private var qtyTotal: Int = 0
    private var vatAmount: Double = 0.0
    private var queue: Int = 0
    private var billNo: Int = 0
    private var totalItem: Int = 0
    private var vat: Int = 0
    private var discount: Int = 0
    private var saveMenu: MenuModel? = null

    private val _state = MutableStateFlow(OrderState())
    val state: StateFlow<OrderState> get() = _state.asStateFlow()

    init {
        getMenu()
    }

    private fun getMenu() {
        screenModelScope.launch {
            repository.getAllMenu().onEach {result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            status = result.status,
                            menus = result.data?.map {
                                MenuModel(
                                    menuId = it.id,
                                    name = it.name,
                                    imageUrl = it.imageUrl
                                )
                            },
                            message = result.message
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            status = result.status,
                            message = result.message
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            status = result.status,
                            message = result.message
                        )
                    }
                }
            }.launchIn(screenModelScope)
        }
    }


    fun onEvent(event: OrderEvent){
        when(event) {
            is OrderEvent.GetMenusEvent -> {
                getMenu()
            }

            else -> {

            }
        }
    }

}