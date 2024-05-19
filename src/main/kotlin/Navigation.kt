
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import view.screens.MainScreen
import view.screens.Screen
import view.screens.SettingsScreen
import viewmodel.MainScreenViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val mainScreenViewModel = MainScreenViewModel()
    NavHost(navController = navController,
        startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route){
            MainScreen(navController = navController, mainScreenViewModel)
        }
        composable(
            route = "${Screen.UndirectedUnweightedGraphScreen.route}/{type}/{graphId}",
            arguments = listOf(navArgument("graphId") { type = NavType.IntType })
        ){ navBackStackEntry ->
            val graphId = navBackStackEntry.arguments?.getInt("graphId")
            graphId?.let{
                navController.navigate("${Screen.UndirectedUnweightedGraphScreen.route}/UU/$graphId")
            }
        }
        composable(
            route = "${Screen.DirectedUnweightedGraphScreen.route}/{type}/{graphId}",
            arguments = listOf(navArgument("graphId") { type = NavType.IntType })
        ){ navBackStackEntry ->
            val graphId = navBackStackEntry.arguments?.getInt("graphId")
            graphId?.let{
                navController.navigate("${Screen.UndirectedUnweightedGraphScreen.route}/DU/$graphId")
            }
        }
        composable(
            route = "${Screen.UndirectedWeightedGraphScreen.route}/{type}/{graphId}",
            arguments = listOf(navArgument("graphId") { type = NavType.IntType })
        ){ navBackStackEntry ->
            val graphId = navBackStackEntry.arguments?.getInt("graphId")
            graphId?.let{
                navController.navigate("${Screen.UndirectedUnweightedGraphScreen.route}/UW/$graphId")
            }
        }
        composable(
            route = "${Screen.DirectedWeightedGraphScreen.route}/{type}/{graphId}",
            arguments = listOf(navArgument("graphId") { type = NavType.IntType })
        ){ navBackStackEntry ->
            val graphId = navBackStackEntry.arguments?.getInt("graphId")
            graphId?.let{
                navController.navigate("${Screen.UndirectedUnweightedGraphScreen.route}/DW/$graphId")
            }
        }
        composable(route = Screen.SettingsScreen.route){
            SettingsScreen(navController = navController)
        }
    }
}
