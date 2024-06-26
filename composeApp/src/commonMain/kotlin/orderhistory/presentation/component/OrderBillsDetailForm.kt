package orderhistory.presentation.component

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
import androidx.compose.ui.draw.alpha
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
import menu.presentation.component.OrderItem
import menu.presentation.component.utils.EmptyBox
import orderhistory.presentation.OrderHistoryState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_cash
import poscomposemultiplatform.composeapp.generated.resources.ic_qr_payment
import setting.domain.model.ItemModel


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OrderBillsDetailForm(
    orderHistoryState: OrderHistoryState? = null,
){
    var list by rememberSaveable { mutableStateOf<List<ItemModel>>(emptyList()) }

    val isOrderUpdated by rememberUpdatedState(orderHistoryState?.productList)
    LaunchedEffect(isOrderUpdated) {
        list = orderHistoryState?.productList?: arrayListOf()
    }

    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(10.dp)
        .clip(shape = Shapes.medium)
        .background(White)
    ){
        Column(modifier = Modifier.weight(1f, fill = true)){

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "Bill No ${orderHistoryState?.orderSelected?.order_no}",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Queue #${orderHistoryState?.orderSelected?.queue_no}",
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
                    text = "${orderHistoryState?.orderSelected?.total_item?: 0} items / Qty ${orderHistoryState?.orderSelected?.total_qty ?: 0}",
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
                    text = (orderHistoryState?.orderSelected?.sub_total?.toDouble() ?: 0.0).dollar(),
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
                    text = (orderHistoryState?.orderSelected?.discount?.toDouble() ?: 0.0).dollar(),
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
                    text = "VAT (${orderHistoryState?.orderSelected?.vat ?: 0}%)",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                Text(
                    text = (orderHistoryState?.orderSelected?.vat?.toDouble() ?: 0.0).dollar(),
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
                    text = (orderHistoryState?.orderSelected?.total?.toDouble() ?: 0.0).dollar(),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryColor
                    )
                )
            }

        }
    }
}