package mario.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import menu.presentation.OrderState

@Composable
fun TabMarioScreen() {

    Navigator(
        GraphMarioScreen(
            screenType = ScreenMario.MainScreen.route,
        )
    ) { navigator ->
        SlideTransition(navigator) { screen ->
            Column (
                modifier = Modifier.fillMaxWidth()
            ){
                screen.Content()
            }
        }
    }
}
