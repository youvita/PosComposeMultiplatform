package login.presentation
data class LoginState(
    var isLogin: Boolean = true,
    var username: String = "",
    var password: String = "",
    var showDialog : Boolean = false,
    var dialogMsg : String = "",
)
