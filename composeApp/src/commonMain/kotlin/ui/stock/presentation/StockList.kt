package ui.stock.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.preat.peekaboo.image.picker.toImageBitmap
import core.app.convertToString
import core.scanner.QrScannerScreen
import core.theme.ColorE1E1E1
import core.theme.ColorE4E4E4
import core.theme.PrimaryColor
import core.theme.Shapes
import core.theme.Styles
import core.theme.White
import core.utils.Constants
import core.utils.DialogPreview
import core.utils.LabelInputRequire
import core.utils.PrimaryButton
import core.utils.dollar
import core.utils.getTextStyle
import getPlatform
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_adjust_stock
import ui.stock.domain.model.Product
import ui.stock.domain.model.ProductMenu
import ui.stock.domain.model.toStockItem

@OptIn(ExperimentalResourceApi::class)
@Composable
fun StockList(
    state: InventoryState,
    product: ProductMenu?,
    onEvent: (InventoryEvent) -> Unit
) {
    val platform = getPlatform()
    var isDownload by remember { mutableStateOf(false) }
    var isUpdate by remember { mutableStateOf(false) }
    var product by remember { mutableStateOf(product) }
    var startBarCodeScan by remember { mutableStateOf(false) }
    var isStockIn by remember { mutableStateOf(false) }
    var stockStatus by remember { mutableStateOf("") }
    var statusName by remember { mutableStateOf("") }
    var isAdjust by remember { mutableStateOf(false) }
    var stockId by remember { mutableStateOf(0L) }

    if (isDownload) {
        platform.download(
            convertToString(state.data?.map { it.toStockItem() })
        )
        isDownload = false
    }

    // alert dialog to adjust stock
    if (isUpdate) {
        var productQty by remember { mutableStateOf(0) }
        var unitPrice by remember { mutableStateOf(0) }

        DialogPreview(
            title = "Stock Management",
            onClose = {
                isUpdate = false
            },
            onDismissRequest = {
                isUpdate = false
            }
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Row {
                    Image(
                        modifier = Modifier.size(200.dp),
                        painter = painterResource(resource = Res.drawable.ic_adjust_stock), contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(25.dp))

                    Column {
//                        Text("Adjust your stock out")
//
//                        Spacer(modifier = Modifier.height(17.dp))

                        LabelInputRequire(
                            modifier = Modifier.fillMaxWidth(),
                            label = "Qty",
                            placeholder = "Enter Qty",
                            keyboardType = KeyboardType.Number,
                            onValueChange = {
                                if (it.isNotEmpty()) {
                                    productQty = it.toInt()
                                }
                            }
                        )

                        if (stockStatus == Constants.StockType.STOCK_OUT) {
                            Spacer(modifier = Modifier.height(10.dp))

                            LabelInputRequire(
                                modifier = Modifier.fillMaxWidth(),
                                label = "Unit Price",
                                placeholder = "Enter Amount",
                                keyboardType = KeyboardType.Number,
                                onValueChange = {
                                    if (it.isNotEmpty()) {
                                        unitPrice = it.toInt()
                                    }
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(17.dp))

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            PrimaryButton(
                                text = "Update",
                                onClick = {
                                    val item = Product(
                                        id = stockId,
                                        menuId = null,
                                        productId = product?.productId,
                                        name = null,
                                        image = null,
                                        imageUrl = null,
                                        uom = null,
                                        qty = productQty.toString(),
                                        price = product?.price.takeIf { stockStatus == Constants.StockType.STOCK_IN } ?: unitPrice.toString(),
                                        amount = null,
                                        discount = null,
                                        statusCode = stockStatus,
                                        statusName = statusName,
                                        isStockIn = true.takeIf { stockStatus == Constants.StockType.STOCK_IN } ?: false
                                    )
                                    if (isAdjust) {
                                        onEvent(InventoryEvent.UpdateStock(item))
                                    } else {
                                        onEvent(InventoryEvent.AdjustProduct(item))
                                    }

                                    onEvent(InventoryEvent.GetProductStock())
                                    isUpdate = false
                                }
                            )
                        }
                    }

                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
        ) {
            Row(modifier = Modifier.fillMaxWidth().weight(1f)) {
                Card(
                    modifier = Modifier
                        .padding(start = 20.dp, top = 10.dp)
                        .clip(Shapes.medium),
                    shape = Shapes.medium,
                    colors = CardDefaults.cardColors(
                        containerColor = ColorE1E1E1
                    ),
                    elevation = CardDefaults.cardElevation(2.dp),
                ) {
                    Box(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Row {
                            Card(
                                modifier = Modifier.size(42.dp),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                product?.image?.let {
                                    Image(
                                        bitmap = it.toImageBitmap(),
                                        contentDescription = null,
                                        contentScale = ContentScale.FillBounds
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {
                                product?.name?.let {
                                    Text(
                                        text = "Product: $it / SKU[${product?.productId}]",
                                        style = getTextStyle(typography = Styles.BodyMedium),
                                        fontSize = 17.sp
                                    )
                                }

                                Spacer(modifier = Modifier.height(5.dp))

                                product?.menuName?.let {
                                    Text(
                                        text = "Category: $it",
                                        style = getTextStyle(typography = Styles.BodySmall),
                                    )
                                }
                            }
                        }
                    }

                }

//                Card(
//                    modifier = Modifier
//                        .padding(20.dp)
//                        .clip(Shapes.medium),
//                    shape = Shapes.medium,
//                    colors = CardDefaults.cardColors(
//                        containerColor = ColorE1E1E1
//                    ),
//                    elevation = CardDefaults.cardElevation(2.dp),
//                ) {
//                    Box(
//                        modifier = Modifier.padding(20.dp)
//                    ) {
//                        Row {
//                            Column {
//                                Text(
//                                    text = "Stock In",
//                                    style = getTextStyle(typography = Styles.BodyMedium)
//                                )
//
//                                Spacer(modifier = Modifier.height(5.dp))
//
//                                product?.price?.let {
//                                    Text(
//                                        text = it.toDouble().dollar(),
//                                        style = getTextStyle(typography = Styles.BodySmall),
//                                        fontSize = 17.sp
//                                    )
//                                }
//                            }
//                        }
//                    }
//
//                }
            }

//            Box(
//                modifier = Modifier
//                    .clip(CircleShape)
//                    .clickable {
//                    startBarCodeScan = true
//                }
//            ) {
//                Image(
//                    modifier = Modifier
//                        .padding(10.dp)
//                        .size(24.dp),
//                    contentDescription = null,
//                    painter = painterResource(resource = Res.drawable.ic_scanner))
//            }
            Row(modifier = Modifier) {
                Box(
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
                ) {
                    PrimaryButton(
                        text = "Stock In",
                        containColor = Color(0xFF04D000),
                        onClick = {
                            statusName = "In"
                            stockStatus = Constants.StockType.STOCK_IN
                            isStockIn = true
                            isUpdate = true
                            isAdjust = false
                        }
                    )
                }

                Box(
                    modifier = Modifier.padding(start = 5.dp, top = 10.dp, bottom = 10.dp)
                ) {
                    PrimaryButton(
                        text = "Stock Out",
                        containColor = Color(0xFFFF0000),
                        onClick = {
                            statusName = "Out"
                            stockStatus = Constants.StockType.STOCK_OUT
                            isStockIn = false
                            isUpdate = true
                            isAdjust = false
                        }
                    )
                }

                Box(
                    modifier = Modifier.padding(start = 5.dp, top = 10.dp, bottom = 10.dp, end = 20.dp)
                ) {
                    PrimaryButton(
                        text = "Download",
                        onClick = {
                            isDownload = true
                        }
                    )
                }
            }

        }

        StockInformation(
            state = state,
            onItemClick = {
                statusName = "Adjust"
                it.stockId?.let { id ->
                    stockId = id
                }
                stockStatus = it.statusCode.orEmpty()
//                stockStatus = "Adjust"
                isStockIn = false
                isUpdate = true
                isAdjust = true
//                productStock = it
//                isAdjustStock = true
            }
        )

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
                val product = Product(
                    menuId = null,
                    productId = it.toLong(),
                    name = null,
                    image = null,
                    imageUrl = null,
                    uom = null,
                    qty = "1",
                    price = null,
                    amount = null,
                    discount = null
                )
                onEvent(InventoryEvent.AdjustProduct(product))
                onEvent(InventoryEvent.GetProductStock())
            }
        )
    }
}