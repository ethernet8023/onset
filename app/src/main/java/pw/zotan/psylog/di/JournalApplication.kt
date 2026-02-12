package pw.zotan.psylog.di

import android.app.Application
import android.util.Log
import pw.zotan.psylog.service.LiveActivityManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class JournalApplication : Application() {

    @Inject
    lateinit var liveActivityManager: LiveActivityManager

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        // resume live activity service if there's an active experience
        applicationScope.launch {
            try {
                liveActivityManager.startIfNeeded(this@JournalApplication)
            } catch (e: Exception) {
                Log.e("JournalApplication", "failed to start live activity service", e)
            }
        }
    }
}
