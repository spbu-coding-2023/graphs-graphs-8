package viewmodel

import Dijkstra
import model.graph.DirectedGraph
import model.graph.edges.Edge

class DirectedGraphViewModel<V>(
    name: String,
    val graph: DirectedGraph<V> = DirectedGraph()
): AbstractGraphViewModel<V>(name, graph){
    val model
        get() = graph
    init {
        for (vertex in graphModel.entries) {
            graphView[vertex.key] = VertexViewModel(vertex.key, vertex.value)
        }
    }
    fun dijkstraAlgo(start: V, end: V){
        val dalg = Dijkstra(graph.matrix, graph.size)
        dalg.dijkstra(start, end)
    }


    override fun addEdge(from: V, to: V, weight: Int) {
        if (graphView[from] == null) return
        for (i in graphView[from]?.edges!!) if (i.to == to) return
        val edgesCopy = graphView[from]?.edges?.toMutableList()!!
        edgesCopy.add(Edge(from, to, weight))
        graphView[from]?.edges = edgesCopy
        graphModel.addEdge(from, to, weight)
    }
}