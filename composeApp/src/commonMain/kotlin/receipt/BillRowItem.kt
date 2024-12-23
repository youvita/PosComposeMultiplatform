package receipt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.theme.Styles
import core.utils.calculateWeight
import core.utils.getTextStyle
import setting.domain.model.ItemModel

@Composable
fun BillRowItem(
    isPreview: Boolean = false,
    columnList: List<String> = arrayListOf(),
    rowList: List<ItemModel> = arrayListOf()
) {

    val columnWeight = remember { MutableList(columnList.size) { 0f } } //column header weight

    for (index in columnList.indices) {
        val columnValues = rowList.mapNotNull { getColumnValue(it, index) } //get all value column list each index of header
        val columnSorted = columnValues.sortedByDescending { it.length } // short length of string to get the biggest. Then we will use it for calculateWeight to make table suitable

        //each columnHeaderWeight
        columnWeight[index] = calculateWeight(
            (
                    if ((columnSorted.firstOrNull()?.length ?: 0) > columnList[index].length) columnSorted.firstOrNull()?.length
                    else columnList[index].length
                    ) ?: columnList[index].length
        )
    }

    rowList.forEachIndexed { _, item ->

        Column(
            modifier = Modifier.size(width = 380.dp, height = Dp.Infinity)
        ) {
            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                columnList.forEachIndexed { columnIndex, _ ->
                    getColumnValue(item, columnIndex)
                        ?.let {
                            Text(
                                modifier = Modifier
                                    .weight(1.5f.takeIf { columnIndex == 0 } ?: columnWeight[columnIndex]),
                                text = it,
                                style = getTextStyle(typography = Styles.BodyMedium.takeIf { !isPreview } ?: Styles.LabelSmall),
                                textAlign = TextAlign.End.takeIf { columnIndex == columnList.size - 1 }
                            )
                        }
                    if (columnIndex != columnList.size - 1) Spacer(modifier = Modifier.width(10.dp))
                }
            }

            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

private fun getColumnValue(item: ItemModel, index: Int): String? {
    return when (index) {
        0 -> item.name
        1 -> item.qtySelected.toString()
        2 -> item.price.toString()
        3 -> item.discount.toString()
        else -> item.qtySelected?.let { item.price?.times(it) }.toString()
    }
}