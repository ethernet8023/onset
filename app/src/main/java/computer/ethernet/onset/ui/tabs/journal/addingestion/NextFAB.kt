package computer.ethernet.onset.ui.tabs.journal.addingestion

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun NextFAB(navigateToNext: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = navigateToNext,
        icon = {
            Icon(
                Icons.AutoMirrored.Filled.NavigateNext,
                contentDescription = "Next"
            )
        },
        text = { Text("Next") },
    )
}