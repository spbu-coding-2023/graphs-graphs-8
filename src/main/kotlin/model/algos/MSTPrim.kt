package model.algos

import model.graph.edges.Edge
import model.graph.UndirectedGraph

object MSTPrim {
    fun <V> findSpanningTree(graph: UndirectedGraph<V>): List<Edge<V>> {
        val visitedVertices = mutableListOf<V>()
        val edges = mutableListOf<Edge<V>>()

        val vertex = graph.vertices.random()
        visitedVertices.add(vertex)

        val allEdgesOfVertices = visitedVertices.flatMap { graph.edgesOf(it) }
        val unvisitedEdges =
            allEdgesOfVertices.filter { !visitedVertices.contains(it.from) || !visitedVertices.contains(it.to) }

        val nextEdge = unvisitedEdges.minBy { it.weight }
        visitedVertices.addAll(setOf(nextEdge.from, nextEdge.to))
        edges.add(nextEdge)

        while (!visitedVertices.containsAll(graph.vertices)) {
            val edge = visitedVertices.flatMap { graph.edgesOf(it) }
                .filter { !visitedVertices.contains(it.from) || !visitedVertices.contains(it.to) }
                .minBy { it.weight }
            visitedVertices.addAll(listOf(edge.from, edge.to))
            edges.add(edge)
        }
        return edges
    }
}