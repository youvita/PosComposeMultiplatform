package ui.stock.data.repository

import core.data.Resource
import core.network.createCall
import di.Network
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import ui.stock.domain.repository.SearchEngineRepository

class SearchEngineRepositoryImpl(private val httpClient: HttpClient): SearchEngineRepository {

    override suspend fun fetchSearchEngine(keyword: String): Flow<Resource<Unit>> {
        return createCall {
            val response = httpClient.get(Network.URL.plus(Network.EndPoint.SHOWS)).body<Unit>()
            Resource.Success(response)
        }
    }
}