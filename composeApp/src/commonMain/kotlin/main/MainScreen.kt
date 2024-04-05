package main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import menu.domain.model.Menu
import menu.presentation.MenuViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class MainScreen: Screen, KoinComponent {
    @Composable
    override fun Content() {
        val menuViewModel = get<MenuViewModel>()

        LaunchedEffect(Unit) {
            menuViewModel.addMenu(Menu(id = 0, name = "Dara", description = "Test"))
        }
    }

}