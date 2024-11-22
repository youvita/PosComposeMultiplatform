package ui.bluetooth.data

sealed interface DeviceEvent {
    data class OnDeviceConnected(val macId: String): DeviceEvent
    data class OnDeviceDisconnected(val macId: String): DeviceEvent
}