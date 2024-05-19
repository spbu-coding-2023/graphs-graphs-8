package model.graph.unweighted

import graph.Graph
import model.graph.edges.Edge

class DirectedGraph<V> : UnweightedGraph<V>() {
    override fun addEdge(from: V, to: V) {
        val edge = Edge(from, to)
        graph[from]?.add(edge) ?: { graph[from] = mutableListOf(edge) }
    }
}