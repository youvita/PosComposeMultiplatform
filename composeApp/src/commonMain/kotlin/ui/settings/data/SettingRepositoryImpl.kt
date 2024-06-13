package ui.settings.data

import core.data.Resource
import core.mapper.toPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    override suspend fun getPreference(): Flow<Resource<List<PreferenceData>>> = flow {
        emit(Resource.Loading())
        val result = db.getPreference().executeAsList().map { it.toPreference() }
        emit(Resource.Success(result))
    }
}