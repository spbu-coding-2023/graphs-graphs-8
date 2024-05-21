package algos

import model.algos.FordBellman
import model.graph.DirectedGraph
import model.graph.edges.Edge
import kotlin.test.*

internal class FordBellmanTest {

    @Test
    fun `basic find without negative cycle and possible to reach destination`() {
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

        val result = FordBellman.findShortestPath(1, 4, graph)

        val shortestLengthExpected = 6
        val shortestLengthActual = result.first
        assertNotNull(shortestLengthActual)
        assertEquals(
            shortestLengthExpected, shortestLengthActual,
            "FordBellman must return weight of the shortest path"
        )

        val pathExpected = listOf(
            Edge(1, 3, 2),
            Edge(3, 2, 3),
            Edge(2, 4, 1)
        )
        val pathActual = result.second
        assertContentEquals(
            pathExpected, pathActual,
            "FordBellman must return shortest path when it is possible to reach destination and there is no negative cycles"
        )
    }

    @Test
    fun `not possible to reach destination`() {
        val graph = DirectedGraph<Int>()
        for (i in 1..6) {
            graph.addVertex(i)
        }

        graph.run {
            this.addEdge(1, 2, 8)
            this.addEdge(2, 3, 2)
            this.addEdge(4, 5, 3)
            this.addEdge(5, 6, 5)
            this.addEdge(6, 4, 9)
            this.addEdge(4, 6, -20)
        }

        val result = FordBellman.findShortestPath(1, 4, graph)

        val shortestLengthExpected = null
        val shortestLengthActual = result.first
        assertEquals(
            shortestLengthExpected, shortestLengthActual,
            "FordBellman must return null as length of path if it is not possible to reach destination"
        )

        val pathExpected = null
        val pathActual = result.second
        assertContentEquals(
            pathExpected, pathActual,
            "FordBellman must return null as shortest path if it is not possible to reach destination"
        )
    }

    @Test
    fun `shortest path with negative cycle`() {
        val graph = DirectedGraph<Int>()
        for (i in 1..4) {
            graph.addVertex(i)
        }

        graph.run {
            this.addEdge(1, 2, 1)
            this.addEdge(2, 3, 4)
            this.addEdge(3, 1, -10)
            this.addEdge(3, 4, 4)
        }

        val result = FordBellman.findShortestPath(1, 4, graph)

        val shortestLengthExpected = null
        val shortestLengthActual = result.first
        assertEquals(
            shortestLengthExpected, shortestLengthActual,
            "FordBellman must return null as length of path if there is negative cycles"
        )

        val pathActual = result.second
        assertNotNull(
            pathActual,
            "FordBellman must return not null path with negative cycles"
        )
        //TODO: implement correctness of returned path with negative cycle
        assertTrue(
            pathActual.size >= 3,
            "FordBellman must return some correct path to destination with negative cycles"
        )
    }

}