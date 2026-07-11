package computer.ethernet.onset.data.room.experiences.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import computer.ethernet.onset.data.substances.AdministrationRoute
import java.time.Instant

@Entity
data class Ingestion(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val substanceName: String,
    var time: Instant,
    var endTime: Instant? = null,
    var creationDate: Instant? = Instant.now(),
    val administrationRoute: AdministrationRoute,
    var dose: Double?,
    var isDoseAnEstimate: Boolean,
    var estimatedDoseStandardDeviation: Double?,
    var units: String?,
    var experienceId: Int,
    var notes: String?,
    var stomachFullness: StomachFullness?,
    var consumerName: String?,
    var customUnitId: Int?
)
