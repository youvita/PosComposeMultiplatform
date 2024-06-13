package ui.settings.domain.repository

import core.data.Resource
import kotlinx.coroutines.flow.Flow
import ui.settings.domain.model.PreferenceData

interface SettingRepository {
    suspend fun updatePreference(preferenceId: Int, preferenceItem: String)
    suspend fun addPreference(preferenceId: Int, preferenceItem: String)
    suspend fun getPreference(): Flow<Resource<List<PreferenceData>>>

}