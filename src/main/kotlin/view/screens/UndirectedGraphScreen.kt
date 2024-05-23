package view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import view.common.AddEdgeDialog
import view.common.DefaultButton
import view.views.GraphViewUndirect
import viewmodel.MainScreenViewModel

@Composable
fun UndirectedGraphScreen(
    navController: NavController,
    mainScreenViewModel: MainScreenViewModel,
    graphId: Int
) {
    val graphVM by mutableStateOf(mainScreenViewModel.graphs.getUndirected(graphId))

    Box(modifier = Modifier.fillMaxSize()) {
        GraphViewUndirect(graphVM)
    }

    Column(modifier = Modifier.zIndex(1f).padding(16.dp).width(300.dp)) {
        Text(text = "Undirected")
        // To MainScreen
        DefaultButton({ navController.popBackStack() }, "home")

        Spacer(modifier = Modifier.height(16.dp))

        // Add vertex
        DefaultButton({ graphVM.addVertex(graphVM.size) }, "add_vertex")

        Spacer(modifier = Modifier.height(16.dp))

        // Open "add edge" dialog window
        var isOpenedEdgeMenu by remember { mutableStateOf(false) }
        DefaultButton({ isOpenedEdgeMenu = !isOpenedEdgeMenu }, "open_edge")

        Spacer(modifier = Modifier.height(10.dp))

        val onClose = { isOpenedEdgeMenu = false }
        AddEdgeDialog(isOpenedEdgeMenu, onClose, graphVM)
    }
}
