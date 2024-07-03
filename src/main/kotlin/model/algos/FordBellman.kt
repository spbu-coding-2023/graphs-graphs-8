package model.algos

import model.graph.Graph
import model.graph.Edge

typealias Path<V> = List<Edge<V>>
typealias Paths<V> = Map<V, Path<V>>

object FordBellman {
    /**
     * @return
     * If it is possible to reach the destination , then return Pair(length of shortest path, path)
     *
     * If it is possible to reach the destination, but graph contains negative cycle,
     * than return Pair(null, some path to destination)
     *
     * If it is not possible to reach the destination, then return Pair(null, null)
     *
     */
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
        if (distances[curVert] != null) {
            path = mutableListOf()
            while (curVert != from) {
                val prevEdge = minSources[curVert]
                    ?: throw IllegalStateException("Can't find previous edge of path")
                path.add(prevEdge)
                curVert = prevEdge.from
            }
        }

        if (lastTimeRelaxed) distances[to] = null
        val pathAnswer = path?.reversed()?.toList()
        return Pair(distances[to], pathAnswer)
    }
}

