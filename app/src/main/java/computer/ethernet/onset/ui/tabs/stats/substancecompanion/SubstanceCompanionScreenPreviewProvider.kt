package computer.ethernet.onset.ui.tabs.stats.substancecompanion

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import computer.ethernet.onset.data.room.experiences.entities.AdaptiveColor
import computer.ethernet.onset.data.room.experiences.entities.Experience
import computer.ethernet.onset.data.room.experiences.entities.Ingestion
import computer.ethernet.onset.data.room.experiences.entities.StomachFullness
import computer.ethernet.onset.data.room.experiences.entities.SubstanceCompanion
import computer.ethernet.onset.data.substances.AdministrationRoute
import computer.ethernet.onset.ui.utils.getInstant

class SubstanceCompanionScreenPreviewProvider :
    PreviewParameterProvider<Pair<SubstanceCompanion, List<IngestionsBurst>>> {
    override val values: Sequence<Pair<SubstanceCompanion, List<IngestionsBurst>>> = sequenceOf(
        Pair(
            first = SubstanceCompanion(
                substanceName = "Cocaine",
                color = AdaptiveColor.BLUE
            ),
            second = listOf(
                IngestionsBurst(
                    timeUntil = "35 min",
                    experience = Experience(
                        id = 1,
                        title = "Vienna Weekend",
                        creationDate = getInstant(
                            year = 2022,
                            month = 7,
                            day = 20,
                            hourOfDay = 14,
                            minute = 20
                        )!!,
                        sortDate = getInstant(
                            year = 2022,
                            month = 7,
                            day = 20,
                            hourOfDay = 14,
                            minute = 20
                        )!!,
                        text = "",
                        isFavorite = false,
                        location = null
                    ),
                    ingestions = listOf(
                        IngestionsBurst.IngestionAndCustomUnit(
                            ingestion = Ingestion(
                                substanceName = "Cocaine",
                                time = getInstant(
                                    year = 2022,
                                    month = 7,
                                    day = 20,
                                    hourOfDay = 14,
                                    minute = 20
                                )!!,
                                endTime = null,
                                administrationRoute = AdministrationRoute.INSUFFLATED,
                                dose = 20.0,
                                isDoseAnEstimate = false,
                                estimatedDoseStandardDeviation = null,
                                units = "mg",
                                experienceId = 0,
                                notes = "This is one note",
                                stomachFullness = StomachFullness.EMPTY,
                                consumerName = null,
                                customUnitId = null
                            ),
                            customUnit = null
                        ),
                        IngestionsBurst.IngestionAndCustomUnit(
                            ingestion = Ingestion(
                                substanceName = "Cocaine",
                                time = getInstant(
                                    year = 2022,
                                    month = 7,
                                    day = 20,
                                    hourOfDay = 13,
                                    minute = 40
                                )!!,
                                endTime = null,
                                administrationRoute = AdministrationRoute.INSUFFLATED,
                                dose = 30.0,
                                isDoseAnEstimate = false,
                                estimatedDoseStandardDeviation = null,
                                units = "mg",
                                experienceId = 0,
                                notes = "This is one note",
                                stomachFullness = StomachFullness.EMPTY,
                                consumerName = null,
                                customUnitId = null
                            ),
                            customUnit = null
                        )
                    )
                ),
                IngestionsBurst(
                    timeUntil = "2 weeks",
                    experience = Experience(
                        id = 1,
                        title = "21. Birthday",
                        creationDate = getInstant(
                            year = 2022,
                            month = 7,
                            day = 4,
                            hourOfDay = 14,
                            minute = 20
                        )!!,
                        sortDate = getInstant(
                            year = 2022,
                            month = 7,
                            day = 4,
                            hourOfDay = 14,
                            minute = 20
                        )!!,
                        text = "",
                        isFavorite = false,
                        location = null
                    ),
                    ingestions = listOf(
                        IngestionsBurst.IngestionAndCustomUnit(
                            ingestion = Ingestion(
                                substanceName = "Cocaine",
                                time = getInstant(
                                    year = 2022,
                                    month = 7,
                                    day = 4,
                                    hourOfDay = 14,
                                    minute = 20
                                )!!,
                                endTime = null,
                                administrationRoute = AdministrationRoute.INSUFFLATED,
                                dose = 10.0,
                                isDoseAnEstimate = false,
                                estimatedDoseStandardDeviation = null,
                                units = "mg",
                                experienceId = 0,
                                notes = "This is one note",
                                stomachFullness = StomachFullness.EMPTY,
                                consumerName = null,
                                customUnitId = null
                            ),
                            customUnit = null
                        ),
                        IngestionsBurst.IngestionAndCustomUnit(
                            ingestion = Ingestion(
                                substanceName = "Cocaine",
                                time = getInstant(
                                    year = 2022,
                                    month = 7,
                                    day = 4,
                                    hourOfDay = 13,
                                    minute = 40
                                )!!,
                                endTime = null,
                                administrationRoute = AdministrationRoute.INSUFFLATED,
                                dose = 20.0,
                                isDoseAnEstimate = false,
                                estimatedDoseStandardDeviation = null,
                                units = "mg",
                                experienceId = 0,
                                notes = "This is one note",
                                stomachFullness = StomachFullness.EMPTY,
                                consumerName = null,
                                customUnitId = null
                            ),
                            customUnit = null
                        )
                    )
                )
            )
        )
    )
}