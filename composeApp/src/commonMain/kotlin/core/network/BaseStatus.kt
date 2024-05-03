package core.network

import kotlinx.serialization.*

@Serializable
data class BaseStatus(

    @SerialName("code")
    var code: String? = null,

    @SerialName("message")
    var message: String? = null,
)
