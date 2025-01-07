package ui.parking.presentation.component

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
import ui.parking.domain.model.Parking
import ui.parking.presentation.ParkingState
import ui.stock.domain.model.ProductStock

@Composable
fun ParkingTable(
    state: ParkingState,
    onItemClick: (Parking) -> Unit = {}
) {

    Box(
        modifier = Modifier.fillMaxWidth().background(White).padding(start = 36.dp, top = 30.dp, end = 36.dp)
    ) {
        val columnList = listOf("No", "Parking No", "Check In", "Check Out", "Duration", "Total Amount")
        val rowList = state.data

        val columnWeight = remember { MutableList(columnList.size) { 0f } } //column header weight

        for (index in columnList.indices) {

            val columnValues =  rowList?.let { parking -> parking.map { getColumnValue(it, index) } } //get all value column list each index of header
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
                        modifier = Modifier.weight(columnWeight[columnIndex]),
                        text = item,
                        textAlign = TextAlign.End.takeIf { columnIndex == columnList.size - 1 || columnIndex == columnList.size - 2 }
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
                rowList?.forEachIndexed { rowIndex , item ->

                    Column(
                        modifier = Modifier.fillMaxWidth().clickable {
                            onItemClick(item)
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            columnList.forEachIndexed { columnIndex, _ ->
                                Text(
                                    modifier = Modifier.weight(columnWeight[columnIndex]),
                                    text = getColumnValue(item, columnIndex),
                                    style = getTextStyle(typography = Styles.BodyMedium),
                                    textAlign = TextAlign.End.takeIf { columnIndex == columnList.size - 1 || columnIndex == columnList.size - 2 },
                                )

                                if (columnIndex != columnList.size - 1) Spacer(modifier = Modifier.width(10.dp))
                            }
                        }

                        if (rowIndex != rowList.size - 1) {
                            LineWrapper(
                                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                            )
                        }
                    }
                }
            }
        }

    }
}

private fun getColumnValue(item: Parking, index: Int): String {
    return when (index) {
        0 -> item.id.toString()
        1 -> item.parkingNo.toString()
        2 -> item.checkIn.toString()
        3 -> "-".takeIf { item.checkOut.isNullOrEmpty() } ?: item.checkOut.toString()
        4 -> item.duration.toString()
        else -> item.total.toString()
    }
}