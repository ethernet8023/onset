package computer.ethernet.onset.service

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LiveActivityManager @Inject constructor() {

    fun startIfNeeded(context: Context) {
        Log.d("LiveActivityManager", "startIfNeeded called")
        val intent = Intent(context, LiveActivityService::class.java)
        ContextCompat.startForegroundService(context, intent)
    }

    fun stop(context: Context) {
        val intent = Intent(context, LiveActivityService::class.java)
        context.stopService(intent)
    }
}
