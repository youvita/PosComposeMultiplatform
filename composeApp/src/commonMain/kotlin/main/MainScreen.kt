package main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import cafe.adriel.voyager.core.screen.Screen
import core.theme.ColorDDE3F9
import core.theme.White
import getPlatform
import history.presentation.HistoryScreen
import history.presentation.HistoryViewModel
import main.component.NavigationTabScaffold
import main.model.NavModel
import menu.domain.model.Menu
import menu.presentation.MenuViewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_background
import poscomposemultiplatform.composeapp.generated.resources.ic_history_menu
import poscomposemultiplatform.composeapp.generated.resources.ic_notification_menu
import poscomposemultiplatform.composeapp.generated.resources.ic_order_menu
import poscomposemultiplatform.composeapp.generated.resources.ic_setting_menu
import poscomposemultiplatform.composeapp.generated.resources.ic_super_mario_menu
import receipt.BillCompanySeal
import receipt.BillCustomerForm1
import receipt.BillCustomerForm2
import receipt.BillFooter
import receipt.BillHeader
import receipt.BillPayment
import receipt.BillQueue

class MainScreen: Screen, KoinComponent {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val platform = getPlatform()

        val historyViewModel = get<HistoryViewModel>()
        val historyState = historyViewModel.uiState.collectAsState().value
        val pagingState = historyViewModel.pagingState.collectAsState().value

        val menuViewModel = get<MenuViewModel>()

        var isPrint by remember {
            mutableStateOf(false)
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
            NavModel(
                id = 4,
                icon = painterResource(resource = Res.drawable.ic_notification_menu),
                label = "Notification"
            ),
        )

        LaunchedEffect(Unit) {
            menuViewModel.addMenu(Menu(id = 0, name = "Dara", description = "Test"))
        }

        if (isPrint) {
            isPrint = false
            platform.Capture(0) {
                Box(
                    modifier = Modifier.fillMaxWidth().background(White)
                ) {
                    BillHeader()
                }
            }
            platform.Capture(1) {
                Box(
                    modifier = Modifier.fillMaxWidth().background(White)
                ) {
                    BillCustomerForm1()
                }
            }
            platform.Capture(2) {
                Box(
                    modifier = Modifier.fillMaxWidth().background(White)
                ) {
                    BillCustomerForm2()
                }
            }
            platform.Capture(3) {
                Box(
                    modifier = Modifier.fillMaxWidth().background(White)
                ) {
                    BillPayment()
                }
            }
            platform.Capture(4) {
                Box(
                    modifier = Modifier.fillMaxWidth().background(White)
                ) {
                    BillCompanySeal()
                }
            }
            platform.Capture(5) {
                Box(
                    modifier = Modifier.fillMaxWidth().background(White)
                ) {
                    BillQueue()
                }
            }
            platform.Capture(6) {
                Box(
                    modifier = Modifier.fillMaxWidth().background(White)
                ) {
                    BillFooter()
                }
            }
        }

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
                    Button(
                        onClick = {
                          isPrint = true
                        }
                    ) {
                        Text("Print Order")
                    }
                }

                1 -> {
                    HistoryScreen(
                        historyState = historyState,
                        pagingState = pagingState,
                        historyEvent = historyViewModel::onEvent
                    )
                }

                2 -> {

                }

                3 -> {

                }
            }
        }
    }

}