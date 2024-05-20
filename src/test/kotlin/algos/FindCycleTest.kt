package algos

import model.algos.FindCycle
import model.graph.UndirectedGraph
import model.graph.edges.Edge
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertNotNull

internal class FindCycleTest {

    @Test
    fun basic() {
        val graph = UndirectedGraph<Int>()
        for (i in 1..4) {
            graph.addVertex(i)
        }

        graph.run {
            this.addEdge(1, 3)
            this.addEdge(1, 2)
            this.addEdge(3, 2)
            this.addEdge(3, 4)
        }

        val pathActual = FindCycle.findCycle(graph, 2)
        assertNotNull(pathActual)
        val pathExpected = listOf(
            Edge(2, 1),
            Edge(1, 3),
            Edge(3, 2)
        )
        assertContentEquals(pathExpected, pathActual)
    }
}