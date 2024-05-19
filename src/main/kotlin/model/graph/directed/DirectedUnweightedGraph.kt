package model.graph.directed

import model.graph.edges.Edge

class DirectedUnweightedGraph<V> : DirectedGraph<V>() {
    override fun addEdge(from: V, to: V, weight: Int) {
        val edge = Edge(from, to)
        graph[from]?.add(edge) ?: { graph[from] = mutableListOf(edge) }
    }
}