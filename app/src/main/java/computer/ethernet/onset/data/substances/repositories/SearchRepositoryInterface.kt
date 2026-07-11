package computer.ethernet.onset.data.substances.repositories

import computer.ethernet.onset.data.substances.classes.SubstanceWithCategories

interface SearchRepositoryInterface {
    fun getMatchingSubstances(
    searchText: String,
    filterCategories: List<String>,
    recentlyUsedSubstanceNamesSorted: List<String>,
    ): List<SubstanceWithCategories>
}