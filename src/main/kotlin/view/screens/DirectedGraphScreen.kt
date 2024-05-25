package view.screens


import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import model.algos.ForceAtlas2
import view.common.*
import view.views.DirectedGraphView
import viewmodel.MainScreenViewModel
import kotlin.math.exp
import kotlin.math.sign

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DirectedGraphScreen(
    navController: NavController,
    mainScreenViewModel: MainScreenViewModel,
    graphId: Int
) {
    val graphVM by mutableStateOf(mainScreenViewModel.graphs.getDirected(graphId))

    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
    }

    fun scale(delta: Int) {
        scale = (scale * exp(delta * 0.2f)).coerceIn(0.01f, 1.75f)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .transformable(state = state)
            .onPointerEvent(PointerEventType.Scroll) {
                val change = it.changes.first()
                val delta = change.scrollDelta.y.toInt().sign
                scale(delta)
            }
            .focusable()
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                rotationZ = rotation,
                translationX = offset.x,
                translationY = offset.y
            )
    ) {
        DirectedGraphView(graphVM)
    }

    Column(modifier = Modifier.zIndex(1f).padding(16.dp).width(300.dp)) {
        Text("directed")

        var isOpenedVertexMenu by remember { mutableStateOf(false) }
        var isOpenedEdgeMenu by remember { mutableStateOf(false) }
        var isOpenedDijkstraMenu by remember { mutableStateOf(false) }
        var isOpenedFordBellmanMenu by remember { mutableStateOf(false) }

        // To MainScreen
        DefaultShortButton({ navController.popBackStack() }, "home")
        Spacer(modifier = Modifier.height(10.dp))

        // Add vertex Button
        DefaultShortButton({ isOpenedVertexMenu = !isOpenedVertexMenu }, "add_vertex")
        Spacer(modifier = Modifier.height(10.dp))

        // Add edge Button
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
        DefaultShortButton({ isOpenedDijkstraMenu = !isOpenedDijkstraMenu }, "dijkstra")
        Spacer(modifier = Modifier.height(10.dp))

        // FordBellman Button
        DefaultShortButton({ isOpenedFordBellmanMenu = !isOpenedFordBellmanMenu }, "ford_bellman")
        Spacer(modifier = Modifier.height(10.dp))

        // Cycles Button
        DefaultShortButton(onClick = { graphVM.drawCycles("1") }, "find_cycles")
        Spacer(modifier = Modifier.height(10.dp))

        // Add edge Dialog
        AddEdgeDialog(
            isOpenedEdgeMenu,
            { isOpenedEdgeMenu = !isOpenedEdgeMenu },
            graphVM,
            isDirected = true
        )

        // Add vertex Dialog
        AddVertexDialog(
            isOpenedVertexMenu,
            { isOpenedVertexMenu = false },
            graphVM,
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

