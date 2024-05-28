package algos

import Dijkstra
import model.graph.DirectedGraph
import model.graph.Edge
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class DijkstraTest {

    @Test
    fun `dijkstra basic find and possible to reach destination`() {
        val graph = DirectedGraph<Int>()
        for (i in 1..4) {
            graph.addVertex(i)
        }

        graph.run {
            this.addEdge(1, 4, 20)
            this.addEdge(1, 2, 2)
            this.addEdge(2, 3, 3)
            this.addEdge(3, 4, 1)
        }
        val result = Dijkstra(graph, 4).dijkstra(1, 4)
        val shortestLengthExpected = 6
        var shortestLengthActual = 0
        for (i in result){
            shortestLengthActual += i.weight
        }
        assertNotNull(shortestLengthActual)
        assertEquals(
            shortestLengthExpected, shortestLengthActual,
            "Dijkstra must return weight of the shortest path"
        )
        val pathExpected = listOf(
            Edge(1, 2, 2),
            Edge(2, 3, 3),
            Edge(3, 4, 1)
        )
        assertContentEquals(
            pathExpected, result,
            "Dijkstra must return shortest path when it is possible to reach destination"
        )
    }

    @Test
    fun `dijkstra not possible to reach destination`() {
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
            this.addEdge(4, 6, 20)
        }

        val result = Dijkstra(graph, 4).dijkstra(1, 4)

        val pathExpected = emptyList<Edge<Int>>()
        val pathActual = result
        assertContentEquals(
            pathExpected, pathActual,
            "Dijkstra must return empty list as shortest path if it is not possible to reach destination"
        )
    }
}