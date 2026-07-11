package computer.ethernet.onset.ui.utils

import java.text.DateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun getInstant(year: Int, month: Int, day: Int, hourOfDay: Int, minute: Int): Instant? {
    val dateTime = LocalDateTime.of(year, month, day, hourOfDay, minute)
    return dateTime.atZone(ZoneId.systemDefault()).toInstant()
}

fun Instant.getStringOfPattern(pattern: String): String {
    val dateTime = LocalDateTime.ofInstant(this, ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return dateTime.format(formatter)
}

fun Instant.getDateWithWeekdayText(): String {
    return getStringOfPattern("EEE dd MMM yyyy")
}

fun Instant.getShortWeekdayText(): String {
    return getStringOfPattern("EEE")
}

fun Instant.getShortTimeWithWeekdayText(): String {
    return getShortWeekdayText() + " " + getShortTimeText()
}

fun Instant.getShortTimeText(): String {
    val timeFormat: DateFormat = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault())
    val date = Date.from(this)
    return timeFormat.format(date)
}

fun LocalDateTime.getStringOfPattern(pattern: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return format(formatter)
}

fun LocalDateTime.getDateWithWeekdayText(): String {
    return getStringOfPattern("EEE dd MMM yyyy")
}

fun LocalDateTime.getShortTimeText(): String {
    val instant = getInstant()
    return instant.getShortTimeText()
}

fun Instant.getLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofInstant(this, ZoneId.systemDefault())
}

fun LocalDateTime.getInstant(): Instant {
    return atZone(ZoneId.systemDefault()).toInstant()
}