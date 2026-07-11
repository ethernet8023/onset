package computer.ethernet.onset.data.room.experiences.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity
data class Experience(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    var text: String,
    val creationDate: Instant = Instant.now(),
    val sortDate: Instant,
    var isFavorite: Boolean = false,
    @Embedded var location: Location?
)
