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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.image.picker.toImageBitmap
import core.theme.Styles
import core.theme.White
import core.utils.ImageLoader
import core.utils.LineWrapper
import core.utils.calculateWeight
import core.utils.dollar
import core.utils.getTextStyle
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_empty
import poscomposemultiplatform.composeapp.generated.resources.ic_gallery
import poscomposemultiplatform.composeapp.generated.resources.ic_unknown
import ui.stock.domain.model.ProductMenu

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ProductInformation(
    data: List<ProductMenu>? = null,
    onItemClick: (ProductMenu) -> Unit = {}
) {
    val columnList = listOf("No", "", "Product Name", "Category", "SKU", "Amount", "Discount", "Qty", "Date")
    val columnWeight = remember { MutableList(columnList.size) { 0f } } //column header weight

    for (index in columnList.indices) {
        val columnValues = data.let { productStock ->
            productStock?.map {
                getColumnValue(
                    it,
                    rowIndex = 0,
                    colIndex = index
                )
            }
        } //get all value column list each index of header
        val columnSorted = columnValues?.sortedByDescending { it.length } // short length of string to get the biggest. Then we will use it for calculateWeight to make table suitable

        //each columnHeaderWeight
        columnWeight[index] = calculateWeight(
            (
                    if ((columnSorted?.firstOrNull()?.length
                            ?: 0) > columnList[index].length
                    ) columnSorted?.firstOrNull()?.length
                    else columnList[index].length
                    ) ?: columnList[index].length
        )
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(top = 20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                columnList.forEachIndexed { columnIndex, item ->
                    Text(
                        modifier = Modifier.weight(0.4f.takeIf { columnIndex == 1 }
                            ?: columnWeight[columnIndex]),
                        text = item,
                        textAlign = TextAlign.End.takeIf { columnIndex == columnList.size - 1 }
                    )
                    if (columnIndex != columnList.size - 1) Spacer(modifier = Modifier.width(10.dp))
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            LineWrapper(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                data?.forEachIndexed { rowIndex, item ->
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier.clickable {
                                onItemClick(item)
                            }
                        ) {
                            Row(
                                modifier = Modifier.padding(start = 20.dp, top = 10.dp, bottom = 10.dp, end = 20.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                columnList.forEachIndexed { columnIndex, _ ->
                                    if (columnIndex == 1) {
                                        Box(
                                            modifier = Modifier.weight(0.4f),
                                            contentAlignment = Alignment.CenterEnd
                                        ) {
                                            Card(
                                                modifier = Modifier.size(42.dp),
                                                shape = RoundedCornerShape(10.dp)
                                            ) {
                                                item.image?.let {
                                                    Image(
                                                        bitmap = it.toImageBitmap(),
                                                        contentDescription = null,
                                                        contentScale = ContentScale.FillBounds
                                                    )
                                                } ?:
                                                Image(
                                                    contentDescription = null,
                                                    painter = painterResource(resource = Res.drawable.ic_unknown)
                                                )
                                            }
                                        }

                                        item.imageUrl?.let {
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
                                            text = getColumnValue(item, rowIndex + 1, columnIndex),
                                            style = getTextStyle(typography = Styles.BodyMedium),
                                            textAlign = TextAlign.End.takeIf { columnIndex == columnList.size - 1 }
                                        )
                                    }

                                    if (columnIndex != columnList.size - 1) Spacer(
                                        modifier = Modifier.width(
                                            10.dp
                                        )
                                    )
                                }
                            }
                        }

                        if (rowIndex != data.size - 1) {
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

private fun getColumnValue(item: ProductMenu, rowIndex: Int, colIndex: Int): String {
    return when (colIndex) {
        0 -> rowIndex.toString()
        1 -> " "
        2 -> item.name.orEmpty()
        3 -> item.menuName.orEmpty()
        4 -> item.productId.toString()
        5 -> item.price?.toDouble()?.dollar().toString()
        6 -> "-".takeIf { item.discount.isNullOrEmpty() } ?: item.discount.orEmpty()
        7 -> item.qty.toString()
        else -> item.date.orEmpty()
    }
}