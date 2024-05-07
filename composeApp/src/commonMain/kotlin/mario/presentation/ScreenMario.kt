package mario.presentation

sealed class ScreenMario(val route: String) {
    object MainScreen: ScreenMario("main_screen")
    object MenuScreen: ScreenMario("menu_screen")
    object EmployeeScreen: ScreenMario("employee_screen")
}
