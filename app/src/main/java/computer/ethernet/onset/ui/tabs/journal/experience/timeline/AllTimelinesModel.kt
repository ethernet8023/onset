package computer.ethernet.onset.ui.tabs.journal.experience.timeline

import computer.ethernet.onset.data.room.experiences.entities.AdaptiveColor
import computer.ethernet.onset.data.substances.classes.roa.RoaDuration
import computer.ethernet.onset.ui.tabs.journal.experience.components.DataForOneEffectLine
import computer.ethernet.onset.ui.tabs.journal.experience.timeline.drawables.AxisDrawable
import computer.ethernet.onset.ui.tabs.journal.experience.timeline.drawables.GroupDrawable
import java.time.Duration
import java.time.Instant
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class AllTimelinesModel(
    dataForLines: List<DataForOneEffectLine>,
    val dataForRatings: List<DataForOneRating>,
    val timedNotes: List<DataForOneTimedNote>,
    areSubstanceHeightsIndependent: Boolean,
) {
    val startTime: Instant
    val widthInSeconds: Float
    val groupDrawables: List<GroupDrawable>
    val axisDrawable: AxisDrawable

    data class RoaGroup(
        val color: AdaptiveColor,
        val roaDuration: RoaDuration?,
        val weightedLines: List<WeightedLine>
    )

    init {
        val ratingTimes = dataForRatings.map { it.time }
        val ingestionTimes = dataForLines.map { it.startTime }
        val noteTimes = timedNotes.map { it.time }
        val allStartTimeCandidates = ratingTimes + ingestionTimes + noteTimes
        startTime =
            allStartTimeCandidates.reduce { acc, date -> if (acc.isBefore(date)) acc else date }
        val roaGroups = dataForLines.groupBy { it.substanceName }
            .flatMap { substanceGroup ->
                val linesPerSubstance = substanceGroup.value
                return@flatMap linesPerSubstance.groupBy { it.route }.map { routeGroup ->
                    val linesPerRoute = routeGroup.value
                    return@map RoaGroup(
                        color = linesPerRoute.first().color,
                        roaDuration = linesPerRoute.first().roaDuration,
                        weightedLines = linesPerRoute.map {
                            WeightedLine(
                                startTime = it.startTime,
                                endTime = it.endTime,
                                horizontalWeight = it.horizontalWeight,
                                height = it.height
                            )
                        })
                }
            }
        val groupDrawables = roaGroups.map { group ->
            GroupDrawable(
                startTimeGraph = startTime,
                color = group.color,
                roaDuration = group.roaDuration,
                weightedLines = group.weightedLines,
                areSubstanceHeightsIndependent = areSubstanceHeightsIndependent
            )
        }
        val overallMaxHeight = groupDrawables.maxOfOrNull { it.nonNormalisedHeight } ?: 1f
        groupDrawables.forEach { it.normaliseHeight(overallMaxHeight) }
        this.groupDrawables = groupDrawables
        val maxWidthOfGroups: Float = groupDrawables.maxOfOrNull {
            it.endOfLineRelativeToStartInSeconds
        } ?: 0f
        val latestRating = ratingTimes.maxOrNull()
        val maxWidthRating: Float = if (latestRating != null) {
            Duration.between(startTime, latestRating).seconds.toFloat()
        } else {
            0f
        }
        val latestNote = noteTimes.maxOrNull()
        val maxWidthNote: Float = if (latestNote != null) {
            Duration.between(startTime, latestNote).seconds.toFloat()
        } else {
            0f
        }
        val maxCandidates = listOf(
            maxWidthOfGroups,
            maxWidthRating,
            maxWidthNote,
            2.hours.inWholeSeconds.toFloat()
        )
        widthInSeconds = maxCandidates.max() + 10.minutes.inWholeSeconds.toFloat()
        axisDrawable = AxisDrawable(startTime, widthInSeconds)
    }
}
