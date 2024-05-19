package model.algos

import model.graph.edges.Edge
import model.graph.weighted.WeightedGraph
import view.defaultStyle

typealias Path<V> = Array<Edge<V>>
typealias Paths<V> = Map<V, Path<V>>

object FordBellman {
    //should be : Pair<Int, Path<V>>
    fun <V> findShortestPath(from: V, to: V, graph: WeightedGraph<V>): Int? {
        val distances = mutableMapOf<V, Int?>()
        for (vertex in graph.vertices) {
            distances[vertex] = null
        }
        distances[from] = 0

        var isRelaxed = false
        repeat(graph.size) {
            isRelaxed = false
            for (edge in graph.edges) {
                val from = edge.from
                val to = edge.to
                if (distances[from] == null) continue

                val newWeight = distances[from]!! + edge.weight
                val oldWeight: Int
                if (distances[to] == null) {
                    distances[to] = newWeight
                    isRelaxed = true
                    continue
                }
                oldWeight = distances[to]!!

                if (oldWeight > newWeight) {
                    distances[to] = newWeight
                    isRelaxed = true
                }
            }
        }

        return distances[to]
    }

    fun <V> findShortestPath(from: V, graph: WeightedGraph<V>): Pair<Map<V, Int>, Paths<V>> {
        TODO()
    }
}

