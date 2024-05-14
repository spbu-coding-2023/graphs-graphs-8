package lib.graph

class Digraph: BaseGraph(){
    override fun addEdge(from: Int, to: Int) {
        graph[from]?.add(to) ?: {graph[from] = mutableListOf(to)}
    }
}
