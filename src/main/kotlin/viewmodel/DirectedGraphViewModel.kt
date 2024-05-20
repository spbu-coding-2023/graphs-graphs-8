package viewmodel

import model.graph.UndirectedGraph
import model.graph.edges.Edge

class DirectedGraphViewModel<V, E : Edge<V>>(
    _name: String,
    graph: UndirectedGraph<V> = UndirectedGraph<V>()
): AbstractGraphViewModel<V, UndirectedGraph<V>>(graph){
    val name = _name
    init {
        for (vertex in graphModel.entries) {
            graphView[vertex.key] = VertexViewModel(vertex.key, vertex.value)
        }
    }

    fun addVertex(vertex: V) {
        graphView.putIfAbsent(vertex, VertexViewModel(vertex))
        graphModel.addVertex(vertex)
    }

}