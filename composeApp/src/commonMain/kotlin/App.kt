
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import login.presentation.LoginScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {

    KoinContext {
        MaterialTheme {
            NavigationHost()
        }
    }
}

@Composable
fun NavigationHost(
) {
    Navigator(
        screen = LoginScreen()
    )
}
