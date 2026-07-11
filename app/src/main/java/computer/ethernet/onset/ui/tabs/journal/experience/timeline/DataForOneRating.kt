package computer.ethernet.onset.ui.tabs.journal.experience.timeline

import computer.ethernet.onset.data.room.experiences.entities.ShulginRatingOption
import java.time.Instant

data class DataForOneRating(
    val time: Instant,
    val option: ShulginRatingOption
)

