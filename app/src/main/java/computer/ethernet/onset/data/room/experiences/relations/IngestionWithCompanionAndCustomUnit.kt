package computer.ethernet.onset.data.room.experiences.relations

import androidx.room.Embedded
import androidx.room.Relation
import computer.ethernet.onset.data.room.experiences.entities.CustomUnit
import computer.ethernet.onset.data.room.experiences.entities.Ingestion
import computer.ethernet.onset.data.room.experiences.entities.SubstanceCompanion
import computer.ethernet.onset.ui.tabs.journal.addingestion.search.suggestion.models.CustomUnitDose
import computer.ethernet.onset.ui.tabs.search.substance.roa.toReadableString

data class IngestionWithCompanionAndCustomUnit(
    @Embedded
    var ingestion: Ingestion,

    @Relation(
        parentColumn = "substanceName",
        entityColumn = "substanceName"
    )
    var substanceCompanion: SubstanceCompanion?,

    @Relation(
        parentColumn = "customUnitId",
        entityColumn = "id"
    )
    var customUnit: CustomUnit?
) {

    val originalUnit: String? get() = customUnit?.originalUnit ?: ingestion.units
    val pureDose: Double? get() {
        customUnitDose?.let {
            return it.calculatedDose
        } ?: return ingestion.dose
    }

    val isEstimate: Boolean get() = ingestion.isDoseAnEstimate || customUnit?.isEstimate ?: false

    val pureDoseStandardDeviation: Double?
        get() = customUnitDose?.calculatedDoseStandardDeviation ?: ingestion.estimatedDoseStandardDeviation

    val customUnitDose: CustomUnitDose?
        get() = ingestion.dose?.let { doseUnwrapped ->
            customUnit?.let { customUnitUnwrapped ->
                CustomUnitDose(
                    dose = doseUnwrapped,
                    isEstimate = ingestion.isDoseAnEstimate,
                    estimatedDoseStandardDeviation = ingestion.estimatedDoseStandardDeviation,
                    customUnit = customUnitUnwrapped
                )
            }
        }
    val doseDescription: String get() = customUnitDose?.doseDescription ?: ingestionDoseDescription

    private val ingestionDoseDescription get() = ingestion.dose?.let { dose ->
        ingestion.estimatedDoseStandardDeviation?.let { estimatedDoseDeviation ->
            "${dose.toReadableString()}±${estimatedDoseDeviation.toReadableString()} ${ingestion.units}"
        } ?: run {
            val description = "${dose.toReadableString()} ${ingestion.units}"
            if (isEstimate) {
                "~$description"
            } else {
                description
            }
        }
    } ?: "Unknown dose"
}