package computer.ethernet.onset.data.room.experiences.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Entity
@Serializable
data class CustomSubstance(
    @Transient
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    var units: String,
    var description: String,
)