package model.algos

import model.graph.DirectedGraph
import model.graph.Edge
import model.graph.Graph

class StrongConnections<V> {
    private val comparatorItoV = emptyMap<Int, V>().toMutableMap()
    private val comparatorVtoI = emptyMap<V, Int>().toMutableMap()

    fun findStrongConnections(graph: Graph<V>): List<List<V>> {
        for (vertex in graph.vertices) {
            comparatorItoV[comparatorItoV.size] = vertex
            comparatorVtoI[vertex] = comparatorVtoI.size
        }
        val adjustment = emptyMap<V, MutableList<V>>().toMutableMap()
        val dim = comparatorItoV.size
        val result: MutableList<List<V>> = ArrayList()
        val listStrongCon: MutableList<Boolean> = List(dim + 1) { false }.toMutableList()
        for (i in comparatorVtoI.keys) adjustment[i] = emptyList<V>().toMutableList()
        for (vertex in graph.vertices)
            for (edge in graph.edgesOf(vertex))
                adjustment[edge.from]?.add(edge.to)
        for (indexV in 0..<dim)
            if (!listStrongCon[indexV]) {
                val connections: MutableList<V> = ArrayList()
                connections.add(comparatorItoV[indexV]!!)
                for (indexN in indexV + 1..<dim) {
                    if (!listStrongCon[indexN] && findPath(indexV, indexN, adjustment) && findPath(
                            indexN,
                            indexV,
                            adjustment
                        )
                    ) {
                        connections.add(comparatorItoV[indexN]!!)
                        listStrongCon[indexN] = true
                    }
                }
                result.add(connections)
            }
        return result
    }

    private fun findPath(
        source: Int,
        top: Int,
        adjustment: MutableMap<V, MutableList<V>>
    ): Boolean {
        val visited: MutableList<Int> = List(comparatorItoV.size + 1) { 0 }.toMutableList()
        return dfs(source, top, adjustment, visited)
    }

    private fun dfs(
        current: Int,
        top: Int,
        adjustment: MutableMap<V, MutableList<V>>,
        visited: MutableList<Int>
    ): Boolean {
        if (current == top) {
            return true
        }
        visited[current] = 1
        for (x in adjustment[comparatorItoV[current]]!!) {
            if (visited[comparatorVtoI[x]!!] == 0) {
                if (dfs(comparatorVtoI[x]!!, top, adjustment, visited)) {
                    return true
                }
            }
        }
        return false
    }
}