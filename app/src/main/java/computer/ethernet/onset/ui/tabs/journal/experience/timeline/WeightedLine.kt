package computer.ethernet.onset.ui.tabs.journal.experience.timeline

import java.time.Instant

data class WeightedLine(
    val startTime: Instant,
    val endTime: Instant?,
    val horizontalWeight: Float,
    val height: Float
)