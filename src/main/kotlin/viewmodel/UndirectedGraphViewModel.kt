package viewmodel

import model.graph.UndirectedGraph
import model.graph.edges.Edge

class UndirectedGraphViewModel<V>(
    _name: String,
    graph: UndirectedGraph<V> = UndirectedGraph()
): AbstractGraphViewModel<V, UndirectedGraph<V>>(graph){
    val name = _name
    init {
        for (vertex in graphModel.entries) {
            graphView[vertex.key] = VertexViewModel(vertex.key, vertex.value)
        }
    }

    fun addVertex(vertex: V) {
        size += 1
        graphView.putIfAbsent(vertex, VertexViewModel(vertex))
        graphModel.addVertex(vertex)
    }
    override fun addEdge(from: V, to: V) {
        if (graphView[from] == null) return
        for (i in graphView[from]?.edges!!) if(i.to == to) return
        val edgesCopy = graphView[from]?.edges?.toMutableList()!!
        edgesCopy.add(Edge(from, to))
        edgesCopy.add(Edge(to, from))
        graphView[from]?.edges = edgesCopy
    }

}