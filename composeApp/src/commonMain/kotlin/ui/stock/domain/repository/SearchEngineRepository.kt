package ui.stock.domain.repository

import core.data.Resource
import kotlinx.coroutines.flow.Flow

interface SearchEngineRepository {
    suspend fun fetchSearchEngine(keyword: String): Flow<Resource<Unit>>
}