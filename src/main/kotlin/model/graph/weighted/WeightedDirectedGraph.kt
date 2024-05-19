package model.graph.weighted

import model.graph.edges.WeightedEdge

class WeightedDirectedGraph<V> : WeightedGraph<V>() {
    override fun addEdge(from: V, to: V, weight: Int) {
        val edge1 = WeightedEdge(from, to, weight)
        val edge2 = WeightedEdge(to, from, weight)
        graph[from]?.add(edge1) ?: { graph[from] = mutableListOf(edge1) }
        graph[to]?.add(edge2) ?: { graph[to] = mutableListOf(edge2) }
    }
}