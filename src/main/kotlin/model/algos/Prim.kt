package model.algos

import model.graph.edges.Edge
import model.graph.UndirectedGraph
import java.util.PriorityQueue

object Prim {
    // find minimum spanning tree
    fun <V> findMst(graph: UndirectedGraph<V>, startVertex: V): List<Edge<V>> {
        val mst = mutableListOf<Edge<V>>()
        val visited = mutableSetOf<V>()
        val edgeQueue = PriorityQueue<Edge<V>>()

        fun addEdges(vertex: V) {
            visited.add(vertex)
            for (edge in graph.edgesOf(vertex)) {
                if (edge.to !in visited) {
                    edgeQueue.add(edge)
                }
            }
        }

        addEdges(startVertex)

        while (edgeQueue.isNotEmpty()) {
            val edge = edgeQueue.poll()
            if (edge.to !in visited) {
                mst.add(edge)
                addEdges(edge.to)
            }
        }

        return mst
    }
}