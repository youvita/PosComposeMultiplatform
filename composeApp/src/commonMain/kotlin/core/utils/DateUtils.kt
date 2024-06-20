package core.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.daysUntil
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.format
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.monthsUntil
import kotlinx.datetime.offsetIn
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun getCurrentDateTime(): String {
    var dateFormat = ""
    try {
        val now = Clock.System.now()
        val dateTime: LocalDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
        val format = DateTimeComponents.Formats.RFC_1123.format {
            setDate(dateTime.date)
            hour = dateTime.time.hour
            minute = dateTime.time.minute
            second = dateTime.time.second
            setOffset(UtcOffset(hours = 0))
        }
        dateFormat = (format.substring(5, 22).takeIf { format.length > 28 } ?: format.subSequence(5, 21)).toString()
    } catch (e: Exception) {
        e.message
    }
    return dateFormat
}

fun getCurrentDate(): String {
    var dateFormat = ""
    try {
        val now = Clock.System.now()
        val dateTime: LocalDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
        val format = DateTimeComponents.Formats.RFC_1123.format {
            setDate(dateTime.date)
            hour = dateTime.time.hour
            minute = dateTime.time.minute
            second = dateTime.time.second
            setOffset(UtcOffset(hours = hour))
        }
        dateFormat = format.substring(5, 16)
    } catch (e: Exception) {
        e.message
    }
    return dateFormat
}

fun getCurrentTime(): String {
    var dateFormat = ""
    try {
        val now = Clock.System.now()
        val dateTime: LocalDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
        val format = DateTimeComponents.Formats.RFC_1123.format {
            setDate(dateTime.date)
            hour = dateTime.time.hour
            minute = dateTime.time.minute
            second = dateTime.time.second
            setOffset(UtcOffset(hours = 0))
        }
        dateFormat = (format.substring(17, 22).takeIf { format.length > 28 } ?: format.subSequence(17, 21)).toString()
    } catch (e: Exception) {
        e.message
    }
    return dateFormat
}

fun getDateByPeriodOfDay(days: Int): String {
    var dateFormat = ""
    try {
        val now = Clock.System.now()
        val untilDate = now.plus(days, DateTimeUnit.DAY,  TimeZone.currentSystemDefault())
        val dateTime: LocalDateTime = untilDate.toLocalDateTime(TimeZone.currentSystemDefault())
        val format = DateTimeComponents.Formats.RFC_1123.format {
            setDate(dateTime.date)
            hour = dateTime.time.hour
            minute = dateTime.time.minute
            second = dateTime.time.second
            setOffset(UtcOffset(hours = 0))
        }
        dateFormat = format.substring(5, 16)
    } catch (e: Exception) {
        e.message
    }
    return dateFormat
}

fun getCurrentDateByDayOfMonth(days: Int): String {
    var dateFormat = ""
    try {
        val now = Clock.System.now()
        val dateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
        val format = DateTimeComponents.Formats.RFC_1123.format {
            setDate(LocalDate(dateTime.year, dateTime.month, dateTime.dayOfMonth.takeIf { days < 1 } ?: days))
            hour = dateTime.time.hour
            minute = dateTime.time.minute
            second = dateTime.time.second
            setOffset(UtcOffset(hours = 0))
        }
        dateFormat = format.substring(5, 16)
    } catch (e: Exception) {
        e.message
    }
    return dateFormat
}