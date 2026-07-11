package computer.ethernet.onset.data.room.experiences.relations

import androidx.room.Embedded
import androidx.room.Relation
import computer.ethernet.onset.data.room.experiences.entities.Experience
import computer.ethernet.onset.data.room.experiences.entities.Ingestion

data class ExperienceWithIngestions(
    @Embedded val experience: Experience,
    @Relation(
        entity = Ingestion::class,
        parentColumn = "id",
        entityColumn = "experienceId"
    ) val ingestions: List<Ingestion>
)