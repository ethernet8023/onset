package computer.ethernet.onset.ui.tabs.journal.experience.components

import computer.ethernet.onset.data.room.experiences.entities.AdaptiveColor
import computer.ethernet.onset.data.substances.AdministrationRoute
import computer.ethernet.onset.data.substances.classes.roa.RoaDuration
import java.time.Instant

data class DataForOneEffectLine(
    val substanceName: String,
    val route: AdministrationRoute,
    val roaDuration: RoaDuration?,
    val height: Float,
    val horizontalWeight: Float,
    val color: AdaptiveColor,
    val startTime: Instant,
    val endTime: Instant?
)