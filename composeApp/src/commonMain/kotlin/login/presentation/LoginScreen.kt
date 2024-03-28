package login.presentation

import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import cafe.adriel.voyager.core.screen.Screen
import core.data.Status
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class LoginScreen: Screen, KoinComponent {

    @Composable
    override fun Content() {
        val loginModel = get<LoginViewModel>()
        val state = loginModel.uiState.collectAsState().value

        val loading by rememberUpdatedState(state.isLoading)
        LaunchedEffect(loading) {
            if (state.status == Status.SUCCESS) {
                println("Move to new screen")
            }
        }

        Scaffold {
            Button(
                onClick = {
                    loginModel.onLoginClick()
                }
            ) {
                Text("Click Me!!!")
            }
        }
    }

}