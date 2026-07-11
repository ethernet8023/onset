package computer.ethernet.onset.data.room.experiences.relations

import androidx.room.Embedded
import androidx.room.Relation
import computer.ethernet.onset.data.room.experiences.entities.Ingestion
import computer.ethernet.onset.data.room.experiences.entities.SubstanceCompanion

data class IngestionWithCompanion(
    @Embedded
    var ingestion: Ingestion,

    @Relation(
        parentColumn = "substanceName",
        entityColumn = "substanceName"
    )
    var substanceCompanion: SubstanceCompanion?,
)