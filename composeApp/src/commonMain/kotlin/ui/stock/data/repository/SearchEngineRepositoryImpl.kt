package ui.stock.data.repository

import core.data.Resource
import core.network.createCall
import di.Network
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import ui.stock.domain.model.ItemEngine
import ui.stock.domain.repository.SearchEngineRepository

class SearchEngineRepositoryImpl(private val httpClient: HttpClient): SearchEngineRepository {

    override suspend fun fetchSearchEngine(keyword: String): Flow<Resource<ItemEngine>> {
        return createCall {
            val response = httpClient.get(Network.URL.plus(Network.EndPoint.SHOWS)) {
                url {
                    parameters.append("key", "AIzaSyAcwDV797pwi7J43MvQNlYJicCPEJOeuBs")
                    parameters.append("q", keyword)
                    parameters.append("cx", "a3a4a06a69c234a9e")
                    parameters.append("imgType", "photo")
                    parameters.append("searchType", "image")
                }
            }.body<ItemEngine>()
            Resource.Success(response)
        }
    }
}