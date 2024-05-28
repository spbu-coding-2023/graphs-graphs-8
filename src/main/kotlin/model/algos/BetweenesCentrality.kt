package model.algos

import model.graph.Graph

object BetweenesCentrality {
    fun <V> pagerank(graph: Graph<V>, topN: Int): List<Pair<V, Double>> {
        val ranks = mutableMapOf<V, Double>()
        val dampingFactor = 0.8
        val vertices = graph.vertices

        vertices.forEach { vertex -> ranks[vertex] = 1.0/ vertices.size }
        repeat(100) {
            val newRanks = mutableMapOf<V, Double>()
            vertices.forEach { vertex ->
                var rankSum = 0.0
                vertices.forEach { neighbor ->
                    val edges = graph.matrix[neighbor]
                    if (neighbor != vertex && edges != null) {
                        if (edges.any { it.to == vertex }) {
                            rankSum += ranks[neighbor]?.div(edges.size) ?: 0.0
                        }
                    }
                }
                newRanks[vertex] = (1 - dampingFactor) / vertices.size + dampingFactor * rankSum
            }
            newRanks.forEach { (vertex, value) ->
                ranks[vertex] = value
            }
        }
        return ranks.entries.sortedByDescending { it.value }.take(topN).map { it.toPair() }
    }
}