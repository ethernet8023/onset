package computer.ethernet.onset.data.room.experiences.relations

import androidx.room.Embedded
import androidx.room.Relation
import computer.ethernet.onset.data.room.experiences.entities.Experience
import computer.ethernet.onset.data.room.experiences.entities.Ingestion
import computer.ethernet.onset.data.room.experiences.entities.ShulginRating
import computer.ethernet.onset.data.room.experiences.entities.TimedNote

data class ExperienceWithIngestionsTimedNotesAndRatings(
    @Embedded val experience: Experience,
    @Relation(
        entity = Ingestion::class,
        parentColumn = "id",
        entityColumn = "experienceId"
    ) val ingestions: List<Ingestion>,
    @Relation(
        entity = TimedNote::class,
        parentColumn = "id",
        entityColumn = "experienceId"
    ) val timedNotes: List<TimedNote>,
    @Relation(
        entity = ShulginRating::class,
        parentColumn = "id",
        entityColumn = "experienceId"
    ) val ratings: List<ShulginRating>
)