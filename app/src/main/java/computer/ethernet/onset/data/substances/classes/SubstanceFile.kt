package computer.ethernet.onset.data.substances.classes

class SubstanceFile(
    val categories: List<Category>,
    val substances: List<Substance>
) {
    val substancesMap: Map<String, Substance> = substances.associateBy { it.name }
}