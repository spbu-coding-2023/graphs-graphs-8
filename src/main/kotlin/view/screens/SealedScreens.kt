package view.screens

sealed class Screen(val route: String){
    object MainScreen: Screen("main_screen")
    object UndirectedUnweightedGraphScreen: Screen("uu_graph_screen")
    object DirectedUnweightedGraphScreen: Screen("du_graph_screen")
    object UndirectedWeightedGraphScreen: Screen("uw_graph_screen")
    object DirectedWeightedGraphScreen: Screen("dw_graph_screen")
    object SettingsScreen: Screen("settings_screen")
}