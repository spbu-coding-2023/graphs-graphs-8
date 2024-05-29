import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import view.screens.*
import viewmodel.DirectedGraphViewModel
import viewmodel.MainScreenViewModel
import viewmodel.UndirectedGraphViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val mainScreenViewModel = MainScreenViewModel()
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController, mainScreenViewModel)
        }

        composable(
            route = "${Screen.UndirectedGraphScreen.route}/{graphName}",
            arguments = listOf(navArgument("graphName") { type = NavType.StringType })
        ) { navBackStackEntry ->

            val graphName = navBackStackEntry.arguments?.getString("graphName")
                ?: throw IllegalArgumentException("graphName must be provided when navigate to UndirectedGraphScreen")
            val graphVM =
                mainScreenViewModel.getGraph(graphName) as? UndirectedGraphViewModel<String>
                    ?: throw IllegalStateException("Can't find graph with given in navigation name")

            UndirectedGraphScreen(mainScreenViewModel, navController, graphVM)
        }

        composable(
            route = "${Screen.DirectedGraphScreen.route}/{graphName}",
            arguments = listOf(navArgument("graphName") { type = NavType.StringType })
        ) { navBackStackEntry ->

            val graphName = navBackStackEntry.arguments?.getString("graphName")
                ?: throw IllegalArgumentException("graphName must be provided when navigate to DirectedGraphScreen")
            val graphVM = mainScreenViewModel.getGraph(graphName) as? DirectedGraphViewModel<String>
                ?: throw IllegalStateException("Can't find graph with given in navigation name")

            DirectedGraphScreen(mainScreenViewModel, navController, graphVM)
        }

        composable(route = Screen.SettingsScreen.route) {
            SettingsScreen(navController = navController)
        }
    }
}
