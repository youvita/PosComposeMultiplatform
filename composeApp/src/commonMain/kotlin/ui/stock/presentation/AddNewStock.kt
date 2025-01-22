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
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.Divider
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap
import core.data.Status
import core.scanner.QrScannerScreen
import core.theme.Black
import core.theme.Color488BFF
import core.theme.ColorDDE3F9
import core.theme.ColorE1E1E1
import core.theme.ColorE4E4E4
import core.theme.ColorF1F1F1
import core.theme.PrimaryColor
import core.theme.Shapes
import core.theme.White
import core.utils.DialogError
import core.utils.DialogFullScreen
import core.utils.DialogLoading
import core.utils.DialogPreview
import core.utils.DialogSuccess
import core.utils.ImageLoader
import core.utils.LabelInputRequire
import core.utils.LineWrapper
import core.utils.PrimaryButton
import core.utils.TextInputDefault
import core.utils.dashedBorder
import core.utils.getCurrentDateTime
import mario.presentation.MarioEvent
import mario.presentation.MarioState
import mario.presentation.component.CreateItem
import mario.presentation.component.CreateMenu
import mario.presentation.component.EditMenu
import menu.domain.model.MenuModel
import menu.presentation.component.CategoryItem
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.arrow
import poscomposemultiplatform.composeapp.generated.resources.ic_arrow_next
import poscomposemultiplatform.composeapp.generated.resources.ic_camera
import poscomposemultiplatform.composeapp.generated.resources.ic_down
import poscomposemultiplatform.composeapp.generated.resources.ic_gallery
import poscomposemultiplatform.composeapp.generated.resources.ic_next
import poscomposemultiplatform.composeapp.generated.resources.ic_plus
import poscomposemultiplatform.composeapp.generated.resources.ic_profie
import poscomposemultiplatform.composeapp.generated.resources.ic_scan_barcode
import poscomposemultiplatform.composeapp.generated.resources.ic_scanner
import poscomposemultiplatform.composeapp.generated.resources.ic_upload
import ui.stock.domain.model.Product
import ui.stock.domain.model.ProductMenu
import ui.stock.domain.model.ProductStock

