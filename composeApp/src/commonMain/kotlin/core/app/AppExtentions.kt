package core.app

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> convertToString(obj: T): String {
    return Json.encodeToString(obj)
}

inline fun <reified T> convertToObject(str: String): T {
    return Json.decodeFromString(str)
}
