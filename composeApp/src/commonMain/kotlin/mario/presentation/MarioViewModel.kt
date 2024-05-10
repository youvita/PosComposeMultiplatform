package mario.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.data.Resource
import core.data.Status
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

class MarioViewModel(
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

    private val _state = MutableStateFlow(MarioState())
    val state: StateFlow<MarioState> get() = _state.asStateFlow()

    init {
        getMenu()
    }

    private fun getMenu() {
        screenModelScope.launch {
            repository.getAllMenu().onEach {result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            menus = result.data?.map {
                                MenuModel(
                                    menuId = it.id,
                                    name = it.name,
                                    imageUrl = it.imageUrl
                                )
                            },
                            message = result.message,
                            loading = false
                        )
                        println("Data menu : ${result.data?.size}")
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            message = result.message
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            message = result.message,
                            loading = true
                        )
                    }
                }
            }.launchIn(screenModelScope)
        }
    }

    private fun addMenu(menu: Menu) {
        screenModelScope.launch {
            repository.addMenu(menu).onEach {result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            loading = false
                        )
                        getMenu()
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            loading = false
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            loading = true
                        )
                    }
                }
            }.launchIn(screenModelScope)
        }
    }

    private fun updateMenu(menu: Menu) {
        screenModelScope.launch {
            repository.updateMenu(menu).onEach {result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            loading = false
                        )
                        getMenu()
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            loading = false
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            loading = true
                        )
                    }
                }
            }.launchIn(screenModelScope)
        }
    }

    private fun deleteMenu(id: Long) {
        screenModelScope.launch {
            repository.deleteMenu(id).onEach {result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            loading = false
                        )
                        getMenu()
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            loading = false
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            loading = true
                        )
                    }
                }
            }.launchIn(screenModelScope)
        }
    }


    fun onEvent(event: MarioEvent){
        when(event) {
            is MarioEvent.AddItemEvent -> {

            }

            is MarioEvent.AddMenuEvent -> {
                addMenu(
                    Menu(
                        event.menu.menuId?:0,
                        event.menu.name?:"",
                        event.menu.imageUrl
                    )
                )
            }

            is MarioEvent.UpdateMenuEvent -> {
                updateMenu(
                    Menu(
                        event.menu.menuId?:1,
                        event.menu.name?:"",
                        event.menu.imageUrl
                    )
                )
            }

            is MarioEvent.UpdateItemEvent -> {

            }

            is MarioEvent.DeleteItemEvent -> {

            }

            is MarioEvent.DeleteMenuEvent -> {
                deleteMenu(event.menuId)
            }

            is MarioEvent.BookmarkItemEvent -> {

            }

            is MarioEvent.GetItemsEvent -> {

            }

            is MarioEvent.ClearEvent -> {

            }

            is MarioEvent.GetMenusEvent -> {

            }
        }
    }

}