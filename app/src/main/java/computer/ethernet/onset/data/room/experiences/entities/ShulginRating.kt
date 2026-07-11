package computer.ethernet.onset.data.room.experiences.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant


@Entity
data class ShulginRating(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var time: Instant?,
    var creationDate: Instant? = Instant.now(),
    var option: ShulginRatingOption,
    var experienceId: Int
)