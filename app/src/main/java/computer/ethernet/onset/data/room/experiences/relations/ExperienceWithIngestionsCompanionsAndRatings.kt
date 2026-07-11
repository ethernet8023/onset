package computer.ethernet.onset.data.room.experiences.relations

import androidx.room.Embedded
import androidx.room.Relation
import computer.ethernet.onset.data.room.experiences.entities.Experience
import computer.ethernet.onset.data.room.experiences.entities.Ingestion
import computer.ethernet.onset.data.room.experiences.entities.ShulginRating
import computer.ethernet.onset.data.room.experiences.entities.ShulginRatingOption
import java.time.Instant

data class ExperienceWithIngestionsCompanionsAndRatings(
    @Embedded val experience: Experience,
    @Relation(
        entity = Ingestion::class,
        parentColumn = "id",
        entityColumn = "experienceId"
    ) val ingestionsWithCompanions: List<IngestionWithCompanionAndCustomUnit>,
    @Relation(
        entity = ShulginRating::class,
        parentColumn = "id",
        entityColumn = "experienceId"
    ) val ratings: List<ShulginRating>
) {
    val sortInstant: Instant get() = ingestionsWithCompanions.firstOrNull()?.ingestion?.time ?: experience.creationDate
    private val highestRatingOption: ShulginRatingOption? get() = ratings.maxOfOrNull { it.option }
    private val overallRatingOption: ShulginRatingOption? get() = ratings.firstOrNull { it.time == null }?.option
    val rating: ShulginRatingOption? get() = overallRatingOption ?: highestRatingOption
}