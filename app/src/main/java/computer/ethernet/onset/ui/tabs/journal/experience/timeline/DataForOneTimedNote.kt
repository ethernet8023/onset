package computer.ethernet.onset.ui.tabs.journal.experience.timeline

import computer.ethernet.onset.data.room.experiences.entities.AdaptiveColor
import java.time.Instant

data class DataForOneTimedNote(
    val time: Instant,
    val color: AdaptiveColor
)
