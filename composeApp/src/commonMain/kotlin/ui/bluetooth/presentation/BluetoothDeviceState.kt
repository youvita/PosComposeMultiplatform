package ui.bluetooth.presentation

data class BluetoothDeviceState(
    val devices: HashMap<String, EnhancedBluetoothPeripheral> = HashMap()
)