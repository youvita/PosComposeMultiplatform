package core.utils

import com.russhwolf.settings.Settings

object SharePrefer {

    private val settings: Settings = Settings()
    fun putPrefer(key: String, value: String) {
        settings.putString(key, value)
    }

    fun getPrefer(key: String): String {
        return settings.getString(key, defaultValue = "")
    }

}

