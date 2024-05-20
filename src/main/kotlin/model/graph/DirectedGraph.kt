package model.graph

import graph.Graph
import model.graph.edges.Edge

class DirectedGraph<V> : Graph<V>() {
    override fun addEdge(from: V, to: V, weight: Int) {
        if (weight != 1) weighted = true
        val edge = Edge(from, to, weight)
        graph[from]?.add(edge) ?: { graph[from] = mutableListOf(edge) }
    }
}
