package algos

import model.algos.BetweenesCentrality
import model.graph.UndirectedGraph
import kotlin.test.Test
import kotlin.test.assertNotNull

class BetweennesCentralityTest {
    @Test
    fun basic() {
        val graph = UndirectedGraph<Int>()
       for (i in 0..9) {
           graph.addVertex(i)
       }
        graph.addEdge(1, 2, 1)
        graph.addEdge(1, 3, 1)
        graph.addEdge(1, 4)
        graph.addEdge(2, 3)
        graph.addEdge(2, 4)
        graph.addEdge(3, 4)
        graph.addEdge(2, 5)
        graph.addEdge(4, 5)
        graph.addEdge(5, 6)
        graph.addEdge(5, 7)
        graph.addEdge(6, 7)
        graph.addEdge(6, 8)
        graph.addEdge(6, 9)
        graph.addEdge(7, 8)
        graph.addEdge(7, 9)
        graph.addEdge(8, 9)
        val (centrality, v) = BetweenesCentrality.hits(100, graph)
        for ((vertex, value) in centrality) {
            println("Vertex: $vertex, Betweenness Centrality: $value")
        }
        for ((vertex, value) in centrality) {
            println("Vertex: $vertex, Betweenness Centrality: $value")
        }
        assertNotNull(centrality)
    }
}