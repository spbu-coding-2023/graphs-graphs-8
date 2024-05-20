package viewmodel

import model.graph.UndirectedGraph

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

}