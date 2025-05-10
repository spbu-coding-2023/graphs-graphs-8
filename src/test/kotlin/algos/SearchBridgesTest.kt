package algos

import model.algos.findBridges
import model.graph.UndirectedGraph
import model.graph.Edge
import kotlin.test.Test
import kotlin.test.assertTrue

internal class SearchBridgesTest {

    private fun <V> bridgesEquals(bridges1: Set<Edge<V>>, bridges2: Set<Edge<V>>): Boolean {
        for (bridge in bridges1) {
            val bridgeReversed = Edge(bridge.to, bridge.from, bridge.weight)
            if (bridges2.contains(bridge) || bridges2.contains(bridgeReversed))
                continue
            return false
        }
        return true
    }

    @Test
    fun `empty graph`() {
        val graph = UndirectedGraph<Int>()

        val expectedBridges = setOf<Edge<Int>>()
        val actualBridges = findBridges(graph)
        assertTrue(
            bridgesEquals(expectedBridges, actualBridges),
            "Empty graph must not contain bridges"
        )
    }

    @Test
    fun `basic bridges search`() {
        val graph = UndirectedGraph<Int>()
        for (i in 1..7) {
            graph.addVertex(i)
        }

        graph.run {
            this.addEdge(1, 2)
            this.addEdge(1, 3)
            this.addEdge(2, 3)
            this.addEdge(3, 4)
            this.addEdge(4, 5)
            this.addEdge(4, 6)
            this.addEdge(5, 6)
            this.addEdge(6, 7)
        }

        val expectedBridges = setOf(Edge(3, 4), Edge(6, 7))
        val actualBridges = findBridges(graph)
        assertTrue(
            bridgesEquals(expectedBridges, actualBridges),
            "searchBridges must return set of graph bridges"
        )
    }
}