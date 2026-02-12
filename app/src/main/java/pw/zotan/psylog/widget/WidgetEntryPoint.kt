package pw.zotan.psylog.widget

import pw.zotan.psylog.service.LiveActivityDataProvider
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WidgetEntryPoint {
    fun liveActivityDataProvider(): LiveActivityDataProvider
}
