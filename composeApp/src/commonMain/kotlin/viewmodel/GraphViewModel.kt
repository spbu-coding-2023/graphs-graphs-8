package viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import graph.Graph

class GraphViewModel(name: String, graph: Graph = Graph()) : ViewModel() {
    val name by mutableStateOf(name)
    val graphView = mutableStateMapOf<Int, VertexViewModel>()
    val graphModel = graph.getGraphProp()

    init {
        for (vertex in graphModel.entries) {
            graphView[vertex.key] = VertexViewModel(vertex.key, vertex.value)
        }
    }

    fun addVertex() {
        graphView[graphView.size] = VertexViewModel(graphView.size)
    }

    fun addEdge(source: Int, destination: Int) {
        if (graphView[source] == null) {
            throw IllegalArgumentException("Can't create edge from non-existent vertex")
        }
        val EdgesCopy = graphView[source]?.edges?.toMutableList()!!
        EdgesCopy.add(destination)
        graphView[source]?.edges = EdgesCopy
    }
}