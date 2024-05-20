
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import view.screens.*
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
            route = "${Screen.UndirectedUnweightedGraphScreen.route}/{graphId}",
            arguments = listOf(navArgument("graphId") { type = NavType.IntType })
        ){ navBackStackEntry ->
            val graphId = navBackStackEntry.arguments?.getInt("graphId")
            graphId?.let{
                println(graphId)
                UndirectedUnweightedGraphScreen(navController, mainScreenViewModel, graphId)
            }
        }
        composable(
            route = "${Screen.DirectedUnweightedGraphScreen.route}/{graphId}",
            arguments = listOf(navArgument("graphId") { type = NavType.IntType })
        ){ navBackStackEntry ->
            val graphId = navBackStackEntry.arguments?.getInt("graphId")
            graphId?.let{
                DirectedUnweightedGraphScreen(navController, mainScreenViewModel, graphId)
            }
        }
        composable(
            route = "${Screen.UndirectedWeightedGraphScreen.route}/{graphId}",
            arguments = listOf(navArgument("graphId") { type = NavType.IntType })
        ){ navBackStackEntry ->
            val graphId = navBackStackEntry.arguments?.getInt("graphId")
            graphId?.let{
                UndirectedWeightedGraphScreen(navController, mainScreenViewModel, graphId)
            }
        }
        composable(
            route = "${Screen.DirectedWeightedGraphScreen.route}/{graphId}",
            arguments = listOf(navArgument("graphId") { type = NavType.IntType })
        ){ navBackStackEntry ->
            val graphId = navBackStackEntry.arguments?.getInt("graphId")
            graphId?.let{
                DirectedWeightedGraphScreen(navController, mainScreenViewModel, graphId)
            }
        }
        composable(route = Screen.SettingsScreen.route){
            SettingsScreen(navController = navController)
        }
    }
}
