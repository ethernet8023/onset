package computer.ethernet.onset.ui.tabs.journal.experience.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import computer.ethernet.onset.ui.theme.horizontalPadding

@Composable
fun CardTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(horizontal = horizontalPadding, vertical = 5.dp)
    )
}