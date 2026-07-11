package computer.ethernet.onset.ui.tabs.journal.experience.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import computer.ethernet.onset.ui.utils.getShortTimeText
import computer.ethernet.onset.ui.utils.getShortTimeWithWeekdayText
import computer.ethernet.onset.ui.utils.getShortWeekdayText
import java.time.Instant

const val TIME_RANGE_SEPARATOR_TEXT = " - "

@Composable
fun IngestionTimeOrDurationText(
    time: Instant,
    endTime: Instant?,
    index: Int,
    timeDisplayOption: TimeDisplayOption,
    allTimesSortedMap: List<Instant>
) {
    val isFirstIngestion = index == 0
    val textStyle = MaterialTheme.typography.labelMedium
    when (timeDisplayOption) {
        TimeDisplayOption.REGULAR -> {
            RegularTimeOrRangeText(time, endTime, textStyle)
        }

        TimeDisplayOption.RELATIVE_TO_NOW -> {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TimeRelativeToNowText(
                    time = time,
                    style = textStyle
                )
                if (endTime != null) {
                    Text(TIME_RANGE_SEPARATOR_TEXT)
                    TimeRelativeToNowText(
                        time = endTime,
                        style = textStyle
                    )
                }
            }
        }

        TimeDisplayOption.TIME_BETWEEN -> {
            if (isFirstIngestion) {
                RegularTimeOrRangeText(time, endTime, textStyle)
            } else {
                val previousTime =
                    allTimesSortedMap[index - 1]
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (endTime != null) {
                        Text(
                            text = getDurationText(
                                fromInstant = previousTime,
                                toInstant = time
                            ),
                            style = textStyle
                        )
                        Text(TIME_RANGE_SEPARATOR_TEXT)
                        Text(
                            text = getDurationText(
                                fromInstant = previousTime,
                                toInstant = endTime
                            ) + " after previous",
                            style = textStyle
                        )
                    } else {
                        Text(
                            text = getDurationText(
                                fromInstant = previousTime,
                                toInstant = time
                            ) + " after previous",
                            style = textStyle
                        )
                    }
                }
            }
        }

        TimeDisplayOption.RELATIVE_TO_START -> {
            if (isFirstIngestion) {
                RegularTimeOrRangeText(time, endTime, textStyle)
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val startTime = allTimesSortedMap.firstOrNull()
                        ?: Instant.now()
                    if (endTime != null) {
                        Text(
                            text = getDurationText(
                                fromInstant = startTime,
                                toInstant = time
                            ),
                            style = textStyle
                        )
                        Text(TIME_RANGE_SEPARATOR_TEXT)
                        Text(
                            text = getDurationText(
                                fromInstant = startTime,
                                toInstant = endTime
                            ) + " after start",
                            style = textStyle
                        )
                    } else {
                        Text(
                            text = getDurationText(
                                fromInstant = startTime,
                                toInstant = time
                            ) + " after start",
                            style = textStyle
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RegularTimeOrRangeText(
    time: Instant,
    endTime: Instant?,
    textStyle: TextStyle
) {
    val startText = time.getShortTimeWithWeekdayText()
    val text = if (endTime == null) {
        startText
    } else {
        val startDay = time.getShortWeekdayText()
        val endDay = endTime.getShortWeekdayText()
        if (startDay == endDay) {
            startText + TIME_RANGE_SEPARATOR_TEXT + endTime.getShortTimeText()
        } else {
            startText + TIME_RANGE_SEPARATOR_TEXT + endTime.getShortTimeWithWeekdayText()
        }
    }
    Text(
        text = text,
        style = textStyle
    )
}

@Composable
fun NoteOrRatingTimeOrDurationText(
    time: Instant,
    timeDisplayOption: TimeDisplayOption,
    firstIngestionTime: Instant
) {
    val textStyle = MaterialTheme.typography.labelMedium
    when (timeDisplayOption) {
        TimeDisplayOption.REGULAR -> {
            val startText = time.getShortTimeWithWeekdayText()
            Text(text = startText, style = textStyle)
        }

        TimeDisplayOption.RELATIVE_TO_NOW -> {
            TimeRelativeToNowText(
                time = time,
                style = textStyle
            )
        }

        TimeDisplayOption.TIME_BETWEEN -> {
            val startText = time.getShortTimeWithWeekdayText()
            Text(text = startText, style = textStyle)
        }

        TimeDisplayOption.RELATIVE_TO_START -> {
            val durationText = getDurationText(
                fromInstant = firstIngestionTime,
                toInstant = time
            ) + if (firstIngestionTime < time) " after start" else "before start"
            Text(
                text = durationText,
                style = textStyle
            )
        }
    }
}