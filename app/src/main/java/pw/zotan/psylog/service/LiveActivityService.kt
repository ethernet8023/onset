package pw.zotan.psylog.service

import android.util.Log
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import pw.zotan.psylog.MainActivity
import pw.zotan.psylog.R
import pw.zotan.psylog.ui.tabs.journal.experience.timeline.renderTimelineBitmap
import pw.zotan.psylog.widget.TimelineWidget
import androidx.glance.appwidget.updateAll
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LiveActivityService : Service() {

    @Inject
    lateinit var dataProvider: LiveActivityDataProvider

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "service created")
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        val initialNotification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(getString(R.string.live_activity_loading))
            .setSilent(true)
            .setOngoing(true)
            .build()

        ServiceCompat.startForeground(
            this,
            NOTIFICATION_ID,
            initialNotification,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            } else {
                0
            }
        )

        serviceScope.launch {
            while (true) {
                val data = dataProvider.getCurrentLiveActivityData()
                if (data == null) {
                    Log.d(TAG, "no active experience — stopping")
                    stopSelf()
                    return@launch
                }
                Log.d(TAG, "updating notification — ${data.ingestions.size} ingestion(s)")
                updateNotification(data)
                updateWidget()
                delay(UPDATE_INTERVAL_MS)
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {
        serviceScope.cancel()
        super.onDestroy()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            getString(R.string.live_activity_channel_name),
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = getString(R.string.live_activity_channel_description)
            setShowBadge(false)
        }
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    private fun updateNotification(data: LiveActivityData) {
        val tapIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(EXTRA_EXPERIENCE_ID, data.experienceId)
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, tapIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // collapsed view — substance summary
        val collapsedView = RemoteViews(packageName, R.layout.notification_live_activity_collapsed)
        val summaryText = data.ingestions.joinToString(" · ") { ingestion ->
            "${ingestion.substanceName} (${ingestion.phase.displayName})"
        }
        collapsedView.setTextViewText(R.id.substance_summary, summaryText)

        // expanded view — timeline bitmap + ingestion rows
        val expandedView = RemoteViews(packageName, R.layout.notification_live_activity)

        // render timeline bitmap if we have a model
        data.timelineModel?.let { model ->
            val density = resources.displayMetrics.density
            val widthPx = (resources.displayMetrics.widthPixels * 0.9f).toInt()
            val heightPx = (80 * density).toInt()
            val composeDensity = androidx.compose.ui.unit.Density(density)
            val bitmap = renderTimelineBitmap(
                model = model,
                widthPx = widthPx,
                heightPx = heightPx,
                isDarkTheme = true,
                density = composeDensity
            )
            expandedView.setImageViewBitmap(R.id.timeline_image, bitmap)
        }

        // clear and rebuild ingestion rows
        expandedView.removeAllViews(R.id.ingestion_container)
        for (ingestion in data.ingestions) {
            val rowView = RemoteViews(packageName, R.layout.notification_ingestion_row)
            val colorInt = ingestion.color.getComposeColor(isDarkTheme = true)
            rowView.setInt(R.id.color_dot, "setColorFilter", colorInt.toArgb())
            rowView.setTextViewText(
                R.id.substance_info,
                "${ingestion.substanceName} ${ingestion.dose} ${ingestion.route}"
            )
            rowView.setTextViewText(R.id.phase_text, ingestion.phase.displayName)
            expandedView.addView(R.id.ingestion_container, rowView)
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(collapsedView)
            .setCustomBigContentView(expandedView)
            .setContentIntent(pendingIntent)
            .setSilent(true)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setCategory(NotificationCompat.CATEGORY_STOPWATCH)
            .setUsesChronometer(true)
            .setWhen(data.firstIngestionTime.toEpochMilli())
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()

        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(NOTIFICATION_ID, notification)
    }

    private suspend fun updateWidget() {
        try {
            TimelineWidget().updateAll(this)
        } catch (_: Exception) {
            // widget might not be placed — that's fine
        }
    }

    companion object {
        const val CHANNEL_ID = "live_activity"
        const val NOTIFICATION_ID = 1
        const val UPDATE_INTERVAL_MS = 30_000L
        const val EXTRA_EXPERIENCE_ID = "experience_id"
        private const val TAG = "LiveActivityService"
    }
}
