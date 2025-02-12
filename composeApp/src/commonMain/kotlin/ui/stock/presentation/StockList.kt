package ui.stock.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import core.app.convertToString
import core.scanner.QrScannerScreen
import core.utils.DialogPreview
import core.utils.LabelInputRequire
import core.utils.PrimaryButton
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
            title = "Stock outs tracking and management",
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
                        Text("Adjust your stock out")

                        Spacer(modifier = Modifier.height(17.dp))

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

                        if (!isStockIn) {
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
                                        menuId = null,
                                        productId = product?.productId,
                                        name = null,
                                        image = null,
                                        imageUrl = null,
                                        uom = null,
                                        qty = productQty.toString(),
                                        price = product?.price.takeIf { stockStatus == "In" } ?: unitPrice.toString(),
                                        amount = null,
                                        discount = null,
                                        status = stockStatus
                                    )
                                    onEvent(InventoryEvent.AdjustProduct(item))
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
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                product?.name?.let {
                    Text(
                        text = it
                    )
                }

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
            Row {
                Box(
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
                ) {
                    PrimaryButton(
                        text = "Stock In",
                        onClick = {
                            stockStatus = "In"
                            isStockIn = true
                            isUpdate = true
                        }
                    )
                }

                Box(
                    modifier = Modifier.padding(start = 5.dp, top = 10.dp, bottom = 10.dp)
                ) {
                    PrimaryButton(
                        text = "Stock Out",
                        onClick = {
                            stockStatus = "Out"
                            isStockIn = false
                            isUpdate = true
                        }
                    )
                }

                Box(
                    modifier = Modifier.padding(start = 5.dp, top = 10.dp, bottom = 10.dp, end = 20.dp)
                ) {
                    PrimaryButton(
                        text = "Download Report",
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
                stockStatus = "Adjust"
                isStockIn = false
                isUpdate = true
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