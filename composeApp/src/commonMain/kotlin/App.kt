
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import di.appModule
import login.presentation.LoginScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

@Composable
@Preview
fun App() {
    initializeKoin()

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


// Injection Initialize
fun initializeKoin() {
    startKoin {
        modules(appModule())
    }
}