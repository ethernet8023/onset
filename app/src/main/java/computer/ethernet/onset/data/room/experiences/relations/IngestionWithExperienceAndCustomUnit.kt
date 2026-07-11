package computer.ethernet.onset.data.room.experiences.relations

import androidx.room.Embedded
import androidx.room.Relation
import computer.ethernet.onset.data.room.experiences.entities.CustomUnit
import computer.ethernet.onset.data.room.experiences.entities.Experience
import computer.ethernet.onset.data.room.experiences.entities.Ingestion

data class IngestionWithExperienceAndCustomUnit(
    @Embedded
    var ingestion: Ingestion,

    @Relation(
        parentColumn = "experienceId",
        entityColumn = "id"
    )
    var experience: Experience,

    @Relation(
        parentColumn = "customUnitId",
        entityColumn = "id"
    )
    var customUnit: CustomUnit?
)