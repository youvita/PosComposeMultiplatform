package main

import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.screen.Screen
import getPlatform
import menu.domain.model.Menu
import menu.presentation.MenuViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class MainScreen: Screen, KoinComponent {
    @Composable
    override fun Content() {
        val platform = getPlatform()

        val menuViewModel = get<MenuViewModel>()

        var isPrint by remember {
            mutableStateOf(false)
        }


        LaunchedEffect(Unit) {
            menuViewModel.addMenu(Menu(id = 0, name = "Dara", description = "Test"))
        }

        if (isPrint) {
            isPrint = false
            platform.print()
        }

        Scaffold {
            Button(
                onClick = {
                    isPrint = true
                }
            ) {
                Text("Print")
            }
        }

    }

}