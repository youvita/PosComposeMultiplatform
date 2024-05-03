package core.data

sealed class Resource<out T>(
    val status: Status,
    val data: T? = null,
    val code: Int? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : Resource<T>(Status.SUCCESS, data)
    class Error<T>(code: Int?, message: String?, data: T? = null) : Resource<T>(Status.ERROR, data, code, message)
    class Loading<T>(data: T? = null) : Resource<T>(Status.LOADING, data)
}