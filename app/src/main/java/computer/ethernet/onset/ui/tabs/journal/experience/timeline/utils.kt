package computer.ethernet.onset.ui.tabs.journal.experience.timeline

import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp

private val strokeWidthInDp = 5.dp

val Density.normalStroke: Stroke
    get() {
        val width = strokeWidthInDp.toPx()
        return Stroke(
            width = width,
            cap = StrokeCap.Round,
            pathEffect = PathEffect.cornerPathEffect(15f)
        )
    }

val Density.strokeWidth: Float get() = strokeWidthInDp.toPx()

val Density.dottedStroke: Stroke
    get() {
        val length = strokeWidth * 4
        val space = strokeWidth * 3
        return Stroke(
            width = strokeWidth,
            cap = StrokeCap.Round,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(space, length))
        )
    }

private val ingestionDotRadiusInDp = 7.dp

val Density.ingestionDotRadius: Float get() = ingestionDotRadiusInDp.toPx()


const val shapeAlpha = 0.25f
