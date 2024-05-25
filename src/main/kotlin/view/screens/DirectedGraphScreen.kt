package view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import model.algos.ForceAtlas2
import view.common.*
import view.views.DirectedGraphView
import viewmodel.MainScreenViewModel


@Composable
fun DirectedGraphScreen(
    navController: NavController,
    mainScreenViewModel: MainScreenViewModel,
    graphId: Int
) {

    val graphVM by mutableStateOf(mainScreenViewModel.graphs.getDirected(graphId))

    Box(modifier = Modifier.fillMaxSize()) {
        DirectedGraphView(graphVM)
    }

    Column(modifier = Modifier.zIndex(1f).padding(16.dp).width(300.dp)) {
        // To MainScreen
        DefaultShortButton({ navController.popBackStack() }, "home")
        Spacer(modifier = Modifier.height(10.dp))

        // Add vertex Button
        DefaultShortButton({ graphVM.addVertex(graphVM.size.toString()) }, "add_vertex")
        Spacer(modifier = Modifier.height(10.dp))

        // Add edge Button
        var isOpenedEdgeMenu by remember { mutableStateOf(false) }
        val onCloseEdge = { isOpenedEdgeMenu = false }
        DefaultShortButton({ isOpenedEdgeMenu = !isOpenedEdgeMenu }, "open_edge")
        Spacer(modifier = Modifier.height(10.dp))

        DefaultShortButton({ graphVM.saveSQLite() }, "save")
        Spacer(modifier = Modifier.height(16.dp))

        DefaultShortButton({ ForceAtlas2.forceDrawing(graphVM) }, "visualize", Color(0xffFFCB32))
        Spacer(modifier = Modifier.height(10.dp))

        DefaultShortButton({ graphVM.resetColors() }, "reset", Color.LightGray)
        Spacer(modifier = Modifier.height(10.dp))

        DefaultShortButton({ graphVM.drawStrongConnections() }, "find_strong_connections")
        Spacer(modifier = Modifier.height(10.dp))

        DefaultShortButton({ graphVM.chinaWhisperCluster() }, "find_clusters")
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

        DefaultShortButton(onClick = { graphVM.drawCycles("1") }, "find_cycles")
        Spacer(modifier = Modifier.height(10.dp))

        AddEdgeDialog(isOpenedEdgeMenu, onCloseEdge, graphVM, isDirected = true)

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

