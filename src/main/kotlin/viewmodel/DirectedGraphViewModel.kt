package viewmodel

import model.graph.DirectedGraph
import model.graph.edges.Edge

class DirectedGraphViewModel<V>(
    name: String,
    graph: DirectedGraph<V> = DirectedGraph()
) : AbstractGraphViewModel<V>(name, graph) {

    override fun addEdge(from: V, to: V) {
        if (graphView[from] == null) return
        for (i in graphView[from]?.edges!!) if (i.to == to) return
        val edgesCopy = graphView[from]?.edges?.toMutableList()!!
        edgesCopy.add(Edge(from, to))
        graphView[from]?.edges = edgesCopy
    }

}