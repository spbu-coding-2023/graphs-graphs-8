package view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import model.algos.ForceAtlas2
import view.common.AddEdgeDialog
import view.common.DefaultButton
import view.common.DirectedAlgorithmDialog
import view.views.UndirectedGraphView
import viewmodel.MainScreenViewModel

@Composable
fun UndirectedGraphScreen(
    navController: NavController,
    mainScreenViewModel: MainScreenViewModel,
    graphId: Int
) {
    val graphVM by mutableStateOf(mainScreenViewModel.graphs.getUndirected(graphId))

    Box(modifier = Modifier.fillMaxSize()) {
        UndirectedGraphView(graphVM)
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

        DefaultButton({ ForceAtlas2.forceDrawing(graphVM) }, "visualize", Color(0xffFFA500))

        Spacer(modifier = Modifier.height(10.dp))

        DefaultButton({ graphVM.resetDrawing() }, "reset", Color.LightGray)

        Spacer(modifier = Modifier.height(10.dp))

        // Dijkstra Button
        var isDijkstraMenu by remember { mutableStateOf(false) }
        val onCloseDijkstra = { isDijkstraMenu = !isDijkstraMenu }
        DefaultButton(onCloseDijkstra, "dijkstra")

        Spacer(modifier = Modifier.height(10.dp))

        // FordBellman Button
        var isFordBellmanMenu by remember { mutableStateOf(false) }
        val onCloseFB = { isFordBellmanMenu = !isFordBellmanMenu }
        DefaultButton(onCloseFB, "ford_bellman")

        Spacer(modifier = Modifier.height(10.dp))

        DefaultButton(onClick = { graphVM.findMst() }, "find_mst")

        Spacer(modifier = Modifier.height(10.dp))

        DefaultButton(onClick = { graphVM.findCycles() }, "find_cycles")

        Spacer(modifier = Modifier.height(10.dp))

        DefaultButton(onClick = { graphVM.findBridges() }, "find_bridges")

        val onClose = { isOpenedEdgeMenu = false }
        AddEdgeDialog(isOpenedEdgeMenu, onClose, graphVM)

        // Dijkstra dialog window
        DirectedAlgorithmDialog(
            isDijkstraMenu,
            "Dijkstra Algorithm",
            onCloseDijkstra,
            graphVM,
            "Dijkstra"
        )

        //Ford-Bellman dialog window
        DirectedAlgorithmDialog(
            isFordBellmanMenu,
            "Ford Bellman Algorithm",
            onCloseFB,
            graphVM,
            "FordBellman"
        )
    }
}
