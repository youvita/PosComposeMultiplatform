package ui.stock.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import core.theme.Styles
import core.theme.White
import core.utils.ImageLoader
import core.utils.LineWrapper
import core.utils.calculateWeight
import core.utils.getTextStyle
import ui.stock.domain.model.ProductStock

@Composable
fun StockInformation(
    state: InventoryState,
    onItemClick: (ProductStock) -> Unit = {}
) {

    Box(
        modifier = Modifier.fillMaxWidth().background(White).padding(start = 36.dp, top = 30.dp, end = 36.dp)
    ) {
        val columnList = listOf("No", "", "Product Name", "Category", "SKU", "Stock In", "Stock Out", "Total", "Date In", "Date Out")
        val rowList = state.data

        val columnWeight = remember { MutableList(columnList.size) { 0f } } //column header weight

        for (index in columnList.indices) {

            val columnValues =  rowList?.let { productStock -> productStock.map { getColumnValue(it, index) } } //get all value column list each index of header
            val columnSorted = columnValues?.sortedByDescending { it.length } // short length of string to get the biggest. Then we will use it for calculateWeight to make table suitable

            //each columnHeaderWeight
            columnWeight[index] = calculateWeight(
                (
                        if ((columnSorted?.firstOrNull()?.length ?: 0) > columnList[index].length) columnSorted?.firstOrNull()?.length
                        else columnList[index].length
                        ) ?: columnList[index].length
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                columnList.forEachIndexed { columnIndex , item ->
                    Text(
                        modifier = Modifier.weight(0.4f.takeIf { columnIndex == 1 } ?: columnWeight[columnIndex]),
                        text = item,
                        textAlign = TextAlign.End.takeIf { columnIndex == columnList.size - 1 }
                    )
                    if (columnIndex != columnList.size - 1) Spacer(modifier = Modifier.width(10.dp))
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            LineWrapper()

            Spacer(modifier = Modifier.height(5.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                rowList?.forEachIndexed { _, item ->

                    Column(
                        modifier = Modifier.fillMaxWidth().clickable {
                            onItemClick(item)
                        }
                    ) {
                        Spacer(modifier = Modifier.height(5.dp))

                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            columnList.forEachIndexed { columnIndex, _ ->
                                if (columnIndex == 1) {
                                    item.productImage?.let {
                                        Box(
                                            modifier = Modifier.weight(0.4f),
                                            contentAlignment = Alignment.CenterEnd
                                        ) {
                                            Card(
                                                modifier = Modifier.size(42.dp),
                                                shape = RoundedCornerShape(10.dp)
                                            ) {
                                                Image(
                                                    bitmap = it,
                                                    contentDescription = null,
                                                    contentScale = ContentScale.FillBounds
                                                )
                                            }
                                        }
                                    }
                                    item.productImageUrl?.let {
                                        if (it.isNotEmpty()) {
                                            Box(
                                                modifier = Modifier.weight(0.4f),
                                                contentAlignment = Alignment.CenterEnd
                                            ) {
                                                Card(
                                                    modifier = Modifier.size(42.dp),
                                                    shape = RoundedCornerShape(10.dp)
                                                ) {
                                                    ImageLoader(image = it)
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    Text(
                                        modifier = Modifier.weight(columnWeight[columnIndex]),
                                        text = getColumnValue(item, columnIndex),
                                        style = getTextStyle(typography = Styles.BodyMedium),
                                        textAlign = TextAlign.End.takeIf { columnIndex == columnList.size - 1 },
                                        color = getColorValue(item, columnIndex)
                                    )
                                }

                                if (columnIndex != columnList.size - 1) Spacer(modifier = Modifier.width(10.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }
        }

    }
}

private fun getColumnValue(item: ProductStock, index: Int): String {
    return when (index) {
        0 -> item.stockId.toString()
        1 -> " "
        2 -> item.productName.toString()
        3 -> item.categoryName.toString()
        4 -> item.productId.toString()
        5 -> "+${item.stockIn.toString()}"
        6 -> "-".takeIf { item.stockOut?.toInt() == 0 } ?: "-${item.stockOut.toString()}"
        7 -> item.stockTotal.toString()
        8 -> "${item.dateIn} ${item.timeIn}"
        else -> "${item.dateOut} ${item.timeOut}"
    }
}

private fun getColorValue(item: ProductStock, index: Int): Color {
    return when (index) {
        5 -> Color(0xFF000000).takeIf { item.stockIn?.toInt() == 0 } ?: Color(0xFF04D000)
        6-> Color(0xFF000000).takeIf { item.stockOut?.toInt() == 0 } ?: Color(0xFFFF0000)
        else -> Color(0xFF000000)
    }
}