package model.algos

import model.graph.UndirectedGraph
import model.graph.edges.Edge

object FCADFS {
    fun <V> findCycleUtil(
        vertex: V,
        visited: MutableMap<V, Boolean>,
        parent: V?,
        graph: UndirectedGraph<V>,
        path: MutableList<Edge<V>>
    ): List<Edge<V>>? {
        visited[vertex] = true

        graph.edgesOf(vertex).forEach { edge ->
            val next = if (edge.from == vertex) edge.to else edge.from
            if (visited[next] != true) {
                path.add(edge)
                val cyclePath = findCycleUtil(next, visited, vertex, graph, path)
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

    fun <V> findCycle(graph: UndirectedGraph<V>): List<Edge<V>>? {
        val visited = mutableMapOf<V, Boolean>()
        val path = mutableListOf<Edge<V>>()

        for (vertex in graph.vertices) {
            if (visited[vertex] != true) {
                val cyclePath = findCycleUtil(vertex, visited, null, graph, path)
                if (cyclePath != null) {
                    return cyclePath
                }
            }
        }
        return null
    }
}




//
//object FCADFS {
//    fun <V> findCycleUtil(
//        v: V,
//        visited: MutableMap<V, Boolean>,
//        parent: V?,
//        path: MutableList<Edge<V>>,
//        graph: UndirectedGraph<V>
//    ): List<Edge<V>>? {
//        visited[v] = true
//
//        for (edge in graph.edgesOf(v)) {
//            val next = if (edge.from == v) edge.to else edge.from
//            if (visited[next] != true) {
//                path.add(edge)
//                val cyclePath = findCycleUtil(next, visited, v, path, graph)
//                if (cyclePath != null) {
//                    return cyclePath
//                }
//                path.removeAt(path.size - 1)
//            } else if (parent != next) {
//                path.add(edge)
//                return path.dropWhile { it.from != next && it.to != next }.toList()
//            }
//        }
//        return null
//    }
//
//    fun <V> findCycle(graph: UndirectedGraph<V>): List<Edge<V>>? {
//        val visited = mutableMapOf<V, Boolean>()
//        val path = mutableListOf<Edge<V>>()
//
//        for (vertex in graph.vertices) {
//            if (visited[vertex] != true) {
//                val cyclePath = findCycleUtil(vertex, visited, null, path, graph)
//                if (cyclePath != null) {
//                    return cyclePath
//                }
//            }
//        }
//        return null
//    }
//}