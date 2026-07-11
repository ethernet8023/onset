package computer.ethernet.onset.ui.tabs.journal.experience.timeline.drawables.timelines

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Density
import computer.ethernet.onset.data.substances.classes.roa.RoaDuration
import computer.ethernet.onset.ui.tabs.journal.experience.timeline.dottedStroke
import computer.ethernet.onset.ui.tabs.journal.experience.timeline.drawables.TimelineDrawable
import computer.ethernet.onset.ui.tabs.journal.experience.timeline.ingestionDotRadius
import computer.ethernet.onset.ui.tabs.journal.experience.timeline.shapeAlpha

data class TotalTimeline(
    val total: FullDurationRange,
    val totalWeight: Float,
    val percentSmoothness: Float = 0.5f,
    val ingestionTimeRelativeToStartInSeconds: Float,
    override val nonNormalisedHeight: Float,
) : TimelineDrawable {

    override var referenceHeight = 1f

    override val endOfLineRelativeToStartInSeconds: Float =
        ingestionTimeRelativeToStartInSeconds + total.maxInSeconds

    override fun drawTimeLine(
        drawScope: DrawScope,
        canvasHeight: Float,
        pixelsPerSec: Float,
        color: Color,
        density: Density
    ) {
        val normalisedHeight = nonNormalisedHeight / referenceHeight
        val heightInPx = normalisedHeight * canvasHeight
        val top = canvasHeight - heightInPx
        val path = Path().apply {
            val totalMinX = total.minInSeconds * pixelsPerSec
            val totalX = total.interpolateAtValueInSeconds(totalWeight) * pixelsPerSec
            val startX = ingestionTimeRelativeToStartInSeconds * pixelsPerSec
            moveTo(startX, canvasHeight)
            endSmoothLineTo(
                smoothnessBetween0And1 = percentSmoothness,
                startX = startX,
                endX = startX + (totalMinX / 2),
                endY = top
            )
            startSmoothLineTo(
                smoothnessBetween0And1 = percentSmoothness,
                startX = startX + (totalMinX / 2),
                startY = top,
                endX = startX + totalX,
                endY = canvasHeight
            )
        }
        drawScope.drawPath(
            path = path,
            color = color,
            style = density.dottedStroke
        )
        path.close()
        drawScope.drawPath(
            path = path,
            color = color.copy(alpha = shapeAlpha)
        )
        drawScope.drawCircle(
            color = color,
            radius = density.ingestionDotRadius,
            center = Offset(x = ingestionTimeRelativeToStartInSeconds * pixelsPerSec, y = canvasHeight)
        )
    }
}

fun RoaDuration.toTotalTimeline(
    totalWeight: Float,
    ingestionTimeRelativeToStartInSeconds: Float,
    nonNormalisedHeight: Float,
): TotalTimeline? {
    val fullTotal = total?.toFullDurationRange()
    return if (fullTotal != null) {
        TotalTimeline(
            total = fullTotal,
            totalWeight = totalWeight,
            ingestionTimeRelativeToStartInSeconds = ingestionTimeRelativeToStartInSeconds,
            nonNormalisedHeight = nonNormalisedHeight,
        )
    } else {
        null
    }
}

// estimate total duration from whatever phases are available -
// sums all non-null phase durations into a synthetic total range
fun RoaDuration.toEstimatedTotalTimeline(
    totalWeight: Float,
    ingestionTimeRelativeToStartInSeconds: Float,
    nonNormalisedHeight: Float,
): TotalTimeline? {
    val phases = listOfNotNull(onset, comeup, peak, offset, afterglow)
    if (phases.isEmpty()) return null
    val mins = phases.mapNotNull { it.minInSec }
    val maxs = phases.mapNotNull { it.maxInSec }
    if (mins.isEmpty() || maxs.isEmpty()) return null
    val estimatedTotal = FullDurationRange(
        minInSeconds = mins.sum(),
        maxInSeconds = maxs.sum()
    )
    return TotalTimeline(
        total = estimatedTotal,
        totalWeight = totalWeight,
        ingestionTimeRelativeToStartInSeconds = ingestionTimeRelativeToStartInSeconds,
        nonNormalisedHeight = nonNormalisedHeight,
    )
}