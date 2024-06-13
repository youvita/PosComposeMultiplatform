package ui.settings.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.data.Resource
import core.data.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ui.settings.domain.model.PreferenceData
import ui.settings.domain.repository.SettingRepository

data class PreferState(
    var status: Status? = null,
    var isLoading: Boolean? = null,
    var data: List<PreferenceData>? = null
)

class SettingsViewModel(
    private val repository: SettingRepository
): ScreenModel {

    private val _state = MutableStateFlow(PreferState())
    val state: StateFlow<PreferState> = _state.asStateFlow()

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
            repository.getPreference().collect { preference ->
                when (preference) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            status = preference.status,
                            data = preference.data,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }
}
