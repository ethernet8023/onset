package computer.ethernet.onset.ui.utils

import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

fun getTimeDifferenceText(fromInstant: Instant, toInstant: Instant): String {
    val diff = Duration.between(fromInstant, toInstant)
    val minutes = diff.toMinutes().toFloat()
    val hours = minutes / 60
    val days = hours / 24
    val weeks = days / 7
    val months = weeks / 4
    val years = months / 12
    return if (years > 2) {
        ChronoUnit.YEARS.between(
            fromInstant.getLocalDateTime(),
            toInstant.getLocalDateTime()
        ).toString() + " years"
    } else if (months > 3) {
        ChronoUnit.MONTHS.between(
            fromInstant.getLocalDateTime(),
            toInstant.getLocalDateTime()
        ).toString() + " months"
    } else if (weeks > 4) {
        ChronoUnit.WEEKS.between(
            fromInstant.getLocalDateTime(),
            toInstant.getLocalDateTime()
        ).toString() + " weeks"
    } else if (days > 2) {
        ChronoUnit.DAYS.between(
            fromInstant.getLocalDateTime(),
            toInstant.getLocalDateTime()
        ).toString() + " days"
    } else if (hours > 3) {
        val duration = Duration.between(fromInstant, toInstant)
        val hoursRounded = (duration.toMinutes() / 60.0 + 0.5).toInt()
        "$hoursRounded hours"
    } else {
        ChronoUnit.MINUTES.between(
            fromInstant.getLocalDateTime(),
            toInstant.getLocalDateTime()
        ).toString() + " minutes"
    }
}