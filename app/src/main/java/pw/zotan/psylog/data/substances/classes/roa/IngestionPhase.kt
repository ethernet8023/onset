package pw.zotan.psylog.data.substances.classes.roa

import java.time.Duration
import java.time.Instant

enum class IngestionPhase(val displayName: String) {
    ONSET("onset"),
    COMEUP("comeup"),
    PEAK("peak"),
    OFFSET("offset"),
    AFTERGLOW("afterglow"),
    ENDED("ended"),
    UNKNOWN("unknown")
}

// walks through phases using midpoint interpolation (0.5) of each DurationRange.
// accumulates elapsed time — if we haven't used up onset yet, we're in onset, etc.
fun calculateCurrentPhase(
    ingestionTime: Instant,
    roaDuration: RoaDuration?,
    now: Instant = Instant.now()
): IngestionPhase {
    if (roaDuration == null) return IngestionPhase.UNKNOWN
    val elapsedSeconds = Duration.between(ingestionTime, now).seconds.toFloat()
    if (elapsedSeconds < 0) return IngestionPhase.ONSET

    var accumulated = 0f

    val onsetSec = roaDuration.onset?.interpolateAtValueInSeconds(0.5f)
    if (onsetSec != null) {
        accumulated += onsetSec
        if (elapsedSeconds < accumulated) return IngestionPhase.ONSET
    }

    val comeupSec = roaDuration.comeup?.interpolateAtValueInSeconds(0.5f)
    if (comeupSec != null) {
        accumulated += comeupSec
        if (elapsedSeconds < accumulated) return IngestionPhase.COMEUP
    }

    val peakSec = roaDuration.peak?.interpolateAtValueInSeconds(0.5f)
    if (peakSec != null) {
        accumulated += peakSec
        if (elapsedSeconds < accumulated) return IngestionPhase.PEAK
    }

    val offsetSec = roaDuration.offset?.interpolateAtValueInSeconds(0.5f)
    if (offsetSec != null) {
        accumulated += offsetSec
        if (elapsedSeconds < accumulated) return IngestionPhase.OFFSET
    }

    val afterglowSec = roaDuration.afterglow?.interpolateAtValueInSeconds(0.5f)
    if (afterglowSec != null) {
        accumulated += afterglowSec
        if (elapsedSeconds < accumulated) return IngestionPhase.AFTERGLOW
    }

    // if we had no phase data at all, unknown
    if (accumulated == 0f) return IngestionPhase.UNKNOWN

    return IngestionPhase.ENDED
}
