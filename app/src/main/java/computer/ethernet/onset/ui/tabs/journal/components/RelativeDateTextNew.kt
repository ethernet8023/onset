package computer.ethernet.onset.ui.tabs.journal.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Composable
fun RelativeDateTextNew(
    dateTime: Instant,
    style: TextStyle = MaterialTheme.typography.bodyMedium
) {
    val now: MutableState<Instant> = remember { mutableStateOf(Instant.now()) }
    LaunchedEffect(key1 = "updateTime") {
        while (true) {
            delay(60000L) // update every minute
            now.value = Instant.now()
        }
    }
    val duration = Duration.between(dateTime, now.value)
    val relativeTime = when {
        duration.toDays() > 500 -> "${roundToOneDecimal(duration.toDays().toDouble()/365.0)} years ago"
        duration.toDays() > 60 -> "${roundToOneDecimal(duration.toDays().toDouble()/30.0)} months ago"
        duration.toDays() > 20 -> "${roundToOneDecimal(duration.toDays().toDouble()/7.0)} weeks ago"
        duration.toHours() > 24 -> "${roundToOneDecimal(duration.toHours().toDouble()/24.0)} days ago"
        duration.toHours() > 0 -> "${roundToOneDecimal(duration.toMinutes().toDouble()/60.0)} hours ago"
        duration.toMinutes() > 0 -> "${duration.toMinutes()} minutes ago"
        else -> "just now"
    }
    Text(
        text = relativeTime,
        style = style
    )
}

fun roundToOneDecimal(num: Double): String {
    return try {
        val df = DecimalFormat("#.#", DecimalFormatSymbols.getInstance(Locale.US))
        df.format(num)
    } catch (e: NumberFormatException) {
        println("Failed to format the number: $e")
        ""
    }
}

@Preview(showBackground = true)
@Composable
fun RelativeDateTextPreview() {
    Column {
        val dateTime1 = remember { Instant.now().minus(3, ChronoUnit.DAYS) }
        val dateTime2 = remember { Instant.now().minus(5, ChronoUnit.HOURS) }
        val dateTime3 = remember { Instant.now().minus(30, ChronoUnit.MINUTES) }
        RelativeDateTextNew(dateTime = dateTime1)
        RelativeDateTextNew(dateTime = dateTime2)
        RelativeDateTextNew(dateTime = dateTime3)
    }
}