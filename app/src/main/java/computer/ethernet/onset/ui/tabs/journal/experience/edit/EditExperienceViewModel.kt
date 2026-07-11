package computer.ethernet.onset.ui.tabs.journal.experience.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import computer.ethernet.onset.data.room.experiences.ExperienceRepository
import computer.ethernet.onset.data.room.experiences.entities.Experience
import computer.ethernet.onset.data.room.experiences.entities.Location
import computer.ethernet.onset.ui.main.navigation.graphs.EditExperienceRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditExperienceViewModel @Inject constructor(
    private val repository: ExperienceRepository,
    state: SavedStateHandle
) :
    ViewModel() {

    var experience: Experience? = null
    var enteredTitle by mutableStateOf("")
    val isEnteredTitleOk get() = enteredTitle.isNotEmpty()
    var enteredText by mutableStateOf("")
    var enteredLocation by mutableStateOf("")
    private var oldLongitude: Double? = null
    private var oldLatitude: Double? = null


    init {
        val editExperienceRoute = state.toRoute<EditExperienceRoute>()
        viewModelScope.launch {
            experience = repository.getExperience(id = editExperienceRoute.experienceId)!!
            enteredTitle = experience!!.title
            enteredText = experience!!.text
            enteredLocation = experience!!.location?.name ?: ""
            oldLongitude = experience!!.location?.longitude
            oldLatitude = experience!!.location?.latitude
        }
    }

    fun onDoneTap() {
        if (enteredTitle.isNotEmpty()) {
            viewModelScope.launch {
                experience!!.title = enteredTitle
                experience!!.text = enteredText
                val location = if (enteredLocation.isNotBlank()) {
                    Location(name = enteredLocation, longitude = oldLongitude, latitude = oldLatitude)
                } else {
                    null
                }
                experience!!.location = location
                repository.update(experience = experience!!)
            }
        }
    }

}