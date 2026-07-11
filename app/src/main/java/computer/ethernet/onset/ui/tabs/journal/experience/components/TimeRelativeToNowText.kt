package computer.ethernet.onset.ui.tabs.journal.experience.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import java.time.Instant
import java.time.temporal.ChronoUnit

@Composable
fun TimeRelativeToNowText(
    time: Instant,
    style: TextStyle = MaterialTheme.typography.bodyMedium
) {
    val now: MutableState<Instant> = remember { mutableStateOf(Instant.now()) }
    LaunchedEffect(key1 = "updateTime") {
        while (true) {
            delay(10000L) // update every 10 seconds
            now.value = Instant.now()
        }
    }
    val isInPast = time < now.value
    val relativeTime = if (isInPast) {
        getDurationText(fromInstant = time, toInstant = now.value) + " ago"
    } else {
        "in " + getDurationText(fromInstant = time, toInstant = now.value)
    }
    Text(
        text = relativeTime,
        style = style
    )
}

@Preview(showBackground = true)
@Composable
fun TimeRelativeToNowTextPreview() {
    Column {
        val dateTime1 = remember { Instant.now().minus(5, ChronoUnit.HOURS) }
        val dateTime2 = remember { Instant.now().minus(30, ChronoUnit.MINUTES) }
        TimeRelativeToNowText(time = dateTime1)
        TimeRelativeToNowText(time = dateTime2)
    }
}