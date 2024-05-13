package ui.stock.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.scanner.QrScannerScreen
import core.utils.ImageLoader
import ui.stock.domain.model.Product

@Composable
fun AddNewStock(
    searchViewModel: SearchEngineViewModel,
    inventoryViewModel: InventoryViewModel,
    callback: () -> Unit
) {
    val searchState = searchViewModel.state.collectAsState().value

    var startBarCodeScan by remember { mutableStateOf(false) }
    var barCode by remember { mutableStateOf(Long.MIN_VALUE) }
    var barImage by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.padding(20.dp)
    ) {
        if (!startBarCodeScan) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Add New Stock")

                    Row {
                        Button(
                            onClick = {

                            }
                        ) {
                            Text(text = "Camera")
                        }
                        Spacer(modifier = Modifier.width(5.dp))

                        Button(
                            onClick = {
                                startBarCodeScan = true
                            }
                        ) {
                            Text(text = "Scan")
                        }
                    }
                }

                TextField(
                    value = "",
                    onValueChange = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .focusRequester(remember { FocusRequester() }),
                    shape = RoundedCornerShape(10.dp),
                    placeholder = { Text("Product Name") },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    value = "",
                    onValueChange = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .focusRequester(remember { FocusRequester() }),
                    shape = RoundedCornerShape(10.dp),
                    placeholder = { Text("Quantity") },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )

                LazyRow(
                    contentPadding = PaddingValues(5.dp)
                ) {
                    searchState.data?.items?.let {
                        items(it) { item ->
                            item.image?.thumbnailLink?.let { url ->
                                ImageLoader(
                                    modifier = Modifier.size(100.dp).clickable {
                                        barImage = url
                                    },
                                    image = url
                                )
                            }
                        }
                    }
                }

                Button(
                    onClick = {
                        val product = Product(product_id = barCode, name = "Product", image = barImage, qty = 1, price = 10, description = "")
                        inventoryViewModel.onAddProduct(product)
                    }
                ) {
                    Text(text = "Add")
                }
            }
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
                    barCode = it.toLong()
                    searchViewModel.onSearchClick(it)
                }
            )
        }
    }

}