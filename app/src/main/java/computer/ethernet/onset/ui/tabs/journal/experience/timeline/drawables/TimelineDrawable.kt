package computer.ethernet.onset.ui.tabs.journal.experience.timeline.drawables

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Density

interface TimelineDrawable {
    fun drawTimeLine(
        drawScope: DrawScope,
        canvasHeight: Float,
        pixelsPerSec: Float,
        color: Color,
        density: Density
    )

    val nonNormalisedHeight: Float
    var referenceHeight: Float
    val endOfLineRelativeToStartInSeconds: Float
}