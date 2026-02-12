package pw.zotan.psylog.service

import pw.zotan.psylog.data.room.experiences.entities.AdaptiveColor
import pw.zotan.psylog.data.substances.classes.roa.IngestionPhase
import pw.zotan.psylog.ui.tabs.journal.experience.timeline.AllTimelinesModel
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
