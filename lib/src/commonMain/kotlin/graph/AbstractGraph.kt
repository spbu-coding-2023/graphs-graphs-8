package graph

abstract class AbstractGraph {
    protected val graph = mutableMapOf<Int, MutableList<Int>>()
    private var size = graph.size

    // temporary
    init{
        graph[2] = mutableListOf(1,3,4,5,6,0)
        graph[4] = mutableListOf(4,5)
        graph[5] = mutableListOf(4,2)
        graph[6] = mutableListOf(1,2,4,5)
        graph[1] = mutableListOf(2,4,5,6)
        graph[3] = mutableListOf(1,2,4,5,6)
        graph[0] = mutableListOf(1,2,3,4,5,6)
        size = 7
    }

    fun addVertex(number: Int){
        graph.putIfAbsent(number, mutableListOf())
    }

    abstract fun addEdge(from: Int, to: Int)

    fun edgesFrom(from: Int): MutableList<Int>{
        return graph[from]?: mutableListOf()
    }

    fun forEach(action : (MutableList<Int>) -> Unit ) {
        graph.forEach { (number, list) -> action(list) }
    }

    operator fun iterator() = graph.entries.iterator()
}
