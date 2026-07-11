package computer.ethernet.onset.data.substances.classes.roa

import kotlin.math.floor
import kotlin.math.roundToInt

data class RoaDose(
    val units: String,
    val lightMin: Double?,
    val commonMin: Double?,
    val strongMin: Double?,
    val heavyMin: Double?,
) {
    fun getDoseClass(ingestionDose: Double?, ingestionUnits: String? = units): DoseClass? {
        if (ingestionUnits != units || ingestionDose == null) return null
        return if (lightMin != null && ingestionDose < lightMin) {
            DoseClass.THRESHOLD
        } else if (lightMin != null && commonMin != null && lightMin <= ingestionDose && ingestionDose < commonMin) {
            DoseClass.LIGHT
        } else if (commonMin != null && strongMin != null && commonMin <= ingestionDose && ingestionDose < strongMin) {
            DoseClass.COMMON
        } else if (strongMin != null && heavyMin != null && strongMin <= ingestionDose && ingestionDose < heavyMin) {
            DoseClass.STRONG
        } else if (heavyMin != null && heavyMin <= ingestionDose) {
            DoseClass.HEAVY
        } else {
            null
        }
    }

    fun getNumDots(ingestionDose: Double?, ingestionUnits: String? = units): Int? {
        if (ingestionUnits != units || ingestionDose == null) return null
        if (lightMin != null && ingestionDose < lightMin) {
            return 0
        } else if (lightMin != null && commonMin != null && lightMin <= ingestionDose && ingestionDose < commonMin) {
            return 1
        } else if (commonMin != null && strongMin != null && commonMin <= ingestionDose && ingestionDose < strongMin) {
            return 2
        } else if (strongMin != null && heavyMin != null && strongMin <= ingestionDose && ingestionDose < heavyMin) {
            return 3
        } else if (heavyMin != null) {
            return if (heavyMin <= ingestionDose) {
                val timesHeavy = floor(ingestionDose.div(heavyMin)).roundToInt()
                val rest = ingestionDose.rem(heavyMin)
                return (timesHeavy * 4) + getNumDotsUpTo4(dose = rest)
            } else {
                floor(ingestionDose / heavyMin).roundToInt()
            }
        } else {
            return null
        }
    }

    private fun getNumDotsUpTo4(dose: Double): Int {
        return if (lightMin != null && dose < lightMin) {
            0
        } else if (lightMin != null && commonMin != null && lightMin <= dose && dose < commonMin) {
            1
        } else if (commonMin != null && strongMin != null && commonMin <= dose && dose < strongMin) {
            2
        } else if (strongMin != null && heavyMin != null && strongMin <= dose && dose < heavyMin) {
            3
        } else if (heavyMin != null) {
            floor(dose / heavyMin).roundToInt()
        } else {
            0
        }
    }

    val shouldUseVolumetricDosing: Boolean
        get() {
            if (units == "µg") return true
            return if (units == "mg") {
                val sample = commonMin ?: strongMin
                sample != null && sample < 15
            } else {
                false
            }
        }

    val averageCommonDose: Double? get() {
        return if (commonMin != null && strongMin != null) {
            (commonMin + strongMin) / 2
        } else {
            null
        }
    }

    fun getStrengthRelativeToCommonDose(dose: Double): Double? {
        return averageCommonDose?.let {
            if (it > 0) {
                dose/it
            } else {
                null
            }
        }
    }
}