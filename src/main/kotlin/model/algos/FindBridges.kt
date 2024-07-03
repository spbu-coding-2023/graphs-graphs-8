package model.algos

import model.graph.UndirectedGraph
import model.graph.Edge

fun <V> findBridges(graph: UndirectedGraph<V>): Set<Edge<V>> {
    val timeIn = mutableMapOf<V, Int>()
    for (vertex in graph.vertices) {
        timeIn[vertex] = -1
    }
    val ret = mutableMapOf<V, Int>()
    var time = 0
    val bridges = mutableSetOf<Edge<V>>()
    val notVisited = graph.vertices.toMutableSet()

    fun dfs(vertex: V, prevVertex: V) {
        timeIn[vertex] = time++
        notVisited.remove(vertex)
        ret[vertex] = timeIn[vertex]!!
        val edges = graph.edgesOf(vertex)
        for (edge in edges) {
            val destination = edge.to
            val timeInDestination = timeIn[destination]!!
            if (timeInDestination != -1 &&
                destination != prevVertex &&
                timeInDestination < ret[vertex]!!
            ) { // if back edge
                ret[vertex] = timeInDestination
            }
            if (timeInDestination != -1) { // if visited vertex
                continue
            }
            dfs(destination, vertex)
            val retDestination = ret[destination]!!
            if (retDestination < ret[vertex]!!) {
                ret[vertex] = retDestination
            }
            if (timeIn[vertex]!! < retDestination) {
                bridges.add(edge)
            }
        }
    }


    while (notVisited.isNotEmpty()) {
        dfs(notVisited.elementAt(0), notVisited.elementAt(0))
    }

    return bridges.toSet()
}
