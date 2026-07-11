package computer.ethernet.onset.ui.tabs.journal.experience.components.ingestion

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import computer.ethernet.onset.data.room.experiences.entities.AdaptiveColor


@Composable
fun VerticalLine(color: AdaptiveColor) {
    val isDarkTheme = isSystemInDarkTheme()
    Surface(
        shape = RoundedCornerShape(size = 2.dp),
        color = color.getComposeColor(isDarkTheme),
        modifier = Modifier
            .fillMaxHeight()
            .width(5.dp)
    ) {}
}