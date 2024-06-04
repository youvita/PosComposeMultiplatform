
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import core.utils.ProvideAppNavigator
import core.utils.SharePrefer
import kotlinx.coroutines.launch
import login.presentation.LoginScreen
import main.MainScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {
    KoinContext {
        MaterialTheme {
            NavigationHost()

            SharePrefer.putPrefer("paper_width", "48")
        }
    }
}

@Composable
fun NavigationHost(
) {
    Navigator(
        screen = MainScreen(),
        content = { navigator ->
            ProvideAppNavigator(
                navigator = navigator,
                content = { SlideTransition(navigator = navigator) },
            )
        }
    )
}
