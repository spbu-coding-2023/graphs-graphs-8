package viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import model.graph.edges.Edge

abstract class AbstractGraphViewModel<V, G>(graph: G) : ViewModel() {
    val graphView = mutableStateMapOf<V, VertexViewModel<V>>()
    val graphModel = graph
    var size = 0

    fun addEdge(from: V, to: V) {
        if (graphView[from] == null) {
            return
        }
        for (i in graphView[from]?.edges!!) if(i.to == to) return
        val edgesCopy = graphView[from]?.edges?.toMutableList()!!
        edgesCopy.add(Edge(from, to))
        graphView[from]?.edges = edgesCopy
    }
}