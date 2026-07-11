package computer.ethernet.onset.ui.tabs.search.substance

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import computer.ethernet.onset.ui.theme.horizontalPadding
import computer.ethernet.onset.ui.theme.verticalPaddingCards

@Composable
fun SectionWithTitle(title: String, content: @Composable () -> Unit) {
    ElevatedCard(
        modifier = Modifier.padding(
            horizontal = horizontalPadding,
            vertical = verticalPaddingCards
        ).fillMaxWidth()
    ) {
        Text(
            color = MaterialTheme.colorScheme.onSurface,
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier =  Modifier.padding(horizontal = horizontalPadding, vertical = 8.dp)
        )
        content()
    }
}