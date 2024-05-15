package graph

class Digraph: GraphAbstract(){
    override fun addEdge(from: Int, to: Int) {
        graph[from]?.add(to) ?: {graph[from] = mutableListOf(to)}
    }
}
