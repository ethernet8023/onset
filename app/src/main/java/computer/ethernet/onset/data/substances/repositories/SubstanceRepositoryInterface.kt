package computer.ethernet.onset.data.substances.repositories

import computer.ethernet.onset.data.substances.classes.Category
import computer.ethernet.onset.data.substances.classes.Substance
import computer.ethernet.onset.data.substances.classes.SubstanceWithCategories

interface SubstanceRepositoryInterface {
    fun getAllSubstances(): List<Substance>
    fun getAllSubstancesWithCategories(): List<SubstanceWithCategories>
    fun getAllCategories(): List<Category>
    fun getSubstance(substanceName: String): Substance?
    fun getCategory(categoryName: String): Category?
    fun getSubstanceWithCategories(substanceName: String): SubstanceWithCategories?
}