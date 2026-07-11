package computer.ethernet.onset.ui.tabs.journal.calendar

import androidx.lifecycle.ViewModel
import computer.ethernet.onset.data.room.experiences.ExperienceRepository
import computer.ethernet.onset.data.room.experiences.entities.AdaptiveColor
import computer.ethernet.onset.ui.utils.getInstant
import com.kizitonwose.calendar.core.CalendarDay
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DayViewModel @Inject constructor(
    val experienceRepo: ExperienceRepository,
) : ViewModel() {

    suspend fun getExperienceInfo(day: CalendarDay): ExperienceInfo {
        val startOfDay = day.date.atStartOfDay().getInstant()
        val endOfDay = startOfDay.plusMillis(24 * 60 * 60 * 1000)
        val ingestions = experienceRepo.getIngestionsWithCompanions(
            fromInstant = startOfDay,
            toInstant = endOfDay
        )
        return ExperienceInfo(
            experienceIds = ingestions.map { it.ingestion.experienceId }.toSet().toList(),
            colors = ingestions.mapNotNull { it.substanceCompanion?.color }
        )
    }
}

data class ExperienceInfo(
    val experienceIds: List<Int>,
    val colors: List<AdaptiveColor>
)