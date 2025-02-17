package mario.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.data.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import menu.domain.model.MenuModel
import menu.domain.repository.MenuRepository
import setting.domain.model.ItemModel
import ui.stock.domain.model.Product
import ui.stock.domain.repository.InventoryRepository

class MarioViewModel(
    private val repositoryMenu: MenuRepository,
    private val repositoryInventory: InventoryRepository
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
            repositoryMenu.getAllMenu().onEach {result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            menus = result.data?.map {
                                MenuModel(
                                    menuId = it.menuId,
                                    name = it.name,
                                    image = it.image
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

    private fun addMenu(menu: MenuModel) {
        screenModelScope.launch {
            repositoryMenu.addMenu(menu).onEach {result ->
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

    private fun updateMenu(menu: MenuModel) {
        screenModelScope.launch {
            repositoryMenu.updateMenu(menu).onEach {result ->
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
            repositoryMenu.deleteMenu(id).onEach {result ->
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

    fun onAddProduct(product: Product) {
        screenModelScope.launch {
            repositoryInventory.addProduct(product)
//
//            val stock = Stock(
//                product_id = product.product_id,
//                stock_in = +1,
//                stock_out = 0,
//                box = 0,
//                total = +1,
//                date_in = "",
//                date_out = ""
//            )
//            repositoryInventory.addStock(stock)
        }
    }

    fun getProduct(id: Long) {
        screenModelScope.launch {
            repositoryInventory.getProductByMenuId(id).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            items = result.data?.map {
                                ItemModel(
                                    itemCode = it.menuId,
                                    name = it.name,
                                    itemId = it.productId,
                                    image_product = it.image,
                                    imageUrl = it.imageUrl,
                                    qty = it.qty?.toInt(),
                                    price = it.price?.toDouble()
                                )
                            },
                            message = result.message,
                            loading = false
                        )
                        println(">>>>> get all item ${result.data?.size}")

                    }
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {
                    }
                }
            }.launchIn(screenModelScope)
        }
    }

    fun getAllProduct() {
        screenModelScope.launch {
            repositoryInventory.getAllProduct().onEach {result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            items = result.data?.map {
                                ItemModel(
                                    menuId  = it.menuId,
                                    name = it.name,
                                    product_id = it.productId,
                                    image_product = it.image,
                                    imageUrl = it.imageUrl,
                                    qty = it.qty?.toInt(),
                                    price = it.price?.toDouble()
                                )
                            },
                            message = result.message,
                        )
                        println(">>>>> get all item ${result.data?.size}")

                    }
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {
                    }
                }
            }.launchIn(screenModelScope)
        }
    }


    fun onEvent(event: MarioEvent){
        when(event) {
            is MarioEvent.AddItemEvent -> {
                onAddProduct(
                    Product(
                        id = event.item.itemId?:0,
                        menuId = event.item.menuId?:0,
                        productId = event.item.itemCode?:0,
                        name = event.item.name?:"",
                        image = event.item.image_product,
                        imageUrl = event.item.imageUrl,
                        uom = event.item.uom.toString(),
                        qty = event.item.qty.toString(),
                        price = event.item.price.toString(),
                        amount = event.item.amount.toString(),
                        discount = event.item.discount.toString()
                    )
                )
            }

            is MarioEvent.AddMenuEvent -> {
                addMenu(
                    MenuModel(
                        menuId = event.menu.menuId?:0,
                        name = event.menu.name?:"",
                        image = event.menu.image
                    )
                )
            }

            is MarioEvent.UpdateMenuEvent -> {
                updateMenu(
                    MenuModel(
                        menuId = event.menu.menuId?:1,
                        name = event.menu.name?:"",
                        image = event.menu.image
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
                if (event.id == 0L){
                    getAllProduct()
                } else {
                    getProduct(event.id)
                }
            }

            is MarioEvent.ClearEvent -> {

            }

            is MarioEvent.GetMenusEvent -> {

            }
        }
    }

}