package mario.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview()
@Composable
fun MarioScreen(
    marioState: MarioState? = null,
    marioEvent: (MarioEvent) -> Unit = {},
) {

    Navigator(MarioChildScreen(ScreenMario.MainScreen.route)) { navigator ->
        SlideTransition(navigator) { screen ->
            Column (
                modifier = Modifier.fillMaxWidth()
            ){
                screen.Content()
            }
        }
    }
}
