package algos

import model.algos.Prim
import model.graph.UndirectedGraph
import model.graph.Edge
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertNotNull

internal class PrimTest {
    @Test
    fun basic() {
        val graph = UndirectedGraph<Int>()
        for (i in 0..7) {
            graph.addVertex(i)
        }

        graph.run {
            this.addEdge(1, 2, 1)
            this.addEdge(2, 3, 2)
            this.addEdge(3, 4, 30)
            this.addEdge(4, 5, 12)
            this.addEdge(5, 3, 8)
            this.addEdge(6, 7, 7)
            this.addEdge(6, 3, 2)
            this.addEdge(7, 1, 20)
            this.addEdge(1, 3, 10)
        }

        val pathActual = Prim.findMst(graph, 1)

        val pathExpected = listOf(
            Edge(1, 2, 1),
            Edge(2, 3, 2),
            Edge(3, 6, 2),
            Edge(6, 7, 7),
            Edge(3, 5, 8),
            Edge(5, 4, 12)
        )

        assertNotNull(pathActual)
        assertContentEquals(pathExpected, pathActual)
    }
}