
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import di.appModule
//import login.presentation.LoginRoute
import login.presentation.LoginScreen
import login.presentation.LoginViewModel
//import moe.tlaster.precompose.navigation.NavHost
//import moe.tlaster.precompose.navigation.path
//import moe.tlaster.precompose.navigation.rememberNavigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(appModule())
    }) {
        MaterialTheme {
            NavigationHost()
        }
    }
}

@Composable
fun NavigationHost() {
//    val navigator = rememberNavigator()
//    val stack = mutableMapOf<String, ViewModel>()
//
//    println("kdkdkdkdkkdkd")
//    NavHost(
//        navigator = navigator,
//        initialRoute = "/login"
//    ) {
//        scene(
//            route = "/login"
//        ) {
//            val viewModel = stack["/login"] as LoginViewModel? ?: koinInject()
//            stack["/login"] = viewModel
//
//            LoginRoute(viewModel)
//        }
//    }
    val viewModel: LoginViewModel = koinInject()
    Navigator(
        screen = LoginScreen(
            state = viewModel.uiState.collectAsState().value,
            loginEvent = viewModel::onLoginClick
        )
    )
}