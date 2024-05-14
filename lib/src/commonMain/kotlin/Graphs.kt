package lib.graph


abstract class Graph(){
    protected val graph = mutableMapOf<Int, MutableList<Int>>()
    var size = graph.size
        private set

    init{
        graph[2] = mutableListOf(1,3,4,5,6,7)
        graph[4] = mutableListOf(4,5)
        graph[5] = mutableListOf(4,2)
        graph[6] = mutableListOf(1,2,4,5)
        graph[1] = mutableListOf(2,4,5,6)
        graph[3] = mutableListOf(1,2,4,5,6)
        graph[7] = mutableListOf(1,2,3,4,5,6)
        size = 7
    }

    fun addVertex(number: Int){
        graph.putIfAbsent(number, mutableListOf<Int>())
    }

    abstract fun addEdge(from: Int, to: Int)

    fun forEach(action : (MutableList<Int>) -> Unit ) {
        graph.forEach { number, list -> action(list) }
    }

    operator fun iterator() = graph.entries.iterator()
}

class DirectedGraph: Graph(){
    override fun addEdge(from: Int, to: Int) {
        graph[from]?.add(to) ?: {graph[from] = mutableListOf(to)}
    }
}

class UndirectedGraph: Graph(){
    override fun addEdge(from: Int, to: Int) {
        graph[from]?.add(to) ?: {graph[from] = mutableListOf(to)}
        graph[to]?.add(from) ?: {graph[to] = mutableListOf(from)}
    }
}
