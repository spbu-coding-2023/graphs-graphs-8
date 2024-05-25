package model.algos

import model.graph.edges.Edge

object FindCycle {
    fun findCycles(graph: MutableMap<V, MutableList<Edge<V>>>, startVertex: Int): List<List<V>> {
        val blockedSet = mutableSetOf<V>()
        val blockedMap = mutableMapOf<V, MutableSet<V>>()
        val stack = mutableListOf<V>()
        val preResult = mutableListOf<List<V>>()
        val sccs = StrongConnections<V>()
        val sccResult = sccs.findStrongConnections(graph)

        for (subGraph in sccResult) {
            if (subGraph.size > 1) {
                val startNode = subGraph.first()
                findCyclesInSCC(startNode, startNode, graph, blockedSet, blockedMap, stack, preResult)
                blockedSet.clear()
                blockedMap.clear()
            }
        }

        val result = mutableListOf<List<V>>()
        for (res in preResult){
            if(res.contains(startVertex)){
                result.add(res)
            }
        }
        return result
    }

    private fun <V> findCyclesInSCC(
        start: V,
        current: V,
        graph: MutableMap<V, MutableList<Edge<V>>>,
        blockedSet: MutableSet<V>,
        blockedMap: MutableMap<V, MutableSet<V>>,
        stack: MutableList<V>,
        result: MutableList<List<V>>
    ): Boolean {
        var foundCycle = false
        stack.add(current)
        blockedSet.add(current)

        for (edge in graph[current] ?: mutableListOf()) {
            val neighbor = edge.to
            if (neighbor == start) {
                val cycle = stack.toList()
                result.add(cycle)
                foundCycle = true
            } else if (neighbor !in blockedSet) {
                if (findCyclesInSCC(start, neighbor, graph, blockedSet, blockedMap, stack, result)) {
                    foundCycle = true
                }
            }
        }

        if (foundCycle) {
            unblock(current, blockedSet, blockedMap)
        } else {
            for (edge in graph[current] ?: mutableListOf()) {
                val neighbor = edge.to
                blockedMap.computeIfAbsent(neighbor) { mutableSetOf() }.add(current)
            }
        }

        stack.removeAt(stack.size - 1)
        return foundCycle
    }

    private fun <V> unblock(node: V, blockedSet: MutableSet<V>, blockedMap: MutableMap<V, MutableSet<V>>) {
        val stack = mutableListOf(node)
        while (stack.isNotEmpty()) {
            val current = stack.removeAt(stack.size - 1)
            if (current in blockedSet) {
                blockedSet.remove(current)
                blockedMap[current]?.forEach { stack.add(it) }
                blockedMap.remove(current)
            }
        }
    }
}
