package computer.ethernet.onset.ui.tabs.settings

import android.net.Uri
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import computer.ethernet.onset.data.room.experiences.ExperienceRepository
import computer.ethernet.onset.ui.tabs.settings.combinations.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val experienceRepository: ExperienceRepository,
    private val fileSystemConnection: FileSystemConnection,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    fun saveDosageDotsAreHidden(value: Boolean) = viewModelScope.launch {
        userPreferences.saveDosageDotsAreHidden(value)
    }

    fun saveAreSubstanceHeightsIndependent(value: Boolean) = viewModelScope.launch {
        userPreferences.saveAreSubstanceHeightsIndependent(value)
    }

    fun saveIsTimelineHidden(value: Boolean) = viewModelScope.launch {
        userPreferences.saveIsTimelineHidden(value)
    }

    val isTimelineHiddenFlow = userPreferences.isTimelineHiddenFlow.stateIn(
        initialValue = false,
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000)
    )

    val areSubstanceHeightsIndependentFlow = userPreferences.areSubstanceHeightsIndependentFlow.stateIn(
        initialValue = false,
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000)
    )

    val areDosageDotsHiddenFlow = userPreferences.areDosageDotsHiddenFlow.stateIn(
        initialValue = false,
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000)
    )

    var importFileError: MutableStateFlow<String?> = MutableStateFlow(null);

    val snackbarHostState = SnackbarHostState()

    fun importFile(uri: Uri) {
        viewModelScope.launch {
            val text = fileSystemConnection.getTextFromUri(uri)
            if (text == null) {
                snackbarHostState.showSnackbar(
                    message = "File not found",
                    duration = SnackbarDuration.Short
                )
            } else {
                try {
                    println("Decoding file...")
                    val json = Json { ignoreUnknownKeys = true }

                    val convertedText = tryConvertFile(json, text)

                    val journalExport = json.decodeFromString<JournalExport>(convertedText)
                    experienceRepository.deleteEverything()
                    experienceRepository.insertEverything(journalExport)
                    snackbarHostState.showSnackbar(
                        message = "Import successful",
                        duration = SnackbarDuration.Short
                    )
                } catch (e: Exception) {
                    println("Error when decoding: ${e.message}")
                    importFileError.value = e.message
                    snackbarHostState.showSnackbar(
                        message = "Decoding file failed",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    fun tryConvertFile(json: Json, text: String): String {
        println(json)
        val jsonElement = json.parseToJsonElement(text)
        val data = (jsonElement as JsonObject).toMutableMap()
        val exportSource = data["exportSource"]?.jsonPrimitive?.content ?: ""

        if (exportSource == "Android Journal") { // v12+ Android Journal
            println("Converting from v12+ Android Journal")
            data.remove("exportSource")

            val customUnits = data["customUnits"]?.jsonArray ?: JsonArray(emptyList())

            val customSubstances = customUnits.map { unit ->
                val unitObj = unit.jsonObject
                val name = unitObj["name"]?.jsonPrimitive?.content ?: ""
                val units = unitObj["unit"]?.jsonPrimitive?.content ?: ""
                val description = unitObj["note"]?.jsonPrimitive?.content ?: ""

                JsonObject(mapOf(
                    "name" to JsonPrimitive(name),
                    "units" to JsonPrimitive(units),
                    "description" to JsonPrimitive(description)
                ))
            }
            data["customSubstances"] = JsonArray(customSubstances)

            // Create customUnitSubstances mapping
            val customUnitSubstances = mutableMapOf<String, Pair<String?, String?>>()

            // First pass: build initial mapping
            customUnits.forEach { unit ->
                val unitObj = unit.jsonObject
                val id = unitObj["id"]?.jsonPrimitive?.content ?: ""
                val name = unitObj["name"]?.jsonPrimitive?.content ?: ""
                val doseComponents = unitObj["doseComponents"]?.jsonArray

                if (!doseComponents.isNullOrEmpty()) {
                    val firstComponent = doseComponents[0].jsonObject
                    val substanceName = firstComponent["substanceName"]?.jsonPrimitive?.content
                    val customUnitId = firstComponent["customUnitId"]?.jsonPrimitive?.content

                    customUnitSubstances[id] = Pair(substanceName, customUnitId)
                } else {
                    customUnitSubstances[id] = Pair(name, null)
                }
            }

            // Second pass: resolve nested customUnitId references
            val resolvedMapping = mutableMapOf<String, String>()
            customUnitSubstances.forEach { (key, value) ->
                if (value.second != null && value.second in customUnitSubstances.keys) {
                    val resolvedValue = customUnitSubstances[value.second] ?: value
                    resolvedMapping[key] = resolvedValue.first ?: value.first ?: "Unknown substance"
                    return@forEach
                }
                resolvedMapping[key] = value.first ?: "Unknown substance"
            }

            data.remove("customUnits")

            val experiences = data["experiences"]?.jsonArray
            if (experiences != null) {
                val cleanedExperiences = experiences.map { experienceElement ->
                    val experience = experienceElement.jsonObject.toMutableMap()
                    val ingestions = experience["ingestions"]?.jsonArray
                    if (ingestions != null) {
                        val cleanedIngestions = ingestions.map { ingestionElement ->
                            val ingestion = ingestionElement.jsonObject.toMutableMap()

                            val substanceName = ingestion["substanceName"]
                            val customUnitId = ingestion["customUnitId"]?.jsonPrimitive?.content

                            if (substanceName == JsonNull && customUnitId != null) {
                                resolvedMapping[customUnitId]?.let { resolvedName ->
                                    ingestion["substanceName"] = JsonPrimitive(resolvedName)
                                }
                            }

                            JsonObject(ingestion)
                        }
                        experience["ingestions"] = JsonArray(cleanedIngestions)
                    }
                    JsonObject(experience)
                }
                data["experiences"] = JsonArray(cleanedExperiences)
            }

            return json.encodeToString(JsonObject.serializer(), JsonObject(data))
        }

        return text
    }

    fun exportFile(uri: Uri) {
        viewModelScope.launch {
            val experiencesWithIngestionsAndRatings =
                experienceRepository.getAllExperiencesWithIngestionsTimedNotesAndRatingsSorted()
            val experiencesSerializable = experiencesWithIngestionsAndRatings.map {
                val location = it.experience.location
                return@map ExperienceSerializable(
                    title = it.experience.title,
                    text = it.experience.text,
                    creationDate = it.experience.creationDate,
                    sortDate = it.experience.sortDate,
                    isFavorite = it.experience.isFavorite,
                    ingestions = it.ingestions.map { ingestion ->
                        IngestionSerializable(
                            substanceName = ingestion.substanceName,
                            time = ingestion.time,
                            endTime = ingestion.endTime,
                            creationDate = ingestion.creationDate,
                            administrationRoute = ingestion.administrationRoute,
                            dose = ingestion.dose,
                            estimatedDoseStandardDeviation = ingestion.estimatedDoseStandardDeviation,
                            isDoseAnEstimate = ingestion.isDoseAnEstimate,
                            units = ingestion.units,
                            notes = ingestion.notes,
                            stomachFullness = ingestion.stomachFullness,
                            consumerName = ingestion.consumerName,
                            customUnitId = ingestion.customUnitId
                        )
                    },
                    location = if (location != null) {
                        LocationSerializable(
                            name = location.name,
                            latitude = location.latitude,
                            longitude = location.longitude
                        )
                    } else {
                        null
                    },
                    ratings = it.ratings.map { rating ->
                        RatingSerializable(
                            option = rating.option,
                            time = rating.time,
                            creationDate = rating.creationDate
                        )
                    },
                    timedNotes = it.timedNotes.map { timedNote ->
                        TimedNoteSerializable(
                            creationDate = timedNote.creationDate,
                            time = timedNote.time,
                            note = timedNote.note,
                            color = timedNote.color,
                            isPartOfTimeline = timedNote.isPartOfTimeline
                        )
                    }
                )
            }
            val customUnitsSerializable = experienceRepository.getAllCustomUnitsSorted().map {
                CustomUnitSerializable(
                    id = it.id,
                    substanceName = it.substanceName,
                    name = it.name,
                    creationDate = it.creationDate,
                    administrationRoute = it.administrationRoute,
                    dose = it.dose,
                    estimatedDoseStandardDeviation = it.estimatedDoseStandardDeviation,
                    isEstimate = it.isEstimate,
                    isArchived = it.isArchived,
                    unit = it.unit,
                    unitPlural = it.unitPlural,
                    originalUnit = it.originalUnit,
                    note = it.note
                )
            }
            val journalExport = JournalExport(
                experiences = experiencesSerializable,
                substanceCompanions = experienceRepository.getAllSubstanceCompanions(),
                customSubstances = experienceRepository.getAllCustomSubstances(),
                customUnits = customUnitsSerializable
            )
            try {
                val jsonList = Json.encodeToString(journalExport)
                fileSystemConnection.saveTextInUri(uri, text = jsonList)
                snackbarHostState.showSnackbar(
                    message = "Export successful",
                    duration = SnackbarDuration.Short
                )
            } catch (_: Exception) {
                snackbarHostState.showSnackbar(
                    message = "Export failed",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    fun clearImportFileError() {
        importFileError.value = null
    }

    fun deleteEverything() {
        viewModelScope.launch {
            experienceRepository.deleteEverything()
        }
    }
}