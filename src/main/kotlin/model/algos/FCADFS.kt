package model.algos

import model.graph.UndirectedGraph
import model.graph.edges.Edge

fun <V> findCycleUtil(
    graph: UndirectedGraph<V>,
    startVertex: V,
    visited: MutableMap<V, Boolean>,
    parent: V?,
    path: MutableList<Edge<V>>
): List<Edge<V>>? {
    visited[startVertex] = true

    graph.edgesOf(startVertex).forEach { edge ->
        val next = if (edge.from == startVertex) edge.to else edge.from
        if (visited[next] != true) {
            path.add(edge)
            val cyclePath = findCycleUtil(graph, next, visited, startVertex, path)
            if (cyclePath != null) {
                return cyclePath
            }
            path.removeAt(path.size - 1)
        } else if (parent != next) {
            path.add(edge)
            return path.dropWhile { it.from != next && it.to != next }.toList()
        }
    }
    return null
}

fun <V> findCycle(graph: UndirectedGraph<V>, start: V): List<Edge<V>>? {
    val visited = mutableMapOf<V, Boolean>()
    val path = mutableListOf<Edge<V>>()

    if (visited[start] != true) {
        val cyclePath = findCycleUtil(graph, start, visited, null, path)
        if (cyclePath != null) {
            return cyclePath
        }
    }

    return null
}