package platform

import android.content.Context
import dev.bluefalcon.ApplicationContext
import dev.bluefalcon.BlueFalcon

actual class BluetoothDeviceFactory(private val context: Context) {
    actual val blueFalcon: BlueFalcon
        get() = BlueFalcon(context as ApplicationContext, null)
}