package orderhistory.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.theme.ColorCancel
import core.theme.ColorPaid
import core.theme.ColorPreOrder
import core.theme.ColorProgress
import core.theme.DarkGreen
import core.theme.PrimaryColor
import core.theme.textStyleBlack14Medium
import orderhistory.domain.model.TransactionHistory
import orderhistory.presentation.OrderHistoryEvent
import orderhistory.presentation.OrderHistoryState
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun TransactionTable(
    modifier: Modifier = Modifier,
    state: OrderHistoryState? = null,
    historyEvent: (OrderHistoryEvent) -> Unit = {}
) {
    val context = Locale.current
    val columnHeaderList = listOf("Bill No", "Date", "Order By", "Discount", "Total", "Status")
    val rowList = arrayListOf<TransactionHistory>()
    state?.orderList?.forEach{
        rowList.add(TransactionHistory(it.order_no.toString(), it.date, "Dara (Cashier)", it.discount.toString(), "$${it.total}", it.status))
    }

//    val rowList = listOf(
//        TransactionHistory("12345678", "23 Jan 2024 10:30", "Dara (Cashier)", null, "$23.00", "Cancel"),
//        TransactionHistory("12345674", "23 Jan 2024 10:30", "Dara (Cashier)", "4", "$23.00", "Progress"),
//        TransactionHistory("12345675", "23 Jan 2024 10:30", "Dara (Cashier)", "11", "$2.00", "Paid"),
//        TransactionHistory("12345677", "23 Jan 2024 10:30", "Dara (Cashier)", "1", "$3.00", "Booking"),
//    )

    val columnWeight = remember { MutableList(columnHeaderList.size) { 0f } } //column header weight

    for (index in columnHeaderList.indices) {
        val columnValues = rowList.mapNotNull { getColumnValue(it, index) } //get all value column list each index of header
        val columnSorted = columnValues.sortedByDescending { it.length } // short length of string to get the biggest. Then we will use it for calculateWeight to make table suitable

        //each columnHeaderWeight
        columnWeight[index] = calculateWeight(
            (
                    if ((columnSorted.firstOrNull()?.length ?: 0) > columnHeaderList[index].length) columnSorted.firstOrNull()?.length
                    else columnHeaderList[index].length
                    ) ?: columnHeaderList[index].length
        )
    }

    TableLayout(
        modifier = modifier,
        border = BorderStroke(0.dp, Color.Transparent),
        dataColumn = {
            Row(modifier = it.padding(start = 20.dp, top = 15.dp, bottom = 15.dp, end = 20.dp)) {
                columnHeaderList.forEachIndexed { index, column ->
                    Text(
                        modifier = Modifier
                            .alpha(0.5f)
                            .weight(columnWeight[index]),
                        text = column,
                        style = textStyleBlack14Medium()
                    )
                    if (index != columnHeaderList.size - 1) Spacer(modifier = Modifier.width(10.dp))
                }
            }
        },
        dataRow = {
            Column (
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ){
                rowList.forEachIndexed { rowIndex, transaction ->
                    LineWrapper()
                    Box(
                        modifier = Modifier
                            .clickable {
                                //get order detail
                                historyEvent(OrderHistoryEvent.GetOrderDetail(transaction.orderId?:0))
                            }
                    ) {
                        Row(
                            modifier = it.padding(start = 20.dp, top = 15.dp, bottom = 15.dp, end = 20.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            columnHeaderList.forEachIndexed { columnIndex, column ->
                                Box(
                                    modifier = Modifier.weight(columnWeight[columnIndex])
                                ) {
                                    getColumnValue(transaction, columnIndex)?.let {
                                        if (columnIndex == columnHeaderList.size-1){
                                            StatusContent(status = it)
                                        } else {
                                            Text(text = it, style = textStyleBlack14Medium())
                                        }
                                    } ?: Text(text = "-", style = textStyleBlack14Medium())
                                }
                                if (columnIndex != columnHeaderList.size - 1) Spacer(modifier = Modifier.width(10.dp))
                            }
                        }
                    }
                }
            }
       }
    )
}

private fun getColumnValue(transaction: TransactionHistory, index: Int): String? {
    return when (index) {
        0 -> transaction.billNo
        1 -> transaction.date
        2 -> transaction.orderBy
        3 -> transaction.discount?: "-"
        4 -> transaction.total
        5 -> transaction.status
        else -> null
    }
}

@Composable
private fun StatusContent(status: String) {

    val textColor = when (status.lowercase()) {
        "progress" -> PrimaryColor
        "paid" -> DarkGreen
        "cancel" -> Color.Red
        "pre-order" -> Color.Black
        else -> Color.Black
    }

    val bgColor = when (status.lowercase()) {
        "progress" -> ColorProgress
        "paid" -> ColorPaid
        "cancel" -> ColorCancel
        "pre-order" -> ColorPreOrder
        else -> ColorPreOrder
    }

    Box(
        modifier = Modifier
            .wrapContentWidth()
            .background(color = bgColor, shape = CircleShape.copy(CornerSize(percent = 10)))
            .padding(horizontal = 5.dp),
    ) {
        Text(
            text = status,
            color = textColor,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
        )
    }
}

fun calculateWeight(value: Int): Float {
    return value.toFloat() / 10
}
