package core.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.atDate
import kotlinx.datetime.atTime
import kotlinx.datetime.daysUntil
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.format
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.monthsUntil
import kotlinx.datetime.offsetIn
import kotlinx.datetime.periodUntil
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.until
import kotlin.time.Duration

fun getCurrentDateTime(): String {
    var dateFormat = ""
    var timeZone = ""
    try {
        val now = Clock.System.now()
        val dateTime: LocalDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
        val format = DateTimeComponents.Formats.RFC_1123.format {
            setDate(dateTime.date)
            hour = dateTime.time.hour
            minute = dateTime.time.minute
            second = dateTime.time.second
            setOffset(UtcOffset(hours = 0))

            hour?.let {
                timeZone = if (it <= 12) {
                    "AM"
                } else {
                    "PM"
                }
            }
        }
        dateFormat = (format.substring(5, 22).takeIf { format.length > 28 } ?: format.subSequence(5, 21)).toString()
    } catch (e: Exception) {
        e.message
    }
    return "$dateFormat $timeZone"
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
    var timeZone = ""
    try {
        val now = Clock.System.now()
        val dateTime: LocalDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
        val format = DateTimeComponents.Formats.RFC_1123.format {
            setDate(dateTime.date)
            hour = dateTime.time.hour
            minute = dateTime.time.minute
            second = dateTime.time.second
            setOffset(UtcOffset(hours = 0))

            hour?.let {
                timeZone = if (it <= 12) {
                    "AM"
                } else {
                    "PM"
                }
            }

        }
        dateFormat = (format.substring(16, 22).takeIf { format.length > 28 } ?: format.subSequence(16, 21)).toString()
    } catch (e: Exception) {
        e.message
    }
    return "$dateFormat $timeZone"
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

/**
 * calculated period of day between two date
 */
fun getDatePeriod(startDate: String, endDate: String): Int {
    if (startDate.isEmpty() && endDate.isEmpty()) return 0

    val startParts = startDate.trim().split(" ")
    val start = LocalDate(startParts[2].toInt(), getMonth(startParts[1]), startParts[0].toInt())
    val endParts = endDate.trim().split(" ")
    val end = LocalDate(endParts[2].toInt(), getMonth(endParts[1]), endParts[0].toInt())
    return start.until(end, DateTimeUnit.DAY)
}

/**
 * calculated duration between two time
 */
fun getTimePeriod(startTime: String): Int {
    if (startTime.isEmpty()) return 0

    val startTimeParts = startTime.trim().split(" ")
    val timeParts = startTimeParts[3].split(":")
    val localTime = LocalTime(hour = timeParts[0].toInt(), minute = timeParts[1].toInt())
    val duration = Clock.System.now().minus(localTime.hour, DateTimeUnit.HOUR)
    return duration.toLocalDateTime(TimeZone.currentSystemDefault()).time.hour
}

/**
 * calculated period of hours between two date
 */
fun getDateTimePeriod(startDateTime: String, endDateTime: String): Int {
    if (startDateTime.isEmpty() && endDateTime.isEmpty()) return 0

    val startInstant = convertDateTimeToInstant(startDateTime)
    val endInstant = convertDateTimeToInstant(endDateTime)

    var duration = startInstant.until(endInstant, DateTimeUnit.HOUR, TimeZone.currentSystemDefault()).toInt()
    if (duration == 0) {
        duration = startInstant.until(endInstant, DateTimeUnit.MINUTE, TimeZone.currentSystemDefault()).toInt()
    }

    return duration
}

fun checkTimeWithinHour(startDateTime: String, endDateTime: String): Boolean {
    val startInstant = convertDateTimeToInstant(startDateTime)
    val endInstant = convertDateTimeToInstant(endDateTime)
    val duration = startInstant.until(endInstant, DateTimeUnit.HOUR, TimeZone.currentSystemDefault()).toInt()
    return duration != 0
}

fun convertDateTimeToInstant(dateTime: String): Instant {
    if (dateTime.isEmpty()) return Instant.DISTANT_FUTURE

    val dateParts = dateTime.trim().split(" ")
    val date = LocalDate(dateParts[2].toInt(), getMonth(dateParts[1]), dateParts[0].toInt())
    val timeParts = dateParts[3].split(":")
    val time = LocalTime(hour = timeParts[0].toInt(), minute = timeParts[1].toInt())
    val instant = time.atDate(date)

    return instant.toInstant(TimeZone.currentSystemDefault())
}

fun getMonth(month: String): Month {
    when(month) {
        "Jan" -> {
            return Month.JANUARY
        }
        "Feb" -> {
            return Month.FEBRUARY
        }
        "Mar" -> {
            return Month.MARCH
        }
        "Apr" -> {
            return Month.APRIL
        }
        "May" -> {
            return Month.MAY
        }
        "Jun" -> {
            return Month.JUNE
        }
        "Jul" -> {
            return Month.JULY
        }
        "Aug" -> {
            return Month.AUGUST
        }
        "Sep" -> {
            return Month.SEPTEMBER
        }
        "Oct" -> {
            return Month.OCTOBER
        }
        "Nov" -> {
            return Month.NOVEMBER
        }
        else -> {
            return Month.DECEMBER
        }
    }
}