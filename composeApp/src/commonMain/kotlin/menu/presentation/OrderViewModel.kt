package menu.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.data.Resource
import core.utils.percentOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.format
import kotlinx.datetime.toLocalDateTime
import menu.domain.model.BillModel
import menu.domain.model.MenuModel
import menu.domain.repository.MenuRepository
import orderhistory.domain.repository.OrderHistoryRepository
import org.topteam.pos.OrderEntity
import org.topteam.pos.ProductOrderEntity
import setting.domain.model.ItemModel
import ui.stock.domain.repository.InventoryRepository

class OrderViewModel(
    private val repository: MenuRepository,
    private val repositoryInventory: InventoryRepository,
    private val orderHistoryRepository: OrderHistoryRepository,
): ScreenModel {

    private var totalAmount: Double = 0.0
    private var subTotal: Double = 0.0
    private var discountAmount: Double = 0.0
    private var vatAmount: Double = 0.0
    private var queue: Int = 0
    private var totalItem: Int = 0
    private var vat: Int = 0
    private var discount: Int = 0
    private var saveMenu: MenuModel? = null

    private val _state = MutableStateFlow(OrderState())
    val state: StateFlow<OrderState> get() = _state.asStateFlow()

    init {
        getMenu()
        //to init queue no
        getAllOrderHistory()

        //init order no and date
        generateBillNoAndDate()

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
                                    menuId = it.menuId,
                                    name = it.name,
                                    image = it.image
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

    private fun getProduct(id: Long) {
        screenModelScope.launch {
            repositoryInventory.getProduct(id).onEach {result ->
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

    private fun getAllProduct() {
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


    fun onEvent(event: OrderEvent){
        when(event) {
            is OrderEvent.GetMenusEvent -> {
                getMenu()
            }

            is OrderEvent.SearchEvent -> {
                _state.value = _state.value.copy(
                    searchText = event.searchText
                )
            }

            is OrderEvent.ClearEvent -> {
                _state.value = _state.value.copy(
                    searchText = ""
                )
            }

            is OrderEvent.GetItemsEvent -> {
                if (event.id == 0L){
                    getAllProduct()
                } else {
                    getProduct(event.id)
                }
            }

            //when click add to billing
            is OrderEvent.SelectOrderEvent -> {
                var isExist = false
                var qtyTotal = 0
                var ordered = _state.value.orders?.toMutableList()
                if(ordered == null) ordered = arrayListOf()

                //exist item in bill not add to list
                ordered.map {
                    if (it.product_id == event.item.product_id){
                        isExist = true
                        return
                    } else
                        isExist = false
                }
                if (!isExist){
                    ordered.add(event.item)
                }

                //get total qty
                ordered.map {
                    qtyTotal += it.qtySelected?:0
                }

                _state.value = _state.value.copy(
                    orders = ordered,
                )
                updateBillState(
                    totalQty = qtyTotal,
                    totalItem = ordered.size
                )
            }

            //quantity change
            is OrderEvent.QuantityChangeEvent -> {
                var qtyTotal = 0
                val ordered = _state.value.orders?.toMutableList()
                val item = ItemModel(
                    itemId = event.item.itemId,
                    menuId = event.item.menuId,
                    product_id = event.item.product_id,
                    image_product = event.item.image_product,
                    imageUrl = event.item.imageUrl,
                    itemCode = event.item.itemCode,
                    name = event.item.name,
                    description = event.item.description,
                    discount = event.item.discount,
                    price = event.item.price,
                    qty = event.item.qty,
                    qtySelected = event.qtyChanged
                )

                //update qty a item by index
                ordered?.set(
                    ordered.indexOf(event.item),
                    item
                )

                //update state total qty
                ordered?.map {
                    qtyTotal += it.qtySelected?:0
                }
                _state.value = _state.value.copy(
                    orders = ordered,
                )
                updateBillState(
                    totalQty = qtyTotal,
                    totalItem = ordered?.size
                )
            }

            is OrderEvent.OnRemoveOrder -> {
                var qtyTotal = 0

                val ordered = _state.value.orders?.toMutableList()
                if(!ordered.isNullOrEmpty()){
                    ordered.remove(event.item)
                }

                //update state total qty
                ordered?.map {
                    qtyTotal += it.qtySelected?:0
                }
                _state.value = _state.value.copy(
                    orders = ordered,
                )
                updateBillState(
                    totalQty = qtyTotal,
                    totalItem = ordered?.size
                )

            }

            is OrderEvent.PlaceOrderEvent -> {
                //when click place order reset data
                generateBillNoAndDate()

                addOrderHistory()
                _state.value = _state.value.copy(
                    orders = arrayListOf()
                )
                updateBillState(
                    totalQty = 0
                )
            }

            else -> {

            }
        }
    }

    private fun generateBillNoAndDate() {
        val currentMoment = Clock.System.now()
        val datetimeInSystemZone: LocalDateTime =
            currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())

        //get order_no
        val orderNo =
            "${datetimeInSystemZone.year}" +
                    "${datetimeInSystemZone.monthNumber}" +
                    "${datetimeInSystemZone.dayOfMonth}" +
                    "${datetimeInSystemZone.hour}" +
                    "${datetimeInSystemZone.minute}" +
                    "${datetimeInSystemZone.second}"

        //get date "Thu, 25 Apr 2024 00:00 GMT" to 25 Apr 2024
        val date = DateTimeComponents.Formats.RFC_1123.format {
            setDate(datetimeInSystemZone.date)
            hour = 0
            minute = 0
            second = 0
            setOffset(UtcOffset(hours = 0))
        }.substring(5,16)

        updateBillState(
            date = date,
            billNo = orderNo
        )
    }

    private fun updateBillState(
        totalQty: Int? = null,
        totalItem: Int? = null,
        totalAmount: Double? = null,
        subTotal: Double? = null,
        discountAmount: Double? = null,
        discount: Int? = null,
        vat: Int? = null,
        vatAmount: Double? = null,
        queue: Int? = null,
        billNo: String? = null,
        date: String? = null,
    ) {

        _state.value = _state.value.copy(
            bill = BillModel(
                totalQty = totalQty?:_state.value.bill?.totalQty,
                totalItem = totalItem?:_state.value.bill?.totalItem,
                totalAmount = totalAmount?:_state.value.bill?.totalAmount,
                subTotal = subTotal?:_state.value.bill?.subTotal,
                discountAmount  = discountAmount?:_state.value.bill?.discountAmount,
                discount = discount?:_state.value.bill?.discount,
                vat = vat?:_state.value.bill?.vat,
                vatAmount= vatAmount?:_state.value.bill?.vatAmount,
                queue = queue?:_state.value.bill?.queue,
                billNo = billNo?:_state.value.bill?.billNo,
                date = date?:_state.value.bill?.date,
            )
        )

    }

    private fun addOrderHistory() {
        //get total qty


        //get sub total

        //get discount

        //get vat

        //get status

        screenModelScope.launch {
            orderHistoryRepository.addOrderHistory(
                OrderEntity(
                    id = 0,
                    order_no = _state.value.bill?.billNo?.toLong(),
                    queue_no = _state.value.queue_no,
                    date = _state.value.bill?.date,
                    total_item = _state.value.orders?.size?.toLong(),
                    total_qty = _state.value.bill?.totalQty?.toLong(),
                    sub_total = null,
                    discount = null,
                    vat = null,
                    total = null,
                    status = null
                )
            ).onEach {result ->
                when (result) {
                    is Resource.Success -> {
                        getAllOrderHistory()
                    }
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {

                    }
                }
            }.launchIn(screenModelScope)
        }
    }

    private fun addProductOrder(list: List<ProductOrderEntity>) {
        screenModelScope.launch {
            orderHistoryRepository.addProductOrder(list).onEach {result ->
                when (result) {
                    is Resource.Success -> {

                    }
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {

                    }
                }
            }.launchIn(screenModelScope)
        }
    }

    private fun getAllOrderHistory() {
        orderHistoryRepository.getOrderHistory().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    //get last index of order and add product order
                    val listProductOrder: ArrayList<ProductOrderEntity> = arrayListOf()
                    state.value.orders?.forEach {
                        listProductOrder.add(
                            ProductOrderEntity(
                                id = 0,
                                product_id = it.product_id,
                                order_id = result.data?.last()?.id,
                                name = it.name,
                                image = it.image_product,
                                imageUrl = it.imageUrl,
                                qty = it.qtySelected?.toLong(),
                                price = it.price?.toLong(),
                                discount = it.discount?.toLong()
                            )
                        )
                    }
                    if (listProductOrder.size>0){
                        addProductOrder(listProductOrder)
                    }

                    //init queue no for order bill
                    if (result.data?.isNotEmpty() == true){
                        _state.value = _state.value.copy(
                            queue_no = result.data.last().id.inc()
                        )
                    }
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        }.launchIn(screenModelScope)
    }


    private fun calculateOrder() {

        subTotal = 0.0
        totalAmount = 0.0
        discountAmount = 0.0
//        qtyTotal = 0
        totalItem = _state.value.orders?.size ?: 0

        for (itemModel in _state.value.orders ?: arrayListOf()) {

            // Sub Total
            val price = itemModel.price ?: 0.0
            val totalPrice = price * (itemModel.qty ?: 1)
            subTotal = totalPrice

            // Quantities
//            qtyTotal += (itemModel.qty ?: 1)
        }

        // VAT
        vatAmount = vat percentOf subTotal

        // Discount
        discountAmount = discount percentOf subTotal

        // Total Price
        totalAmount = subTotal - discountAmount + vatAmount

//        val bill = BillModel(
//            totalAmount = totalAmount,
//            subTotal = subTotal,
//            discountAmount = discountAmount,
//            discount = discount,
//            totalItem = totalItem,
//            totalQty = 0,
//            vat = vat,
//            vatAmount = vatAmount,
//            queue = queue,
//            billNo = billNo,
//        )

//        _state.value = _state.value.copy(
//            bill = bill,
//            status = null,
//            message = null
//        )
    }
}