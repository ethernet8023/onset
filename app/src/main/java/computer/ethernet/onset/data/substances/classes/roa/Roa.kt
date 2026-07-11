package computer.ethernet.onset.data.substances.classes.roa

import computer.ethernet.onset.data.substances.AdministrationRoute

data class Roa(
    val route: AdministrationRoute,
    val roaDose: RoaDose?,
    val roaDuration: RoaDuration?,
    val bioavailability: Bioavailability?
)