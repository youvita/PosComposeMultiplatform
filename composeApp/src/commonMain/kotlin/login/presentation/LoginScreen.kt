package login.presentation

import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen

//@Composable
//fun LoginRoute(
//    viewModel: LoginViewModel
//) {
//    val state by viewModel.uiState.collectAsState()
//    viewModel.onPasswordChange("")
//    viewModel.onUsernameChange("")
//    LoginScreen(state = state, loginEvent = viewModel::onLoginClick)
//}
class LoginScreen(
    state: LoginState,
    loginEvent: () -> Unit
): Screen {
    @Composable
    override fun Content() {
        Scaffold {
            Button(
                onClick = {  }
            ) {
                Text("Click Me!!!")
            }
        }
    }

}