package model.algos

import graph.Graph
import model.graph.edges.Edge

typealias Path<V> = List<Edge<V>>
typealias Paths<V> = Map<V, Path<V>>

object SPAFordBellman {
    //should be : Pair<Int, Path<V>>
    fun <V> findShortestPath(from: V, to: V, graph: Graph<V>): Pair<Int?, Path<V>?> {
        val distances = mutableMapOf<V, Int?>()
        val minSources = mutableMapOf<V, Edge<V>>()
        for (vertex in graph.vertices) {
            distances[vertex] = null
        }
        distances[from] = 0

        var lastTimeRelaxed = false
        repeat(graph.size) {
            lastTimeRelaxed = false
            for (edge in graph.edges) {
                val from = edge.from
                val to = edge.to
                if (distances[from] == null) continue

                val newWeight = distances[from]!! + edge.weight
                val oldWeight: Int
                if (distances[to] == null) {
                    distances[to] = newWeight
                    minSources[to] = edge
                    lastTimeRelaxed = true
                    continue
                }

                oldWeight = distances[to]!!
                if (oldWeight > newWeight) {
                    distances[to] = newWeight
                    minSources[to] = edge
                    lastTimeRelaxed = true
                }
            }
        }
        var path: MutableList<Edge<V>>? = null
        var curVert = to
        if (!lastTimeRelaxed && distances[curVert] != null) {
            path = mutableListOf()
            while (curVert != from) {
                val prevEdge = minSources[curVert]
                    ?: throw IllegalStateException("Can't find previous edge of path")
                path.add(prevEdge)
                curVert = prevEdge.from
            }
        }
        val pathAnswer = path?.reversed()?.toList()
        return Pair(distances[to], pathAnswer)
    }

    fun <V> findShortestPath(from: V, graph: Graph<V>): Pair<Map<V, Int>, Paths<V>> {
        TODO()
    }
}

