package computer.ethernet.onset.data.substances.classes

import computer.ethernet.onset.ui.tabs.search.CategoryModel
import computer.ethernet.onset.ui.tabs.search.SubstanceModel

data class SubstanceWithCategories(
    val substance: Substance, val categories: List<Category>


) {
    fun toSubstanceModel(): SubstanceModel {
        return SubstanceModel(
            name = substance.name,
            commonNames = substance.commonNames,
            categories = categories.map { category ->
                CategoryModel(
                    name = category.name, color = category.color
                )
            },
            hasSaferUse = substance.saferUse.isNotEmpty(),
            hasInteractions = substance.hasInteractions
        )
    }
}
