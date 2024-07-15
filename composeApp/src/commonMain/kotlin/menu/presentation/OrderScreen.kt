package menu.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import core.app.convertToObject
import core.scanner.QrScannerScreen
import core.theme.Black
import core.theme.PrimaryColor
import core.theme.Shapes
import core.theme.White
import core.utils.Constants
import core.utils.PrimaryButton
import core.utils.RedRippleTheme
import core.utils.SharePrefer
import core.utils.calculatePoint
import core.utils.formatDouble
import customer.presentation.CustomerEvent
import customer.presentation.CustomerState
import getPlatform
import menu.domain.model.BillModel
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
import receipt.BillRowItem
import receipt.BillTotalItem
import setting.domain.model.ItemModel
import ui.settings.domain.model.ExchangeRateData
import ui.settings.domain.model.InvoiceData
import ui.settings.domain.model.InvoiceFooterData
import ui.settings.domain.model.InvoiceSealData
import ui.settings.domain.model.PaymentData
import ui.settings.domain.model.QueueData
import ui.settings.domain.model.SavePointData
import ui.settings.domain.model.ShopData
import ui.settings.domain.model.WifiData

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalResourceApi::class
)
@Composable
fun OrderScreen(
    orderState: OrderState? = null,
    itemSearchList: List<ItemModel>? = null,
    customerState: CustomerState? = null,
    customerEvent: (CustomerEvent) -> Unit = {},
    orderEvent: (OrderEvent) -> Unit = {},
) {
    val platform = getPlatform()
    var shopData = ShopData()
    var invoiceData = InvoiceData()
    var exchangeData = ExchangeRateData()
    var pointData = SavePointData()
    var paymentData = PaymentData()
    var sealData = InvoiceSealData()
    var queueData = QueueData()
    var wifiData = WifiData()
    var footerData = InvoiceFooterData()

    val shopHeader = SharePrefer.getPrefer("${Constants.PreferenceType.SHOP_HEADER}")
    val invoiceNo = SharePrefer.getPrefer("${Constants.PreferenceType.INVOICE_NO}")
    val exchangeRate = SharePrefer.getPrefer("${Constants.PreferenceType.EXCHANGE_RATE}")
    val savePoint = SharePrefer.getPrefer("${Constants.PreferenceType.SAVE_POINT}")
    val paymentMethod = SharePrefer.getPrefer("${Constants.PreferenceType.PAYMENT_METHOD}")
    val invoiceSeal = SharePrefer.getPrefer("${Constants.PreferenceType.INVOICE_SEAL}")
    val queueNumber = SharePrefer.getPrefer("${Constants.PreferenceType.QUEUE}")
    val wifiPassword = SharePrefer.getPrefer("${Constants.PreferenceType.WIFI}")
    val invoiceFooter = SharePrefer.getPrefer("${Constants.PreferenceType.FOOTER}")

    if (shopHeader.isNotEmpty()) {
        shopData = convertToObject<ShopData>(shopHeader)
    }
    if (invoiceNo.isNotEmpty()) {
        invoiceData = convertToObject<InvoiceData>(invoiceNo)
    }
    if (exchangeRate.isNotEmpty()) {
        exchangeData = convertToObject<ExchangeRateData>(exchangeRate)
    }
    if (savePoint.isNotEmpty()) {
        pointData = convertToObject<SavePointData>(savePoint)
    }
    if (paymentMethod.isNotEmpty()) {
        paymentData = convertToObject<PaymentData>(paymentMethod)
    }
    if (invoiceSeal.isNotEmpty()) {
        sealData = convertToObject<InvoiceSealData>(invoiceSeal)
    }
    if (queueNumber.isNotEmpty()) {
        queueData = convertToObject<QueueData>(queueNumber)
    }
    if (wifiPassword.isNotEmpty()) {
        wifiData = convertToObject<WifiData>(wifiPassword)
    }
    if (invoiceFooter.isNotEmpty()) {
        footerData = convertToObject<InvoiceFooterData>(invoiceFooter)
    }

    var selectedItem by remember { mutableIntStateOf(-1) }
    var listItem by remember { mutableStateOf<List<ItemModel>>(emptyList()) }
    var footerItem by remember { mutableStateOf(BillModel()) }
    var selectedMenuIndex by remember { mutableIntStateOf(0) }
    var isInputEmpty by remember { mutableStateOf(true) }
    val focusManager = LocalFocusManager.current
    var startBarCodeScan by remember { mutableStateOf(false) }

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
        listItem = orderState?.items?: emptyList()
    }

    //If have search item set to listItem
    LaunchedEffect(itemSearchList,orderState?.searchText){
        listItem = itemSearchList ?: orderState?.items?: emptyList()
    }

    LaunchedEffect(Unit){
        orderEvent(OrderEvent.GetMenusEvent)
        orderEvent(OrderEvent.ClearEvent)
        //first request ot all item
        orderEvent(OrderEvent.GetItemsEvent(0))
    }

    var isPreview by remember { mutableStateOf(false) }
    var isPrint by remember { mutableStateOf(false) }

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
                                                startBarCodeScan = true
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
                            text = "${listItem.size} Item in '$size' Menu",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        if(listItem.isEmpty()){
                            EmptyBox(modifier = Modifier.padding(bottom = 150.dp))
                        }
                        else{

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(3),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxSize()
                            ){
                                itemsIndexed(listItem){ index, item ->
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

                    // print receipt
                    Box(
                        modifier = Modifier.padding(10.dp).clip(Shapes.medium)
                    ) {
                        if (isPrint) {
                            if (shopData.isUsed) {
                                platform.Capture(0) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().background(White)
                                    ) {
                                        BillHeader()
                                    }
                                }
                            }

                            if (invoiceData.isUsed) {
                                platform.Capture(1) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().background(White)
                                    ) {
                                        BillCustomerForm1(
                                            invoiceData = invoiceData,
                                            exchangeData = exchangeData
                                        )
                                    }
                                }
                            }

