package core.bluetooth

import dev.bluefalcon.BluetoothPeripheral

data class EnhancedBluetoothPeripheral(
    val connected: Boolean,
    val peripheral: BluetoothPeripheral
)