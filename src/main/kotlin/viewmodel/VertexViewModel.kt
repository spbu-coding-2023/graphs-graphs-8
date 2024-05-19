package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import model.graph.edges.Edge

class VertexViewModel<V>(_vertex: V, _edges: MutableList<Edge<V>> = mutableListOf()) :
    ViewModel() {
    val vertex: V = _vertex
    var edges by mutableStateOf(_edges)
    var offsetX by mutableStateOf(1000f)
    var offsetY by mutableStateOf(540f)
    val vertexSize = 120f
}