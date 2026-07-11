package computer.ethernet.onset.ui.tabs.search.substance.roa

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale


fun Double.toReadableString(): String {
    val numberOfSignificantDigits = if (this > 1) 3 else 2
    val roundedNumber = roundToSignificantDigits(this, numberOfSignificantDigits)
    return formatToMaximumFractionDigits(roundedNumber, 6)
}

fun Double.toPreservedString(): String {
    val numberFormat = NumberFormat.getInstance(Locale.US).apply {
        isGroupingUsed = false
    }
    return numberFormat.format(this)
}

fun roundToSignificantDigits(value: Double, significantDigits: Int): Double {
    if (value == 0.0) return 0.0
    val bigDecimal = BigDecimal(value)
    val scale = significantDigits - bigDecimal.precision() + bigDecimal.scale()
    return bigDecimal.setScale(scale, RoundingMode.HALF_UP).toDouble()
}

fun formatToMaximumFractionDigits(value: Double, maximumFractionDigits: Int): String {
    val df = DecimalFormat()
    df.decimalFormatSymbols = DecimalFormatSymbols(Locale.US)
    df.isDecimalSeparatorAlwaysShown = false
    df.minimumFractionDigits = 0
    df.maximumFractionDigits = maximumFractionDigits
    df.isGroupingUsed = false

    return df.format(value)
}
