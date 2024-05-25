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
import view.common.DefaultShortButton
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
        DefaultShortButton({ navController.popBackStack() }, "home")
        Spacer(modifier = Modifier.height(10.dp))

        // Add vertex
        DefaultShortButton({ graphVM.addVertex(graphVM.size.toString()) }, "add_vertex")
        Spacer(modifier = Modifier.height(10.dp))

        // Add edge button
        var isOpenedEdgeMenu by remember { mutableStateOf(false) }
        DefaultShortButton({ isOpenedEdgeMenu = !isOpenedEdgeMenu }, "open_edge")
        Spacer(modifier = Modifier.height(16.dp))

        // Save button
        DefaultShortButton({ graphVM.saveSQLite() }, "save")
        Spacer(modifier = Modifier.height(10.dp))

        DefaultShortButton({ ForceAtlas2.forceDrawing(graphVM) }, "visualize", Color(0xffFFCB32))
        Spacer(modifier = Modifier.height(10.dp))

        DefaultShortButton({ graphVM.resetColors() }, "reset", Color.LightGray)
        Spacer(modifier = Modifier.height(10.dp))

        // Dijkstra Button
        var isDijkstraMenu by remember { mutableStateOf(false) }
        val onCloseDijkstra = { isDijkstraMenu = !isDijkstraMenu }
        DefaultShortButton(onCloseDijkstra, "dijkstra")
        Spacer(modifier = Modifier.height(10.dp))

        // FordBellman Button
        var isFordBellmanMenu by remember { mutableStateOf(false) }
        val onCloseFB = { isFordBellmanMenu = !isFordBellmanMenu }
        DefaultShortButton(onCloseFB, "ford_bellman")
        Spacer(modifier = Modifier.height(10.dp))

        DefaultShortButton(onClick = { graphVM.findMst() }, "find_mst")
        Spacer(modifier = Modifier.height(10.dp))

        DefaultShortButton(onClick = { graphVM.findCycles() }, "find_cycles")
        Spacer(modifier = Modifier.height(10.dp))

        DefaultShortButton(onClick = { graphVM.findBridges() }, "find_bridges")
        Spacer(modifier = Modifier.height(10.dp))

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
