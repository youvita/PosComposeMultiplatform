package main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import cafe.adriel.voyager.core.screen.Screen
import core.theme.ColorDDE3F9
import getPlatform
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

class MainScreen: Screen, KoinComponent {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val platform = getPlatform()

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
            platform.print()
        }

        NavigationTabScaffold(
            containerColor = ColorDDE3F9,
            navModels = allNavModels
        ) { selectedItem ->
            Row(modifier = Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                    painter = painterResource(resource = Res.drawable.ic_background), contentDescription = null
                )
            }

            when(selectedItem) {
                0 -> {

                }

                1 -> {

                }

                2 -> {

                }

                3 -> {

                }
            }
        }
    }

}