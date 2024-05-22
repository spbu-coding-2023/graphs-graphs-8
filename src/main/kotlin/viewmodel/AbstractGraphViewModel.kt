package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import model.graph.Graph
import model.graph.edges.Edge

abstract class AbstractGraphViewModel<V>(_name: String, graph: Graph<V>) : ViewModel() {
    val name = _name
    var graphView by mutableStateOf(mutableMapOf<V, VertexViewModel<V>>())
    val graphModel = graph
    var size = 0

    init {
        for (vertex in graphModel.entries) {
            graphView[vertex.key] = VertexViewModel(vertex.key, vertex.value)
        }
    }

    fun addVertex(vertex: V) {
        size += 1
        graphView.putIfAbsent(vertex, VertexViewModel(vertex))
        graphModel.addVertex(vertex)
        updateView()
    }

    fun updateView() {
        val keep = graphView
        graphView = mutableMapOf<V, VertexViewModel<V>>()
        graphView = keep
    }

    abstract fun addEdge(from: V, to: V)
}