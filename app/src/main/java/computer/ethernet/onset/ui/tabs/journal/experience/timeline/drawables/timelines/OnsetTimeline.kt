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


data class OnsetTimeline(
    val onset: FullDurationRange,
    val ingestionTimeRelativeToStartInSeconds: Float,
) : TimelineDrawable {

    override val nonNormalisedHeight: Float = 0f
    override var referenceHeight = 1f

    override val endOfLineRelativeToStartInSeconds: Float =
        ingestionTimeRelativeToStartInSeconds + onset.maxInSeconds

    override fun drawTimeLine(
        drawScope: DrawScope,
        canvasHeight: Float,
        pixelsPerSec: Float,
        color: Color,
        density: Density
    ) {
        val weight = 0.5f
        val startX = ingestionTimeRelativeToStartInSeconds * pixelsPerSec
        val onsetEndX =
            startX + (onset.interpolateAtValueInSeconds(weight) * pixelsPerSec)
        drawScope.drawPath(
            path = Path().apply {
                moveTo(x = startX, y = canvasHeight)
                lineTo(x = onsetEndX, y = canvasHeight)
            },
            color = color,
            style = density.normalStroke
        )
        drawScope.drawCircle(
            color = color,
            radius = density.ingestionDotRadius,
            center = Offset(x = ingestionTimeRelativeToStartInSeconds * pixelsPerSec, y = canvasHeight)
        )
    }
}

fun RoaDuration.toOnsetTimeline(ingestionTimeRelativeToStartInSeconds: Float): OnsetTimeline? {
    val fullOnset = onset?.toFullDurationRange()
    return if (fullOnset != null) {
        OnsetTimeline(
            onset = fullOnset,
            ingestionTimeRelativeToStartInSeconds = ingestionTimeRelativeToStartInSeconds
        )
    } else {
        null
    }
}