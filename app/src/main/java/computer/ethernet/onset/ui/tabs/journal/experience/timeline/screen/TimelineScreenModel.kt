package computer.ethernet.onset.ui.tabs.journal.experience.timeline.screen

import computer.ethernet.onset.data.room.experiences.entities.ShulginRating
import computer.ethernet.onset.data.room.experiences.entities.TimedNote
import computer.ethernet.onset.ui.tabs.journal.experience.components.DataForOneEffectLine
import computer.ethernet.onset.ui.tabs.journal.experience.components.TimeDisplayOption

data class TimelineScreenModel(
    val title: String,
    val dataForEffectLines: List<DataForOneEffectLine>,
    val ratings: List<ShulginRating>,
    val timedNotes: List<TimedNote>,
    val timeDisplayOption: TimeDisplayOption,
    val areSubstanceHeightsIndependent: Boolean,
)