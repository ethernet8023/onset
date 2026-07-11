package computer.ethernet.onset.ui.tabs.journal.addingestion.dose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import computer.ethernet.onset.ui.theme.horizontalPadding

@Composable
fun PurityCalculation(
    purityText: String,
    onPurityChange: (purity: String) -> Unit,
    isValidPurity: Boolean,
    convertedDoseAndUnitText: String?
) {
    Column(horizontalAlignment = Alignment.Start) {
        val focusManager = LocalFocusManager.current
        OutlinedTextField(
            value = purityText,
            onValueChange = {
                onPurityChange(it.replace(oldChar = ',', newChar = '.'))
            },
            label = { Text("Purity") },
            isError = !isValidPurity,
            trailingIcon = {
                Text(
                    text = "%",
                    modifier = Modifier.padding(horizontal = horizontalPadding)
                )
            },
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        if (!isValidPurity) {
            Text(
                text = "Purity must be between 1 and 100%",
                color = MaterialTheme.colorScheme.error,
            )
        }
        if (convertedDoseAndUnitText != null) {
            val textStyle = MaterialTheme.typography.titleMedium
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Impure dose", style = textStyle)
                Text(text = convertedDoseAndUnitText, style = textStyle)
            }
        }
    }
}