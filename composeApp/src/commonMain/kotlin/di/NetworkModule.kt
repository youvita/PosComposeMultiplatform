package di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

object Network {
    const val URL =  "https://raw.githubusercontent.com/piappstudio/resources/main/biggboss/"
    object EndPoint {
        const val SHOWS = "json/shows.json"
    }
}

val httpClientModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(json = Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
            }
        }
    }
}

expect fun isNetworkAvailable():Boolean