package computer.ethernet.onset.ui.tabs.journal.experience

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import computer.ethernet.onset.data.room.experiences.entities.AdaptiveColor
import computer.ethernet.onset.data.room.experiences.entities.Ingestion
import computer.ethernet.onset.data.room.experiences.entities.ShulginRating
import computer.ethernet.onset.data.room.experiences.entities.ShulginRatingOption
import computer.ethernet.onset.data.room.experiences.entities.StomachFullness
import computer.ethernet.onset.data.room.experiences.entities.SubstanceCompanion
import computer.ethernet.onset.data.room.experiences.entities.TimedNote
import computer.ethernet.onset.data.room.experiences.relations.IngestionWithCompanionAndCustomUnit
import computer.ethernet.onset.data.substances.AdministrationRoute
import computer.ethernet.onset.data.substances.classes.InteractionType
import computer.ethernet.onset.data.substances.classes.roa.DurationRange
import computer.ethernet.onset.data.substances.classes.roa.DurationUnits
import computer.ethernet.onset.data.substances.classes.roa.RoaDuration
import computer.ethernet.onset.ui.tabs.journal.addingestion.interactions.Interaction
import computer.ethernet.onset.ui.tabs.journal.experience.models.CumulativeDose
import computer.ethernet.onset.ui.tabs.journal.experience.models.CumulativeRouteAndDose
import computer.ethernet.onset.ui.tabs.journal.experience.models.IngestionElement
import computer.ethernet.onset.ui.tabs.journal.experience.models.InteractionExplanation
import computer.ethernet.onset.ui.tabs.journal.experience.models.OneExperienceScreenModel
import computer.ethernet.onset.ui.utils.getInstant

