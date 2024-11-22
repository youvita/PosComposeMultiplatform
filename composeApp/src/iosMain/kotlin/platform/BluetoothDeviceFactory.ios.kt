package platform

import dev.bluefalcon.ApplicationContext
import dev.bluefalcon.BlueFalcon

actual class BluetoothDeviceFactory {
    actual val blueFalcon: BlueFalcon
        get() = BlueFalcon(ApplicationContext(), null)
}