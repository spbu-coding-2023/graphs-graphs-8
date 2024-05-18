package view.screens

sealed class Screen(val route: String){
    object MainScreen: Screen("main_screen")
    object GraphScreen: Screen("graph_screen")
    object SettingsScreen: Screen("settings_screen")
}