package menu.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.app.convertToObject
import menu.presentation.component.utils.DialogCustomer
import core.theme.ColorD9D9D9
import core.theme.ColorDDE3F9
import core.theme.PrimaryColor
import core.theme.Shapes
import core.theme.White
import core.utils.Constants
import core.utils.DottedShape
import core.utils.SharePrefer
import core.utils.dollar
import customer.domain.model.CustomerModel
import customer.presentation.CustomerEvent
import customer.presentation.CustomerState
import menu.domain.model.BillModel
import menu.presentation.OrderEvent
import menu.presentation.OrderState
import menu.presentation.component.utils.EmptyBox
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_cash
import poscomposemultiplatform.composeapp.generated.resources.ic_qr_payment
import setting.domain.model.ItemModel


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OrderBillsForm(
    orderState: OrderState? = null,
    customerState: CustomerState? = null,
    customerEvent: (CustomerEvent) -> Unit = {},
    orderEvent: (OrderEvent) -> Unit = {},
    onPrint: (List<ItemModel>, BillModel) -> Unit = {_, _ ->}
){
    var list by rememberSaveable { mutableStateOf<List<ItemModel>>(emptyList()) }
    var selectedCustomer by remember { mutableStateOf(CustomerModel()) }
    var showCustomerDialog by rememberSaveable { mutableStateOf(false) }
    var selectedPaymentMethod by rememberSaveable { mutableIntStateOf(0) }

    val isOrderUpdated by rememberUpdatedState(orderState?.orders)
    LaunchedEffect(isOrderUpdated) {
        list = orderState?.orders?: arrayListOf()

        println(">>>>>>list:::  $list")
    }

    if(showCustomerDialog){
        DialogCustomer(
            customerState = customerState,
            customerEvent = customerEvent,
            onClose = {
                showCustomerDialog = false
            },
            onSelectCustomer = {
                selectedCustomer = it
                showCustomerDialog = false
            }
        )
    }

    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(10.dp)
        .clip(shape = Shapes.medium)
        .background(White)
    ){
        Column(modifier = Modifier.weight(1f, fill = true)){
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Column(Modifier.padding(horizontal = 16.dp)) {
//
//                FlowRow(
//                    verticalArrangement = Arrangement.spacedBy(10.dp),
//                    horizontalArrangement = Arrangement.spacedBy(10.dp),
//                ){
//                    Text(
//                        modifier = Modifier
//                            .align(Alignment.CenterVertically),
//                        text = "Customer Information",
//                        style = TextStyle(
//                            fontSize = 20.sp,
//                            fontWeight = FontWeight.Bold
//                        )
//                    )
//
//                    if(selectedCustomer.customer_id == null){
//                        OutlinedButton(
//                            enabled = true,
//                            onClick = {
//                                showCustomerDialog = true
//                                customerEvent(CustomerEvent.GetAllCustomersEvent)
//                            },
//                            colors = ButtonDefaults.elevatedButtonColors(
//                                containerColor = Color(0xB7C1F2B0),
//                                contentColor = Color(0xFF318B0A)
//                            ),
//                            border = null,
//                            contentPadding = PaddingValues(8.dp),
//                            shape = Shapes.medium,
//                            modifier = Modifier
//                                .align(Alignment.CenterVertically)
//                                .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
//                        ) {
//                            Icon(
//                                imageVector = Icons.Rounded.Add,
//                                contentDescription = "add customer",
//                                modifier = Modifier.size(16.dp)
//                            )
//                            Text(
//                                text = "Add customer",
//                                style = TextStyle(
//                                    fontSize = 14.sp,
//                                    fontWeight = FontWeight.Normal
//                                )
//                            )
//                        }
//                    }
//                }
//
//                if(selectedCustomer.customer_id != null){
//                    val cus = selectedCustomer.name
//                    var phone = selectedCustomer.phone_number
//                    phone = if(phone.isNullOrEmpty()) "" else "#$phone"
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clip(shape = Shapes.medium)
//                            .background(ColorDDE3F9)
//                            .padding(8.dp)
//                    ) {
//                        Text(
//                            modifier = Modifier
//                                .weight(1f)
//                                .align(Alignment.CenterVertically),
//                            text = cus + phone,
//                            style = TextStyle(
//                                fontSize = 15.sp,
//                                fontWeight = FontWeight.Normal
//                            )
//                        )
//
//                        OutlinedButton(
//                            enabled = true,
//                            onClick = {
//                                selectedCustomer = CustomerModel()
//                            },
//                            colors = ButtonDefaults.elevatedButtonColors(
//                                containerColor = RedBg,
//                                contentColor = Red
//                            ),
//                            border = null,
//                            contentPadding = PaddingValues(9.dp),
//                            shape = Shapes.medium,
//                            modifier = Modifier
//                                .align(Alignment.CenterVertically)
//                                .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
//                        ) {
//                            Text(
//                                text = "Remove",
//                                style = TextStyle(
//                                    fontSize = 12.sp,
//                                    fontWeight = FontWeight.Normal
//                                ),
//                                maxLines = 2,
//                                overflow = TextOverflow.Ellipsis
//                            )
//                        }
//                    }
//                }
//            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "Bill No ${orderState?.bill?.billNo}",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Queue #${orderState?.queue_no}",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            HorizontalDivider(thickness = 1.dp, color = ColorD9D9D9)


            if(list.isEmpty()){
                EmptyBox()
            }else{
                LazyColumn{
                    items(list){item ->
                        OrderItem(
                            item = item,
                            onQtyChanged = {
                                println(">>>> $it")
//                                orderEvent(OrderEvent.QuantityChangeEvent(item))
                                  orderEvent(OrderEvent.QuantityChangeEvent(item,it))
                            },
                            onRemove = {
                                orderEvent(OrderEvent.OnRemoveOrder(it))
                            }
                        )
                        HorizontalDivider(thickness = 1.dp, color = ColorD9D9D9)
                    }
                }
            }
        }

        // Bottom
        Column(
            modifier = Modifier
                .background(color = White, shape = Shapes.extraSmall)
                .padding(15.dp)
        ) {
            // Item / Qty
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "Item / Qty",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                Text(
                    text = "${orderState?.bill?.totalItem?: 0} items / Qty ${orderState?.bill?.totalQty?: 0}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }

            // Sub Total
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "Sub Total",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                Text(
                    text = orderState?.bill?.subTotal?.dollar()?: (0.0).dollar(),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }

            // Discount
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "Discount",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                Text(
                    text = orderState?.bill?.discountAmount?.dollar()?: (0.0).dollar(),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }

            // VAT
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "VAT (${orderState?.bill?.vat?: 0}%)",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                Text(
                    text = orderState?.bill?.vatAmount?.dollar()?: (0.0).dollar(),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }


            Spacer(modifier = Modifier.height(10.dp))

            // Dotted line
            Box(
                Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Gray, shape = DottedShape(step = 10.dp))
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Total
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "Total",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )

                Text(
                    text = orderState?.bill?.totalAmount?.dollar()?: (0.0).dollar(),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryColor
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            //payment method
//            FlowRow(
//                verticalArrangement = Arrangement.spacedBy(10.dp),
//            ){
//                Text(
//                    modifier = Modifier.align(Alignment.CenterVertically),
//                    text = "Payment method",
//                    style = TextStyle(
//                        fontSize = 15.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                )
//                Spacer(modifier = Modifier.width(10.dp))
//
//                LazyRow{
//                    val mod = Modifier
//                        .height(40.dp)
//                        .widthIn(min = 60.dp)
//                        .clip(shape = Shapes.medium)
//                        .background(Color(0xFFFFFFFF))
//
//                    item{
//                        Box(
//                            modifier = mod.then(
//                                    Modifier
//                                        .border(
//                                            1.dp,
//                                            color = if(selectedPaymentMethod == 0) PrimaryColor else Color(0x37333030),
//                                            shape = Shapes.medium
//                                        )
//                                ).clickable {
//                                    selectedPaymentMethod = 0
//                                }
//                        ){
//                            PaymentMethod(
//                                modifier = Modifier.align(Alignment.Center),
//                                payCash = true
//                            )
//                        }
//
//                        Spacer(modifier = Modifier.width(10.dp))
//                    }
//
//                    item{
//                        Box(
//                            modifier = mod.then(
//                                    Modifier
//                                        .border(
//                                            1.dp,
//                                            color = if(selectedPaymentMethod == 1) PrimaryColor else Color(0x37333030),
//                                            shape = Shapes.medium
//                                        )
//                                ).clickable {
//                                    selectedPaymentMethod = 1
//                                }
//                        ){
//                            PaymentMethod(
//                                modifier = Modifier.align(Alignment.Center),
//                                payCash = false
//                            )
//                        }
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.height(10.dp))

            // Pre-Order
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                OutlinedButton(
                    enabled = list.isNotEmpty(),
                    onClick = {
//                        if(!orderState?.orders.isNullOrEmpty()){
//                            orderEvent(OrderEvent.PreOrderEvent(selectedCustomer))
//                            selectedCustomer = CustomerModel()
//                        }
                        if (list.isNotEmpty()) {
                            onPrint(list, orderState?.bill ?: BillModel())
                        }
                    },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = ColorDDE3F9,
                        contentColor = PrimaryColor
                    ),
                    border = null,
                    contentPadding = PaddingValues(10.dp),
                    shape = Shapes.medium,
                    modifier = Modifier
                        .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
                ) {
                    Text(
                        text = "Print",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }

                Spacer(modifier = Modifier.width(5.dp))

                OutlinedButton(
                    enabled = list.isNotEmpty(),
                    onClick = {
                        orderEvent(OrderEvent.PlaceOrderEvent(
                            BillModel()
                        ))
                        //insert ProductOrderEntity
                        //insert OrderEntity
                    },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = PrimaryColor,
                        contentColor = White
                    ),
                    border = null,
                    shape = Shapes.medium,
                    contentPadding = PaddingValues(10.dp),
                    modifier = Modifier
                        .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
                ) {
                    Text(
                        text = "Place order",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun PaymentMethod(
    modifier: Modifier = Modifier,
    payCash: Boolean = true
){
    Column(
        modifier = modifier,
    ){
        Image(
            modifier = Modifier.size(20.dp).align(Alignment.CenterHorizontally),
            painter = painterResource(resource = if(payCash) Res.drawable.ic_cash else Res.drawable.ic_qr_payment),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = TextStyle(
                fontSize = 11.sp,
                fontWeight = FontWeight.Normal
            ),
            text = if(payCash) "Cash" else "QR Scan"
        )
    }
}