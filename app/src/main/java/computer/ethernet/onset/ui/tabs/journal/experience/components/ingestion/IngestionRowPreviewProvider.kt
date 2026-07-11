package computer.ethernet.onset.ui.tabs.journal.experience.components.ingestion

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import computer.ethernet.onset.data.room.experiences.entities.AdaptiveColor
import computer.ethernet.onset.data.room.experiences.entities.CustomUnit
import computer.ethernet.onset.data.room.experiences.entities.Ingestion
import computer.ethernet.onset.data.room.experiences.entities.StomachFullness
import computer.ethernet.onset.data.room.experiences.entities.SubstanceCompanion
import computer.ethernet.onset.data.room.experiences.relations.IngestionWithCompanionAndCustomUnit
import computer.ethernet.onset.data.substances.AdministrationRoute
import computer.ethernet.onset.ui.tabs.journal.experience.models.IngestionElement
import java.time.Instant

class IngestionRowPreviewProvider : PreviewParameterProvider<IngestionElement> {
    override val values: Sequence<IngestionElement> = sequenceOf(
        IngestionElement(
            ingestionWithCompanionAndCustomUnit = IngestionWithCompanionAndCustomUnit(
                ingestion = Ingestion(
                    substanceName = "MDMA",
                    time = Instant.now(),
                    endTime = null,
                    administrationRoute = AdministrationRoute.ORAL,
                    dose = 90.0,
                    isDoseAnEstimate = false,
                    estimatedDoseStandardDeviation = null,
                    units = "mg",
                    experienceId = 0,
                    notes = "This is a very long note which I wrote to see how it looks like if the note spans more than one line in the ingestion row.",
                    stomachFullness = StomachFullness.EMPTY,
                    consumerName = null,
                    customUnitId = null
                ),
                substanceCompanion = SubstanceCompanion(
                    substanceName = "MDMA",
                    color = AdaptiveColor.PINK
                ),
                customUnit = null
            ),
            roaDuration = null,
            numDots = 2
        ),
        IngestionElement(
            ingestionWithCompanionAndCustomUnit = IngestionWithCompanionAndCustomUnit(
                ingestion = Ingestion(
                    substanceName = "2C-B",
                    time = Instant.now(),
                    endTime = null,
                    administrationRoute = AdministrationRoute.ORAL,
                    dose = 1.5,
                    isDoseAnEstimate = false,
                    estimatedDoseStandardDeviation = null,
                    units = "pill",
                    experienceId = 0,
                    notes = "This is a very long note which I wrote to see how it looks like if the note spans more than one line in the ingestion row.",
                    stomachFullness = StomachFullness.EMPTY,
                    consumerName = null,
                    customUnitId = 2
                ),
                substanceCompanion = SubstanceCompanion(
                    substanceName = "2C-B",
                    color = AdaptiveColor.GREEN
                ),
                customUnit = CustomUnit(
                    id = 2,
                    substanceName = "2C-B",
                    name = "Red rocket",
                    administrationRoute = AdministrationRoute.ORAL,
                    dose = 14.0,
                    estimatedDoseStandardDeviation = 4.0,
                    isEstimate = true,
                    isArchived = false,
                    unit = "pill",
                    unitPlural = "pills",
                    originalUnit = "mg",
                    note = "this is a note"
                )
            ),
            roaDuration = null,
            numDots = 8
        ),
        IngestionElement(
            ingestionWithCompanionAndCustomUnit = IngestionWithCompanionAndCustomUnit(
                ingestion = Ingestion(
                    substanceName = "2C-B",
                    time = Instant.now(),
                    endTime = null,
                    administrationRoute = AdministrationRoute.ORAL,
                    dose = 1.5,
                    isDoseAnEstimate = false,
                    estimatedDoseStandardDeviation = null,
                    units = "pill",
                    experienceId = 0,
                    notes = null,
                    stomachFullness = StomachFullness.EMPTY,
                    consumerName = null,
                    customUnitId = 5
                ),
                substanceCompanion = SubstanceCompanion(
                    substanceName = "2C-B",
                    color = AdaptiveColor.GREEN
                ),
                customUnit = CustomUnit(
                    id = 5,
                    substanceName = "2C-B",
                    name = "Blue rocket",
                    administrationRoute = AdministrationRoute.ORAL,
                    dose = null,
                    estimatedDoseStandardDeviation = null,
                    isEstimate = false,
                    isArchived = false,
                    unit = "pill",
                    unitPlural = "pills",
                    originalUnit = "mg",
                    note = "this is a note"
                )
            ),
            roaDuration = null,
            numDots = null
        ),
        IngestionElement(
            ingestionWithCompanionAndCustomUnit = IngestionWithCompanionAndCustomUnit(
                ingestion = Ingestion(
                    substanceName = "LSD",
                    time = Instant.now(),
                    endTime = null,
                    administrationRoute = AdministrationRoute.ORAL,
                    dose = null,
                    isDoseAnEstimate = false,
                    estimatedDoseStandardDeviation = null,
                    units = "µg",
                    experienceId = 0,
                    notes = null,
                    stomachFullness = StomachFullness.EMPTY,
                    consumerName = null,
                    customUnitId = null
                ),
                substanceCompanion = SubstanceCompanion(
                    substanceName = "LSD",
                    color = AdaptiveColor.BLUE
                ),
                customUnit = null
            ),
            roaDuration = null,
            numDots = null
        )
    )
}