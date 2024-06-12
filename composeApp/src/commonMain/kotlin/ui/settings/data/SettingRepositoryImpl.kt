package ui.settings.data

import core.data.Resource
import kotlinx.coroutines.flow.Flow
import org.topteam.pos.PosDatabase
import ui.settings.domain.model.PreferenceData
import ui.settings.domain.repository.SettingRepository

class SettingRepositoryImpl(posDatabase: PosDatabase): SettingRepository {
    private val db = posDatabase.appDatabaseQueries

    override suspend fun updatePreference(preferenceId: Int, preferenceItem: String) {
        db.updatePreference(preferenceItem, preferenceId.toLong())
    }

    override suspend fun addPreference(preferenceId: Int, preferenceItem: String) {
        db.insertPreference(preferenceId.toLong(), preferenceItem)
    }

    override suspend fun getPreference(preferenceId: Int): Flow<Resource<List<PreferenceData>>> {
        TODO("Not yet implemented")
    }
}