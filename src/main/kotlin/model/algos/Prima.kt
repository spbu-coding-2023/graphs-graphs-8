package model.algos

import model.graph.edges.WeightedEdge
import model.graph.weighted.WeightedGraph

object Prim {
    fun <V> findSpanningTree(graph: WeightedGraph<V>): List<WeightedEdge<V>> {
        val visitedVertices = mutableSetOf<V>()
        val edges = mutableListOf<WeightedEdge<V>>()

        val vertex = graph.vertices.random()
        visitedVertices.add(vertex)

        val allEdgesOfVertices = visitedVertices.flatMap { graph.edgesOf(it) }
        val unvisitedVertices =
            allEdgesOfVertices.filter { !visitedVertices.contains(it.from) || !visitedVertices.contains(it.to) }

        val nextEdge = unvisitedVertices.minBy { it.weight }
        visitedVertices.addAll(setOf(nextEdge.from, nextEdge.to))

        while (!visitedVertices.containsAll(graph.vertices)) {
            val nextEdge = visitedVertices.flatMap { graph.edgesOf(it) }
                .filter { !visitedVertices.contains(it.from) && !visitedVertices.contains(it.to) }
                .minBy { it.weight }
            visitedVertices.addAll(setOf(nextEdge.from, nextEdge.to))
            edges.add(nextEdge)
        }
        return edges
    }
}