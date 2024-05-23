package core.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.format
import kotlinx.datetime.toLocalDateTime

fun getCurrentDateTime(): String {
    var dateFormat = ""
    try {
        val now = Clock.System.now()
        val dateTime: LocalDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
        dateFormat = DateTimeComponents.Formats.RFC_1123.format {
            setDate(dateTime.date)
            hour = dateTime.time.hour
            minute = dateTime.time.minute
            second = dateTime.time.second
            setOffset(UtcOffset(hours = 0))
        }.substring(5, 22)
    } catch (e: Exception) {
        e.message
    }
    return dateFormat
}