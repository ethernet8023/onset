package computer.ethernet.onset.service

import computer.ethernet.onset.data.room.experiences.entities.AdaptiveColor
import computer.ethernet.onset.data.substances.classes.roa.IngestionPhase
import computer.ethernet.onset.ui.tabs.journal.experience.timeline.AllTimelinesModel
import java.time.Instant

data class LiveActivityData(
    val experienceId: Int,
    val experienceTitle: String,
    val ingestions: List<IngestionDisplay>,
    val timelineModel: AllTimelinesModel?,
    val firstIngestionTime: Instant
)

data class IngestionDisplay(
    val substanceName: String,
    val dose: String,
    val route: String,
    val phase: IngestionPhase,
    val color: AdaptiveColor
)
