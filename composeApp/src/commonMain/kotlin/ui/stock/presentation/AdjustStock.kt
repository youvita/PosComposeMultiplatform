package ui.stock.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import poscomposemultiplatform.composeapp.generated.resources.ic_scan_barcode
import poscomposemultiplatform.composeapp.generated.resources.ic_scanner
import ui.stock.domain.model.Product
import ui.stock.domain.model.ProductStock
import ui.stock.domain.model.toStockItem

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AdjustStock(
    state: InventoryState,
    onEvent: (InventoryEvent) -> Unit
) {
    val platform = getPlatform()
    var isDownload by remember { mutableStateOf(false) }
    var isAdjustStock by remember { mutableStateOf(false) }
    var productStock by remember { mutableStateOf(ProductStock()) }
    var startBarCodeScan by remember { mutableStateOf(false) }

    if (isDownload) {
        platform.download(
            convertToString(state.data?.map { it.toStockItem() })
        )
        isDownload = false
    }

    // alert dialog to adjust stock
    if (isAdjustStock) {
        var productQty by remember { mutableStateOf(0) }
        DialogPreview(
            title = "Stock outs tracking and management",
            onClose = {
                isAdjustStock = false
            },
            onDismissRequest = {
                isAdjustStock = false
            }
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Row {
                    Image(painter = painterResource(resource = Res.drawable.ic_adjust_stock), contentDescription = null)

                    Spacer(modifier = Modifier.width(25.dp))

                    Column {
                        Text("Adjust your stock out")

                        Spacer(modifier = Modifier.height(17.dp))

                        LabelInputRequire(
                            modifier = Modifier.fillMaxWidth(),
                            label = "Amount of stock out",
                            placeholder = "Enter qty",
                            keyboardType = KeyboardType.Number,
                            onValueChange = {
                                if (it.isNotEmpty()) {
                                    productQty = it.toInt()
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(17.dp))

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            PrimaryButton(
                                text = "Update",
                                onClick = {
                                    val product = Product(
                                        menuId = null,
                                        productId = productStock.productId,
                                        name = null,
                                        image = null,
                                        imageUrl = null,
                                        qty = productQty.toString(),
                                        price = null,
                                        discount = null
                                    )
                                    onEvent(InventoryEvent.AdjustProduct(product))
                                    onEvent(InventoryEvent.GetProductStock())
                                    isAdjustStock = false
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                    startBarCodeScan = true
                }
            ) {
                Image(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(24.dp),
                    contentDescription = null,
                    painter = painterResource(resource = Res.drawable.ic_scanner))
            }

            Spacer(modifier = Modifier.width(10.dp))

            Box(
                modifier = Modifier.padding(10.dp)
            ) {
                PrimaryButton(
                    text = "Download Report",
                    onClick = {
                        isDownload = true
                    }
                )
            }
        }

        StockInformation(
            state = state,
            onItemClick = {
                productStock = it
                isAdjustStock = true
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
                    qty = "1",
                    price = null,
                    discount = null
                )
                onEvent(InventoryEvent.AdjustProduct(product))
                onEvent(InventoryEvent.GetProductStock())
            }
        )
    }
}