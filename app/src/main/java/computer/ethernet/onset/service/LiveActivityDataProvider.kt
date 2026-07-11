package computer.ethernet.onset.service

import computer.ethernet.onset.data.room.experiences.ExperienceRepository
import computer.ethernet.onset.data.room.experiences.entities.AdaptiveColor
import computer.ethernet.onset.data.substances.classes.roa.calculateCurrentPhase
import computer.ethernet.onset.data.substances.repositories.SubstanceRepository
import computer.ethernet.onset.ui.tabs.journal.addingestion.time.hourLimitToSeparateIngestions
import computer.ethernet.onset.ui.tabs.journal.experience.ExperienceViewModel
import computer.ethernet.onset.ui.tabs.journal.experience.models.IngestionElement
import computer.ethernet.onset.ui.tabs.journal.experience.timeline.AllTimelinesModel
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LiveActivityDataProvider @Inject constructor(
    private val experienceRepo: ExperienceRepository,
    private val substanceRepo: SubstanceRepository
) {
    suspend fun getCurrentLiveActivityData(): LiveActivityData? {
        val since = Instant.now().minus(hourLimitToSeparateIngestions, ChronoUnit.HOURS)
        val experienceWithIngestions =
            experienceRepo.getMostRecentActiveExperience(since) ?: return null

        val experience = experienceWithIngestions.experience
        val ingestionsWithCompanions = experienceWithIngestions.ingestionsWithCompanions
            .sortedBy { it.ingestion.time }

        if (ingestionsWithCompanions.isEmpty()) return null

        val now = Instant.now()

        // build ingestion display list
        val ingestionDisplays = ingestionsWithCompanions.map { iwc ->
            val ingestion = iwc.ingestion
            val substance = substanceRepo.getSubstance(ingestion.substanceName)
            val roa = substance?.getRoa(ingestion.administrationRoute)

            val doseText = iwc.doseDescription
            val phase = calculateCurrentPhase(
                ingestionTime = ingestion.time,
                roaDuration = roa?.roaDuration,
                now = now
            )

            IngestionDisplay(
                substanceName = ingestion.substanceName,
                dose = doseText,
                route = ingestion.administrationRoute.displayText.lowercase(),
                phase = phase,
                color = iwc.substanceCompanion?.color ?: AdaptiveColor.BLUE
            )
        }

        // build timeline model — reuse the same logic as ExperienceViewModel
        val ingestionElements = ingestionsWithCompanions.map { iwc ->
            val ingestion = iwc.ingestion
            val substance = substanceRepo.getSubstance(ingestion.substanceName)
            val roa = substance?.getRoa(ingestion.administrationRoute)
            val numDots = roa?.roaDose?.getNumDots(
                ingestionDose = iwc.pureDose,
                ingestionUnits = iwc.originalUnit
            )
            IngestionElement(
                ingestionWithCompanionAndCustomUnit = iwc,
                roaDuration = roa?.roaDuration,
                numDots = numDots
            )
        }

        val substances = ingestionElements.mapNotNull {
            substanceRepo.getSubstance(it.ingestionWithCompanionAndCustomUnit.ingestion.substanceName)
        }

        val dataForEffectLines = ExperienceViewModel.getDataForEffectTimelines(
            ingestionElements = ingestionElements,
            substances = substances
        )

        val timelineModel = if (dataForEffectLines.isNotEmpty() &&
            !ingestionElements.all { it.roaDuration == null }
        ) {
            AllTimelinesModel(
                dataForLines = dataForEffectLines,
                dataForRatings = emptyList(),
                timedNotes = emptyList(),
                areSubstanceHeightsIndependent = true
            )
        } else {
            null
        }

        return LiveActivityData(
            experienceId = experience.id,
            experienceTitle = experience.title,
            ingestions = ingestionDisplays,
            timelineModel = timelineModel,
            firstIngestionTime = ingestionsWithCompanions.first().ingestion.time
        )
    }
}
