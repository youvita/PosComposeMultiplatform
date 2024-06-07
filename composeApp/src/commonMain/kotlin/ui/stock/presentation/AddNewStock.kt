package ui.stock.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.Scaffold
import androidx.compose.material.TabRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap
import core.scanner.QrScannerScreen
import core.theme.Black
import core.theme.ColorE4E4E4
import core.theme.ColorF1F1F1
import core.theme.PrimaryColor
import core.theme.Shapes
import core.theme.White
import core.utils.ImageLoader
import core.utils.LineWrapper
import core.utils.PrimaryButton
import core.utils.TextInputDefault
import core.utils.dashedBorder
import core.utils.getCurrentDateTime
import menu.domain.model.MenuModel
import menu.presentation.component.CategoryItem
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.arrow
import poscomposemultiplatform.composeapp.generated.resources.ic_camera
import poscomposemultiplatform.composeapp.generated.resources.ic_down
import poscomposemultiplatform.composeapp.generated.resources.ic_gallery
import poscomposemultiplatform.composeapp.generated.resources.ic_next
import poscomposemultiplatform.composeapp.generated.resources.ic_profie
import poscomposemultiplatform.composeapp.generated.resources.ic_scan_barcode
import poscomposemultiplatform.composeapp.generated.resources.ic_scanner
import poscomposemultiplatform.composeapp.generated.resources.ic_upload
import ui.stock.domain.model.Product
import ui.stock.domain.model.ProductMenu

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AddNewStock(
    productItem: ProductMenu? = null,
    state: InventoryState,
    searchViewModel: SearchEngineViewModel,
    onEvent: (InventoryEvent) -> Unit,
    callback: () -> Unit
) {
    val searchState = searchViewModel.state.collectAsState().value
//    val stockState = inventoryViewModel.stateProductStock.collectAsState().value

    val isNew by remember { mutableStateOf(productItem?.productId == 0L) }
    var startBarCodeScan by remember { mutableStateOf(false) }
    var barCode by remember { mutableStateOf(productItem?.productId ?: 0) }
    var barImage by remember { mutableStateOf("") }
    var name by remember { mutableStateOf(productItem?.name) }
    var price by remember { mutableStateOf(productItem?.price) }
    var discount by remember { mutableStateOf(productItem?.discount) }
    var category by remember { mutableStateOf("") }
//    var stockBox by remember { mutableStateOf(Long.MIN_VALUE) }
//    var stockQty by remember { mutableStateOf(Long.MIN_VALUE) }
    var byteImage by remember { mutableStateOf(productItem?.image) }
    var indexSelected by remember { mutableStateOf(-1) }

    var isSelectCategory by remember { mutableStateOf(false) }
    var menuSelected by remember { mutableStateOf<MenuModel?>(MenuModel(menuId = productItem?.menuId, name = productItem?.menuName, image = productItem?.menuImage)) }

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
                                            onEvent(InventoryEvent.GetProductStock())
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

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Text("Upload Product Image *")

                                AnimatedVisibility(
                                    visible = tabIndex == 0
                                ) {
                                    PrimaryButton(
                                        text = "Save Product".takeIf { isNew } ?: "Update Product",
                                        callBack = {
                                            if (isNew) {
                                                val product = Product(
                                                    menuId = menuSelected?.menuId,
                                                    productId = barCode,
                                                    name = name,
                                                    image = byteImage,
                                                    imageUrl = barImage,
                                                    qty = "0",
                                                    price = price,
                                                    discount = discount
                                                )
                                                onEvent(InventoryEvent.AddProduct(product))
                                            } else {
                                                val product = Product(
                                                    menuId = menuSelected?.menuId,
                                                    productId = barCode,
                                                    name = name,
                                                    image = byteImage,
                                                    imageUrl = barImage,
                                                    qty = "0",
                                                    price = price,
                                                    discount = discount
                                                )
                                                onEvent(InventoryEvent.UpdateProduct(product))
                                            }

                                            callback()
                                        }
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Box(
                                modifier = Modifier
                                    .background(color = ColorF1F1F1, shape = Shapes.medium)
                                    .dashedBorder(1.dp, ColorE4E4E4, 10.dp), contentAlignment = Alignment.Center) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        modifier = Modifier.padding(start = 20.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(text = "※ Choose from library")

                                        Spacer(modifier = Modifier.height(17.dp))

                                        Card(
                                            modifier = Modifier
                                                .clip(Shapes.medium)
                                                .clickable {
                                                    singleImagePicker.launch()
                                            },
                                            shape = Shapes.medium,
                                            colors = CardDefaults.cardColors(
                                                containerColor = White
                                            ),
                                            elevation = CardDefaults.cardElevation(1.dp),
                                            border = BorderStroke(2.dp, PrimaryColor.takeIf { indexSelected == 0 } ?: ColorE4E4E4)
                                        ) {
                                            if (byteImage != null) {
                                                byteImage?.let {
                                                    Image(
                                                        modifier = Modifier
                                                            .size(62.dp)
                                                            .alpha(0.5f.takeIf { indexSelected == 0 } ?: 1f),
                                                        bitmap = it.toImageBitmap(),
                                                        contentDescription = null,
                                                        contentScale = ContentScale.FillBounds
                                                    )
                                                }
                                            } else {
                                                Column(
                                                    modifier = Modifier.padding(
                                                        start = 29.dp,
                                                        top = 10.dp,
                                                        bottom = 14.dp,
                                                        end = 29.dp
                                                    ),
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
                                                    Image(
                                                        modifier = Modifier.size(62.dp),
                                                        contentDescription = null,
                                                        painter = painterResource(resource = Res.drawable.ic_upload)
                                                    )

                                                    Text(text = "Upload", color = PrimaryColor)
                                                }
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(44.dp))

                                    Row(
                                        modifier = Modifier.padding(20.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(
                                            modifier = Modifier.fillMaxWidth().weight(1f)
                                        ) {
                                            Text(text = "※ Scan barcode product of image for fast")

                                            Spacer(modifier = Modifier.height(17.dp))

                                            Row(
                                                modifier = Modifier.fillMaxWidth().background(color = Color.Yellow),
                                                verticalAlignment = Alignment.Top,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                PrimaryButton(
                                                    text = "Scan"
                                                )

                                                Image(
                                                    modifier = Modifier.size(107.dp),
                                                    contentDescription = null,
                                                    painter = painterResource(resource = Res.drawable.ic_scan_barcode))
                                            }
                                        }

                                        Spacer(modifier = Modifier.width(21.dp))

                                        Image(
                                            modifier = Modifier,
                                            contentDescription = null,
                                            painter = painterResource(resource = Res.drawable.ic_next))

                                        Spacer(modifier = Modifier.width(21.dp))

                                        Column(
                                            modifier = Modifier.fillMaxWidth().weight(1f)
                                        ) {
                                            Text(text = "There are 3 images for recommend")

                                            Spacer(modifier = Modifier.height(17.dp))

                                            Row(
                                                modifier = Modifier,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Card(
                                                    modifier = Modifier,
                                                    shape = RoundedCornerShape(10.dp),
                                                    colors = CardDefaults.cardColors(
                                                        containerColor = White
                                                    ),
                                                    elevation = CardDefaults.cardElevation(1.dp)
                                                ) {
                                                    Column(
                                                        modifier = Modifier.padding(start = 29.dp, top = 10.dp, bottom = 14.dp, end = 29.dp),
                                                        horizontalAlignment = Alignment.CenterHorizontally                                            ) {
                                                        Image(
                                                            modifier = Modifier.size(62.dp),
                                                            contentDescription = null,
                                                            painter = painterResource(resource = Res.drawable.ic_gallery))

                                                        Text(text = "Empty")
                                                    }
                                                }

                                                Spacer(modifier = Modifier.width(10.dp))

                                                Card(
                                                    modifier = Modifier,
                                                    shape = RoundedCornerShape(10.dp),
                                                    colors = CardDefaults.cardColors(
                                                        containerColor = White
                                                    ),
                                                    elevation = CardDefaults.cardElevation(1.dp)
                                                ) {
                                                    Column(
                                                        modifier = Modifier.padding(start = 29.dp, top = 10.dp, bottom = 14.dp, end = 29.dp),
                                                        horizontalAlignment = Alignment.CenterHorizontally                                            ) {
                                                        Image(
                                                            modifier = Modifier.size(62.dp),
                                                            contentDescription = null,
                                                            painter = painterResource(resource = Res.drawable.ic_gallery))

                                                        Text(text = "Empty")
                                                    }
                                                }

                                                Spacer(modifier = Modifier.width(10.dp))

                                                Card(
                                                    modifier = Modifier,
                                                    shape = RoundedCornerShape(10.dp),
                                                    colors = CardDefaults.cardColors(
                                                        containerColor = White
                                                    ),
                                                    elevation = CardDefaults.cardElevation(1.dp)
                                                ) {
                                                    Column(
                                                        modifier = Modifier.padding(start = 29.dp, top = 10.dp, bottom = 14.dp, end = 29.dp),
                                                        horizontalAlignment = Alignment.CenterHorizontally                                            ) {
                                                        Image(
                                                            modifier = Modifier.size(62.dp),
                                                            contentDescription = null,
                                                            painter = painterResource(resource = Res.drawable.ic_gallery))

                                                        Text(text = "Empty")
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

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
                                                if (it.isNotEmpty()) {
                                                    barCode = it.toLong()
                                                }
                                            },
                                            keyboardType = KeyboardType.Number
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Column {
                                        Text(text = "Product Name")

                                        Spacer(modifier = Modifier.height(5.dp))

                                        TextInputDefault(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = name.orEmpty(),
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
                                            text = price.orEmpty(),
                                            placeholder = "Enter Price",
                                            onValueChange = {
                                                price = it
                                            },
                                            isInputPrice = true,
                                            keyboardType = KeyboardType.Decimal
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Column {
                                        Text(text = "Discount")

                                        Spacer(modifier = Modifier.height(5.dp))

                                        TextInputDefault(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = discount.orEmpty(),
                                            placeholder = "Enter Discount",
                                            onValueChange = {
                                                discount = it
                                            },
                                            keyboardType = KeyboardType.Number
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Column {
                                        Text(text = "Category")

                                        Spacer(modifier = Modifier.height(5.dp))

//                                        TextInputDefault(
//                                            modifier = Modifier.fillMaxWidth(),
//                                            text = category,
//                                            placeholder = "Select Category",
//                                            onValueChange = {
//                                                category = it
//                                            },
//                                            keyboardType = KeyboardType.Number
//                                        )

                                        Button(
                                            modifier = Modifier.fillMaxWidth(),
                                            shape = Shapes.medium,
                                            colors = ButtonDefaults.buttonColors(containerColor = White),
                                            border = BorderStroke(1.dp, color = ColorE4E4E4),
                                            contentPadding = PaddingValues(start = 0.dp, top = 5.dp, bottom = 5.dp, end = 0.dp),
                                            onClick = {
                                                isSelectCategory = !isSelectCategory
                                                onEvent(InventoryEvent.GetMenu())
                                            }
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth().padding(10.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Row {
                                                    menuSelected?.image?.let {
                                                        Image(
                                                            modifier = Modifier.size(24.dp).clip(shape = RoundedCornerShape(5.dp)),
                                                            bitmap = it.toImageBitmap(),
                                                            contentDescription = null,
                                                            contentScale = ContentScale.Crop
                                                        )
                                                    }

                                                    Spacer(modifier = Modifier.width(5.dp))

                                                    menuSelected?.name?.let {
                                                        Text(text = it, color = Black)
                                                    }

                                                }

                                                Image(
                                                    painter = painterResource(resource = Res.drawable.ic_down),
                                                    colorFilter = ColorFilter.tint(color = Black),
                                                    contentDescription = null
                                                )
                                            }

                                        }

                                        Spacer(modifier = Modifier.height(5.dp))

                                        Card(
                                            modifier = Modifier.fillMaxWidth(),
                                            shape = RoundedCornerShape(10.dp),
                                            colors = CardDefaults.cardColors(
                                                containerColor = White
                                            ),
                                            elevation = CardDefaults.cardElevation(1.dp)
                                        ) {
                                            AnimatedVisibility(
                                                visible = isSelectCategory
                                            ) {
                                                LazyRow(
                                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                                    contentPadding = PaddingValues(10.dp)
                                                ) {
                                                    state.menu?.let {
                                                        itemsIndexed(it) { _, item ->
                                                            CategoryItem(
                                                                modifier = Modifier.fillMaxWidth()
                                                                    .clickable {
                                                                        menuSelected = item
                                                                        isSelectCategory = false
                                                                },
                                                                category = item
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    }


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
                                                                barImage = ""
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
                                                                    byteImage = null
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
                                                                    byteImage = null
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
                                                                    byteImage = null
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
                            state = state
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
                    startBarCodeScan = false

                    if (it.isEmpty()) return@QrScannerScreen
                    barCode = it.toLong()
                    searchViewModel.onSearchClick(it)
                }
            )
        }
    }

}