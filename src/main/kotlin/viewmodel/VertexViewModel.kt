package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import height
import model.graph.edges.Edge
import width
import kotlin.random.Random

class VertexViewModel<V>(_vertex: V, _edges: MutableList<EdgeViewModel<V>> = mutableListOf()) :
    ViewModel() {
    val vertex: V = _vertex
    var edges = mutableStateListOf<EdgeViewModel<V>>()

    init {
        for (edge in _edges) {
            edges.add(edge)
        }
    }

    var x by mutableStateOf(Random.nextInt(100, width - 100).toFloat())
    var y by mutableStateOf(Random.nextInt(100, height - 100).toFloat())
    val vertexSize = 60f
    val degree
        get() = edges.size
}