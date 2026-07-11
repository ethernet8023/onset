package computer.ethernet.onset.ui.tabs.journal.experience.components.rating

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import computer.ethernet.onset.data.room.experiences.entities.ShulginRating
import computer.ethernet.onset.data.room.experiences.entities.ShulginRatingOption
import computer.ethernet.onset.ui.utils.getInstant


class RatingPreviewProvider : PreviewParameterProvider<ShulginRating> {
    override val values: Sequence<ShulginRating> = sequenceOf(
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
            option = ShulginRatingOption.THREE_PLUS,
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
                month = 2,
                day = 20,
                hourOfDay = 3,
                minute = 15
            )!!,
            option = ShulginRatingOption.TWO_PLUS,
            experienceId = 0
        ),
    )
}