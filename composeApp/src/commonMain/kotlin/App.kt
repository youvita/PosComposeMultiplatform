
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import login.presentation.LoginScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        NavigationHost()
    }
}

@Composable
fun NavigationHost(
) {
    Navigator(
        screen = LoginScreen()
    )
}
