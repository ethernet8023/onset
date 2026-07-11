package computer.ethernet.onset.ui.tabs.journal.addingestion.time

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.clearAndSetSemantics
import computer.ethernet.onset.R
import java.time.LocalDateTime

@Composable
fun DatePickerButton(
    localDateTime: LocalDateTime,
    onChange: (LocalDateTime) -> Unit,
    dateString: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val dialogTheme =
        if (isSystemInDarkTheme()) R.style.DialogThemeDark else R.style.DialogThemeLight
    val datePickerDialog = DatePickerDialog(
        context,
        dialogTheme,
        { _: DatePicker, newYear: Int, newMonth: Int, newDay: Int ->
            onChange(
                LocalDateTime.now()
                    .withYear(newYear)
                    .withMonth(newMonth + 1)
                    .withDayOfMonth(newDay)
                    .withHour(localDateTime.hour)
                    .withMinute(localDateTime.minute)
            )
        }, localDateTime.year, localDateTime.monthValue - 1, localDateTime.dayOfMonth
    )
    OutlinedButton(onClick = datePickerDialog::show, modifier = modifier.clearAndSetSemantics {  }) {
        Icon(
            Icons.Outlined.Event,
            contentDescription = "Open calendar"
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(dateString)
    }
}