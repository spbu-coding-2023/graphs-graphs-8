package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import model.graph.Edge

class VertexViewModel<V, E : Edge<V>>(_vertex: V, _edges: MutableList<Edge<V>> = mutableListOf()) :
    ViewModel() {
    val vertex: V = _vertex
    var edges by mutableStateOf(_edges)
    var offsetX by mutableStateOf(1000f)
    var offsetY by mutableStateOf(540f)
    val vertexSize = 120f
}