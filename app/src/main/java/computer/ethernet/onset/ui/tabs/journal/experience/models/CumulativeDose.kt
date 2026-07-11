package computer.ethernet.onset.ui.tabs.journal.experience.models

import computer.ethernet.onset.data.substances.AdministrationRoute
import computer.ethernet.onset.ui.tabs.search.substance.roa.toReadableString

data class CumulativeDose(
    val substanceName: String,
    val cumulativeRouteAndDose: List<CumulativeRouteAndDose>
)

data class CumulativeRouteAndDose(
    val cumulativeDose: Double,
    val units: String,
    val isEstimate: Boolean,
    val cumulativeDoseStandardDeviation: Double?,
    val numDots: Int?,
    val route: AdministrationRoute,
    val hasMoreThanOneIngestion: Boolean
) {
    val doseDescription: String get()
    {
        val description = cumulativeDose.toReadableString() + " $units"
        return if (isEstimate) {
            if (cumulativeDoseStandardDeviation != null && cumulativeDoseStandardDeviation > 0) {
                "${cumulativeDose.toReadableString()}±${cumulativeDoseStandardDeviation.toReadableString()} $units"
            } else {
                "~$description"
            }
        } else {
            description
        }
    }
}