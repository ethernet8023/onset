package computer.ethernet.onset.ui.tabs.search.substance

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SectionText(text: AnnotatedString) {
    Text(
        text = text,
        textAlign = TextAlign.Left,
        modifier = Modifier.padding(vertical = 5.dp)
    )
}

@Composable
fun SectionText(text: String) {
    Text(
        text = text,
        textAlign = TextAlign.Left,
        modifier = Modifier.padding(vertical = 5.dp)
    )
}