package viewmodel

import model.graph.DirectedGraph

class DirectedGraphViewModel<V>(
    _name: String,
    graph: DirectedGraph<V> = DirectedGraph()
): AbstractGraphViewModel<V, DirectedGraph<V>>(graph){
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