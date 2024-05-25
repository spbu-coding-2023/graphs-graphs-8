package model.graph

abstract class Graph<V>() {
    protected val graph = mutableMapOf<V, MutableList<Edge<V>>>()
    val entries
        get() = graph.entries
    var isWeighted = false
        protected set
    var negativeWeights = false
        protected set

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

    fun degreeOfVertex(vertex: V): Int {
        return graph[vertex]?.size ?: 0
    }

    abstract fun addEdge(from: V, to: V, weight: Int = 1)

    fun edgesOf(from: V): MutableList<Edge<V>> {
        return graph[from] ?: mutableListOf()
    }

    fun forEach(action: (MutableList<Edge<V>>) -> Unit) {
        graph.forEach { number, list -> action(list) }
    }

    operator fun iterator() = graph.entries.iterator()
}
