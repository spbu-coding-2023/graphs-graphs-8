package graph

abstract class GraphAbstract() {
    protected val graph = mutableMapOf<Int, MutableList<Int>>()
    val vertices
        get() = graph.keys
    val entries
        get() = graph.entries
    var size = graph.size
        private set

    fun addVertex(number: Int) {
        graph.putIfAbsent(number, mutableListOf<Int>())
        size++
    }

    abstract fun addEdge(from: Int, to: Int)

    fun edgesOf(from: Int): MutableList<Int> {
        return graph[from] ?: mutableListOf()
    }

    fun forEach(action: (MutableList<Int>) -> Unit) {
        graph.forEach { number, list -> action(list) }
    }

    operator fun iterator() = graph.entries.iterator()
}