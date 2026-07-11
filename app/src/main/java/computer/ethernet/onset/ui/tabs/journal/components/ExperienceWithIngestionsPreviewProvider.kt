package computer.ethernet.onset.ui.tabs.journal.components

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import computer.ethernet.onset.data.room.experiences.entities.AdaptiveColor
import computer.ethernet.onset.data.room.experiences.entities.Experience
import computer.ethernet.onset.data.room.experiences.entities.Ingestion
import computer.ethernet.onset.data.room.experiences.entities.Location
import computer.ethernet.onset.data.room.experiences.entities.ShulginRating
import computer.ethernet.onset.data.room.experiences.entities.ShulginRatingOption
import computer.ethernet.onset.data.room.experiences.entities.StomachFullness
import computer.ethernet.onset.data.room.experiences.entities.SubstanceCompanion
import computer.ethernet.onset.data.room.experiences.relations.ExperienceWithIngestionsCompanionsAndRatings
import computer.ethernet.onset.data.room.experiences.relations.IngestionWithCompanionAndCustomUnit
import computer.ethernet.onset.data.substances.AdministrationRoute
import java.time.Instant
import java.time.temporal.ChronoUnit

class ExperienceWithIngestionsCompanionsAndRatingsPreviewProvider :
    PreviewParameterProvider<ExperienceWithIngestionsCompanionsAndRatings> {
    override val values: Sequence<ExperienceWithIngestionsCompanionsAndRatings> = sequenceOf(
        ExperienceWithIngestionsCompanionsAndRatings(
            experience = Experience(
                id = 0,
                title = "Day at Lake Geneva",
                text = "Some notes",
                isFavorite = true,
                sortDate = Instant.now().minus(2, ChronoUnit.HOURS),
                location = Location(name = "Max place", longitude = 4.0, latitude = 5.0)
            ),
            ingestionsWithCompanions = listOf(
                IngestionWithCompanionAndCustomUnit(
                    ingestion = Ingestion(
                        substanceName = "MDMA",
                        time = Instant.now().minus(2, ChronoUnit.HOURS),
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
                IngestionWithCompanionAndCustomUnit(
                    ingestion = Ingestion(
                        substanceName = "Cocaine",
                        time = Instant.now().minus(1, ChronoUnit.HOURS),
                        endTime = null,
                        administrationRoute = AdministrationRoute.INSUFFLATED,
                        dose = 30.0,
                        isDoseAnEstimate = false,
                        estimatedDoseStandardDeviation = null,
                        units = "mg",
                        experienceId = 0,
                        notes = null,
                        stomachFullness = null,
                        consumerName = null,
                        customUnitId = null
                    ),
                    substanceCompanion = SubstanceCompanion(
                        substanceName = "Cocaine",
                        color = AdaptiveColor.BLUE
                    ),
                    customUnit = null
                ),
                IngestionWithCompanionAndCustomUnit(
                    ingestion = Ingestion(
                        substanceName = "Cocaine",
                        time = Instant.now().minus(30, ChronoUnit.MINUTES),
                        endTime = null,
                        administrationRoute = AdministrationRoute.INSUFFLATED,
                        dose = 20.0,
                        isDoseAnEstimate = false,
                        estimatedDoseStandardDeviation = null,
                        units = "mg",
                        experienceId = 0,
                        notes = null,
                        stomachFullness = null,
                        consumerName = null,
                        customUnitId = null
                    ),
                    substanceCompanion = SubstanceCompanion(
                        substanceName = "Cocaine",
                        color = AdaptiveColor.BLUE
                    ),
                    customUnit = null
                )
            ),
            ratings = listOf(
                ShulginRating(
                    time = Instant.now().minus(30, ChronoUnit.MINUTES),
                    creationDate = Instant.now(),
                    option = ShulginRatingOption.TWO_PLUS,
                    experienceId = 0
                ),
                ShulginRating(
                    time = Instant.now().minus(15, ChronoUnit.MINUTES),
                    creationDate = Instant.now(),
                    option = ShulginRatingOption.THREE_PLUS,
                    experienceId = 0
                ),
                ShulginRating(
                    time = Instant.now().minus(5, ChronoUnit.MINUTES),
                    creationDate = Instant.now(),
                    option = ShulginRatingOption.TWO_PLUS,
                    experienceId = 0
                )
            )
        ),
        ExperienceWithIngestionsCompanionsAndRatings(
            experience = Experience(
                id = 0,
                title = "This one has a very very very long title in case somebody wants to be creative with the naming.",
                text = "Some notes",
                isFavorite = true,
                sortDate = Instant.now(),
                location = null
            ),
            ingestionsWithCompanions = listOf(
                IngestionWithCompanionAndCustomUnit(
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
                        notes = null,
                        stomachFullness = null,
                        consumerName = null,
                        customUnitId = null
                    ),
                    substanceCompanion = SubstanceCompanion(
                        substanceName = "MDMA",
                        color = AdaptiveColor.PINK
                    ),
                    customUnit = null
                ),
                IngestionWithCompanionAndCustomUnit(
                    ingestion = Ingestion(
                        substanceName = "Cocaine",
                        time = Instant.now(),
                        endTime = null,
                        administrationRoute = AdministrationRoute.INSUFFLATED,
                        dose = 20.0,
                        isDoseAnEstimate = false,
                        estimatedDoseStandardDeviation = null,
                        units = "mg",
                        experienceId = 0,
                        notes = null,
                        stomachFullness = null,
                        consumerName = "David",
                        customUnitId = null
                    ),
                    substanceCompanion = SubstanceCompanion(
                        substanceName = "Cocaine",
                        color = AdaptiveColor.BLUE
                    ),
                    customUnit = null
                ),
                IngestionWithCompanionAndCustomUnit(
                    ingestion = Ingestion(
                        substanceName = "Cocaine",
                        time = Instant.now(),
                        endTime = null,
                        administrationRoute = AdministrationRoute.INSUFFLATED,
                        dose = 20.0,
                        isDoseAnEstimate = false,
                        estimatedDoseStandardDeviation = null,
                        units = "mg",
                        experienceId = 0,
                        notes = null,
                        stomachFullness = null,
                        consumerName = "Sven",
                        customUnitId = null
                    ),
                    substanceCompanion = SubstanceCompanion(
                        substanceName = "Cocaine",
                        color = AdaptiveColor.BLUE
                    ),
                    customUnit = null
                )
            ),
            ratings = listOf(
                ShulginRating(
                    time = Instant.now().minus(15, ChronoUnit.MINUTES),
                    creationDate = Instant.now(),
                    option = ShulginRatingOption.FOUR_PLUS,
                    experienceId = 0
                )
            )
        ),
        ExperienceWithIngestionsCompanionsAndRatings(
            experience = Experience(
                id = 0,
                title = "Day at Lake Geneva",
                text = "Some notes",
                isFavorite = true,
                sortDate = Instant.now().minus(2, ChronoUnit.HOURS),
                location = Location(name = "Location name with a very long name that does not fit in one line", longitude = 4.0, latitude = 5.0)
            ),
            ingestionsWithCompanions = listOf(
                IngestionWithCompanionAndCustomUnit(
                    ingestion = Ingestion(
                        substanceName = "MDMA",
                        time = Instant.now().minus(2, ChronoUnit.HOURS),
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
            ),
            ratings = emptyList()
        ),
    )
}