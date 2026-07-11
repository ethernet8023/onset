package computer.ethernet.onset.ui.tabs.journal.experience.rating

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FloatingDoneButton(onDone: ()->Unit, modifier: Modifier = Modifier) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = onDone,
        icon = {
            Icon(
                Icons.Filled.Done,
                contentDescription = "Done"
            )
        },
        text = { Text("Done") }
    )
}