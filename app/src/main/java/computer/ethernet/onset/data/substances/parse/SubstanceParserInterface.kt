package computer.ethernet.onset.data.substances.parse

import computer.ethernet.onset.data.substances.classes.SubstanceFile

interface SubstanceParserInterface {
    fun parseSubstanceFile(string: String): SubstanceFile
    fun extractSubstanceString(string: String): String?
}