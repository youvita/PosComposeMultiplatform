package login.presentation

import core.data.Status
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import login.domain.model.User
import login.domain.repository.LoginRepository

class LoginViewModel(
    private val repository: LoginRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<User>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onUsernameChange(username: String) {
        _uiState.value = _uiState.value.copy(username = username)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun onLoginClick() {
        viewModelScope.launch {
            val result = repository.login(_uiState.value.username, _uiState.value.password)
            result.onEach { resource ->
                if (resource.status == Status.SUCCESS){
                    _uiState.update { it.copy(showDialog = false) }
                    _uiEvent.send(User())
                } else {
                    _uiState.update { it.copy(showDialog = false) }
                    _uiState.value = _uiState.value.copy(dialogMsg = resource.message.toString())
                }
            }
        }
    }
}