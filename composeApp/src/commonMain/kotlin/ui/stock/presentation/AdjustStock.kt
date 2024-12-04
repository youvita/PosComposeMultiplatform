package ui.stock.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import core.utils.DialogPreview
import core.utils.LabelInputRequire
import core.utils.PrimaryButton
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.stock.domain.model.Product
import ui.stock.domain.model.ProductStock

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AdjustStock(
    state: InventoryState,
    onEvent: (InventoryEvent) -> Unit
) {
    var isAdjustStock by remember { mutableStateOf(false) }
    var productStock by remember { mutableStateOf(ProductStock()) }

    // alert dialog to adjust stock
    if (isAdjustStock) {
        var productQty by remember { mutableStateOf(0) }
        DialogPreview(
            title = "Adjust Stock",
            onClose = {
                isAdjustStock = false
            },
            onDismissRequest = {
                isAdjustStock = false
            }
        ) {
            LabelInputRequire(
                modifier = Modifier.fillMaxWidth(),
                label = "Stock Out",
                placeholder = "Enter Stock",
                keyboardType = KeyboardType.Number,
                onValueChange = {
                    if (it.isNotEmpty()) {
                        productQty = it.toInt()
                    }
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

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

    StockInformation(
        state = state,
        onItemClick = {
            productStock = it
            isAdjustStock = true
        }
    )
}