package login.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.data.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import login.domain.model.User
import login.domain.repository.LoginRepository

class LoginViewModel(
    private val repository: LoginRepository
): ScreenModel {

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
        repository.login(_uiState.value.username, _uiState.value.password)
            .onEach { result ->
                when(result) {
                    is Resource.Success -> {
                        _uiState.value = uiState.value.copy(
                            status = result.status,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = uiState.value.copy(
                            status = result.status,
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _uiState.value = uiState.value.copy(isLoading = true)
                    }
                }
            }.launchIn(screenModelScope)

    }
}