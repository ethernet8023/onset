package computer.ethernet.onset.ui.tabs.journal.experience.timeline.drawables.timelines

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Density
import computer.ethernet.onset.data.substances.classes.roa.RoaDuration
import computer.ethernet.onset.ui.tabs.journal.experience.timeline.*
import computer.ethernet.onset.ui.tabs.journal.experience.timeline.drawables.TimelineDrawable

data class OnsetComeupPeakTotalTimeline(
    val onset: FullDurationRange,
    val comeup: FullDurationRange,
    val peak: FullDurationRange,
    val total: FullDurationRange,
    val peakAndTotalWeight: Float,
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
        val onsetAndComeupWeight = 0.5f
        val startX = ingestionTimeRelativeToStartInSeconds * pixelsPerSec
        val onsetEndX =
            startX + (onset.interpolateAtValueInSeconds(onsetAndComeupWeight) * pixelsPerSec)
        val comeupEndX =
            onsetEndX + (comeup.interpolateAtValueInSeconds(onsetAndComeupWeight) * pixelsPerSec)
        val peakEndX =
            comeupEndX + (peak.interpolateAtValueInSeconds(peakAndTotalWeight) * pixelsPerSec)
        drawScope.drawPath(
            path = Path().apply {
                moveTo(x = startX, y = canvasHeight)
                lineTo(x = onsetEndX, y = canvasHeight)
                lineTo(x = comeupEndX, y = top)
                lineTo(x = peakEndX, y = top)
            },
            color = color,
            style = density.normalStroke
        )
        val offsetEndX =
            startX + (total.interpolateAtValueInSeconds(peakAndTotalWeight) * pixelsPerSec)
        drawScope.drawPath(
            path = Path().apply {
                moveTo(x = peakEndX, y = top)
                lineTo(x = offsetEndX, y = canvasHeight)
            },
            color = color,
            style = density.dottedStroke
        )
        val combinedPath = Path().apply {
            val alignedBottom = canvasHeight + drawScope.strokeWidth / 2
            moveTo(x = startX, y = alignedBottom)
            lineTo(x = onsetEndX, y = alignedBottom)
            lineTo(x = comeupEndX, y = top)
            lineTo(x = peakEndX, y = top)
            lineTo(x = offsetEndX, y = alignedBottom)
            close()
        }
        drawScope.drawPath(
            path = combinedPath,
            color = color.copy(alpha = shapeAlpha)
        )
        drawScope.drawCircle(
            color = color,
            radius = density.ingestionDotRadius,
            center = Offset(x = ingestionTimeRelativeToStartInSeconds * pixelsPerSec, y = canvasHeight)
        )
    }
}

fun RoaDuration.toOnsetComeupPeakTotalTimeline(
    peakAndTotalWeight: Float,
    ingestionTimeRelativeToStartInSeconds: Float,
    nonNormalisedHeight: Float,
): OnsetComeupPeakTotalTimeline? {
    val fullOnset = onset?.toFullDurationRange()
    val fullComeup = comeup?.toFullDurationRange()
    val fullPeak = peak?.toFullDurationRange()
    val fullTotal = total?.toFullDurationRange()
    return if (fullOnset != null && fullComeup != null && fullPeak != null && fullTotal != null) {
        OnsetComeupPeakTotalTimeline(
            onset = fullOnset,
            comeup = fullComeup,
            peak = fullPeak,
            total = fullTotal,
            peakAndTotalWeight = peakAndTotalWeight,
            ingestionTimeRelativeToStartInSeconds = ingestionTimeRelativeToStartInSeconds,
            nonNormalisedHeight = nonNormalisedHeight,
        )
    } else {
        null
    }
}