//                            platform.Capture(2) {
//                                Box(
//                                    modifier = Modifier.fillMaxWidth().background(White)
//                                ) {
//                                    BillCustomerForm2()
//                                }
//                            }

                            platform.Capture(3) {
                                Box(
                                    modifier = Modifier.fillMaxWidth().background(White)
                                ) {
                                    val columnList = listOf("Description", "Qty", "Price", "Dis.", "Amount")
                                    val rowList = listItem
                                    BillHeaderItem(
                                        columnList = columnList,
                                        rowList = rowList
                                    )
                                }
                            }

                            platform.Capture(4) {
                                Box(
                                    modifier = Modifier.fillMaxWidth().background(White)
                                ) {
                                    val columnList = listOf("Description", "Qty", "Price", "Dis.", "Amount")
                                    val rowList = listItem
                                    BillRowItem(
                                        columnList = columnList,
                                        rowList = rowList
                                    )
                                }
                            }

                            if (pointData.isUsed) {
                                platform.Capture(5) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().background(White)
                                    ) {
                                        val totalItem = footerItem.totalItem ?: 0
                                        val qty = footerItem.totalQty ?: 0
                                        val subTotal = footerItem.subTotal ?: 0.0
                                        val discount = footerItem.discount ?: 0
                                        val vat = footerItem.vat ?: 0
                                        val totalAmount = footerItem.totalAmount ?: 0.0

                                        val columnList = listOf(
                                            "ទំនិញ / ចំនួន Item/Qty :",
                                            "សរុបរង / Sub Total :",
                                            "បញ្ចុះតម្លៃ / Discount :",
                                            "អាករ / VAT :",
                                            "សរុប / Total :"
                                        )
                                        val rowList = listOf(
                                            "$totalItem items / Qty $qty",
                                            "$ ${formatDouble(subTotal)}",
                                            discount.toString(),
                                            vat.toString(),
                                            "$ ${formatDouble(totalAmount)}"
                                        )

                                        val newPoint = calculatePoint(totalAmount = totalAmount, exchangeAmount = pointData.amtUsdExchange, points = pointData.point)
                                        val oldPoint = if (totalAmount >= 10.0) newPoint else 0
                                        val pointColumnList = listOf("Old Point :", "New Point :", "Total Current Point :")
                                        val pointRowList = listOf(oldPoint.toString(), newPoint, "999")
                                        BillTotalItem(
                                            savePointData = pointData,
                                            columnList = columnList,
                                            rowList = rowList,
                                            pointColumnList = pointColumnList,
                                            pointRowList = pointRowList
                                        )
                                    }
                                }
                            }

                            if (paymentData.isUsed) {
                                platform.Capture(6) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().background(White)
                                    ) {
                                        BillPayment(
                                            payment = paymentData
                                        )
                                    }
                                }
                            }

                            if (sealData.isUsed) {
                                platform.Capture(7) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().background(White)
                                    ) {
                                        BillCompanySeal(
                                            invoiceSeal = sealData
                                        )
                                    }
                                }
                            }

                            if (wifiData.isUsed || queueData.isUsed) {
                                platform.Capture(8) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().background(White)
                                    ) {
                                        BillQueue(
                                            wifiData = wifiData,
                                            queueData = queueData
                                        )
                                    }
                                }
                            }

                            if (footerData.isUsed) {
                                platform.Capture(9) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().background(White)
                                    ) {
                                        BillFooter(
                                            footerData = footerData
                                        )
                                    }
                                }
                            }
                        }
                    }

                    OrderBillsForm(
                        orderState = orderState,
                        orderEvent = orderEvent,
                        customerState = customerState,
                        customerEvent = customerEvent,
                        onPrint = { items, subItem ->
                            footerItem = subItem
                            listItem = items
                            isPreview = true
                        }
                    )

                    // preview receipt
                    if (isPreview) {
                        Dialog(
                            onDismissRequest = {
                                isPreview = false
                            },
                            properties = DialogProperties(
                                usePlatformDefaultWidth = false
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(20.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Column {

                                    Row(
                                        modifier = Modifier.width(380.dp),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(35.dp)
                                                .background(color = White, shape = CircleShape)
                                                .clip(CircleShape)
                                                .clickable {
                                                    isPreview = false
                                                },
                                            contentAlignment = Alignment.Center,
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Clear,
                                                contentDescription = "Clear",
                                                tint = Black
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(7.dp))

                                    Column(
                                        modifier = Modifier
                                            .weight(1f)
                                            .width(380.dp)
                                            .verticalScroll(rememberScrollState())
                                            .background(White)
                                    ) {
                                        Box(
                                            modifier = Modifier.fillMaxWidth().background(White)
                                        ) {
                                            if (shopData.isUsed) {
                                                BillHeader(isPreview = isPreview, shop = shopData)
                                            }
                                        }
                                        Box(
                                            modifier = Modifier.fillMaxWidth().background(White)
                                        ) {
                                            BillCustomerForm1(
                                                isPreview = isPreview,
                                                invoiceData = invoiceData,
                                                exchangeData = exchangeData
                                            )
                                        }
//                                        Box(
//                                            modifier = Modifier.fillMaxWidth().background(White)
//                                        ) {
//                                            BillCustomerForm2(isPreview)
//                                        }
                                        Box(
                                            modifier = Modifier.fillMaxWidth().background(White)
                                        ) {
                                            val columnList = listOf("Description", "Qty", "Price", "Dis.", "Amount")
                                            val rowList = listItem
                                            BillHeaderItem(
                                                isPreview = isPreview,
                                                columnList = columnList,
                                                rowList = rowList
                                            )
                                        }

                                        Box(
                                            modifier = Modifier.fillMaxWidth().background(White)
                                        ) {
                                            val columnList = listOf("Description", "Qty", "Price", "Dis.", "Amount")
                                            val rowList = listItem

                                            // display each item as a rows
                                            for (item in listItem) {
                                                Column {
                                                    BillRowItem(
                                                        isPreview = isPreview,
                                                        columnList = columnList,
                                                        rowList = rowList
                                                    )
                                                }
                                            }
                                        }

                                        Box(
                                            modifier = Modifier.fillMaxWidth().background(White)
                                        ) {
                                            val totalItem = footerItem.totalItem ?: 0
                                            val qty = footerItem.totalQty ?: 0
                                            val subTotal = footerItem.subTotal ?: 0.0
                                            val discount = footerItem.discount ?: 0
                                            val vat = footerItem.vat ?: 0
                                            val totalAmount = footerItem.totalAmount ?: 0.0

                                            val columnList = listOf(
                                                "ទំនិញ / ចំនួន Item/Qty :",
                                                "សរុបរង / Sub Total :",
                                                "បញ្ចុះតម្លៃ / Discount :",
                                                "អាករ / VAT :",
                                                "សរុប / Total :"
                                            )
                                            val rowList = listOf(
                                                "$totalItem items / Qty $qty",
                                                "$ ${formatDouble(subTotal)}",
                                                discount.toString(),
                                                vat.toString(),
                                                "$ ${formatDouble(totalAmount)}"
                                            )
                                            val newPoint = calculatePoint(totalAmount = totalAmount, exchangeAmount = pointData.amtUsdExchange, points = pointData.point)
                                            val oldPoint = if (totalAmount >= 10.0) newPoint else 0
                                            val pointColumnList = listOf("Old Point :", "New Point :", "Total Current Point :")
                                            val pointRowList = listOf(oldPoint.toString(), newPoint, "999")

                                            BillTotalItem(
                                                isPreview = isPreview,
                                                savePointData = pointData,
                                                columnList = columnList,
                                                rowList = rowList,
                                                pointColumnList = pointColumnList,
                                                pointRowList = pointRowList
                                            )
                                        }

                                        Box(
                                            modifier = Modifier.fillMaxWidth().background(White)
                                        ) {
                                            BillPayment(
                                                isPreview = isPreview,
                                                payment = paymentData
                                            )
                                        }

                                        Box(
                                            modifier = Modifier.fillMaxWidth().background(White)
                                        ) {
                                            BillCompanySeal(
                                                isPreview = isPreview,
                                                invoiceSeal = sealData
                                            )
                                        }

                                        Box(
                                            modifier = Modifier.fillMaxWidth().background(White)
                                        ) {
                                            BillQueue(
                                                isPreview = isPreview,
                                                wifiData = wifiData,
                                                queueData = queueData
                                            )
                                        }

                                        Box(
                                            modifier = Modifier.fillMaxWidth().background(White)
                                        ) {
                                            BillFooter(
                                                isPreview = isPreview,
                                                footerData = footerData
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Box(
                                        modifier = Modifier.width(380.dp),
                                    ) {
                                        PrimaryButton(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = "Print Receipt",
                                            onClick = {
                                                isPreview = false
                                                isPrint = true
                                            }
                                        )
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }

        AnimatedVisibility(
            visible = startBarCodeScan,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            QrScannerScreen(
                result = {
                    startBarCodeScan = false

                    if (it.isEmpty()) return@QrScannerScreen
                    orderEvent(OrderEvent.ScanItemEvent(it.toLong()))
                }
            )
        }
    }
}
