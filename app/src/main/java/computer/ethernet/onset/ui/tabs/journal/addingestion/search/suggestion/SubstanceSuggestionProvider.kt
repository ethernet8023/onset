package computer.ethernet.onset.ui.tabs.journal.addingestion.search.suggestion

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import computer.ethernet.onset.data.room.experiences.entities.AdaptiveColor
import computer.ethernet.onset.data.room.experiences.entities.CustomUnit
import computer.ethernet.onset.data.substances.AdministrationRoute
import computer.ethernet.onset.ui.tabs.journal.addingestion.search.suggestion.models.CustomUnitDoseSuggestion
import computer.ethernet.onset.ui.tabs.journal.addingestion.search.suggestion.models.DoseAndUnit
import computer.ethernet.onset.ui.tabs.journal.addingestion.search.suggestion.models.Suggestion
import computer.ethernet.onset.ui.utils.getInstant

class SubstanceSuggestionProvider : PreviewParameterProvider<Suggestion> {
    override val values: Sequence<Suggestion> = sequenceOf(
        Suggestion.PureSubstanceSuggestion(
            adaptiveColor = AdaptiveColor.PINK,
            administrationRoute = AdministrationRoute.ORAL,
            substanceName = "MDMA",
            dosesAndUnit = listOf(
                DoseAndUnit(
                    dose = 50.0,
                    unit = "mg",
                    isEstimate = false,
                    estimatedDoseStandardDeviation = null
                ),
                DoseAndUnit(
                    dose = 100.0,
                    unit = "mg",
                    isEstimate = false,
                    estimatedDoseStandardDeviation = null
                ),
                DoseAndUnit(
                    dose = null,
                    unit = "mg",
                    isEstimate = false,
                    estimatedDoseStandardDeviation = null
                ),
            ),
            sortInstant = getInstant(year = 2023, month = 4, day = 10, hourOfDay = 5, minute = 20)!!,
        ),
        Suggestion.CustomUnitSuggestion(
            customUnit = CustomUnit.mdmaSample,
            adaptiveColor = AdaptiveColor.PINK,
            dosesAndUnit = listOf(
                CustomUnitDoseSuggestion(
                    dose = 2.0,
                    isEstimate = false,
                    estimatedDoseStandardDeviation = null
                ),
                CustomUnitDoseSuggestion(
                    dose = 3.0,
                    isEstimate = false,
                    estimatedDoseStandardDeviation = null
                )
            ),
            sortInstant = getInstant(year = 2023, month = 4, day = 10, hourOfDay = 5, minute = 20)!!
        )
    )
}