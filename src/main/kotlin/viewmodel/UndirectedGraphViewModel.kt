package viewmodel

import model.graph.UndirectedGraph
import model.graph.edges.Edge

class UndirectedGraphViewModel<V>(
    name: String,
    graph: UndirectedGraph<V> = UndirectedGraph()
) : AbstractGraphViewModel<V>(name, graph) {

    override fun addEdge(from: V, to: V) {
        if (graphView[from] == null) return
        for (i in graphView[from]?.edges!!) if (i.to == to) return
        val edgesCopy = graphView[from]?.edges?.toMutableList()!!
        edgesCopy.add(Edge(from, to))
        edgesCopy.add(Edge(to, from))
        graphView[from]?.edges = edgesCopy
    }

}