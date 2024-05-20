package model.graph

import graph.Graph
import model.graph.edges.Edge

class UndirectedGraph<V> : Graph<V>() {
    override fun addEdge(from: V, to: V, weight: Int) {
        if (weight != 1) weighted = true
        val edge1 = Edge(from, to, weight)
        val edge2 = Edge(to, from, weight)
        graph[from]?.add(edge1) ?: { graph[from] = mutableListOf(edge1) }
        graph[to]?.add(edge2) ?: { graph[to] = mutableListOf(edge2) }
    }
}
