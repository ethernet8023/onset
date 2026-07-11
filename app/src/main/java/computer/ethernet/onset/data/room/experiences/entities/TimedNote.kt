package computer.ethernet.onset.data.room.experiences.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity
data class TimedNote(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var creationDate: Instant = Instant.now(),
    var time: Instant,
    var note: String,
    var color: AdaptiveColor,
    var experienceId: Int,
    var isPartOfTimeline: Boolean
)
