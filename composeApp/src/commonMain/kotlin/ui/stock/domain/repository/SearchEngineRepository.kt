package ui.stock.domain.repository

import core.data.Resource
import kotlinx.coroutines.flow.Flow
import ui.stock.domain.model.ItemEngine

interface SearchEngineRepository {
    suspend fun fetchSearchEngine(keyword: String): Flow<Resource<ItemEngine>>
}