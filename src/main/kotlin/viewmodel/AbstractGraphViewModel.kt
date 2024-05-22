package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import model.graph.Graph

abstract class AbstractGraphViewModel<V>(_name: String, graph: Graph<V>) : ViewModel() {
    val name = _name
    var vertexView by mutableStateOf(mutableMapOf<V, VertexViewModel<V>>())
    var edgesView by mutableStateOf(mutableListOf<EdgeViewModel<V>>())
    val graphModel = graph
    var size = 0

    init {
        for (vertex in graphModel.entries) {
            vertexView[vertex.key] = VertexViewModel(vertex.key, vertex.value)
        }
    }

    fun addVertex(vertex: V) {
        size += 1
        vertexView.putIfAbsent(vertex, VertexViewModel(vertex))
        graphModel.addVertex(vertex)
        updateView()
    }

    fun updateView() {
        val keep = vertexView
        vertexView = mutableMapOf<V, VertexViewModel<V>>()
        vertexView = keep
    }

    abstract fun addEdge(from: V, to: V, weight: Int = 1)
}