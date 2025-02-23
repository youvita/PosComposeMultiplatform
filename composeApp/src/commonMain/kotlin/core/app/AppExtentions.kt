package core.app

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> convertToString(obj: T): String {
    val json = Json { ignoreUnknownKeys = true }
    return json.encodeToString(obj)
}

inline fun <reified T> convertToObject(str: String): T {
    val json = Json { ignoreUnknownKeys = true }
    return json.decodeFromString(str)
}
