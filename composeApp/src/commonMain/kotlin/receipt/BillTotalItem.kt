package receipt

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.app.convertToObject
import core.utils.Constants
import core.utils.DashedDivider
import core.utils.SharePrefer
import core.utils.calculateWeight
import ui.settings.domain.model.SavePointData
import ui.settings.domain.model.ShopData

@Composable
fun BillTotalItem(
    isPreview: Boolean = false,
    savePointData: SavePointData? = null,
    columnList: List<String> = arrayListOf(),
    rowList: List<String> = arrayListOf(),
    pointColumnList: List<String> = arrayListOf(),
    pointRowList: List<String> = arrayListOf()
) {

    var columnWeight by remember {
        mutableStateOf(0.1f)
    }

    var rowWeight by remember {
        mutableStateOf(0.1f)
    }

    val columnWeightList = mutableListOf<Int>()
    val rowWeightList = mutableListOf<Int>()

    for (element in columnList) {
        columnWeightList.add(element.length)
    }

    for (element in rowList) {
        rowWeightList.add(element.length)
    }

    val columnSorted = columnWeightList.sortedByDescending { it }
    columnWeight = calculateWeight(columnSorted.first()).takeIf { columnWeight < calculateWeight(columnSorted.first()) } ?: columnWeight

    val rowSorted = rowWeightList.sortedByDescending { it }
    rowWeight = calculateWeight(rowSorted.first()).takeIf { rowWeight < calculateWeight(rowSorted.first()) } ?: rowWeight

    Column(
        modifier = Modifier.size(width = 380.dp, height = Dp.Infinity)
    ) {
        Spacer(modifier = Modifier.height(5.dp))

        DashedDivider(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
            color = Color.Black, thickness = 2.dp.takeIf { !isPreview } ?: 1.dp)

        Spacer(modifier = Modifier.height(5.dp))

        Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
            columnList.forEachIndexed { index, column ->
                ResultTotalItem(label = column, value = rowList[index], labelWeight = columnWeight, valueWeight = rowWeight, isPreview = isPreview)
            }
        }

        if (savePointData?.isUsed == true) {

            Spacer(modifier = Modifier.height(5.dp))

            DashedDivider(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
                color = Color.Black, thickness = 2.dp.takeIf { !isPreview } ?: 1.dp)

            Spacer(modifier = Modifier.height(5.dp))

            Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
                pointColumnList.forEachIndexed { index, column ->
                    ResultTotalItem(
                        label = column,
                        value = pointRowList[index],
                        labelWeight = columnWeight,
                        valueWeight = rowWeight,
                        isPreview = isPreview
                    )
                }
            }
        }
    }
}