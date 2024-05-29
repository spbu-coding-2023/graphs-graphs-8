package viewmodel.graph

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import height
import width
import view.common.DefaultColors
import kotlin.random.Random

class VertexViewModel<V>(
    _vertex: V,
    _edges: MutableList<EdgeViewModel<V>> = mutableListOf(),
    graphVM: AbstractGraphViewModel<V>,
    centerCoordinates: Boolean = false
) :
    ViewModel() {
    val vertex: V = _vertex
    var edges = mutableStateListOf<EdgeViewModel<V>>()
    var x by mutableStateOf(0f)
    var y by mutableStateOf(0f)
    val graphVM = graphVM

    init {
        for (edge in _edges) {
            edges.add(edge)
        }
        if (centerCoordinates) {
            x = Random.nextInt(width / 2 - 300, width / 2 + 300).toFloat()
            y = Random.nextInt(height / 2 - 300, height / 2 + 300).toFloat()
        } else {
            x = Random.nextInt(0, 30000).toFloat()
            y = Random.nextInt(0, 30000).toFloat()
        }
    }

    val offsetX
        get() = (graphVM.canvasSize.x / 2) + ((x - graphVM.center.x) * graphVM.zoom)
    val offsetY
        get() = (graphVM.canvasSize.y / 2) + ((y - graphVM.center.y) * graphVM.zoom)

    var vertexSize by mutableStateOf(60f)
    var centrality by mutableStateOf(0.0)
    var color by mutableStateOf(DefaultColors.primaryBright)
    val degree
        get() = edges.size
}