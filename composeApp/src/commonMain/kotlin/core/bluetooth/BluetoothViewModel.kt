package core.bluetooth

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.bluefalcon.BlueFalcon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
sealed interface UiEvent {
    object OnScanClick: UiEvent
    data class OnConnectClick(val macId: String): UiEvent
    data class OnDisconnectClick(val macId: String): UiEvent
}

class BluetoothViewModel(
    private val blueFalcon: BlueFalcon,
    delegate: BluetoothDelegate = BluetoothDelegate()
): ScreenModel {

    private val _deviceState: MutableStateFlow<BluetoothDeviceState> = MutableStateFlow(
        BluetoothDeviceState()
    )
    val deviceState: StateFlow<BluetoothDeviceState> get() = _deviceState

    init {
        delegate.setListener {event ->
            when(event) {
                is DeviceEvent.OnDeviceConnected -> {
                    _deviceState.update { state ->
                        val updateDevices = state.devices.toMutableMap()
                        updateDevices[event.macId]?.let {
                            updateDevices[event.macId] = it.copy(connected = true)
                        }
                        state.copy(
                            devices = HashMap(updateDevices)
                        )
                    }
                }

                is DeviceEvent.OnDeviceDisconnected -> {
                    _deviceState.update { state ->
                        val updateDevices = state.devices.toMutableMap()
                        updateDevices[event.macId]?.let {
                            updateDevices[event.macId] = it.copy(connected = false)
                        }
                        state.copy(
                            devices = HashMap(updateDevices)
                        )
                    }
                }
            }
        }
        blueFalcon.delegates.add(delegate)
        screenModelScope.launch {
            blueFalcon.peripherals.collect { peripherals ->
                val uniqueKeys = _deviceState.value.devices.keys.toList()
                val filteredPeripheral = peripherals.filter { !uniqueKeys.contains(it.uuid) }
                filteredPeripheral.map { peripheral ->
                    println(">>>>> peripheral::: $peripheral")
                    _deviceState.update {
                        val updateDevices = it.devices.toMutableMap()
                        updateDevices[peripheral.uuid] = EnhancedBluetoothPeripheral(false, peripheral)
                        it.copy(
                            devices = HashMap(updateDevices)
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: UiEvent) {
        when(event) {
            UiEvent.OnScanClick -> {
                blueFalcon.scan()
            }

            is UiEvent.OnConnectClick -> {
                _deviceState.value.devices[event.macId]?.let {
                    blueFalcon.connect(it.peripheral, false)
                }
            }

            is UiEvent.OnDisconnectClick -> {
                _deviceState.value.devices[event.macId]?.let {
                    blueFalcon.disconnect(it.peripheral)
                }
            }
        }
    }
}