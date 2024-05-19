package algos.fordbellman

import model.algos.FordBellman
import model.graph.unweighted.DirectedGraph
import model.graph.weighted.WeightedDirectedGraph
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class FordBellmanTest {

    @Test
    fun basicFind() {
        val graph = WeightedDirectedGraph<Int>()
        for (i in 1..4) {
            graph.addVertex(i)
        }

        graph.run {
            this.addEdge(1, 3, 2)
            this.addEdge(1, 2, 7)
            this.addEdge(3, 2, 3)
            this.addEdge(2, 4, 1)
            this.addEdge(4, 3, -1)
        }

        val shortestExpected = 6
        val shortestActual = FordBellman.findShortestPath(1, 4, graph)
        assertNotNull(shortestActual)
        assertEquals(
            shortestExpected, shortestActual,
            "FordBellman with single weight of path must return weight of the shortest path"
        )

    }

}