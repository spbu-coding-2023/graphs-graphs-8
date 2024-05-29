package model.algos

import model.graph.DirectedGraph
import model.graph.UndirectedGraph
import java.util.LinkedList
import java.util.Queue
import java.util.Stack

object BetweenesCentralityDirected {
    fun <V> pagerank(graph: DirectedGraph<V>, top: Int): Map<V, Double> {
        val ranks = mutableMapOf<V, Double>()
        val dampingFactor = 0.8
        val vertices = graph.vertices

        vertices.forEach { vertex -> ranks[vertex] = 1.0 / vertices.size }
        repeat(100) {
            val newRanks = mutableMapOf<V, Double>()
            vertices.forEach(fun(vertex: V) {
                var rankSum = 0.0
                vertices.forEach { neighbor ->
                    val edges = graph.edgesOf(neighbor)
                    if (neighbor != vertex) {
                        if (edges.any { it.to == vertex }) {
                            rankSum += ranks[neighbor]?.div(edges.size) ?: 0.0
                        }
                    }
                }
                newRanks[vertex] = (1 - dampingFactor) / vertices.size + dampingFactor * rankSum
            })
            newRanks.forEach { (vertex, value) ->
                ranks[vertex] = value
            }
        }
        return ranks.entries.sortedByDescending { it.value }.take(top).associate { it.toPair() }
    }
}

object BetweenesCentralityUndirected {
    fun <V> compute(graph: UndirectedGraph<V>, top: Int): Map<V, Double> {
        val centrality = mutableMapOf<V, Double>()
        for (v in graph.vertices) {
            centrality[v] = 0.0
        }

        for (s in graph.vertices) {
            val stack = Stack<V>()
            val predecessors = mutableMapOf<V, MutableList<V>>()
            val shortestPaths = mutableMapOf<V, Int>()
            val distance = mutableMapOf<V, Int>()
            val dependency = mutableMapOf<V, Double>()

            for (v in graph.vertices) {
                predecessors[v] = mutableListOf()
                shortestPaths[v] = 0
                distance[v] = -1
                dependency[v] = 0.0
            }

            shortestPaths[s] = 1
            distance[s] = 0
            val queue: Queue<V> = LinkedList()
            queue.add(s)

            while (queue.isNotEmpty()) {
                val v = queue.poll()
                stack.push(v)
                for (edge in graph.edgesOf(v)) {
                    val w = edge.to
                    if (distance[w]!! < 0) {
                        queue.add(w)
                        distance[w] = distance[v]!! + 1
                    }
                    if (distance[w] == distance[v]!! + 1) {
                        shortestPaths[w] = shortestPaths[w]!! + shortestPaths[v]!!
                        predecessors[w]!!.add(v)
                    }
                }
            }

            while (stack.isNotEmpty()) {
                val w = stack.pop()
                for (v in predecessors[w]!!) {
                    dependency[v] =
                        dependency[v]!! + (shortestPaths[v]!!.toDouble() / shortestPaths[w]!!) * (1 + dependency[w]!!)
                }
                if (w != s) {
                    centrality[w] = centrality[w]!! + dependency[w]!!
                }
            }
        }

        return centrality.entries.sortedByDescending { it.value }.take(top)
            .associate { it.toPair() }
    }
}