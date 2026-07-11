package computer.ethernet.onset.ui.tabs.journal.addingestion.time

import android.app.TimePickerDialog
import android.text.format.DateFormat
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.clearAndSetSemantics
import computer.ethernet.onset.R
import java.time.LocalDateTime

@Composable
fun TimePickerButton(
    localDateTime: LocalDateTime,
    onChange: (LocalDateTime) -> Unit,
    timeString: String,
    modifier: Modifier = Modifier,
    hasOutline: Boolean = true
) {
    val context = LocalContext.current
    val dialogTheme =
        if (isSystemInDarkTheme()) R.style.DialogThemeDark else R.style.DialogThemeLight
    val timePickerDialog = TimePickerDialog(
        context,
        dialogTheme,
        { _, newHour: Int, newMinute: Int ->
            onChange(
                LocalDateTime.now()
                    .withYear(localDateTime.year)
                    .withMonth(localDateTime.monthValue)
                    .withDayOfMonth(localDateTime.dayOfMonth)
                    .withHour(newHour)
                    .withMinute(newMinute)
            )
        }, localDateTime.hour, localDateTime.minute, DateFormat.is24HourFormat(context)
    )
    if (hasOutline) {
        OutlinedButton(onClick = timePickerDialog::show, modifier = modifier.clearAndSetSemantics {  }) {
            Icon(
                Icons.Outlined.Schedule,
                contentDescription = "Open time picker"
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(timeString)
        }
    } else {
        TextButton(onClick = timePickerDialog::show, modifier = modifier.clearAndSetSemantics {  }) {
            Icon(
                Icons.Outlined.Schedule,
                contentDescription = "Open time picker"
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(timeString)
        }
    }

}