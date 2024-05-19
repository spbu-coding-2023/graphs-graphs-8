package viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import graph.UndirectedGraph
import model.graph.Edge

class GraphViewModel<out V, out E : Edge<V>>(
    name: String,
    graph: UndirectedGraph<V> = UndirectedGraph<V>()
) :
    ViewModel() {
    val name by mutableStateOf(name)
    val size
        get() = graphModel.size
    val graphView = mutableStateMapOf<V, VertexViewModel<V, E>>()
    val graphModel = graph

    init {
        for (vertex in graphModel.entries) {
            graphView[vertex.key] = VertexViewModel(vertex.key, vertex.value)
        }
    }

    fun addVertex(vertex: V) {
        graphView.putIfAbsent(vertex, VertexViewModel(vertex))
        graphModel.addVertex(vertex)
    }

    fun addEdge(from: V, to: V) {
        if (graphView[from] == null) {
            return
        }
        val edgesCopy = graphView[from]?.edges?.toMutableList()!!
        edgesCopy.add(Edge(from, to))
        graphView[from]?.edges = edgesCopy
    }
}