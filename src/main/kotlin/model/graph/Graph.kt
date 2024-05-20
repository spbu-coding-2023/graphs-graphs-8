package graph

import model.graph.edges.Edge

abstract class Graph<V>() {
    protected val graph = mutableMapOf<V, MutableList<Edge<V>>>()
    val entries
        get() = graph.entries
    protected var unweighted = true


    val vertices
        get() = graph.keys

    val edges: List<Edge<V>>
        get() {
            val edges = mutableListOf<Edge<V>>()
            for (vertex in vertices) {
                val edgesOf = edgesOf(vertex)
                edges.addAll(edgesOf)
            }
            return edges.toList()
        }

    var size = graph.size
        private set

    fun addVertex(vertex: V) {
        graph.putIfAbsent(vertex, mutableListOf<Edge<V>>())
        size++
    }

    abstract fun addEdge(from: V, to: V, weight: Int)

    fun edgesOf(from: V): MutableList<Edge<V>> {
        return graph[from] ?: mutableListOf()
    }

    fun forEach(action: (MutableList<Edge<V>>) -> Unit) {
        graph.forEach { number, list -> action(list) }
    }

    operator fun iterator() = graph.entries.iterator()
}
