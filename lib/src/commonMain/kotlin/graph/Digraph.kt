package graph

import graph.AbstractGraph

class Digraph: AbstractGraph(){
    override fun addEdge(from: Int, to: Int) {
        graph[from]?.add(to) ?: {graph[from] = mutableListOf(to)}
    }
}
