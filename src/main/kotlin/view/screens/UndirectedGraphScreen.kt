package view.screens

import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import model.algos.ForceAtlas2
import view.common.AddEdgeDialog
import view.common.AddVertexDialog
import view.common.DefaultShortButton
import view.common.DirectedAlgorithmDialog
import view.views.UndirectedGraphView
import viewmodel.MainScreenViewModel
import kotlin.math.exp
import kotlin.math.sign

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UndirectedGraphScreen(
    navController: NavController,
    mainScreenViewModel: MainScreenViewModel,
    graphId: Int
) {
    val graphVM by mutableStateOf(mainScreenViewModel.graphs.getUndirected(graphId))

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

    Box(modifier = Modifier
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
        )) {
        UndirectedGraphView(graphVM)
    }

    Column(modifier = Modifier.zIndex(1f).padding(16.dp).width(300.dp)) {
        Text("Undirected")

        var isOpenedVertexMenu by remember { mutableStateOf(false) }
        var isOpenedEdgeMenu by remember { mutableStateOf(false) }
        var isDijkstraMenu by remember { mutableStateOf(false) }
        var isFordBellmanMenu by remember { mutableStateOf(false) }
        var isVisualizationRunning by remember { mutableStateOf(false) }

        // To MainScreen
        DefaultShortButton({ navController.popBackStack() }, "home")
        Spacer(modifier = Modifier.height(10.dp))

        // Add vertex Button
        DefaultShortButton({ isOpenedVertexMenu = !isOpenedVertexMenu }, "add_vertex")
        Spacer(modifier = Modifier.height(10.dp))

        // Add edge button
        DefaultShortButton({ isOpenedEdgeMenu = !isOpenedEdgeMenu }, "open_edge")
        Spacer(modifier = Modifier.height(16.dp))

        // Save button
        DefaultShortButton({ graphVM.saveSQLite() }, "save")
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
            }, "visualize",
            if (isVisualizationRunning) Color.Red else Color(0xffFFCB32)
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Reset colors Button
        DefaultShortButton({ graphVM.resetColors() }, "reset", Color.LightGray)
        Spacer(modifier = Modifier.height(10.dp))

        // Dijkstra Button
        DefaultShortButton({ isDijkstraMenu = !isDijkstraMenu }, "dijkstra")
        Spacer(modifier = Modifier.height(10.dp))

        // FordBellman Button
        DefaultShortButton({ isFordBellmanMenu = !isFordBellmanMenu }, "ford_bellman")
        Spacer(modifier = Modifier.height(10.dp))

        DefaultShortButton(onClick = { graphVM.drawMst() }, "find_mst")
        Spacer(modifier = Modifier.height(10.dp))

        DefaultShortButton(onClick = { graphVM.drawCycles("1") }, "find_cycles")
        Spacer(modifier = Modifier.height(10.dp))

        DefaultShortButton(onClick = { graphVM.drawBridges() }, "find_bridges")
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
