package ui.bluetooth.presentation

sealed interface UiEvent {
    object OnScanClick: UiEvent
    data class OnConnectClick(val macId: String): UiEvent
    data class OnDisconnectClick(val macId: String): UiEvent
}