package algos

import model.algos.FindCycle
import model.graph.DirectedGraph
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertNotNull

internal class FindCycleTest {

    @Test
    fun `3 vertices directed cycle`() {
        val graph = DirectedGraph<Int>()
        for (i in 1..3) {
            graph.addVertex(i)
        }

        graph.run {
            this.addEdge(1, 2)
            this.addEdge(2, 3)
            this.addEdge(3, 1)
        }

        val pathActual = FindCycle.findCycles(graph, 2).elementAt(0)
        assertNotNull(pathActual)
        val pathExpected = listOf(1, 2, 3)
        assertContentEquals(pathExpected, pathActual, "TODO")
    }
}