class OneExperienceScreenPreviewProvider :
    PreviewParameterProvider<OneExperienceScreenModel> {

    override val values: Sequence<OneExperienceScreenModel> = sequenceOf(
        OneExperienceScreenModel(
            isFavorite = false,
            title = "Day at Lake Geneva",
            firstIngestionTime = getInstant(
                year = 2022,
                month = 2,
                day = 19,
                hourOfDay = 20,
                minute = 5
            )!!,
            notes = "Some Notes",
            locationName = "Zurich",
            isCurrentExperience = true,
            ingestionElements = ingestionElements,
            cumulativeDoses = listOf(
                CumulativeDose(
                    substanceName = "Cocaine",
                    cumulativeRouteAndDose = listOf(
                        CumulativeRouteAndDose(
                            cumulativeDose = 60.0,
                            units = "mg",
                            isEstimate = false,
                            cumulativeDoseStandardDeviation = 12.0,
                            numDots = 6,
                            route = AdministrationRoute.INSUFFLATED,
                            hasMoreThanOneIngestion = true
                        )
                    )
                )
            ),
            interactions = listOf(
                Interaction(
                    aName = "MDMA",
                    bName = "Cocaine",
                    interactionType = InteractionType.UNSAFE,
                )
            ),
            interactionExplanations = listOf(
                InteractionExplanation(
                    name = "Cocaine",
                    url = "www.google.com"
                ),
                InteractionExplanation(
                    name = "MDMA",
                    url = "www.google.com"
                )
            ),
            ratings = listOf(
                ShulginRating(
                    time = getInstant(
                        year = 2022,
                        month = 2,
                        day = 20,
                        hourOfDay = 1,
                        minute = 15
                    )!!,
                    creationDate = getInstant(
                        year = 2022,
                        month = 2,
                        day = 20,
                        hourOfDay = 1,
                        minute = 15
                    )!!,
                    option = ShulginRatingOption.TWO_PLUS,
                    experienceId = 0
                ),
                ShulginRating(
                    time = getInstant(
                        year = 2022,
                        month = 2,
                        day = 20,
                        hourOfDay = 3,
                        minute = 15
                    )!!,
                    creationDate = getInstant(
                        year = 2022,
                        month = 3,
                        day = 20,
                        hourOfDay = 1,
                        minute = 15
                    )!!,
                    option = ShulginRatingOption.PLUS,
                    experienceId = 0
                )
            ),
            timedNotesSorted = listOf(
                TimedNote(
                    creationDate = getInstant(
                        year = 2022,
                        month = 2,
                        day = 19,
                        hourOfDay = 23,
                        minute = 25
                    )!!,
                    time = getInstant(
                        year = 2022,
                        month = 2,
                        day = 19,
                        hourOfDay = 23,
                        minute = 25
                    )!!,
                    note = "Onset start",
                    color = AdaptiveColor.PURPLE,
                    experienceId = 0,
                    isPartOfTimeline = true
                ),
                TimedNote(
                    creationDate = getInstant(
                        year = 2022,
                        month = 2,
                        day = 19,
                        hourOfDay = 23,
                        minute = 25
                    )!!,
                    time = getInstant(
                        year = 2022,
                        month = 2,
                        day = 19,
                        hourOfDay = 23,
                        minute = 45
                    )!!,
                    note = "Peak start and this is a note that spans multiple lines, so long that we can see what a bigger layout looks like.",
                    color = AdaptiveColor.BLUE,
                    experienceId = 0,
                    isPartOfTimeline = true
                )
            ),
            consumersWithIngestions = emptyList(),
            dataForEffectLines = emptyList()
        )
    )

    companion object {
        val ingestionElements = listOf(
            IngestionElement(
                ingestionWithCompanionAndCustomUnit = IngestionWithCompanionAndCustomUnit(
                    ingestion = Ingestion(
                        substanceName = "MDMA",
                        time = getInstant(
                            year = 2022,
                            month = 2,
                            day = 19,
                            hourOfDay = 20,
                            minute = 5
                        )!!,
                        endTime = null,
                        administrationRoute = AdministrationRoute.ORAL,
                        dose = 90.0,
                        isDoseAnEstimate = false,
                        estimatedDoseStandardDeviation = null,
                        units = "mg",
                        experienceId = 0,
                        notes = null,
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
                roaDuration = RoaDuration(
                    onset = DurationRange(
                        min = 30f,
                        max = 45f,
                        units = DurationUnits.MINUTES
                    ),
                    comeup = DurationRange(
                        min = 15f,
                        max = 30f,
                        units = DurationUnits.MINUTES
                    ),
                    peak = DurationRange(
                        min = 1.5f,
                        max = 2.5f,
                        units = DurationUnits.HOURS
                    ),
                    offset = DurationRange(
                        min = 1f,
                        max = 1.5f,
                        units = DurationUnits.HOURS
                    ),
                    total = DurationRange(
                        min = 3f,
                        max = 6f,
                        units = DurationUnits.HOURS
                    ),
                    afterglow = DurationRange(
                        min = 12f,
                        max = 48f,
                        units = DurationUnits.HOURS
                    )
                ),
                numDots = 2
            ),
            IngestionElement(
                ingestionWithCompanionAndCustomUnit = IngestionWithCompanionAndCustomUnit(
                    ingestion = Ingestion(
                        substanceName = "Cocaine",
                        time = getInstant(
                            year = 2022,
                            month = 2,
                            day = 19,
                            hourOfDay = 23,
                            minute = 5
                        )!!,
                        endTime = null,
                        administrationRoute = AdministrationRoute.INSUFFLATED,
                        dose = 80.0,
                        isDoseAnEstimate = false,
                        estimatedDoseStandardDeviation = null,
                        units = "mg",
                        experienceId = 0,
                        notes = null,
                        stomachFullness = StomachFullness.EMPTY,
                        consumerName = null,
                        customUnitId = null
                    ),
                    substanceCompanion = SubstanceCompanion(
                        substanceName = "Cocaine",
                        color = AdaptiveColor.BLUE
                    ),
                    customUnit = null
                ),
                roaDuration = RoaDuration(
                    onset = DurationRange(
                        min = 1f,
                        max = 10f,
                        units = DurationUnits.MINUTES
                    ),
                    comeup = DurationRange(
                        min = 5f,
                        max = 15f,
                        units = DurationUnits.MINUTES
                    ),
                    peak = DurationRange(
                        min = 15f,
                        max = 30f,
                        units = DurationUnits.MINUTES
                    ),
                    offset = DurationRange(
                        min = 10f,
                        max = 30f,
                        units = DurationUnits.MINUTES
                    ),
                    total = DurationRange(
                        min = 10f,
                        max = 90f,
                        units = DurationUnits.MINUTES
                    ),
                    afterglow = null
                ),
                numDots = 3
            ),
            IngestionElement(
                ingestionWithCompanionAndCustomUnit = IngestionWithCompanionAndCustomUnit(
                    ingestion = Ingestion(
                        substanceName = "Cocaine",
                        time = getInstant(
                            year = 2022,
                            month = 2,
                            day = 20,
                            hourOfDay = 1,
                            minute = 15
                        )!!,
                        endTime = null,
                        administrationRoute = AdministrationRoute.INSUFFLATED,
                        dose = 50.0,
                        isDoseAnEstimate = false,
                        estimatedDoseStandardDeviation = null,
                        units = "mg",
                        experienceId = 0,
                        notes = null,
                        stomachFullness = StomachFullness.EMPTY,
                        consumerName = null,
                        customUnitId = null
                    ),
                    substanceCompanion = SubstanceCompanion(
                        substanceName = "Cocaine",
                        color = AdaptiveColor.BLUE
                    ),
                    customUnit = null
                ),
                roaDuration = RoaDuration(
                    onset = DurationRange(
                        min = 1f,
                        max = 10f,
                        units = DurationUnits.MINUTES
                    ),
                    comeup = DurationRange(
                        min = 5f,
                        max = 15f,
                        units = DurationUnits.MINUTES
                    ),
                    peak = DurationRange(
                        min = 15f,
                        max = 30f,
                        units = DurationUnits.MINUTES
                    ),
                    offset = DurationRange(
                        min = 10f,
                        max = 30f,
                        units = DurationUnits.MINUTES
                    ),
                    total = DurationRange(
                        min = 10f,
                        max = 90f,
                        units = DurationUnits.MINUTES
                    ),
                    afterglow = null
                ),
                numDots = 2
            )
        )
    }
}