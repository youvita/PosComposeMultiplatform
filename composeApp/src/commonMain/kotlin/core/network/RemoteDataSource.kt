package core.network

import core.data.Resource
import core.data.Status
import di.isNetworkAvailable
import kotlinx.coroutines.flow.flow

suspend inline fun <reified T> createCall(crossinline api: suspend () -> Resource<T?>) = flow {
    emit(Resource.Loading())
    try {
        if (isNetworkAvailable()) {
            val response = api.invoke()
            val data = response.data
            if (response.status == Status.SUCCESS) {
                emit(Resource.Success(data))
            }
        } else {
            emit(Resource.Error(code = ErrorCode.NETWORK_NOT_AVAILABLE, message = "File not found", data = null))
        }
    } catch (ex: Exception) {
        emit(Resource.Error(code = ErrorCode.NETWORK_CONNECTION_FAILED, message = ex.message))
    }
}