package algos

import model.algos.StrongConnections
import model.graph.DirectedGraph
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class StrongConnectionsTest {

    @Test
    fun `strong connections unconnected find`() {
        val graph = DirectedGraph<Int>()
        for (i in 1..4) {
            graph.addVertex(i)
        }
        val resultActual = StrongConnections<Int>().findStrongConnections(graph)
        val resultExpected = listOf(listOf(1), listOf(2), listOf(3), listOf(4))
        assertNotNull(resultActual)
        assertEquals(
            resultExpected, resultActual,
            "Unconnected vertices should be in different strong connections"
        )
    }

    @Test
    fun `strong connections cylce`() {
        val graph = DirectedGraph<Int>()
        for (i in 1..4) {
            graph.addVertex(i)
        }
        graph.run {
            this.addEdge(1, 2, 8)
            this.addEdge(2, 3, 2)
            this.addEdge(3, 4, -3)
            this.addEdge(4, 1, 3)
        }
        val resultActual = StrongConnections<Int>().findStrongConnections(graph)
        val resultExpected = listOf(listOf(1, 2, 3, 4))
        assertNotNull(resultActual)
        assertEquals(
            resultExpected, resultActual,
            "Cycle is a strong connection"
        )
    }

    @Test
    fun `strong connections cycles joined with a bridge`() {
        val graph = DirectedGraph<Int>()
        for (i in 1..6) {
            graph.addVertex(i)
        }
        graph.run {
            this.addEdge(1, 2, 52)
            this.addEdge(2, 3, 52)
            this.addEdge(3, 1, -52)
            this.addEdge(4, 5, 52)
            this.addEdge(5, 6, -52)
            this.addEdge(6, 4, 52)
            this.addEdge(1, 6, 52)

        }
        val resultActual = StrongConnections<Int>().findStrongConnections(graph)
        val resultExpected = listOf(listOf(1, 2, 3), listOf(4, 5, 6))
        assertNotNull(resultActual)
        assertEquals(
            resultExpected, resultActual,
            "Cycles joined with a bridge is a 2 strong connections"
        )
    }
}