package ui.settings.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import ui.settings.domain.model.PreferenceData
import ui.settings.domain.repository.SettingRepository

class SettingsViewModel(
    private val repository: SettingRepository
): ScreenModel {

    fun onEvent(event: SettingsEvent) {
        when(event) {
            is SettingsEvent.AddPreference -> {
                val preference = PreferenceData(preferId = event.preferId, preferItem = event.preferItem)
                addPreference(preference)
            }

            is SettingsEvent.UpdatePreference -> {
                val preference = PreferenceData(preferId = event.preferId, preferItem = event.preferItem)
                updatePreference(preference)
            }

            is SettingsEvent.GetPreference -> {
                getPreference()
            }
        }
    }

    private fun addPreference(preference: PreferenceData) {
        screenModelScope.launch {
            repository.addPreference(preference.preferId, preference.preferItem)
        }
    }

    private fun updatePreference(preference: PreferenceData) {
        screenModelScope.launch {
            repository.updatePreference(preference.preferId, preference.preferItem)
        }
    }

    private fun getPreference() {
        screenModelScope.launch {
//            repository.getPreference()
        }
    }
}
