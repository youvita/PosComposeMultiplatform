package ui.stock.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import core.data.Status
import core.theme.ColorDDE3F9
import core.theme.PrimaryColor
import core.theme.Shapes
import core.theme.White
import core.utils.DialogError
import core.utils.DialogFullScreen
import core.utils.DialogLoading
import core.utils.DialogPreview
import core.utils.DialogSuccess
import core.utils.PrimaryButton
import core.utils.RedRippleTheme
import mario.presentation.MarioEvent
import mario.presentation.MarioState
import mario.presentation.component.CreateItem
import mario.presentation.component.CreateMenu
import mario.presentation.component.EditItemCollapse
import mario.presentation.component.EditMenu
import menu.domain.model.MenuModel
import menu.presentation.component.CategoryItem
import setting.domain.model.ItemModel
import ui.stock.domain.model.Product

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddNewProduct(
    marioState: MarioState? = null,
    data: List<Product>? = null,
    marioEvent: (MarioEvent) -> Unit = {},
    callBack: () -> Unit = {},
    menuClick: (Long) -> Unit = {}
) {

    var menuOptionSize by remember { mutableStateOf(Size(74.0f, 0.0f)) }
    var required by remember { mutableStateOf(false) }
    var showAddMenuDialog by remember { mutableStateOf(false) }
    var showEditMenuDialog by remember { mutableStateOf(false) }
    var showEditMenu by remember { mutableStateOf(false) }
    var showAddItem by remember { mutableStateOf(false) }
    var selectedItemIndex by remember { mutableIntStateOf(-1) }
    var selectedMenuIndex by remember { mutableIntStateOf(0) }

    //add first category menu
    val categoryMenuList = ArrayList<MenuModel>()
    categoryMenuList.add(MenuModel(menuId = 0, name = "All"))

    marioState?.menus?.map {
        categoryMenuList.add(it)
    }

    var menuList by remember {
        mutableStateOf<List<MenuModel>>(arrayListOf(MenuModel(menuId = 0, name = "All")))
    }


    LaunchedEffect(marioState?.menus){
        menuList = categoryMenuList
        if (categoryMenuList.size == 1){
            showEditMenu = false
        }
    }

    var list by remember { mutableStateOf<List<ItemModel>>(emptyList()) }
    LaunchedEffect(marioState?.items){
        list = marioState?.items?: arrayListOf()
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
                    showAddMenuDialog = false
                }
            }
        }
    }

    //show edit menu dialog
    if(showEditMenuDialog){
        DialogPreview(
            title = "Edit Menu",
            onClose = { showEditMenuDialog = false },
            onDismissRequest = { showEditMenuDialog = false }
        ){
            EditMenu(
                menuModel = menuList[selectedMenuIndex],
                onUpdate = { menu, require ->
                    required = require
                    if(!require){
                        marioEvent(MarioEvent.UpdateMenuEvent(menu))
                        showEditMenuDialog = false
                    }
                },
                onDelete = {
                    val menuId = it.menuId
                    if(menuId != null){
                        marioEvent(MarioEvent.DeleteMenuEvent(menuId))
                        showEditMenuDialog = false
                        selectedMenuIndex = 0
                        showEditMenu = false
                    }
                }
            )
        }
    }


    //show dialog for create Item
    if(showAddItem){
        DialogFullScreen(
            title = "Create New Item",
            onClose = { showAddItem = false },
            onDismissRequest = { showAddItem = false },
        ){
            CreateItem(menuList[selectedMenuIndex]){ item, require ->
                required = require
                if(!require){
                    marioEvent(MarioEvent.AddItemEvent(item))
                    showAddItem = false
                }
            }
        }
    }


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

    if(marioState?.status == Status.SUCCESS){
        DialogSuccess()
        marioEvent(MarioEvent.ClearEvent)
        selectedItemIndex = -1
        selectedMenuIndex = 0
    }

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
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp),
        containerColor = Color.Transparent
    ){ paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ){
            Row(modifier = Modifier.fillMaxSize()){
                Column(Modifier.weight(1f)){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PrimaryButton(
                            text = "New Product",
                            icon = Icons.Rounded.Add,
                            callBack = callBack
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Box {
                        //category menu path
                        //Display all category menu
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            itemsIndexed(menuList) { index, item ->
                                CompositionLocalProvider(LocalRippleTheme provides RedRippleTheme) {
                                    Box(
                                        modifier = Modifier
                                            .clip(Shapes.medium)
                                            .clickable {
                                                selectedMenuIndex = index
                                                selectedItemIndex = -1
                                                showEditMenu = index != 0
//                                                marioEvent(MarioEvent.GetItemsEvent(0))
                                                item.menuId?.let {
                                                    menuClick(it)
                                                }

                                            }
                                            .then(
                                                if (selectedMenuIndex == index) {
                                                    Modifier
                                                        .background(
                                                            color = ColorDDE3F9,
                                                            shape = Shapes.medium
                                                        )
                                                        .border(
                                                            2.dp,
                                                            color = PrimaryColor,
                                                            shape = Shapes.medium
                                                        )
                                                } else {
                                                    Modifier
                                                }
                                            )
                                    ){
                                        CategoryItem(
                                            category = item,
                                            color = if(selectedMenuIndex == index) ColorDDE3F9 else White
                                        )
                                    }
                                }
                            }

                            item{
                                Spacer(modifier = Modifier
                                    .width(with(LocalDensity.current) {
                                        menuOptionSize.width.toDp()
                                    })
                                )
                            }
                        }


                        Row(
                            modifier = Modifier
                                .height(80.dp)
                                .align(Alignment.CenterEnd)
                                .onGloballyPositioned { coordinates ->
                                    //This value is used to assign to the DropDown the same width
                                    menuOptionSize = coordinates.size.toSize()
                                },
                        ){

                            //Button add category menu
                            Card(
                                modifier = Modifier.size(80.dp),
                                shape = Shapes.medium,
                                colors = CardDefaults.cardColors(PrimaryColor),
                                elevation = CardDefaults.cardElevation(2.dp)
                            ){
                                IconButton(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    onClick = {
                                        showAddMenuDialog = true
                                        selectedItemIndex = -1
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Add,
                                        contentDescription = "",
                                        tint = Color(0xFFFFFFFF)
                                    )
                                }
                            }

                            //Button setting for edit category menu
                            AnimatedVisibility(visible = showEditMenu) {
                                Row {
                                    Spacer(modifier = Modifier.width(10.dp))

                                    Card(
                                        modifier = Modifier.size(80.dp),
                                        shape = Shapes.medium,
                                        colors = CardDefaults.cardColors(PrimaryColor),
                                        elevation = CardDefaults.cardElevation(2.dp)
                                    ){
                                        IconButton(
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            onClick = {
                                                showEditMenuDialog = true
                                                selectedItemIndex = -1
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Rounded.Settings,
                                                contentDescription = "",
                                                tint = Color(0xFFFFFFFF)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    //item menu path
                    val size = if(menuList.isEmpty()) "0" else menuList[selectedMenuIndex].name
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = "${list.size} $size in Menu",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    data?.let { product ->

                            for (i in product.indices) {
                                println(">>>>fffff ${product[i].name}")
                            }

                        ProductInformation(
                            data = data
                        )
                    }


//                    LazyVerticalStaggeredGrid(
//                        columns = StaggeredGridCells.Fixed(1),
//                        verticalItemSpacing = 8.dp,
//                        horizontalArrangement = Arrangement.spacedBy(10.dp),
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(vertical = 20.dp)
//                    ){
//                        item {
////                            Card(
////                                modifier = Modifier.fillMaxHeight(),
////                                shape = Shapes.medium,
////                                colors = CardDefaults.cardColors(PrimaryColor),
////                                elevation = CardDefaults.cardElevation(2.dp)
////                            ){
////                                IconButton(
////                                    modifier = Modifier
////                                        .padding(24.dp)
////                                        .fillMaxSize(),
////                                    onClick = {
////                                        showAddItem = true
////                                        selectedItemIndex = -1
////                                    }
////                                ) {
////                                    Icon(
////                                        imageVector = Icons.Rounded.Add,
////                                        contentDescription = "",
////                                        tint = Color(0xFFFFFFFF)
////                                    )
////                                }
////                            }
//                        }
//
//
//                        productState?.data?.let {
//                            itemsIndexed(it){ index, item ->
//                                CompositionLocalProvider(LocalRippleTheme provides RedRippleTheme){
//                                    Box(modifier = Modifier
//                                        .animateItemPlacement()
//                                        .clickable(
//                                            indication = null,
//                                            interactionSource = remember { MutableInteractionSource() }
//                                        ) {
//                                            selectedItemIndex = if (selectedItemIndex == index) {
//                                                -1
//                                            } else index
//                                        }
//                                        .then(
//                                            if (selectedItemIndex == index) {
//                                                Modifier
//                                                    .background(
//                                                        color = White,
//                                                        shape = Shapes.medium
//                                                    )
//                                                    .border(
//                                                        1.dp,
//                                                        color = PrimaryColor,
//                                                        shape = Shapes.medium
//                                                    )
//                                            } else {
//                                                Modifier
//                                            }
//                                        )
//                                    ){
////                                        EditItemCollapse(
////                                            item = item,
////                                            selected = selectedItemIndex == index
////                                        ){
////                                            item.bookmark = it
////                                            marioEvent(MarioEvent.BookmarkItemEvent(item))
////                                        }
//                                    }
//                                }
//                            }
//                        }
//
//                    }
                }

                Column(
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .weight(if (selectedItemIndex == -1) 0.01f else 1f)
                ){
                    AnimatedVisibility(visible = selectedItemIndex > -1 || list.isNotEmpty()) {
                        val item = if(selectedItemIndex == -1) null else list[selectedItemIndex]
//                        UpdateItemInformation(
//                            menu = menuList[selectedMenuIndex],
//                            item = item,
//                            marioEvent = marioEvent
//                        )
                    }
                }
            }
        }
    }
}