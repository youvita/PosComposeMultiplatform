package ui.bluetooth.presentation

import androidx.lifecycle.ViewModel
import ui.bluetooth.data.BleDelegate
import dev.bluefalcon.BlueFalcon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ui.bluetooth.data.DeviceEvent

class BluetoothDeviceViewModel(
    private val blueFalcon: BlueFalcon,
    delegate: BleDelegate = BleDelegate()
): ViewModel() {

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
        CoroutineScope(Dispatchers.IO).launch {
            blueFalcon.peripherals.collect { peripherals ->
                val uniqueKeys = _deviceState.value.devices.keys.toList()
                val filteredPeripheral = peripherals.filter { !uniqueKeys.contains(it.uuid) }
                filteredPeripheral.map { peripheral ->
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