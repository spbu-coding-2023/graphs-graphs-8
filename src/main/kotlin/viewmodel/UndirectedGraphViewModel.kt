package viewmodel

import model.graph.UndirectedGraph
import model.graph.edges.Edge

class UndirectedGraphViewModel<V>(
    _name: String,
    val graph: UndirectedGraph<V> = UndirectedGraph()
): AbstractGraphViewModel<V, UndirectedGraph<V>>(graph){
    val name = _name
    val model
        get() = graph
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
    fun addEdge(from: V, to: V, weight: Int) {
        if (graphView[from] == null) return
        for (i in graphView[from]?.edges!!) if(i.to == to) return
        val edgesCopy = graphView[from]?.edges?.toMutableList()!!
        edgesCopy.add(Edge(from, to, weight))
        edgesCopy.add(Edge(to, from, weight))
        graphView[from]?.edges = edgesCopy
        graphModel.addEdge(from, to, weight)
    }

}