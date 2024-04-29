package receipt

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.theme.Styles
import core.utils.DashedDivider
import core.utils.calculateWeight
import core.utils.getTextStyle
import setting.domain.model.ItemModel

@Composable
fun BillHeaderItem(
    columnList: List<String> = arrayListOf(),
    rowList: List<ItemModel> = arrayListOf()
) {

    val columnHeaderList1 = listOf("ការពិពណ៌នា", "ចំនួន", "តំម្លៃ", "ចុះតំម្លៃ", "ទឹកប្រាក់")
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

    Column(
        modifier = Modifier.size(width = 380.dp, height = Dp.Infinity)
    ) {
        Spacer(modifier = Modifier.height(5.dp))

        DashedDivider(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp), color = Color.Black, thickness = 2.dp)

        Spacer(modifier = Modifier.height(5.dp))

        Row(modifier = Modifier) {
            columnHeaderList1.forEachIndexed { index, column ->
                Text(
                    modifier = Modifier
                        .alpha(0.5f)
                        .weight(1.5f.takeIf { index == 0 } ?: columnWeight[index]),
                    text = column,
                    style = getTextStyle(typography = Styles.BodyMedium),
                    textAlign = TextAlign.End.takeIf { index == columnList.size - 1 }
                )
                if (index != columnHeaderList1.size - 1) Spacer(modifier = Modifier.width(10.dp))
            }
        }

        Row(modifier = Modifier) {
            columnList.forEachIndexed { index, column ->
                Text(
                    modifier = Modifier
                        .alpha(0.5f)
                        .weight(1.5f.takeIf { index == 0 } ?: columnWeight[index]),
                    text = column,
                    style = getTextStyle(typography = Styles.BodyMedium),
                    textAlign = TextAlign.End.takeIf { index == columnList.size - 1 }
                )
                if (index != columnList.size - 1) Spacer(modifier = Modifier.width(10.dp))
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        DashedDivider(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp), color = Color.Black, thickness = 2.dp)

        Spacer(modifier = Modifier.height(5.dp))
    }
}

private fun getColumnValue(item: ItemModel, index: Int): String? {
    return when (index) {
        0 -> item.name
        1 -> item.qty.toString()
        2 -> item.price.toString()
        3 -> item.discount.toString()
        else -> item.qty?.let { item.price?.times(it) }.toString()
    }
}