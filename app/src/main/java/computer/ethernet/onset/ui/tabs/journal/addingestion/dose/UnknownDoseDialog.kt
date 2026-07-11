package computer.ethernet.onset.ui.tabs.journal.addingestion.dose

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun UnknownDoseDialogPreview() {
    UnknownDoseDialog(
        useUnknownDoseAndNavigate = {},
        dismiss = {}
    )
}

@Composable
fun UnknownDoseDialog(
    useUnknownDoseAndNavigate: () -> Unit,
    dismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = dismiss,
        title = {
            Text(text = "Don't know the dose?", style = MaterialTheme.typography.headlineSmall)
        },
        text = {
            Text(
                "You can log an unknown dose. But note that administering the wrong dosage of a substance can lead to negative experiences such as extreme anxiety, uncomfortable physical side effects, hospitalization, or (in extreme cases) death."
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    dismiss()
                    useUnknownDoseAndNavigate()
                }
            ) {
                Text("Log unknown dose")
            }
        },
        dismissButton = {
            TextButton(
                onClick = dismiss
            ) {
                Text("Cancel")
            }
        }
    )
}