package model.graph.weighted

import model.graph.edges.WeightedEdge

class WeightedDirectedGraph<V> : WeightedGraph<V>() {
    override fun addEdge(from: V, to: V, weight: Int) {
        val edge = WeightedEdge(from, to, weight)
        graph[from]?.add(edge) ?: { graph[from] = mutableListOf(edge) }
    }
}