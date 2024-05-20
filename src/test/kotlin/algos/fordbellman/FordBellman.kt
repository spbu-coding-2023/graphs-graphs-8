package algos.fordbellman

import model.algos.SPAFordBellman
import model.graph.DirectedGraph
import model.graph.edges.Edge
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class SPAFordBellmanTest {

    @Test
    fun basicFind() {
        val graph = DirectedGraph<Int>()
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

        val result = SPAFordBellman.findShortestPath(1, 4, graph)

        val shortestExpected = 6
        val shortestActual = result.first
        assertNotNull(shortestActual)
        assertEquals(
            shortestExpected, shortestActual,
            "FordBellman with single weight of path must return weight of the shortest path"
        )

        val pathExpected = listOf<Edge<Int>>(
            Edge(1, 3, 2),
            Edge(3, 2, 3),
            Edge(2, 4, 1)
        )
        val pathActual = result.second
        assertContentEquals(pathExpected, pathActual, "")

    }

}