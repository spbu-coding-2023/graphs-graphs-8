package graph

import model.graph.Edge

class DirectedGraph<V> : Graph<V, Edge<V>>() {
    override fun addEdge(from: V, to: V) {
        val edge = Edge(from, to)
        graph[from]?.add(edge) ?: { graph[from] = mutableListOf(edge) }
    }
}