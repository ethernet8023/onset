package computer.ethernet.onset.data.substances.repositories

import computer.ethernet.onset.data.substances.classes.SubstanceWithCategories
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    val substanceRepo: SubstanceRepository
) : SearchRepositoryInterface {

    override fun getMatchingSubstances(
        searchText: String,
        filterCategories: List<String>,
        recentlyUsedSubstanceNamesSorted: List<String>,
    ): List<SubstanceWithCategories> {
        val substancesMatchingCategories = getSubstancesMatchingCategories(filterCategories)
        val substancesFilteredWithText = getSubstancesMatchingSearchText(searchText, prefilteredSubstances = substancesMatchingCategories)
        return getSubstancesSorted(prefilteredSubstances = substancesFilteredWithText, recentlyUsedSubstanceNamesSorted = recentlyUsedSubstanceNamesSorted)
    }

    private fun getSubstancesMatchingCategories(filterCategories: List<String>): List<SubstanceWithCategories> {
        return substanceRepo.getAllSubstancesWithCategories().filter { substanceWithCategories ->
            filterCategories.all { substanceWithCategories.substance.categories.contains(it) }
        }
    }

    private fun getSubstancesMatchingSearchText(searchText: String, prefilteredSubstances: List<SubstanceWithCategories>): List<SubstanceWithCategories> {
        return if (searchText.isEmpty()) {
            prefilteredSubstances
        } else {
            val searchString = searchText.replace(Regex("[- ]"), "")
            // substances whose primary name begins with the search string
            val mainPrefixMatches = prefilteredSubstances.filter { substanceWithCategories ->
                substanceWithCategories.substance.name.replace(Regex("[- ]"), "").startsWith(
                    prefix = searchString, ignoreCase = true
                )
            }
            // substances with any name beginning with the search string
            val prefixMatches = prefilteredSubstances.filter { substanceWithCategories ->
                val allNames =
                    substanceWithCategories.substance.commonNames + substanceWithCategories.substance.name
                allNames.any { name ->
                    name.replace(Regex("[- ]"), "").startsWith(
                        prefix = searchString, ignoreCase = true
                    )
                }
            }
            // substances containing the search string in any of their names
            val matches = prefilteredSubstances.filter { substanceWithCategories ->
                val allNames =
                    substanceWithCategories.substance.commonNames + substanceWithCategories.substance.name
                allNames.any { name ->
                    name.replace(Regex("[- ]"), "").contains(
                        other = searchString, ignoreCase = true
                    )
                }
            }
            return (mainPrefixMatches + prefixMatches + matches).distinctBy { it.substance.name }
        }
    }

    private fun getSubstancesSorted(
        prefilteredSubstances: List<SubstanceWithCategories>,
        recentlyUsedSubstanceNamesSorted: List<String>
    ): List<SubstanceWithCategories> {
        val recentNames = recentlyUsedSubstanceNamesSorted.distinct()
        val recentlyUsedMatches =
            recentNames.filter { recent -> prefilteredSubstances.any { it.substance.name == recent } }
                .mapNotNull {
                    substanceRepo.getSubstanceWithCategories(
                        substanceName = it
                    )
                }
        val commonSubstanceMatches =
            prefilteredSubstances.filter { sub -> sub.categories.any { cat -> cat.name == "common" } }
        return (recentlyUsedMatches + commonSubstanceMatches + prefilteredSubstances).distinctBy { it.substance.name }
    }
}