
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
            route = "${Screen.UndirectedGraphScreen.route}/{graphId}",
            arguments = listOf(navArgument("graphId") { type = NavType.IntType })
        ){ navBackStackEntry ->
            val graphId = navBackStackEntry.arguments?.getInt("graphId")
            graphId?.let{
                println(graphId)
                UndirectedGraphScreen(navController, mainScreenViewModel, graphId)
            }
        }
        composable(
            route = "${Screen.DirectedGraphScreen.route}/{graphId}",
            arguments = listOf(navArgument("graphId") { type = NavType.IntType })
        ){ navBackStackEntry ->
            val graphId = navBackStackEntry.arguments?.getInt("graphId")
            graphId?.let{
                DirectedGraphScreen(navController, mainScreenViewModel, graphId)
            }
        }

        composable(route = Screen.SettingsScreen.route){
            SettingsScreen(navController = navController)
        }
    }
}
