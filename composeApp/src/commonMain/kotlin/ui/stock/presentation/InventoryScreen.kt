package ui.stock.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.theme.PrimaryColor
import core.theme.White
import mario.presentation.MarioViewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_back
import ui.stock.domain.model.ProductMenu

@OptIn(ExperimentalResourceApi::class)
class InventoryScreen: Screen, KoinComponent {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val searchViewModel = get<SearchEngineViewModel>()
        val inventoryViewModel = get<InventoryViewModel>()
        val marioViewModel = get<MarioViewModel>()

        var addNewProduct by remember { mutableStateOf(false) }
        var adjustStock by remember { mutableStateOf(false) }
        var previousScreen by remember { mutableStateOf(listOf("Super Mario (admin)")) }
        var currentScreen by remember { mutableStateOf(listOf("Product & Stock")) }
        var productItem by remember { mutableStateOf<ProductMenu?>(null) }

        val marioState = marioViewModel.state.collectAsState().value
        val productState = inventoryViewModel.product.collectAsState().value
        val inventoryState = inventoryViewModel.stateProductStock.collectAsState().value

        LaunchedEffect(true) {
            inventoryViewModel.onEvent(InventoryEvent.GetProductStock())
            inventoryViewModel.onEvent(InventoryEvent.GetProduct(0))
        }

        Scaffold(
            modifier = Modifier.padding(10.dp),
            backgroundColor = Color.Transparent,
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(resource = Res.drawable.ic_back),
                        contentDescription = "",
                        modifier = Modifier.clickable(
                                indication = null,
                                interactionSource = remember {
                                    MutableInteractionSource()
                                }
                            ) {
                                navigator.pop()
                            }
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    LazyRow {
                        items(previousScreen.size) {
                            Text(
                                modifier = Modifier
                                    .clickable(
                                        indication = null,
                                        interactionSource = remember {
                                            MutableInteractionSource()
                                        }
                                    ) {
                                        if (it > 0) {
                                            currentScreen = currentScreen.toMutableList().apply { removeLast() }
                                            previousScreen = previousScreen.toMutableList().apply { removeLast() }

                                            addNewProduct = false
                                            adjustStock = false

                                            // load data table
                                            inventoryViewModel.onEvent(InventoryEvent.GetProductStock())
                                            inventoryViewModel.onEvent(InventoryEvent.GetProduct(0))
                                        }
                                    },
                                text = previousScreen[it],
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryColor
                                )
                            )

                            Text(
                                text = " / ",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFBABABA)
                                )
                            )
                        }
                    }

                    Text(
                        text = currentScreen.last(),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )
                }
            }
        ) {
            Box(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxSize().padding(start = 5.dp, bottom = 5.dp, end = 5.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    AnimatedVisibility(
                        visible = addNewProduct && !adjustStock,
                        enter = fadeIn() + slideInHorizontally(),
                        exit = fadeOut() + slideOutHorizontally()
                    ) {
                        AddNewStock(
                            state = inventoryState,
                            productItem = productItem,
                            searchViewModel = searchViewModel,
                            onEvent = inventoryViewModel::onEvent,
                            marioState = marioState,
                            marioEvent = marioViewModel::onEvent,
                            callback = {
                                addNewProduct = false
                                currentScreen = currentScreen.toMutableList().apply { removeLast() }
                                previousScreen = previousScreen.toMutableList().apply { removeLast() }

                                // refresh product table
                                inventoryViewModel.onEvent(InventoryEvent.GetProduct(0))
                            }
                        )
                    }

                    AnimatedVisibility(
                        visible = !addNewProduct && !adjustStock,
                        enter = fadeIn() + slideInHorizontally(),
                        exit = fadeOut() + slideOutHorizontally()
                    ) {
                        AddNewProduct(
                            data = productState,
                            searchText = inventoryViewModel.searchText.collectAsState().value,
                            onEvent = inventoryViewModel::onEvent,
                            marioState = marioState,
                            marioEvent = marioViewModel::onEvent,
                            callBack = { product ->
                                productItem = product
                                addNewProduct = true
                                currentScreen = currentScreen.toMutableList().apply { add("New".takeIf { product == null } ?: "Edit") }
                                previousScreen = previousScreen.toMutableList().apply { add("Product & Stock") }
                            },
                            menuClick = {
                                inventoryViewModel.onEvent(InventoryEvent.GetProduct(it))
                            },
                            adjustStock = {
                                adjustStock = true
                                addNewProduct = false
                                currentScreen = currentScreen.toMutableList().apply { add("Adjustment") }
                                previousScreen = previousScreen.toMutableList().apply { add("Product & Stock") }

                                // refresh stock table
                                inventoryViewModel.onEvent(InventoryEvent.GetProductStock())
                            }
                        )
                    }

                    // Adjust stock
                    AnimatedVisibility(
                        visible = adjustStock,
                        enter = fadeIn() + slideInHorizontally(),
                        exit = fadeOut() + slideOutHorizontally()
                    ) {
                        AdjustStock(
                            state = inventoryState,
                            onEvent = inventoryViewModel::onEvent
                        )
                    }
                }
            }
        }
    }
}