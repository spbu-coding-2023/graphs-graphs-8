package viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import graph.GraphAbstract
import graph.UndirectedGraph

class GraphViewModel(name: String, graph: GraphAbstract = UndirectedGraph()) : ViewModel() {
    val name by mutableStateOf(name)
    val size
        get() = graphModel.size
    val graphView = mutableStateMapOf<Int, VertexViewModel>()
    val graphModel = graph

    init {
        for (vertex in graphModel.entries) {
            graphView[vertex.key] = VertexViewModel(vertex.key, vertex.value)
        }
    }

    fun addVertex(number: Int) {
        graphView.putIfAbsent(number,VertexViewModel(number))
        graphModel.addVertex(number)
    }

    fun addEdge(source: Int, destination: Int) {
        if (graphView[source] == null) {
            return
        }
        val edgesCopy = graphView[source]?.edges?.toMutableList()!!
        edgesCopy.add(destination)
        graphView[source]?.edges = edgesCopy
    }
}