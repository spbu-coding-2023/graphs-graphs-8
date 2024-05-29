package view.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.PointerMatcher
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
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
import localisation.getLocalisation
import model.algos.ForceAtlas2
import view.common.*
import view.graph.UndirectedGraphView
import viewmodel.MainScreenViewModel

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun UndirectedGraphScreen(
    navController: NavController,
    mainScreenViewModel: MainScreenViewModel,
    graphId: Int
) {
    val graphVM by mutableStateOf(mainScreenViewModel.graphs.getUndirected(graphId))
    val language = getLocalisation()

    Box(modifier = Modifier
        .fillMaxSize()
        .onPointerEvent(PointerEventType.Scroll) {
            if (it.changes.first().scrollDelta.y > 0) {
                graphVM.zoom = (graphVM.zoom - graphVM.zoom / 8).coerceIn(0.01f, 15f)
            } else {
                graphVM.zoom = (graphVM.zoom + graphVM.zoom / 8).coerceIn(0.01f, 15f)

                val awtEvent = it.awtEventOrNull
                if (awtEvent != null) {
                    val xPosition = awtEvent.x.toFloat()
                    val yPosition = awtEvent.y.toFloat()
                    val pointerVector =
                        (Offset(
                            xPosition,
                            yPosition
                        ) - (graphVM.canvasSize / 2f)) * (1 / graphVM.zoom)
                    graphVM.center += pointerVector * 0.15f
                }
            }
        }.pointerInput(Unit) {
            detectDragGestures(
                matcher = PointerMatcher.Primary
            ) {
                graphVM.center -= it * (1 / graphVM.zoom)
            }
        }.pointerHoverIcon(PointerIcon.Hand)
        .onSizeChanged {
            graphVM.canvasSize = Offset(it.width.toFloat(), it.height.toFloat())
        }
        .clipToBounds()
    ) {
        UndirectedGraphView(graphVM)
    }

    Column(modifier = Modifier.zIndex(1f).padding(16.dp).width(300.dp)) {
        var isOpenedVertexMenu by remember { mutableStateOf(false) }
        var isOpenedEdgeMenu by remember { mutableStateOf(false) }
        var isDijkstraMenu by remember { mutableStateOf(false) }
        var isFordBellmanMenu by remember { mutableStateOf(false) }
        var isVisualizationRunning by remember { mutableStateOf(false) }

        // To MainScreen
        DefaultShortButton({ navController.popBackStack() }, "home", defaultStyle)
        Spacer(modifier = Modifier.height(10.dp))

        // Add vertex Button
        DefaultShortButton(
            { isOpenedVertexMenu = !isOpenedVertexMenu }, "add_vertex", when (language) {
                ("en-US") -> defaultStyle
                ("ru-RU") -> smallSize
                else -> defaultStyle
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Add edge button
        DefaultShortButton({ isOpenedEdgeMenu = !isOpenedEdgeMenu }, "open_edge", defaultStyle)
        Spacer(modifier = Modifier.height(16.dp))

        // Save button
        DefaultShortButton({ graphVM.saveSQLite() }, "save", defaultStyle)
        Spacer(modifier = Modifier.height(10.dp))

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
            }, "visualize", defaultStyle,
            if (isVisualizationRunning) Color.Red else Color(0xffFFCB32)
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Reset colors Button
        DefaultShortButton(
            { graphVM.resetColors() }, "reset", when (language) {
                ("en-US") -> defaultStyle
                ("ru-RU") -> smallSize
                else -> defaultStyle
            }, Color.LightGray
        )
        Spacer(modifier = Modifier.height(10.dp))

        DefaultShortButton(
            { graphVM.drawBetweennessCentrality() },
            "betweenness_centrality",
            microSize
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Dijkstra Button
        DefaultShortButton(
            { isDijkstraMenu = !isDijkstraMenu }, "dijkstra", when (language) {
                ("en-US") -> defaultStyle
                ("ru-RU") -> smallSize
                ("cn-CN") -> smallSize
                else -> defaultStyle
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        // FordBellman Button
        DefaultShortButton(
            { isFordBellmanMenu = !isFordBellmanMenu }, "ford_bellman", when (language) {
                ("en-US") -> defaultStyle
                ("ru-RU") -> microSize
                ("cn-CN") -> smallSize
                else -> defaultStyle
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        DefaultShortButton(
            onClick = { graphVM.drawMst() }, "find_mst", when (language) {
                ("en-US") -> smallSize
                ("ru-RU") -> microSize
                else -> defaultStyle
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        DefaultShortButton(
            onClick = { graphVM.drawCycles("1") }, "find_cycles", when (language) {
                ("en-US") -> defaultStyle
                ("ru-RU") -> mediumSize
                else -> defaultStyle
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        DefaultShortButton(
            onClick = { graphVM.drawBridges() }, "find_bridges", when (language) {
                ("en-US") -> defaultStyle
                ("ru-RU") -> smallSize
                else -> defaultStyle
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Add vertex Dialog
        AddVertexDialog(
            isOpenedVertexMenu && isVisualizationRunning.not(),
            { isOpenedVertexMenu = false },
            graphVM,
        )

        // Add edge Dialog
        AddEdgeDialog(isOpenedEdgeMenu, { isOpenedEdgeMenu = false }, graphVM)

        // Dijkstra Dialog
        DirectedAlgorithmDialog(
            isDijkstraMenu,
            "Dijkstra Algorithm",
            { isDijkstraMenu = false },
            graphVM,
            "Dijkstra"
        )

        // Ford-Bellman Dialog
        DirectedAlgorithmDialog(
            isFordBellmanMenu,
            "Ford Bellman Algorithm",
            { isFordBellmanMenu = false },
            graphVM,
            "FordBellman"
        )

    }
}
