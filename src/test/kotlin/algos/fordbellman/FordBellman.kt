package algos.fordbellman

import model.algos.FordBellman
import model.graph.edges.Edge
import model.graph.edges.WeightedEdge
import model.graph.weighted.WeightedDirectedGraph
import kotlin.test.*

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

        val result = FordBellman.findShortestPath(1, 4, graph)

        val shortestExpected = 6
        val shortestActual = result.first
        assertNotNull(shortestActual)
        assertEquals(
            shortestExpected, shortestActual,
            "FordBellman with single weight of path must return weight of the shortest path"
        )

        val pathExpected = listOf<Edge<Int>>(
            WeightedEdge(1, 3, 2),
            WeightedEdge(3, 2, 3),
            WeightedEdge(2, 4, 1)
        )
        val pathActual = result.second
        assertContentEquals(pathExpected, pathActual, "")

    }

}