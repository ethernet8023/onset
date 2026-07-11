package computer.ethernet.onset.widget

import android.content.Context
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.BitmapImageProvider
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.ContentScale
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.FontStyle
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import dagger.hilt.android.EntryPointAccessors
import computer.ethernet.onset.R
import computer.ethernet.onset.service.IngestionDisplay
import computer.ethernet.onset.service.LiveActivityData
import computer.ethernet.onset.ui.tabs.journal.experience.timeline.renderTimelineBitmap

class TimelineWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val entryPoint = EntryPointAccessors.fromApplication(
            context.applicationContext,
            WidgetEntryPoint::class.java
        )
        val dataProvider = entryPoint.liveActivityDataProvider()
        val data = dataProvider.getCurrentLiveActivityData()

        provideContent {
            GlanceTheme {
                if (data != null) {
                    WidgetContent(context, data)
                } else {
                    NoActiveExperience(context)
                }
            }
        }
    }

}

@androidx.compose.runtime.Composable
private fun WidgetContent(context: Context, data: LiveActivityData) {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .appWidgetBackground()
            .background(GlanceTheme.colors.widgetBackground)
            .cornerRadius(16.dp)
            .padding(12.dp),
    ) {
        // timeline bitmap
        data.timelineModel?.let { model ->
            val density = context.resources.displayMetrics.density
            val widthPx = (context.resources.displayMetrics.widthPixels * 0.8f).toInt()
                .coerceAtLeast(200)
            val heightPx = (60 * density).toInt()
            val composeDensity = Density(density)
            val bitmap = renderTimelineBitmap(
                model = model,
                widthPx = widthPx,
                heightPx = heightPx,
                isDarkTheme = true,
                density = composeDensity
            )
            Image(
                provider = BitmapImageProvider(bitmap),
                contentDescription = context.getString(R.string.timeline_description),
                modifier = GlanceModifier.fillMaxWidth().height(60.dp),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = GlanceModifier.height(4.dp))
        }

        // ingestion list
        for (ingestion in data.ingestions) {
            IngestionRow(ingestion)
        }
    }
}

@androidx.compose.runtime.Composable
private fun IngestionRow(ingestion: IngestionDisplay) {
    Row(
        modifier = GlanceModifier
            .fillMaxWidth()
            .padding(vertical = 1.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${ingestion.substanceName} ${ingestion.dose} ${ingestion.route}",
            style = TextStyle(fontSize = 12.sp),
            modifier = GlanceModifier.defaultWeight()
        )
        Text(
            text = ingestion.phase.displayName,
            style = TextStyle(
                fontSize = 12.sp,
                fontStyle = FontStyle.Italic
            )
        )
    }
}

@androidx.compose.runtime.Composable
private fun NoActiveExperience(context: Context) {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .appWidgetBackground()
            .background(GlanceTheme.colors.widgetBackground)
            .cornerRadius(16.dp)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = context.getString(R.string.widget_no_active_experience),
            style = TextStyle(fontSize = 14.sp)
        )
    }
}
