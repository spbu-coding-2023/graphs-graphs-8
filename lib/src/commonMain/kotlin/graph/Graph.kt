package graph

class Graph: GraphAbstract(){
    override fun addEdge(from: Int, to: Int) {
        graph[from]?.add(to) ?: {graph[from] = mutableListOf(to)}
        graph[to]?.add(from) ?: {graph[to] = mutableListOf(from)}
    }
}