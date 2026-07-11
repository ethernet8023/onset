package computer.ethernet.onset.ui.tabs.journal.experience.models

import computer.ethernet.onset.data.room.experiences.entities.ShulginRating
import computer.ethernet.onset.data.room.experiences.entities.TimedNote
import computer.ethernet.onset.ui.tabs.journal.addingestion.interactions.Interaction
import computer.ethernet.onset.ui.tabs.journal.experience.components.DataForOneEffectLine
import java.time.Instant

data class OneExperienceScreenModel(
    val isFavorite: Boolean,
    val title: String,
    val firstIngestionTime: Instant,
    val notes: String,
    val locationName: String,
    val isCurrentExperience: Boolean,
    val ingestionElements: List<IngestionElement>,
    val cumulativeDoses: List<CumulativeDose>,
    val interactions: List<Interaction>,
    val interactionExplanations: List<InteractionExplanation>,
    val ratings: List<ShulginRating>,
    val timedNotesSorted: List<TimedNote>,
    val consumersWithIngestions: List<ConsumerWithIngestions>,
    val dataForEffectLines: List<DataForOneEffectLine>,
)