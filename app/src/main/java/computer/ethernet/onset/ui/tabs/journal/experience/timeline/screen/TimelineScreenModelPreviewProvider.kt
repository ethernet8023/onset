package computer.ethernet.onset.ui.tabs.journal.experience.timeline.screen

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import computer.ethernet.onset.data.room.experiences.entities.AdaptiveColor
import computer.ethernet.onset.data.room.experiences.entities.ShulginRating
import computer.ethernet.onset.data.room.experiences.entities.ShulginRatingOption
import computer.ethernet.onset.data.room.experiences.entities.TimedNote
import computer.ethernet.onset.ui.tabs.journal.experience.components.TimeDisplayOption
import computer.ethernet.onset.ui.utils.getInstant

class TimelineScreenModelPreviewProvider :
    PreviewParameterProvider<TimelineScreenModel> {

    override val values: Sequence<TimelineScreenModel> = sequenceOf(
        TimelineScreenModel(
            title = "Dave",
            dataForEffectLines = emptyList(),
            ratings = listOf(
                ShulginRating(
                    time = getInstant(
                        year = 2022,
                        month = 2,
                        day = 20,
                        hourOfDay = 1,
                        minute = 15
                    )!!,
                    creationDate = getInstant(
                        year = 2022,
                        month = 2,
                        day = 20,
                        hourOfDay = 1,
                        minute = 15
                    )!!,
                    option = ShulginRatingOption.TWO_PLUS,
                    experienceId = 0
                ),
                ShulginRating(
                    time = getInstant(
                        year = 2022,
                        month = 2,
                        day = 20,
                        hourOfDay = 3,
                        minute = 15
                    )!!,
                    creationDate = getInstant(
                        year = 2022,
                        month = 3,
                        day = 20,
                        hourOfDay = 1,
                        minute = 15
                    )!!,
                    option = ShulginRatingOption.PLUS,
                    experienceId = 0
                )
            ),
            timedNotes = listOf(
                TimedNote(
                    creationDate = getInstant(
                        year = 2022,
                        month = 2,
                        day = 19,
                        hourOfDay = 23,
                        minute = 25
                    )!!,
                    time = getInstant(
                        year = 2022,
                        month = 2,
                        day = 19,
                        hourOfDay = 23,
                        minute = 25
                    )!!,
                    note = "Onset start",
                    color = AdaptiveColor.PURPLE,
                    experienceId = 0,
                    isPartOfTimeline = true
                ),
                TimedNote(
                    creationDate = getInstant(
                        year = 2022,
                        month = 2,
                        day = 19,
                        hourOfDay = 23,
                        minute = 25
                    )!!,
                    time = getInstant(
                        year = 2022,
                        month = 2,
                        day = 19,
                        hourOfDay = 23,
                        minute = 45
                    )!!,
                    note = "Peak start and this is a note that spans multiple lines, so long that we can see what a bigger layout looks like.",
                    color = AdaptiveColor.BLUE,
                    experienceId = 0,
                    isPartOfTimeline = true
                )
            ),
            timeDisplayOption = TimeDisplayOption.REGULAR,
            areSubstanceHeightsIndependent = false
        )
    )
}