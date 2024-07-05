package io

import org.junit.Test
import viewmodel.graph.DirectedGraphViewModel
import viewmodel.graph.UndirectedGraphViewModel
import viewmodel.io.Neo4jRepository
import kotlin.test.assertEquals

internal class Neo4jTest {

    @Test
    fun `check`() {
        val rep =
            Neo4jRepository<String>(
                "bolt://localhost:7687",
                "neo4j",
                "penguin-carlo-ceramic-invite-wheel-2163"
            )
        rep.clearDB()
        val graph1 = UndirectedGraphViewModel<String>("name1")
        val graph2 = DirectedGraphViewModel<String>("name2")
        graph1.run {
            this.addVertex("1")
            this.addVertex("2")
            this.addVertex("3")
            this.addEdge("1", "2")
            this.addEdge("2", "3")
        }
        graph2.run {
            this.addVertex("14")
            this.addVertex("15")
            this.addVertex("16")
            this.addEdge("14", "15")
            this.addEdge("15", "16")
            this.addEdge("16", "15")
        }
        rep.saveGraph(graph1)
        rep.saveGraph(graph2)
        graph1.addVertex("4")
        rep.saveGraph(graph1)
        val graphsSaved = rep.getAllGraphs()
        val graph1Saved = graphsSaved.find { it.name == "name1" }
        val graph2Saved = graphsSaved.find { it.name == "name2" }
        assertEquals(graph1.model.vertices, graph1Saved?.model?.vertices)
        assertEquals(graph1.model.edges, graph1Saved?.model?.edges)
        assertEquals(graph2.model.vertices, graph2Saved?.model?.vertices)
        assertEquals(graph2.model.edges, graph2Saved?.model?.edges)
    }
}