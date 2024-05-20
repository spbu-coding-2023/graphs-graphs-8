
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
            route = "${Screen.UndirectedUnweightedGraphScreen.route}/{graphId}",
            arguments = listOf(navArgument("graphId") { type = NavType.IntType })
        ){ navBackStackEntry ->
            val graphId = navBackStackEntry.arguments?.getInt("graphId")
            graphId?.let{
                navController.navigate("${Screen.UndirectedUnweightedGraphScreen.route}/$graphId")
                println("${Screen.UndirectedUnweightedGraphScreen.route}/$graphId")
            }
        }
        composable(
            route = "${Screen.DirectedUnweightedGraphScreen.route}/{graphId}",
            arguments = listOf(navArgument("graphId") { type = NavType.IntType })
        ){ navBackStackEntry ->
            val graphId = navBackStackEntry.arguments?.getInt("graphId")
            graphId?.let{
                navController.navigate("${Screen.UndirectedUnweightedGraphScreen.route}/$graphId")
            }
        }
        composable(
            route = "${Screen.UndirectedWeightedGraphScreen.route}/{graphId}",
            arguments = listOf(navArgument("graphId") { type = NavType.IntType })
        ){ navBackStackEntry ->
            val graphId = navBackStackEntry.arguments?.getInt("graphId")
            graphId?.let{
                navController.navigate("${Screen.UndirectedUnweightedGraphScreen.route}/$graphId")
            }
        }
        composable(
            route = "${Screen.DirectedWeightedGraphScreen.route}/{graphId}",
            arguments = listOf(navArgument("graphId") { type = NavType.IntType })
        ){ navBackStackEntry ->
            val graphId = navBackStackEntry.arguments?.getInt("graphId")
            graphId?.let{
                navController.navigate("${Screen.UndirectedUnweightedGraphScreen.route}/$graphId")
            }
        }
        composable(route = Screen.SettingsScreen.route){
            SettingsScreen(navController = navController)
        }
    }
}
