package menu.presentation

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.theme.PrimaryColor
import core.theme.Shapes
import core.theme.White
import core.utils.RedRippleTheme
import customer.presentation.CustomerEvent
import customer.presentation.CustomerState
import history.presentation.HistoryEvent
import menu.presentation.component.utils.EmptyBox
import menu.domain.model.MenuModel
import menu.presentation.component.ItemView
import menu.presentation.component.MenuCategoryForm
import menu.presentation.component.OrderBillsForm
import setting.domain.model.ItemModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrderScreen(
    orderState: OrderState? = null,
    customerState: CustomerState? = null,
    customerEvent: (CustomerEvent) -> Unit = {},
    orderEvent: (OrderEvent) -> Unit = {},
) {

    var selectedItem by rememberSaveable { mutableIntStateOf(-1) }
    var list by rememberSaveable { mutableStateOf<List<ItemModel>>(emptyList()) }
    var selectedMenuIndex by rememberSaveable { mutableIntStateOf(0) }

    //add first category menu
    val categoryMenuList = ArrayList<MenuModel>()
    categoryMenuList.add(MenuModel(menuId = 0, name = "All"))

    orderState?.menus?.map {
        categoryMenuList.add(it)
    }

    var menuList by remember {
        mutableStateOf<List<MenuModel>>(arrayListOf(MenuModel(menuId = 0, name = "All")))
    }

    LaunchedEffect(orderState?.menus){
        menuList = categoryMenuList
    }

    LaunchedEffect(Unit){
        orderEvent(OrderEvent.GetMenusEvent)
    }

    Scaffold(
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(Modifier.weight(2f)) {
                    Column(Modifier.padding(10.dp)) {

                        // category menu path
                        MenuCategoryForm(menuList){ index ->
                            selectedMenuIndex = index
                            orderEvent(OrderEvent.MenuSelectEvent(menuList[index]))
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

                        if(list.isEmpty()){
                            EmptyBox(modifier = Modifier.padding(bottom = 150.dp))
                        }
                        else{
                            LazyVerticalStaggeredGrid(
                                columns = StaggeredGridCells.Fixed(3),
                                verticalItemSpacing = 8.dp,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxSize()
                            ){
                                itemsIndexed(list){ index, item ->
                                    CompositionLocalProvider(LocalRippleTheme provides RedRippleTheme){
                                        Box(modifier = Modifier
                                            .animateItemPlacement()
                                            .clickable(
                                                indication = null,
                                                interactionSource = remember { MutableInteractionSource() }
                                            ) {
                                                selectedItem = if (selectedItem == index) {
                                                    -1
                                                } else index
                                            }
                                            .then(
                                                if (selectedItem == index) {
                                                    Modifier
                                                        .background(
                                                            color = White,
                                                            shape = Shapes.medium
                                                        )
                                                        .border(
                                                            1.dp,
                                                            color = PrimaryColor,
                                                            shape = Shapes.medium
                                                        )
                                                } else {
                                                    Modifier
                                                }
                                            )
                                        ){
                                            ItemView(
                                                item = item,
                                                selected = selectedItem == index,
                                                orderEvent = orderEvent
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Box(modifier = Modifier.weight(1f)){
                    OrderBillsForm(
                        orderState = orderState,
                        orderEvent = orderEvent,
                        customerState = customerState,
                        customerEvent = customerEvent,
                    )
                }
            }
        }
    }
}
