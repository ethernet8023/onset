package computer.ethernet.onset.ui.tabs.journal.experience.models

import computer.ethernet.onset.data.room.experiences.relations.IngestionWithCompanionAndCustomUnit
import computer.ethernet.onset.data.substances.classes.roa.RoaDuration

data class IngestionElement(
    val ingestionWithCompanionAndCustomUnit: IngestionWithCompanionAndCustomUnit,
    val roaDuration: RoaDuration?,
    val numDots: Int?
)