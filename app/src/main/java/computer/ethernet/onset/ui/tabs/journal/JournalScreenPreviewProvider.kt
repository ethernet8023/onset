package computer.ethernet.onset.ui.tabs.journal

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
import computer.ethernet.onset.ui.utils.getInstant
import java.time.Instant
import java.time.temporal.ChronoUnit

class JournalScreenPreviewProvider :
    PreviewParameterProvider<List<ExperienceWithIngestionsCompanionsAndRatings>> {
    override val values: Sequence<List<ExperienceWithIngestionsCompanionsAndRatings>> =
        sequenceOf(
            listOf(
                ExperienceWithIngestionsCompanionsAndRatings(
                    experience = Experience(
                        id = 0,
                        title = "Festival",
                        text = "Some notes",
                        isFavorite = true,
                        sortDate = getInstant(
                            year = 2022,
                            month = 7,
                            day = 5,
                            hourOfDay = 14,
                            minute = 20
                        )!!,
                        location = Location(name = "Max place", longitude = 4.0, latitude = 5.0)
                    ),
                    ingestionsWithCompanions = listOf(
                        IngestionWithCompanionAndCustomUnit(
                            ingestion = Ingestion(
                                substanceName = "MDMA",
                                time = getInstant(
                                    year = 2022,
                                    month = 7,
                                    day = 5,
                                    hourOfDay = 14,
                                    minute = 20
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
                        IngestionWithCompanionAndCustomUnit(
                            ingestion = Ingestion(
                                substanceName = "Cocaine",
                                time = getInstant(
                                    year = 2022,
                                    month = 7,
                                    day = 5,
                                    hourOfDay = 14,
                                    minute = 20
                                )!!,
                                endTime = null,
                                administrationRoute = AdministrationRoute.INSUFFLATED,
                                dose = 30.0,
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
                        IngestionWithCompanionAndCustomUnit(
                            ingestion = Ingestion(
                                substanceName = "Ketamine",
                                time = getInstant(
                                    year = 2022,
                                    month = 7,
                                    day = 5,
                                    hourOfDay = 14,
                                    minute = 20
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
                                substanceName = "Ketamine",
                                color = AdaptiveColor.MINT
                            ),
                            customUnit = null
                        )
                    ),
                    ratings = listOf(
                        ShulginRating(
                            time = Instant.now().minus(15, ChronoUnit.MINUTES),
                            creationDate = Instant.now(),
                            option = ShulginRatingOption.TWO_PLUS,
                            experienceId = 0
                        )
                    )
                ),
                ExperienceWithIngestionsCompanionsAndRatings(
                    experience = Experience(
                        id = 0,
                        title = "Bachelor Party",
                        text = "Some notes",
                        sortDate = getInstant(
                            year = 2022,
                            month = 6,
                            day = 21,
                            hourOfDay = 12,
                            minute = 20
                        )!!,
                        location = Location(name = "Max place", longitude = 4.0, latitude = 5.0)
                    ),
                    ingestionsWithCompanions = listOf(
                        IngestionWithCompanionAndCustomUnit(
                            ingestion = Ingestion(
                                substanceName = "MDMA",
                                time = getInstant(
                                    year = 2022,
                                    month = 6,
                                    day = 21,
                                    hourOfDay = 12,
                                    minute = 20
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
                        )
                    ),
                    ratings = listOf()
                ),
                ExperienceWithIngestionsCompanionsAndRatings(
                    experience = Experience(
                        id = 0,
                        title = "Liam's Birthday",
                        text = "Some notes",
                        sortDate = getInstant(
                            year = 2021,
                            month = 9,
                            day = 2,
                            hourOfDay = 18,
                            minute = 13
                        )!!,
                        location = Location(name = "Max place", longitude = 4.0, latitude = 5.0)
                    ),
                    ingestionsWithCompanions = listOf(
                        IngestionWithCompanionAndCustomUnit(
                            ingestion = Ingestion(
                                substanceName = "Cocaine",
                                time = getInstant(
                                    year = 2021,
                                    month = 9,
                                    day = 2,
                                    hourOfDay = 18,
                                    minute = 13
                                )!!,
                                endTime = null,
                                administrationRoute = AdministrationRoute.INSUFFLATED,
                                dose = 30.0,
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
                        IngestionWithCompanionAndCustomUnit(
                            ingestion = Ingestion(
                                substanceName = "Ketamine",
                                time = getInstant(
                                    year = 2021,
                                    month = 9,
                                    day = 2,
                                    hourOfDay = 18,
                                    minute = 13
                                )!!,
                                endTime = null,
                                administrationRoute = AdministrationRoute.INSUFFLATED,
                                dose = 20.0,
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
                                substanceName = "Ketamine",
                                color = AdaptiveColor.MINT
                            ),
                            customUnit = null
                        )
                    ),
                    ratings = listOf(
                        ShulginRating(
                            time = Instant.now().minus(15, ChronoUnit.MINUTES),
                            creationDate = Instant.now(),
                            option = ShulginRatingOption.TWO_PLUS,
                            experienceId = 0
                        )
                    )
                ),
                ExperienceWithIngestionsCompanionsAndRatings(
                    experience = Experience(
                        id = 0,
                        title = "Last day in Stockholm",
                        text = "Some notes",
                        sortDate = getInstant(
                            year = 2021,
                            month = 7,
                            day = 22,
                            hourOfDay = 18,
                            minute = 13
                        )!!,
                        location = Location(name = "Max place", longitude = 4.0, latitude = 5.0)
                    ),
                    ingestionsWithCompanions = listOf(
                        IngestionWithCompanionAndCustomUnit(
                            ingestion = Ingestion(
                                substanceName = "LSD",
                                time = getInstant(
                                    year = 2021,
                                    month = 7,
                                    day = 22,
                                    hourOfDay = 18,
                                    minute = 13
                                )!!,
                                endTime = null,
                                administrationRoute = AdministrationRoute.ORAL,
                                dose = 90.0,
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
                                color = AdaptiveColor.PURPLE
                            ),
                            customUnit = null
                        ),
                        IngestionWithCompanionAndCustomUnit(
                            ingestion = Ingestion(
                                substanceName = "Cocaine",
                                time = getInstant(
                                    year = 2021,
                                    month = 7,
                                    day = 22,
                                    hourOfDay = 18,
                                    minute = 13
                                )!!,
                                endTime = null,
                                administrationRoute = AdministrationRoute.INSUFFLATED,
                                dose = 20.0,
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
                        )
                    ),
                    ratings = listOf()
                ),
            )
        )
}