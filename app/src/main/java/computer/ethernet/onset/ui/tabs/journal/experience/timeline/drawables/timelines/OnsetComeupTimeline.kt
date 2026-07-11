package computer.ethernet.onset.ui.tabs.journal.experience.timeline.drawables.timelines

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Density
import computer.ethernet.onset.data.substances.classes.roa.RoaDuration
import computer.ethernet.onset.ui.tabs.journal.experience.timeline.drawables.TimelineDrawable
import computer.ethernet.onset.ui.tabs.journal.experience.timeline.ingestionDotRadius
import computer.ethernet.onset.ui.tabs.journal.experience.timeline.normalStroke
import computer.ethernet.onset.ui.tabs.journal.experience.timeline.shapeAlpha
import computer.ethernet.onset.ui.tabs.journal.experience.timeline.strokeWidth

data class OnsetComeupTimeline(
    val onset: FullDurationRange,
    val comeup: FullDurationRange,
    val ingestionTimeRelativeToStartInSeconds: Float,
    override val nonNormalisedHeight: Float,
) : TimelineDrawable {

    override var referenceHeight = 1f

    override val endOfLineRelativeToStartInSeconds: Float =
        ingestionTimeRelativeToStartInSeconds + onset.maxInSeconds + comeup.maxInSeconds

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
        val weight = 0.5f
        val startX = ingestionTimeRelativeToStartInSeconds * pixelsPerSec
        val onsetEndX =
            startX + (onset.interpolateAtValueInSeconds(weight) * pixelsPerSec)
        val comeupEndX =
            onsetEndX + (comeup.interpolateAtValueInSeconds(weight) * pixelsPerSec)
        val path = Path().apply {
            moveTo(x = startX, y = canvasHeight)
            lineTo(x = onsetEndX, y = canvasHeight)
            lineTo(x = comeupEndX, y = top)
        }
        drawScope.drawPath(
            path = path,
            color = color,
            style = density.normalStroke
        )
        path.lineTo(x = comeupEndX, y = canvasHeight + drawScope.strokeWidth / 2)
        path.lineTo(x = startX, y = canvasHeight + drawScope.strokeWidth / 2)
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

fun RoaDuration.toOnsetComeupTimeline(
    ingestionTimeRelativeToStartInSeconds: Float,
    nonNormalisedHeight: Float,
): OnsetComeupTimeline? {
    val fullOnset = onset?.toFullDurationRange()
    val fullComeup = comeup?.toFullDurationRange()
    return if (fullOnset != null && fullComeup != null) {
        OnsetComeupTimeline(
            onset = fullOnset,
            comeup = fullComeup,
            ingestionTimeRelativeToStartInSeconds = ingestionTimeRelativeToStartInSeconds,
            nonNormalisedHeight = nonNormalisedHeight,
        )
    } else {
        null
    }
}