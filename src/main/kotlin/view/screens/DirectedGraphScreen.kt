package view.screens


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.PointerMatcher
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.awtEventOrNull
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import model.algos.ForceAtlas2
import view.common.*
import view.graph.DirectedGraphView
import viewmodel.MainScreenViewModel

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun DirectedGraphScreen(
    navController: NavController,
    mainScreenViewModel: MainScreenViewModel,
    graphId: Int
) {
    val graphVM by mutableStateOf(mainScreenViewModel.graphs.getDirected(graphId))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onPointerEvent(PointerEventType.Scroll) {
                if (it.changes.first().scrollDelta.y > 0) {
                    graphVM.zoom = (graphVM.zoom - graphVM.zoom / 8).coerceIn(0.01f, 15f).dp.toPx()
                } else {
                    graphVM.zoom = (graphVM.zoom + graphVM.zoom / 8).coerceIn(0.01f, 15f).dp.toPx()

                    val awtEvent = it.awtEventOrNull
                    if (awtEvent != null) {
                        val xPosition = awtEvent.x.toFloat()
                        val yPosition = awtEvent.y.toFloat()
                        val pointerVector =
                            (Offset(
                                xPosition.dp.toPx(),
                                yPosition.dp.toPx()
                            ) - (graphVM.canvasSize / 2f)) * (1 / graphVM.zoom)
                        graphVM.center += pointerVector * (0.15f.dp.toPx())
                    }
                }
            }.pointerInput(Unit) {
                detectDragGestures(
                    matcher = PointerMatcher.Primary
                ) {
                    graphVM.center -= it * (1 / graphVM.zoom).dp.toPx()
                }
            }.pointerHoverIcon(PointerIcon.Hand)
            .onSizeChanged {
                graphVM.canvasSize = Offset(it.width.toFloat(), it.height.toFloat())
            }
            .clipToBounds()
    ) {
        DirectedGraphView(graphVM)
    }

    Column(modifier = Modifier.zIndex(1f).padding(16.dp).width(300.dp)) {
        Text("directed")

        var isOpenedVertexMenu by remember { mutableStateOf(false) }
        var isOpenedEdgeMenu by remember { mutableStateOf(false) }
        var isOpenedDijkstraMenu by remember { mutableStateOf(false) }
        var isOpenedFordBellmanMenu by remember { mutableStateOf(false) }
        var isVisualizationRunning by remember { mutableStateOf(false) }

        // To MainScreen
        DefaultShortButton({ navController.popBackStack() }, "home")
        Spacer(modifier = Modifier.height(10.dp))

        // Add vertex Button
        DefaultShortButton({ isOpenedVertexMenu = !isOpenedVertexMenu }, "add_vertex")
        Spacer(modifier = Modifier.height(10.dp))

        // Add edge Button
        DefaultShortButton({ isOpenedEdgeMenu = !isOpenedEdgeMenu }, "open_edge")
        Spacer(modifier = Modifier.height(10.dp))

        // Save button
        DefaultShortButton({ graphVM.saveSQLite() }, "save")
        Spacer(modifier = Modifier.height(16.dp))

        // Visualization Button
        val scope = rememberCoroutineScope { Dispatchers.Default }
        DefaultShortButton(
            {
                isVisualizationRunning = !isVisualizationRunning
                if (isVisualizationRunning) {
                    scope.launch {
                        ForceAtlas2.forceDrawing(graphVM)
                    }
                } else {
                    scope.coroutineContext.cancelChildren()
                }
            }, "visualize",
            if (isVisualizationRunning) Color.Red else Color(0xffFFCB32)
        )
        Spacer(modifier = Modifier.height(10.dp))

        DefaultShortButton({ graphVM.resetColors() }, "reset", Color.LightGray)
        Spacer(modifier = Modifier.height(10.dp))

        DefaultShortButton({ graphVM.drawStrongConnections() }, "find_strong_connections")
        Spacer(modifier = Modifier.height(10.dp))

        DefaultShortButton({ graphVM.chinaWhisperCluster() }, "find_clusters")
        Spacer(modifier = Modifier.height(10.dp))

        // Dijkstra Button
        DefaultShortButton({ isOpenedDijkstraMenu = !isOpenedDijkstraMenu }, "dijkstra")
        Spacer(modifier = Modifier.height(10.dp))

        // FordBellman Button
        DefaultShortButton({ isOpenedFordBellmanMenu = !isOpenedFordBellmanMenu }, "ford_bellman")
        Spacer(modifier = Modifier.height(10.dp))

        // Cycles Button
        DefaultShortButton(onClick = { graphVM.drawCycles("1") }, "find_cycles")
        Spacer(modifier = Modifier.height(10.dp))

        // Add vertex Dialog
        AddVertexDialog(
            isOpenedVertexMenu && isVisualizationRunning.not(),
            { isOpenedVertexMenu = false },
            graphVM,
        )

        // Add edge Dialog
        AddEdgeDialog(
            isOpenedEdgeMenu,
            { isOpenedEdgeMenu = !isOpenedEdgeMenu },
            graphVM,
            isDirected = true
        )

        // Dijkstra Dialog
        DirectedAlgorithmDialog(
            isOpenedDijkstraMenu,
            "Dijkstra Algorithm",
            { isOpenedDijkstraMenu = false },
            graphVM,
            "Dijkstra"
        )

        // Ford-Bellman Dialog
        DirectedAlgorithmDialog(
            isOpenedFordBellmanMenu,
            "Ford Bellman Algorithm",
            { isOpenedFordBellmanMenu = false },
            graphVM,
            "FordBellman"
        )
    }
}

