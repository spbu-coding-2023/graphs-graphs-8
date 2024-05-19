package graph

class DirectedGraph: GraphAbstract(){
    override fun addEdge(from: Int, to: Int) {
       graph[from]?.add(to) ?: {graph[from] = mutableListOf(to)}
    }
}
