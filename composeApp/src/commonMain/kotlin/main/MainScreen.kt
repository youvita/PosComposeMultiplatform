package main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import core.app.convertToObject
import core.bluetooth.BluetoothViewModel
import core.data.Status
import core.theme.ColorDDE3F9
import core.theme.White
import core.utils.Constants
import core.utils.ProvideAppNavigator
import core.utils.SharePrefer
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import getPlatform
import orderhistory.presentation.OrderHistoryScreen
import orderhistory.presentation.OrderHistoryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import main.component.NavigationTabScaffold
import main.model.NavModel
import mario.presentation.TabMarioScreen
import menu.presentation.OrderViewModel
import menu.presentation.OrderScreen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_background
import poscomposemultiplatform.composeapp.generated.resources.ic_history_menu
import poscomposemultiplatform.composeapp.generated.resources.ic_order_menu
import poscomposemultiplatform.composeapp.generated.resources.ic_setting_menu
import poscomposemultiplatform.composeapp.generated.resources.ic_super_mario_menu
import receipt.BillRowItem
import setting.domain.model.ItemModel
import ui.settings.presentation.SettingsEvent
import ui.settings.presentation.SettingsScreen
import ui.settings.presentation.SettingsViewModel
import ui.stock.presentation.InventoryViewModel
import ui.stock.presentation.SearchEngineViewModel

class MainScreen: Screen, KoinComponent {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val platform = getPlatform()

        val orderHistoryViewModel = get<OrderHistoryViewModel>()
        val historyState = orderHistoryViewModel.uiState.collectAsState().value
        val pagingState = orderHistoryViewModel.pagingState.collectAsState().value

        val orderViewModel = get<OrderViewModel>()
        val orderState = orderViewModel.state.collectAsState().value

        val searchViewModel = get<SearchEngineViewModel>()
        val inventoryViewModel = get<InventoryViewModel>()
        val searchState = searchViewModel.state.collectAsState().value

        val settingsViewModel = get<SettingsViewModel>()
        val settingsState = settingsViewModel.state.collectAsState().value

        val itemSearchList = orderViewModel.product.collectAsState().value
        val itemOrderSearchList = orderHistoryViewModel.orderList.collectAsState().value

        var isAddItem by remember {
            mutableStateOf(false)
        }

        var startBarCodeScan by remember { mutableStateOf(false) }

        val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
        val controller: PermissionsController = remember(factory) { factory.createPermissionsController() }
        val coroutineScope: CoroutineScope = rememberCoroutineScope()

        val blueViewModel = get<BluetoothViewModel>()
        val bluetoothState by blueViewModel.deviceState.collectAsState()
        BindEffect(controller)
        coroutineScope.launch {
            controller.providePermission(Permission.BLUETOOTH_CONNECT)
            controller.providePermission(Permission.BLUETOOTH_SCAN)
            controller.providePermission(Permission.LOCATION)
        }

        val allNavModels = arrayOf(
            NavModel(
                id = 0,
                icon = painterResource(resource = Res.drawable.ic_order_menu),
                label = "Menu"
            ),
            NavModel(
                id = 1,
                icon = painterResource(resource = Res.drawable.ic_history_menu),
                label = "History"
            ),
            NavModel(
                id = 2,
                icon = painterResource(resource = Res.drawable.ic_setting_menu),
                label = "Settings"
            ),
            NavModel(
                id = 3,
                icon = painterResource(resource = Res.drawable.ic_super_mario_menu),
                label = "Mario"
            ),
//            NavModel(
//                id = 4,
//                icon = painterResource(resource = Res.drawable.ic_notification_menu),
//                label = "Notification"
//            ),
        )

        NavigationTabScaffold(
            containerColor = ColorDDE3F9,
            navModels = allNavModels
        ) { selectedItem ->

            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                    painter = painterResource(resource = Res.drawable.ic_background), contentDescription = null
                )
            }

            when(selectedItem) {
                0 -> {
//                    Row {
//                        Button(
//                            onClick = {
//                                platform.printer()
//                            }
//                        ) {
//                            Text("Print Order")
//                        }
//
//                        Spacer(modifier = Modifier.width(10.dp))
//
//                        Button(
//                            onClick = {
//                                isAddItem = true
//                            }
//                        ) {
//                            Text("Add Item")
//                        }
//
//                        Spacer(modifier = Modifier.width(10.dp))
//
//                        Button(
//                            onClick = {
//                                startBarCodeScan = true
//                            }
//                        ) {
//                            Text("Scan Item")
//                        }
//                    }
////
//                    if (startBarCodeScan) {
//                        QrScannerScreen(
//                            result = {
//                                searchViewModel.onSearchClick(it)
//                                startBarCodeScan = false
//                            }
//                        )
//                    }
//
//                    LazyRow(
//                        contentPadding = PaddingValues(5.dp)
//                    ) {
//                        searchState.data?.items?.let {
//                            items(it) { item ->
//                                item.image?.thumbnailLink?.let { url ->
//                                    ImageLoader(
//                                        image = url
//                                    )
//                                }
//                            }
//                        }
//                    }

                    OrderScreen(
                        orderState = orderState,
                        itemSearchList = itemSearchList,
                        orderEvent = orderViewModel::onEvent
                    )

                    // call to refresh receipt data for print
//                    settingsViewModel.onEvent(SettingsEvent.GetPreference())
                }

                1 -> {
                    OrderHistoryScreen(
                        orderHistoryState = historyState,
                        orderSearchList = itemOrderSearchList,
                        pagingState = pagingState,
                        historyEvent = orderHistoryViewModel::onEvent
                    )
                }

                2 -> {
                    Navigator(
                        screen = SettingsScreen(),
                        content = { navigator ->
                            ProvideAppNavigator(
                                navigator = navigator,
                                content = { SlideTransition(navigator = navigator) },
                            )
                        }
                    )
                }

                3 -> {
                    TabMarioScreen()
                }
            }
        }
    }

}
