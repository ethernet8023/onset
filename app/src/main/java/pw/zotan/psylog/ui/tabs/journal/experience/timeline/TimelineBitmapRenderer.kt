package pw.zotan.psylog.ui.tabs.journal.experience.timeline

import android.graphics.Bitmap
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import java.time.Instant

// renders an AllTimelinesModel to a bitmap — used for notification and widget.
// skips ratings/notes/drag interaction since those don't make sense at this size.
fun renderTimelineBitmap(
    model: AllTimelinesModel,
    widthPx: Int,
    heightPx: Int,
    isDarkTheme: Boolean,
    density: Density
): Bitmap {
    val bitmap = Bitmap.createBitmap(widthPx, heightPx, Bitmap.Config.ARGB_8888)
    val androidCanvas = android.graphics.Canvas(bitmap)
    val composeCanvas = Canvas(androidCanvas)
    val size = Size(widthPx.toFloat(), heightPx.toFloat())

    val drawScope = CanvasDrawScope()
    drawScope.draw(
        density = density,
        layoutDirection = LayoutDirection.Ltr,
        canvas = composeCanvas,
        size = size
    ) {
        val pixelsPerSec = size.width / model.widthInSeconds

        model.groupDrawables.forEach { group ->
            group.drawTimeLine(
                drawScope = this,
                canvasHeight = size.height,
                pixelsPerSec = pixelsPerSec,
                color = group.color.getComposeColor(isDarkTheme),
                density = density
            )
        }

        drawCurrentTime(
            startTime = model.startTime,
            timelineWidthInSeconds = model.widthInSeconds,
            currentTime = Instant.now(),
            pixelsPerSec = pixelsPerSec,
            isDarkTheme = isDarkTheme,
            canvasHeightOuter = size.height
        )
    }

    return bitmap
}
