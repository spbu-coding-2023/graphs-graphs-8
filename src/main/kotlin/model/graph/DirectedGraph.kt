package model.graph

class DirectedGraph<V> : Graph<V>() {
    override fun addEdge(from: V, to: V, weight: Int) {
        if (weight != 1) isWeighted = true
        if (weight < 0) negativeWeights = true
        val edge = Edge(from, to, weight)
        graph[from]?.add(edge) ?: { graph[from] = mutableListOf(edge) }
    }
}
