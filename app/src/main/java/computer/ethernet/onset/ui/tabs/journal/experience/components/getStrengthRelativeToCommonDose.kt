package computer.ethernet.onset.ui.tabs.journal.experience.components

import computer.ethernet.onset.data.room.experiences.relations.IngestionWithCompanionAndCustomUnit
import computer.ethernet.onset.data.substances.classes.roa.RoaDose
import kotlin.math.max

fun getStrengthRelativeToCommonDose(
    ingestion: IngestionWithCompanionAndCustomUnit,
    allIngestions: List<IngestionWithCompanionAndCustomUnit>,
    roaDose: RoaDose?,
): Double {
    val allKnownDoses = allIngestions
        .filter { it.ingestion.substanceName == ingestion.ingestion.substanceName && it.ingestion.administrationRoute == ingestion.ingestion.administrationRoute}.mapNotNull {
            it.pureDose
        }
    val sumDose = allKnownDoses.reduceOrNull { acc, d -> acc + d } ?: 0.0
    val averageDose = sumDose / max(1.0, allKnownDoses.size.toDouble())
    val commonDose = roaDose?.averageCommonDose ?: averageDose

    return ingestion.pureDose?.let { doseSnap ->
        doseSnap / commonDose
    } ?: 1.0
}