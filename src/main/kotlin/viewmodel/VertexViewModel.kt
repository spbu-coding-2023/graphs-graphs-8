package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import height
import model.graph.edges.Edge
import width
import kotlin.random.Random

class VertexViewModel<V>(_vertex: V, _edges: MutableList<Edge<V>> = mutableListOf()) :
    ViewModel() {
    val vertex: V = _vertex
    var edges by mutableStateOf(_edges)
    var x by mutableStateOf(Random.nextInt(100, width - 100).toFloat())
    var y by mutableStateOf(Random.nextInt(100, height - 100).toFloat())
    val vertexSize = 80f
}