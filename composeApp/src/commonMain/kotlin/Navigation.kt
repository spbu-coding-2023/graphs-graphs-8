import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import view.screens.GraphScreen
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
            route = "${Screen.GraphScreen.route}/{graphId}",
            arguments = listOf(navArgument("graphId") { type = NavType.IntType })
        ){ navBackStackEntry ->
            val graphId = navBackStackEntry.arguments?.getInt("graphId")
            graphId?.let{
                GraphScreen(navController, mainScreenViewModel, graphId)
            }

        }
        composable(route = Screen.SettingsScreen.route){
            SettingsScreen(navController = navController)
        }
    }
}
