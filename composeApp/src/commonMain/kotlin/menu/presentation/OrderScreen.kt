package menu.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.app.convertToObject
import core.theme.PrimaryColor
import core.theme.Shapes
import core.theme.White
import core.utils.Constants
import core.utils.RedRippleTheme
import core.utils.SharePrefer
import customer.presentation.CustomerEvent
import customer.presentation.CustomerState
import menu.presentation.component.utils.EmptyBox
import menu.domain.model.MenuModel
import menu.presentation.component.ItemView
import menu.presentation.component.MenuCategoryForm
import menu.presentation.component.OrderBillsForm
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_scanner
import receipt.BillCompanySeal
import receipt.BillCustomerForm1
import receipt.BillCustomerForm2
import receipt.BillFooter
import receipt.BillHeader
import receipt.BillHeaderItem
import receipt.BillPayment
import receipt.BillQueue
import receipt.BillTotalItem
import receipt.CaptureItem
import setting.domain.model.ItemModel
import ui.settings.domain.model.InvoiceFooterData
import ui.settings.domain.model.InvoiceSealData
import ui.settings.domain.model.PaymentData
import ui.settings.domain.model.QueueData
import ui.settings.domain.model.ShopData

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalResourceApi::class
)
@Composable
fun OrderScreen(
    orderState: OrderState? = null,
    customerState: CustomerState? = null,
    customerEvent: (CustomerEvent) -> Unit = {},
    orderEvent: (OrderEvent) -> Unit = {},
) {

    var selectedItem by remember { mutableIntStateOf(-1) }
    var list by remember { mutableStateOf<List<ItemModel>>(emptyList()) }
    var selectedMenuIndex by remember { mutableIntStateOf(0) }
    var isInputEmpty by remember { mutableStateOf(true) }
    val focusManager = LocalFocusManager.current

    //add first category menu
    val categoryMenuList = ArrayList<MenuModel>()
    categoryMenuList.add(MenuModel(menuId = 0, name = "All"))

    orderState?.menus?.map {
        categoryMenuList.add(it)
    }

    var menuList by remember {
        mutableStateOf<List<MenuModel>>(arrayListOf(MenuModel(menuId = 0, name = "All")))
    }

    LaunchedEffect(orderState?.menus){
        menuList = categoryMenuList
    }
    LaunchedEffect(orderState?.items){
        list = orderState?.items?: emptyList()
    }

    LaunchedEffect(Unit){
        orderEvent(OrderEvent.GetMenusEvent)
        orderEvent(OrderEvent.ClearEvent)
        //first request ot all item
        orderEvent(OrderEvent.GetItemsEvent(0))
    }

    var isPreview by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .clickable (
                    indication = null,
                    interactionSource = remember {
                        MutableInteractionSource()
                    }
                ){
                    focusManager.clearFocus()
                    selectedItem = -1
                }
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(Modifier.weight(2f)) {
                    Column(Modifier.padding(10.dp)) {
                        //search place
                        TextField(
                            value = orderState?.searchText?:"",
                            onValueChange = {
                                orderEvent(OrderEvent.SearchEvent(it))
                                isInputEmpty = it.isEmpty()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                                .focusRequester(remember { FocusRequester() }),
                            shape = RoundedCornerShape(10.dp),
                            placeholder = { Text("Search Name and ID", maxLines = 1) },
                            trailingIcon = {
                                if (!isInputEmpty){
                                    //clear search text
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear",
                                        tint = PrimaryColor,
                                        modifier = Modifier.clickable {
                                            // Handle clear action
                                            orderEvent(OrderEvent.ClearEvent)
                                            isInputEmpty = true
                                        }
                                    )
                                } else {
                                    //select order item by scan
                                    Image(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clickable {
                                                println(">>>>>> Scan start")
                                            },
                                        painter = painterResource(Res.drawable.ic_scanner),
                                        contentDescription = ""
                                    )
                                }

                            },
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                containerColor = Color.White
                            )
                        )

                        // category menu path
                        MenuCategoryForm(menuList){ index ->
                            selectedMenuIndex = index
                            orderEvent(OrderEvent.MenuSelectEvent(menuList[index]))
                            orderEvent(OrderEvent.GetItemsEvent(menuList[index].menuId?:0))
                            focusManager.clearFocus()
                            selectedItem = -1
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        //item menu path
                        val size = if(menuList.isEmpty()) "0" else menuList[selectedMenuIndex].name
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            text = "${list.size} Item in '$size' Menu",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        if(list.isEmpty()){
                            EmptyBox(modifier = Modifier.padding(bottom = 150.dp))
                        }
                        else{

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(3),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxSize()
                            ){
                                itemsIndexed(list){ index, item ->
                                    CompositionLocalProvider(LocalRippleTheme provides RedRippleTheme){
                                        Box(
                                            modifier = Modifier
                                                .animateItemPlacement()
                                                .clickable(
                                                    indication = null,
                                                    interactionSource = remember { MutableInteractionSource() }
                                                ) {
                                                    focusManager.clearFocus()
                                                    selectedItem = if (selectedItem == index) {
                                                        -1
                                                    } else index
                                                }
                                                .then(
                                                    if (selectedItem == index) {
                                                        Modifier
                                                            .background(
                                                                color = White,
                                                                shape = Shapes.medium
                                                            )
                                                            .border(
                                                                1.dp,
                                                                color = PrimaryColor,
                                                                shape = Shapes.medium
                                                            )
                                                    } else {
                                                        Modifier
                                                    }
                                                )
                                        ){
                                            ItemView(
                                                item = item,
                                                selected = selectedItem == index,
                                                orderEvent = orderEvent
                                            )
                                        }
                                    }
                                }
                            }

//                            LazyVerticalStaggeredGrid(
//                                columns = StaggeredGridCells.Fixed(3),
//                                verticalItemSpacing = 8.dp,
//                                horizontalArrangement = Arrangement.spacedBy(10.dp),
//                                modifier = Modifier.fillMaxSize()
//                            ){
//                                itemsIndexed(list){ index, item ->
//                                    CompositionLocalProvider(LocalRippleTheme provides RedRippleTheme){
//                                        Box(
//                                            modifier = Modifier
//                                                .animateItemPlacement()
//                                                .clickable(
//                                                    indication = null,
//                                                    interactionSource = remember { MutableInteractionSource() }
//                                                ) {
//                                                    focusManager.clearFocus()
//                                                    selectedItem = if (selectedItem == index) {
//                                                        -1
//                                                    } else index
//                                                }
//                                                .then(
//                                                    if (selectedItem == index) {
//                                                        Modifier
//                                                            .background(
//                                                                color = White,
//                                                                shape = Shapes.medium
//                                                            )
//                                                            .border(
//                                                                1.dp,
//                                                                color = PrimaryColor,
//                                                                shape = Shapes.medium
//                                                            )
//                                                    } else {
//                                                        Modifier
//                                                    }
//                                                )
//                                        ){
//                                            ItemView(
//                                                item = item,
//                                                selected = selectedItem == index,
//                                                orderEvent = orderEvent
//                                            )
//                                        }
//                                    }
//                                }
//                            }
                        }
                    }
                }

                Box(modifier = Modifier.weight(1f)){
                    OrderBillsForm(
                        orderState = orderState,
                        orderEvent = orderEvent,
                        customerState = customerState,
                        customerEvent = customerEvent,
                        onPrint = {
                            isPreview = true
                        }
                    )

                    if (isPreview) {
                        CaptureItem()

                        Column(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .fillMaxSize()
                                .background(White)
                                .padding(10.dp),
                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth().background(White)
                            ) {
                                val shopHeader = SharePrefer.getPrefer("${Constants.PreferenceType.SHOP_HEADER}")
                                val shop = convertToObject<ShopData>(shopHeader)
                                if (shop.isUsed) {
                                    BillHeader(isPreview = isPreview, shop = shop)
                                }
                            }
                            Box(
                                modifier = Modifier.fillMaxWidth().background(White)
                            ) {
                                BillCustomerForm1(isPreview)
                            }
                            Box(
                                modifier = Modifier.fillMaxWidth().background(White)
                            ) {
                                BillCustomerForm2(isPreview)
                            }
                            Box(
                                modifier = Modifier.fillMaxWidth().background(White)
                            ) {
                                val columnList = listOf("Description", "Qty", "Price", "Dis.", "Amount")
                                val rowList = listOf(
                                    ItemModel(
                                        name = "Caramel Frappuccino Caramel",
                                        qty = 1,
                                        price = 1.0,
                                        discount = 0
                                    )
                                )
                                BillHeaderItem(
                                    isPreview = isPreview,
                                    columnList = columnList,
                                    rowList = rowList
                                )
                            }
                            Box(
                                modifier = Modifier.fillMaxWidth().background(White)
                            ) {
                                val columnList = listOf(
                                    "ទំនិញ / ចំនួន Item/Qty :",
                                    "សរុបរង / Sub Total :",
                                    "បញ្ចុះតម្លៃ / Discount :",
                                    "អាករ / VAT :",
                                    "សរុប / Total :"
                                )
                                val rowList = listOf("99 items / Qty 999", "22222.22 $", "99%", "10%", "234,234.00 $")
                                val pointColumnList = listOf("Old Point :", "New Point :", "Total Current Point :")
                                val pointRowList = listOf("999", "999", "999")
                                BillTotalItem(
                                    isPreview = isPreview,
                                    columnList = columnList,
                                    rowList = rowList,
                                    pointColumnList = pointColumnList,
                                    pointRowList = pointRowList
                                )
                            }

                            Box(
                                modifier = Modifier.fillMaxWidth().background(White)
                            ) {
                                val paymentMethod = SharePrefer.getPrefer("${Constants.PreferenceType.PAYMENT_METHOD}")
                                val payment = convertToObject<PaymentData>(paymentMethod)
                                if (payment.isUsed) {
                                    BillPayment(
                                        isPreview
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier.fillMaxWidth().background(White)
                            ) {
                                val invoiceSeal = SharePrefer.getPrefer("${Constants.PreferenceType.INVOICE_SEAL}")
                                val seal = convertToObject<InvoiceSealData>(invoiceSeal)
                                if (seal.isUsed) {
                                    BillCompanySeal(
                                        isPreview
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier.fillMaxWidth().background(White)
                            ) {
                                BillQueue(
                                    isPreview
                                )
                            }

                            Box(
                                modifier = Modifier.fillMaxWidth().background(White)
                            ) {
                                BillFooter(
                                    isPreview
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}
