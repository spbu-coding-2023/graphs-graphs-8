package view.screens

sealed class Screen(val route: String){
    object MainScreen: Screen("main_screen")
    object UndirectedGraphScreen: Screen("undirected_graph_screen")
    object DirectedGraphScreen: Screen("directed_graph_screen")
    object SettingsScreen: Screen("settings_screen")
}