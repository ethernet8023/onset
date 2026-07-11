package computer.ethernet.onset.ui.tabs.journal.experience.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import computer.ethernet.onset.ui.theme.horizontalPadding


@Composable
fun CardWithTitle(
    modifier: Modifier = Modifier,
    title: String,
    innerPaddingHorizontal: Dp = 10.dp,
    content: @Composable (ColumnScope.() -> Unit)
) {
    ElevatedCard(modifier = modifier.padding(vertical = 5.dp)) {
        Column(modifier = Modifier.padding(bottom = 5.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 3.dp, horizontal = horizontalPadding)
            )
            Column(
                Modifier
                    .padding(horizontal = innerPaddingHorizontal)
            ) {
                content()
            }
        }
    }
}

@Preview
@Composable
fun CardWithTitlePreview() {
    CardWithTitle(title = "Your card") {
        Text(text = "Content", modifier = Modifier.fillMaxWidth())
    }
}