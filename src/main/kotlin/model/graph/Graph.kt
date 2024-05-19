package graph

import model.graph.edges.Edge

abstract class Graph<V, E : Edge<V>>() {
    protected val graph = mutableMapOf<V, MutableList<E>>()
    val entries
        get() = graph.entries

    val vertices
        get() = graph.keys

    val edges: List<E>
        get() {
            val edges = mutableListOf<E>()
            for (vertex in vertices) {
                val edgesOf = edgesOf(vertex)
                edges.addAll(edgesOf)
            }
            return edges.toList()
        }

    var size = graph.size
        private set

    fun addVertex(vertex: V) {
        graph.putIfAbsent(vertex, mutableListOf<E>())
        size++
    }

    fun edgesOf(from: V): MutableList<E> {
        return graph[from] ?: mutableListOf()
    }

    fun forEach(action: (MutableList<E>) -> Unit) {
        graph.forEach { number, list -> action(list) }
    }

    operator fun iterator() = graph.entries.iterator()
}
