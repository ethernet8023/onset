package computer.ethernet.onset.ui.tabs.settings.combinations

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CombinationSettingsStorage @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private val substanceInteractionKey = stringSetPreferencesKey("substanceInteractions")

    val enabledInteractionsFlow: Flow<Set<String>> = dataStore.data
        .map { preferences ->
            preferences[substanceInteractionKey] ?: emptySet()
        }

    suspend fun toggleSubstanceInteraction(substanceInteraction: String) {
        dataStore.edit { settings ->
            val oldSet = settings[substanceInteractionKey] ?: emptySet()
            val newSet =
                if (oldSet.contains(substanceInteraction)) oldSet.minus(substanceInteraction) else oldSet.plus(
                    substanceInteraction
                )
            settings[substanceInteractionKey] = newSet
        }
    }

    private val substanceInteractionOptions = listOf(
        "Alcohol",
        "Caffeine",
        "Cannabis",
        "Grapefruit",
        "Hormonal birth control",
        "Nicotine",
        "Lithium",
        "MAOI",
        "SSRIs",
        "SNRIs",
        "5-Hydroxytryptophan",
        "Tricyclic antidepressants",
        "Antibiotics",
        "Antihistamine"
    )

    val optionFlow: Flow<List<Option>> = enabledInteractionsFlow.map { set ->
        substanceInteractionOptions.map { name ->
            Option(name = name, enabled = set.contains(name))
        }
    }
}

data class Option(
    val name: String,
    val enabled: Boolean
)