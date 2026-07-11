package computer.ethernet.onset.data.substances.repositories

import android.content.Context
import computer.ethernet.onset.data.substances.classes.Category
import computer.ethernet.onset.data.substances.classes.Substance
import computer.ethernet.onset.data.substances.classes.SubstanceFile
import computer.ethernet.onset.data.substances.classes.SubstanceWithCategories
import computer.ethernet.onset.data.substances.parse.SubstanceParserInterface
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubstanceRepository @Inject constructor(
    @ApplicationContext private val appContext: Context,
    substanceParser: SubstanceParserInterface,
) : SubstanceRepositoryInterface {

    private var substanceFile: SubstanceFile

    init {
        val fileContent = getAssetsSubstanceFileContent()
        substanceFile = substanceParser.parseSubstanceFile(string = fileContent)
    }

    private fun getAssetsSubstanceFileContent(): String {
        return appContext.assets.open("Substances.json").bufferedReader().use { it.readText() }
    }

    override fun getAllSubstances(): List<Substance> {
        return substanceFile.substances
    }

    override fun getAllSubstancesWithCategories(): List<SubstanceWithCategories> {
        return substanceFile.substances.map { substance ->
            SubstanceWithCategories(
                substance = substance,
                categories = substanceFile.categories.filter { category ->
                    substance.categories.contains(category.name)
                }
            )
        }
    }

    override fun getAllCategories(): List<Category> {
        return substanceFile.categories
    }

    override fun getSubstance(substanceName: String): Substance? {
        return substanceFile.substancesMap.getOrDefault(key = substanceName, defaultValue = null)
    }

    override fun getCategory(categoryName: String): Category? {
        return substanceFile.categories.firstOrNull { it.name == categoryName }
    }

    override fun getSubstanceWithCategories(substanceName: String): SubstanceWithCategories? {
        val substance =
            substanceFile.substances.firstOrNull { it.name == substanceName } ?: return null
        return SubstanceWithCategories(
            substance = substance,
            categories = substanceFile.categories.filter { substance.categories.contains(it.name) }
        )
    }
}