package computer.ethernet.onset.ui.tabs.journal.addingestion.dose.customunit

import androidx.compose.runtime.Composable
import computer.ethernet.onset.data.room.experiences.entities.CustomUnit
import computer.ethernet.onset.data.substances.classes.roa.RoaDose
import computer.ethernet.onset.ui.tabs.search.substance.roa.dose.DoseClassificationRow
import kotlin.math.round

@Composable
fun CustomUnitRoaDoseView(roaDose: RoaDose, customUnit: CustomUnit) {
    fun convertToNewUnit(oldDose: Double?): Double? {
        return customUnit.dose?.let { dosePerUnit ->
            if (oldDose != null) {
                return@let roundToSensibly(oldDose / dosePerUnit)
            } else {
                return@let null
            }
        }
    }
    DoseClassificationRow(
        lightMin = convertToNewUnit(roaDose.lightMin),
        commonMin = convertToNewUnit(roaDose.commonMin),
        strongMin = convertToNewUnit(roaDose.strongMin),
        heavyMin = convertToNewUnit(roaDose.heavyMin),
        unit = customUnit.unit
    )
}

fun roundToSensibly(num: Double): Double {
    return if (num > 100) {
        round(num)
    } else if (num > 10) {
        round(num * 10.0) / 10.0
    } else {
        round(num * 100.0) / 100.0
    }
}