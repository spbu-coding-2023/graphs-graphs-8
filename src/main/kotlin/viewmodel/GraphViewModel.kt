package viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import graph.Graph
import model.graph.UndirectedGraph
import model.graph.edges.Edge

class GraphViewModel<V, E : Edge<V>>(
    _name: String,
    graph: Graph<V> = UndirectedGraph<V>()
) : ViewModel() {
    val name = _name
    val size
        get() = graphModel.size
    val graphView = mutableStateMapOf<V, VertexViewModel<V>>()
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
        for (i in graphView[from]?.edges!!) if (i.to == to) return
        val edgesCopy = graphView[from]?.edges?.toMutableList()!!
        edgesCopy.add(Edge(from, to))
        graphView[from]?.edges = edgesCopy
    }
}