@OptIn(ExperimentalResourceApi::class, ExperimentalLayoutApi::class)
@Composable
fun AddNewStock(
    productItem: ProductMenu? = null,
    state: InventoryState,
    searchViewModel: SearchEngineViewModel,
    onEvent: (InventoryEvent) -> Unit,
    callback: () -> Unit,
    marioState: MarioState? = null,
    marioEvent: (MarioEvent) -> Unit = {}
) {
    val formPadding = 36.dp
    val searchState = searchViewModel.state.collectAsState().value
//    val stockState = inventoryViewModel.stateProductStock.collectAsState().value

    val isNew by remember { mutableStateOf(productItem?.productId == null) }
    var startBarCodeScan by remember { mutableStateOf(false) }
    var barCode by remember { mutableStateOf(productItem?.productId ?: 0) }
    var barImage by remember { mutableStateOf("") }
    var name by remember { mutableStateOf(productItem?.name) }
    var price by remember { mutableStateOf(productItem?.price) }
    var qty by remember { mutableStateOf(productItem?.qty) }
    var discount by remember { mutableStateOf(productItem?.discount) }
    var category by remember { mutableStateOf("") }
//    var stockBox by remember { mutableStateOf(Long.MIN_VALUE) }
//    var stockQty by remember { mutableStateOf(Long.MIN_VALUE) }
    var byteImage by remember { mutableStateOf(productItem?.image) }
    var indexSelected by remember { mutableStateOf(-1) }
    var isSelectCategory by remember { mutableStateOf(productItem != null) }
    var menuSelected by remember { mutableStateOf<MenuModel?>(MenuModel(menuId = productItem?.menuId, name = productItem?.menuName, image = productItem?.menuImage)) }
    var requiredField by remember { mutableStateOf(true) }
    var productImageSelected by remember { mutableStateOf(false.takeIf { byteImage?.isEmpty() == true } ?: true) }

    val scope = rememberCoroutineScope()
    val singleImagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = {
            it.firstOrNull()?.let { image ->
                indexSelected = 0
                byteImage = image
                productImageSelected = true
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

    LaunchedEffect(true) {
        onEvent(InventoryEvent.GetMenu())
    }

    //check require field
    LaunchedEffect(isSelectCategory,name,barCode,productImageSelected,price,qty){
        if (productImageSelected) indexSelected = 0

        requiredField = required(
            sku = barCode,
            name = name,
            categorySelected = isSelectCategory,
            productImageSelected = productImageSelected,
            price = price,
            qty = qty,
        )
    }

    var showAddMenuDialog by remember { mutableStateOf(false) }
    var required by remember { mutableStateOf(false) }
    var showAddItem by remember { mutableStateOf(false) }
    var selectedItemIndex by remember { mutableIntStateOf(-1) }
    var selectedMenuIndex by remember { mutableIntStateOf(0) }

    var menuList by remember {
        mutableStateOf<List<MenuModel>>(arrayListOf(MenuModel(menuId = 0, name = "All")))
    }

    //show dialog for create Menu
    if(showAddMenuDialog){
        DialogPreview(
            title = "Create New Menu",
            onClose = { showAddMenuDialog = false },
            onDismissRequest = { showAddMenuDialog = false }
        ){
            CreateMenu{ menu, require ->
                required = require
                if(!require){
                    marioEvent(MarioEvent.AddMenuEvent(menu))
                    onEvent(InventoryEvent.GetMenu())
                    showAddMenuDialog = false
                }
            }
        }
    }

//    //show dialog for create Item
//    if(showAddItem){
//        DialogFullScreen(
//            title = "Create New Item",
//            onClose = { showAddItem = false },
//            onDismissRequest = { showAddItem = false },
//        ){
//            CreateItem(menuList[selectedMenuIndex]){ item, require ->
//                required = require
//                if(!require){
//                    marioEvent(MarioEvent.AddItemEvent(item))
//                    showAddItem = false
//                }
//            }
//        }
//    }


    //show dialog alert required fields
    if(required){
        DialogError(
            message = "Please fill the required fields",
            onClose = {
                required = false
            },
            onDismissRequest = {
                required = false
            }
        )
    }

//    if(marioState?.status == Status.SUCCESS){
//        onEvent(InventoryEvent.GetMenu())
//    }

    if(marioState?.status == Status.LOADING){
        DialogLoading()
    }

    if(marioState?.status == Status.ERROR){
        DialogError(
            title = "Error",
            message = marioState.message,
            onClose = {
                marioEvent(MarioEvent.ClearEvent)
            },
            onDismissRequest = {
                marioEvent(MarioEvent.ClearEvent)
            }
        )
    }

    Scaffold(
        modifier = Modifier
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

                    LineWrapper()

                    AnimatedVisibility(
                        visible = tabIndex == 0,
                        enter = fadeIn() + slideInHorizontally(),
                        exit = fadeOut() + slideOutHorizontally()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(start = formPadding, end = formPadding)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Spacer(modifier = Modifier.height(15.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Text(text = buildAnnotatedString {
                                    append("Upload Product Image")
                                    withStyle(
                                        SpanStyle(
                                            color = Color.Red
                                        )
                                    ) {
                                        append(" ")
                                        append("*")
                                    }
                                })

                                PrimaryButton(
                                    text = "Save Product".takeIf { isNew } ?: "Update Product",
                                    icon = Res.drawable.ic_plus,
                                    onClick = {
                                        if (isNew) {
                                            val product = Product(
                                                menuId = menuSelected?.menuId,
                                                productId = barCode,
                                                name = name,
                                                image = byteImage,
                                                imageUrl = barImage,
                                                qty = qty,
                                                price = price,
                                                discount = discount
                                            )
                                            if (requiredField){
                                                onEvent(InventoryEvent.AddProduct(product))
                                                callback()
                                            } else {
                                                required = true
                                            }
                                        } else {
                                            val product = Product(
                                                menuId = menuSelected?.menuId,
                                                productId = barCode,
                                                name = name,
                                                image = byteImage,
                                                imageUrl = barImage,
                                                qty = qty,
                                                price = price,
                                                discount = discount
                                            )
                                            if (requiredField){
                                                onEvent(InventoryEvent.UpdateProduct(product))
                                                callback()
                                            } else {
                                                required = true
                                            }
                                        }
                                    }
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Box(
                                modifier = Modifier
                                    .background(color = ColorF1F1F1, shape = Shapes.medium)
                                    .dashedBorder(1.dp, ColorE4E4E4, 10.dp), contentAlignment = Alignment.Center) {

                                FlowRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(10.dp),
                                ) {
                                    Column(
                                        modifier = Modifier.padding(20.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(text = buildAnnotatedString {
                                            withStyle(
                                                SpanStyle(
                                                    color = PrimaryColor
                                                )
                                            ) {
                                                append("※")
                                                append(" ")
                                            }
                                            append("Choose from library")
                                        })

                                        Spacer(modifier = Modifier.height(17.dp))

                                        Card(
                                            modifier = Modifier
                                                .size(width = 120.dp, height = 120.dp)
                                                .clip(Shapes.medium)
                                                .clickable {
                                                    singleImagePicker.launch()
                                                },
                                            shape = Shapes.medium,
                                            colors = CardDefaults.cardColors(
                                                containerColor = White
                                            ),
                                            elevation = CardDefaults.cardElevation(2.dp),
                                            border = BorderStroke(2.dp.takeIf { indexSelected == 0 } ?: 0.dp, PrimaryColor.takeIf { indexSelected == 0 } ?: ColorE4E4E4)
                                        ) {
                                            if (byteImage != null) {
                                                byteImage?.let {
                                                    Image(
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .alpha(0.5f.takeIf { indexSelected == 0 } ?: 1f),
                                                        bitmap = it.toImageBitmap(),
                                                        contentDescription = null,
                                                        contentScale = ContentScale.FillBounds
                                                    )
                                                }
                                            } else {
                                                Column(
                                                    modifier = Modifier.fillMaxSize(),
                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                    verticalArrangement = Arrangement.Center
                                                ) {
                                                    Image(
                                                        modifier = Modifier.size(62.dp),
                                                        contentDescription = null,
                                                        painter = painterResource(resource = Res.drawable.ic_upload)
                                                    )

                                                    Text(text = "Upload", color = Black)
                                                }
                                            }
                                        }
                                    }

                                    Box(
                                        modifier = Modifier
                                            .height(185.dp)
                                            .width(1.dp)
                                            .background(color = ColorE1E1E1)
                                    )

                                    Row(
                                        modifier = Modifier.padding(start = 40.dp, top = 20.dp, bottom = 20.dp, end = 20.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Column(
                                            modifier = Modifier
                                        ) {
                                            Text(text = buildAnnotatedString {
                                                withStyle(
                                                    SpanStyle(
                                                        color = PrimaryColor
                                                    )
                                                ) {
                                                    append("※")
                                                    append(" ")
                                                }
                                                append("Scan barcode product of image for fast")
                                            })

                                            Spacer(modifier = Modifier.height(17.dp))

                                            Row(
                                                modifier = Modifier.fillMaxWidth(0.3f),
                                                verticalAlignment = Alignment.Top,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Card(
                                                    modifier = Modifier
                                                        .size(width = 120.dp, height = 120.dp)
                                                        .clip(Shapes.medium)
                                                        .clickable {
                                                            startBarCodeScan = true
                                                        },
                                                    shape = Shapes.medium,
                                                    colors = CardDefaults.cardColors(
                                                        containerColor = White
                                                    ),
                                                    elevation = CardDefaults.cardElevation(2.dp)
                                                ) {
                                                    Column(
                                                        modifier = Modifier.fillMaxSize(),
                                                        horizontalAlignment = Alignment.CenterHorizontally,
                                                        verticalArrangement = Arrangement.Center
                                                    ) {
                                                        Image(
                                                            modifier = Modifier.size(45.dp),
                                                            contentDescription = null,
                                                            painter = painterResource(resource = Res.drawable.ic_scanner),
                                                            colorFilter = ColorFilter.tint(Color488BFF)
                                                        )

                                                        Text(text = "Scan", color = Black)
                                                    }
                                                }

                                                Spacer(modifier = Modifier.width(20.dp))

                                                Image(
                                                    modifier = Modifier.size(107.dp),
                                                    contentDescription = null,
                                                    painter = painterResource(resource = Res.drawable.ic_scan_barcode))
                                            }
                                        }

                                        Spacer(modifier = Modifier.width(21.dp))

                                        Image(
                                            modifier = Modifier.padding(top = 30.dp),
                                            contentDescription = null,
                                            painter = painterResource(resource = Res.drawable.ic_arrow_next))

                                    }

                                    Column(
                                        modifier = Modifier.padding(start = 21.dp, top = 20.dp, bottom = 20.dp)
                                    ) {
                                        Text(text = "There are 3 images for recommend")

                                        Spacer(modifier = Modifier.height(17.dp))

                                        Row(
                                            modifier = Modifier,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Card(
                                                modifier = Modifier.size(width = 120.dp, height = 120.dp),
                                                shape = RoundedCornerShape(10.dp),
                                                border = BorderStroke(2.dp.takeIf { indexSelected == 1 } ?: 0.dp, PrimaryColor.takeIf { indexSelected == 1 } ?: White),
                                                colors = CardDefaults.cardColors(
                                                    containerColor = ColorE4E4E4.takeIf { indexSelected == 1 } ?: White
                                                ),
                                                elevation = CardDefaults.cardElevation(1.dp)
                                            ) {
                                                if (searchState.data?.items == null) {
                                                    Column(
                                                        modifier = Modifier.fillMaxSize(),
                                                        horizontalAlignment = Alignment.CenterHorizontally,
                                                        verticalArrangement = Arrangement.Center
                                                    ) {
                                                        Image(
                                                            modifier = Modifier.size(62.dp),
                                                            contentDescription = null,
                                                            painter = painterResource(resource = Res.drawable.ic_gallery))

                                                        Text(text = "Empty")
                                                    }
                                                } else {
                                                    searchState.data?.items?.let {
                                                        it[0].image?.thumbnailLink?.let { url ->
                                                            ImageLoader(
                                                                modifier = Modifier
                                                                    .alpha(0.5f.takeIf { indexSelected == 1 } ?: 1f)
                                                                    .clickable {
                                                                        indexSelected = 1
                                                                        barImage = url
                                                                        byteImage = null
                                                                        productImageSelected = true
                                                                    },
                                                                image = url
                                                            )
                                                        }
                                                    }
                                                }
                                            }

                                            Spacer(modifier = Modifier.width(10.dp))

                                            Card(
                                                modifier = Modifier.size(width = 120.dp, height = 120.dp),
                                                shape = RoundedCornerShape(10.dp),
                                                border = BorderStroke(2.dp.takeIf { indexSelected == 2 } ?: 0.dp, PrimaryColor.takeIf { indexSelected == 2 } ?: White),
                                                colors = CardDefaults.cardColors(
                                                    containerColor = ColorE4E4E4.takeIf { indexSelected == 2 } ?: White
                                                ),
                                                elevation = CardDefaults.cardElevation(2.dp)
                                            ) {
                                                if (searchState.data?.items == null) {
                                                    Column(
                                                        modifier = Modifier.fillMaxSize(),
                                                        horizontalAlignment = Alignment.CenterHorizontally,
                                                        verticalArrangement = Arrangement.Center
                                                    ) {
                                                        Image(
                                                            modifier = Modifier.size(62.dp),
                                                            contentDescription = null,
                                                            painter = painterResource(resource = Res.drawable.ic_gallery))

                                                        Text(text = "Empty")
                                                    }
                                                } else {
                                                    searchState.data?.items?.let {
                                                        it[1].image?.thumbnailLink?.let { url ->
                                                            ImageLoader(
                                                                modifier = Modifier
                                                                    .alpha(0.5f.takeIf { indexSelected == 2 } ?: 1f)
                                                                    .clickable {
                                                                        indexSelected = 2
                                                                        barImage = url
                                                                        byteImage = null
                                                                        productImageSelected = true
                                                                    },
                                                                image = url
                                                            )
                                                        }
                                                    }
                                                }
                                            }

                                            Spacer(modifier = Modifier.width(10.dp))

                                            Card(
                                                modifier = Modifier.size(width = 120.dp, height = 120.dp),
                                                shape = RoundedCornerShape(10.dp),
                                                border = BorderStroke(2.dp.takeIf { indexSelected == 3 } ?: 0.dp, PrimaryColor.takeIf { indexSelected == 3 } ?: White),
                                                colors = CardDefaults.cardColors(
                                                    containerColor = ColorE4E4E4.takeIf { indexSelected == 3 } ?: White
                                                ),
                                                elevation = CardDefaults.cardElevation(2.dp)
                                            ) {
                                                if (searchState.data?.items == null) {
                                                    Column(
                                                        modifier = Modifier.fillMaxSize(),
                                                        horizontalAlignment = Alignment.CenterHorizontally,
                                                        verticalArrangement = Arrangement.Center
                                                    ) {
                                                        Image(
                                                            modifier = Modifier.size(62.dp),
                                                            contentDescription = null,
                                                            painter = painterResource(resource = Res.drawable.ic_gallery))

                                                        Text(text = "Empty")
                                                    }
                                                } else {
                                                    searchState.data?.items?.let {
                                                        it[2].image?.thumbnailLink?.let { url ->
                                                            ImageLoader(
                                                                modifier = Modifier
                                                                    .alpha(0.5f.takeIf { indexSelected == 3 } ?: 1f)
                                                                    .clickable {
                                                                        indexSelected = 3
                                                                        barImage = url
                                                                        byteImage = null
                                                                        productImageSelected = true
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

                            Spacer(modifier = Modifier.height(20.dp))

                            // Category
                            Column {

                                Text(text = buildAnnotatedString {
                                    append("Select Product Category")
                                    withStyle(
                                        SpanStyle(
                                            color = Color.Red
                                        )
                                    ) {
                                        append(" ")
                                        append("*")
                                    }
                                })

                                Spacer(modifier = Modifier.height(14.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Card(
                                        modifier = Modifier
                                            .size(80.dp)
                                            .clip(Shapes.medium)
                                            .clickable {
                                                showAddMenuDialog = true
                                            },
                                        shape = Shapes.medium,
                                        colors = CardDefaults.cardColors(PrimaryColor),
                                        elevation = CardDefaults.cardElevation(2.dp)
                                    ){
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ){
                                            Icon(
                                                imageVector = Icons.Rounded.Add,
                                                contentDescription = "",
                                                tint = Color(0xFFFFFFFF)
                                            )
                                        }
                                    }

                                    if (state.menu != null) {
                                        LazyRow(
                                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                                            contentPadding = PaddingValues(10.dp)
                                        ) {
                                            state.menu?.let {
                                                itemsIndexed(it) { index, item ->
                                                    Card(
                                                        modifier = Modifier,
                                                        shape = Shapes.medium,
                                                        colors = CardDefaults.cardColors(White),
                                                        border = BorderStroke(2.dp.takeIf { menuSelected?.menuId == item.menuId } ?: 0.dp, PrimaryColor.takeIf { menuSelected?.menuId == item.menuId } ?: White),
                                                        elevation = CardDefaults.cardElevation(2.dp)
                                                    ) {
                                                        Box(modifier = Modifier
                                                            .clip(Shapes.medium)
                                                            .clickable {
                                                                menuSelected = item
                                                                isSelectCategory = index >= 0
                                                        }) {
                                                            CategoryItem(
                                                                modifier = Modifier.fillMaxWidth(),
                                                                category = item
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                            }

                            // Fields input
                            Column(
                                modifier = Modifier.fillMaxWidth(0.7f)
                            ) {
                                Column {
                                    Text(text = buildAnnotatedString {
                                        append("Product SKU")
                                        withStyle(
                                            SpanStyle(
                                                color = Color.Red
                                            )
                                        ) {
                                            append(" ")
                                            append("*")
                                        }
                                    })

                                    Spacer(modifier = Modifier.height(5.dp))

                                    TextInputDefault(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = barCode.toString().takeIf { barCode > 0 } ?: "",
                                        placeholder = "Barcode",
                                        onValueChange = {
                                            barCode = if (it.isNotEmpty()) it.toLong() else 0
                                        },
                                        keyboardType = KeyboardType.Number
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))


                                Row {
                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(text = buildAnnotatedString {
                                            append("Product Name")
                                            withStyle(
                                                SpanStyle(
                                                    color = Color.Red
                                                )
                                            ) {
                                                append(" ")
                                                append("*")
                                            }
                                        })

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

                                    Spacer(modifier = Modifier.width(18.dp))

                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(text = buildAnnotatedString {
                                            append("Price($)")
                                            withStyle(
                                                SpanStyle(
                                                    color = Color.Red
                                                )
                                            ) {
                                                append(" ")
                                                append("*")
                                            }
                                        })

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
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Row {
                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {

                                        Text(text = buildAnnotatedString {
                                            append("Quality")
                                            withStyle(
                                                SpanStyle(
                                                    color = Color.Red
                                                )
                                            ) {
                                                append(" ")
                                                append("*")
                                            }
                                        })

                                        Spacer(modifier = Modifier.height(5.dp))

                                        TextInputDefault(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = qty.orEmpty(),
                                            placeholder = "Enter Quality",
                                            onValueChange = {
                                                qty = it
                                            },
                                            keyboardType = KeyboardType.Number
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(18.dp))

                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
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
                                }
                            }

                            Spacer(modifier = Modifier.height(36.dp))
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
private fun required(
    sku: Long,
    name: String?,
    categorySelected: Boolean,
    productImageSelected: Boolean,
    price: String?,
    qty: String?
): Boolean{
    return (sku > 0 && name?.isNotEmpty() == true) && categorySelected && productImageSelected && price?.isNotEmpty() == true && qty?.isNotEmpty() == true
}