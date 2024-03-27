package login.presentation

import core.data.Status

data class LoginState(
    var status: Status? = null,
    var isLoading: Boolean? = null,
    var username: String = "",
    var password: String = "",
    var showDialog : Boolean = false,
    var dialogMsg : String = "",
)
