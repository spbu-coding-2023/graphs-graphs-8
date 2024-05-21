package viewmodel

import Dijkstra
import model.graph.DirectedGraph
import kotlin.collections.set


class DirectedGraphViewModel<V>(
    _name: String,
    val graph: DirectedGraph<V> = DirectedGraph()
): AbstractGraphViewModel<V, DirectedGraph<V>>(graph){
    val name = _name
    init {
        for (vertex in graphModel.entries) {
            graphView[vertex.key] = VertexViewModel(vertex.key, vertex.value)
        }
    }
    fun dijkstraAlgo(start: V){
        val dalg = Dijkstra(graph.matrix, graph.size)
        dalg.dijkstra(start)
    }

    fun addVertex(vertex: V) {
        size += 1
        graphView.putIfAbsent(vertex, VertexViewModel(vertex))
        graphModel.addVertex(vertex)
    }

}