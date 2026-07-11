package computer.ethernet.onset.ui.tabs.journal.experience.timeline.drawables.timelines

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Density
import computer.ethernet.onset.ui.tabs.journal.experience.timeline.drawables.TimelineDrawable
import computer.ethernet.onset.ui.tabs.journal.experience.timeline.ingestionDotRadius

data class NoTimeline(
    val ingestionTimeRelativeToStartInSeconds: Float
) : TimelineDrawable {

    override val nonNormalisedHeight: Float = 0.01f // low number because if this turns out to be max of timeline then it will be used as overall max which will downsize ingestions with timelines
    override var referenceHeight: Float = 1f

    override val endOfLineRelativeToStartInSeconds: Float =
        ingestionTimeRelativeToStartInSeconds

    override fun drawTimeLine(
        drawScope: DrawScope,
        canvasHeight: Float,
        pixelsPerSec: Float,
        color: Color,
        density: Density
    ) {
        drawScope.drawCircle(
            color = color,
            radius = density.ingestionDotRadius,
            center = Offset(x = ingestionTimeRelativeToStartInSeconds*pixelsPerSec, y = canvasHeight)
        )
    }
}