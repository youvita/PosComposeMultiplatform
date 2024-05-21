package ui.stock.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.TabRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.primaryContentColor
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap
import core.scanner.QrScannerScreen
import core.theme.Black
import core.theme.ColorE4E4E4
import core.theme.PrimaryColor
import core.theme.Shapes
import core.theme.White
import core.utils.ImageLoader
import core.utils.LineWrapper
import core.utils.PrimaryButton
import core.utils.TextInputDefault
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_camera
import poscomposemultiplatform.composeapp.generated.resources.ic_profie
import poscomposemultiplatform.composeapp.generated.resources.ic_scanner
import ui.stock.domain.model.Product

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AddNewStock(
    searchViewModel: SearchEngineViewModel,
    inventoryViewModel: InventoryViewModel,
    callback: () -> Unit
) {
    val searchState = searchViewModel.state.collectAsState().value
    val stockState = inventoryViewModel.stateProductStock.collectAsState().value

    var startBarCodeScan by remember { mutableStateOf(false) }
    var barCode by remember { mutableStateOf(Long.MIN_VALUE) }
    var barImage by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf(Long.MIN_VALUE) }
    var discount by remember { mutableStateOf(Long.MIN_VALUE) }
    var category by remember { mutableStateOf("") }
//    var stockBox by remember { mutableStateOf(Long.MIN_VALUE) }
//    var stockQty by remember { mutableStateOf(Long.MIN_VALUE) }
    var byteImage by remember { mutableStateOf<ByteArray?>(null) }
    var indexSelected by remember { mutableStateOf(-1) }

    val scope = rememberCoroutineScope()
    val singleImagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = {
            it.firstOrNull()?.let { image ->
                indexSelected = 0
                byteImage = image
            }
        }
    )

    var tabIndex by rememberSaveable {
        mutableStateOf(0)
    }

    val tabItems = listOf(
        "Product Detail",
        "Stock"
    )

    Scaffold(
        modifier = Modifier.padding(end = 20.dp)
    ) {
        if (!startBarCodeScan) {
            Row {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TabRow(
                            modifier = Modifier.fillMaxWidth(0.4f),
                            selectedTabIndex = tabIndex,
                            backgroundColor = White,
                            contentColor = White,
                        ) {
                            tabItems.forEachIndexed { index, title ->
                                Tab(
                                    selected = false,
                                    onClick = {
                                        tabIndex = index
                                        if (tabIndex == 1) {
                                            inventoryViewModel.onGetProductStock()
                                        }
                                    },
                                    text = {
                                        Text(
                                            text = title
                                        )
                                    },
                                    selectedContentColor = Black.takeIf { tabIndex == index} ?: Color.Gray,
                                )
                            }
                        }

                        PrimaryButton(
                            text = "Save Product",
                            callBack = {
                                val product = Product(
                                    menu_id = 0,
                                    product_id = barCode,
                                    name = name,
                                    image = byteImage,
                                    imageUrl = barImage,
                                    qty = 0,
                                    price = price,
                                    discount = discount,
                                    category_name = category,
                                    category_image = byteImage,
                                )
                                inventoryViewModel.onAddProduct(product)
                                callback()
                            }
                        )
                    }

                    LineWrapper(modifier = Modifier.padding(start = 20.dp))

                    AnimatedVisibility(
                        visible = tabIndex == 0,
                        enter = fadeIn() + slideInHorizontally(),
                        exit = fadeOut() + slideOutHorizontally()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Spacer(modifier = Modifier.height(15.dp))

                            Text(text = "※ Scan barcode product of image for fast")

                            Spacer(modifier = Modifier.height(15.dp))

                            Image(
                                modifier = Modifier.clickable {
                                    startBarCodeScan = true
                                },
                                painter = painterResource(resource = Res.drawable.ic_scanner),
                                contentDescription = ""
                            )

                            Spacer(modifier = Modifier.height(23.dp))

                            Row {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Column {
                                        Text(text = "Product SKU *")

                                        Spacer(modifier = Modifier.height(5.dp))

                                        TextInputDefault(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = barCode.toString().takeIf { barCode > 0 } ?: "",
                                            placeholder = "Barcode",
                                            onValueChange = {
                                                barCode = it.toLong()
                                            }
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Column {
                                        Text(text = "Product Name")

                                        Spacer(modifier = Modifier.height(5.dp))

                                        TextInputDefault(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = name,
                                            placeholder = "Enter Product Name",
                                            onValueChange = {
                                                name = it
                                            }
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Column {
                                        Text(text = "Price")

                                        Spacer(modifier = Modifier.height(5.dp))

                                        TextInputDefault(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = price.toString().takeIf { price > 0 } ?: "",
                                            placeholder = "Enter Price",
                                            onValueChange = {
                                                if (it.isNotEmpty()) {
                                                    price = it.toLong()
                                                }
                                            },
                                            keyboardType = KeyboardType.Number
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Column {
                                        Text(text = "Discount")

                                        Spacer(modifier = Modifier.height(5.dp))

                                        TextInputDefault(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = discount.toString().takeIf { discount > 0 } ?: "",
                                            placeholder = "Enter Discount",
                                            onValueChange = {
                                                discount = it.toLong()
                                            },
                                            keyboardType = KeyboardType.Number
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Column {
                                        Text(text = "Category")

                                        Spacer(modifier = Modifier.height(5.dp))

                                        TextInputDefault(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = category,
                                            placeholder = "Select Category",
                                            onValueChange = {
                                                category = it
                                            },
                                            keyboardType = KeyboardType.Number
                                        )
                                    }

//                                Spacer(modifier = Modifier.height(10.dp))
//
//                                Column {
//                                    Text(text = "Stock as Box")
//
//                                    Spacer(modifier = Modifier.height(5.dp))
//
//                                    TextInputDefault(
//                                        modifier = Modifier.fillMaxWidth(),
//                                        text = stockBox.toString().takeIf { stockBox > 0 } ?: "",
//                                        placeholder = "Enter Box",
//                                        onValueChange = {
//                                            stockBox = it.toLong()
//                                        },
//                                        keyboardType = KeyboardType.Number
//                                    )
//                                }
//
//                                Spacer(modifier = Modifier.height(10.dp))
//
//                                Column {
//                                    Text(text = "Stock Qty")
//
//                                    Spacer(modifier = Modifier.height(5.dp))
//
//                                    TextInputDefault(
//                                        modifier = Modifier.fillMaxWidth(),
//                                        text = stockQty.toString().takeIf { stockQty > 0 } ?: "",
//                                        placeholder = "Enter Quality",
//                                        onValueChange = {
//                                            stockQty = it.toLong()
//                                        },
//                                        keyboardType = KeyboardType.Number
//                                    )
//                                }

                                    Spacer(modifier = Modifier.height(20.dp))
                                }

                                Spacer(modifier = Modifier.width(42.dp))

                                Column {
                                    Row {
                                        Box(
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (byteImage != null) {
                                                byteImage?.let {
                                                    Card(
                                                        modifier = Modifier.size(100.dp)
                                                            .clickable {
                                                                indexSelected = 0
                                                            },
                                                        shape = RoundedCornerShape(10.dp),
                                                        border = BorderStroke(2.dp, PrimaryColor.takeIf { indexSelected == 0 } ?: ColorE4E4E4)
                                                    ) {
                                                        Image(
                                                            modifier = Modifier.alpha(0.5f.takeIf { indexSelected == 0 } ?: 1f),
                                                            bitmap = it.toImageBitmap(),
                                                            contentDescription = null,
                                                            contentScale = ContentScale.FillBounds
                                                        )
                                                    }
                                                }
                                            } else {
                                                Image(
                                                    modifier = Modifier.size(100.dp),
                                                    painter = painterResource(resource = Res.drawable.ic_profie),
                                                    contentDescription = null
                                                )
                                            }

                                            Box(
                                                modifier = Modifier
                                                    .clip(shape = CircleShape)
                                                    .size(40.dp)
                                                    .background(White, shape = CircleShape)
                                                    .clickable {
                                                        singleImagePicker.launch()
                                                    },
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Image(
                                                    painter = painterResource(resource = Res.drawable.ic_camera),
                                                    contentDescription = null
                                                )
                                            }

                                        }

                                        Spacer(modifier = Modifier.width(15.dp))

                                        if (searchState.data?.items == null) {
                                            Image(
                                                modifier = Modifier.size(100.dp),
                                                painter = painterResource(resource = Res.drawable.ic_profie),
                                                contentDescription = null
                                            )
                                        } else {
                                            searchState.data?.items?.let {
                                                it[0].image?.thumbnailLink?.let { url ->
                                                    Card(
                                                        modifier = Modifier.size(100.dp),
                                                        shape = RoundedCornerShape(10.dp),
                                                        border = BorderStroke(2.dp, PrimaryColor.takeIf { indexSelected == 1 } ?: ColorE4E4E4),
                                                        colors = CardDefaults.cardColors(
                                                            contentColor = ColorE4E4E4.takeIf { indexSelected == 1 } ?: White
                                                        )
                                                    ) {
                                                        ImageLoader(
                                                            modifier = Modifier
                                                                .alpha(0.5f.takeIf { indexSelected == 1 } ?: 1f)
                                                                .clickable {
                                                                    indexSelected = 1
                                                                    barImage = url
                                                                },
                                                            image = url
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(15.dp))

                                    Row {
                                        if (searchState.data?.items == null) {
                                            Image(
                                                modifier = Modifier.size(100.dp),
                                                painter = painterResource(resource = Res.drawable.ic_profie),
                                                contentDescription = null
                                            )
                                        } else {
                                            searchState.data?.items?.let {
                                                it[1].image?.thumbnailLink?.let { url ->
                                                    Card(
                                                        modifier = Modifier.size(100.dp),
                                                        shape = RoundedCornerShape(10.dp),
                                                        border = BorderStroke(2.dp, PrimaryColor.takeIf { indexSelected == 2 } ?: ColorE4E4E4),
                                                        colors = CardDefaults.cardColors(
                                                            contentColor = ColorE4E4E4.takeIf { indexSelected == 2 } ?: White
                                                        )
                                                    ) {
                                                        ImageLoader(
                                                            modifier = Modifier
                                                                .alpha(0.5f.takeIf { indexSelected == 2 } ?: 1f)
                                                                .clickable {
                                                                    indexSelected = 2
                                                                    barImage = url
                                                                },
                                                            image = url
                                                        )
                                                    }
                                                }
                                            }
                                        }

                                        Spacer(modifier = Modifier.width(15.dp))

                                        if (searchState.data?.items == null) {
                                            Image(
                                                modifier = Modifier.size(100.dp),
                                                painter = painterResource(resource = Res.drawable.ic_profie),
                                                contentDescription = null
                                            )
                                        } else {
                                            searchState.data?.items?.let {
                                                it[2].image?.thumbnailLink?.let { url ->
                                                    Card(
                                                        modifier = Modifier.size(100.dp),
                                                        shape = RoundedCornerShape(10.dp),
                                                        border = BorderStroke(2.dp, PrimaryColor.takeIf { indexSelected == 3 } ?: ColorE4E4E4),
                                                        colors = CardDefaults.cardColors(
                                                            contentColor = ColorE4E4E4.takeIf { indexSelected == 3 } ?: White
                                                        )
                                                    ) {
                                                        ImageLoader(
                                                            modifier = Modifier
                                                                .alpha(0.5f.takeIf { indexSelected == 3 } ?: 1f)
                                                                .clickable {
                                                                    indexSelected = 3
                                                                    barImage = url
                                                                },
                                                            image = url
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }

                    AnimatedVisibility(
                        visible = tabIndex == 1,
                        enter = fadeIn() + slideInHorizontally(),
                        exit = fadeOut() + slideOutHorizontally()
                    ) {
                        StockInformation(
                            state = stockState
                        )
                    }
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
                    println(">>>>>> $it")
                    startBarCodeScan = false

                    if (it.isEmpty()) return@QrScannerScreen
                    barCode = it.toLong()
                    searchViewModel.onSearchClick(it)
                }
            )
        }
    }

}