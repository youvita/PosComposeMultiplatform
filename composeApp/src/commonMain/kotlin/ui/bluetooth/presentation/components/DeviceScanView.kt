package ui.bluetooth.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.theme.DarkGreen
import core.theme.PrimaryColor
import core.utils.LineWrapper
import core.utils.PrimaryButton
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_check
import poscomposemultiplatform.composeapp.generated.resources.ic_plus
import poscomposemultiplatform.composeapp.generated.resources.ic_wifi
import ui.bluetooth.data.DeviceModel
import ui.bluetooth.presentation.BluetoothDeviceState
import ui.bluetooth.presentation.EnhancedBluetoothPeripheral
import ui.bluetooth.presentation.UiEvent

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DeviceScanView(
    state: BluetoothDeviceState,
    onEvent: (UiEvent) -> Unit
) {
    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val controller: PermissionsController = remember(factory) { factory.createPermissionsController() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    var deviceName by remember {
        mutableStateOf("")
    }
    var deviceUUID by remember {
        mutableStateOf("")
    }
    var isDeviceConnected by remember {
        mutableStateOf(false)
    }

    BindEffect(controller)
    coroutineScope.launch {
        controller.providePermission(Permission.BLUETOOTH_CONNECT)
        controller.providePermission(Permission.BLUETOOTH_SCAN)
        controller.providePermission(Permission.LOCATION)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 20.dp, top = 10.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                text = "Print Connection",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            PrimaryButton(
                text = "Search Connection",
                onClick = {
                    onEvent(UiEvent.OnScanClick)
                }
            )
        }

        if (isDeviceConnected) {
            Box(modifier = Modifier.clickable {
                isDeviceConnected = false
//                    onEvent(UiEvent.OnDisconnectClick(deviceUUID))
            }) {
                Row(
                    modifier = Modifier
                        .padding(start = 20.dp, top = 10.dp, bottom = 10.dp, end = 25.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                painter = painterResource(resource = Res.drawable.ic_check),
                                contentDescription = null,
                                tint = PrimaryColor
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = deviceName,
                                fontSize = 12.sp
                            )
                        }

                        Icon(
                            painter = painterResource(resource = Res.drawable.ic_wifi),
                            contentDescription = null,
                            tint = DarkGreen
                        )
                    }
                }
            }
        }

        if (state.devices.size > 0) {
            Box(
                modifier = Modifier.padding(start = 20.dp, top = 10.dp, end = 20.dp)
            ) {
                Text(
                    modifier = Modifier.alpha(0.5f),
                    text = "Network Search"
                )
            }
        }

        LazyColumn {
            items(state.devices.values.toList()) { device ->
                println(device.peripheral.name)
                FoundDeviceCard(
                    deviceName = if (!device.peripheral.name.isNullOrBlank()) device.peripheral.name else "No Name",
                    macId = device.peripheral.uuid,
                    onClick = {
                        isDeviceConnected = true
                        deviceName = device.peripheral.name.orEmpty()
                        deviceUUID = it

                        println(device.connected)
//                        onEvent(UiEvent.OnConnectClick(it))
                    }
                )

                LineWrapper(
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                )
            }
        }
    }
}