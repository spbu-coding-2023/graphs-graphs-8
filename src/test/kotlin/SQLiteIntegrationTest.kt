import model.graph.DirectedGraph
import model.graph.Edge
import viewmodel.MainScreenViewModel
import java.io.File
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class SQLiteIntegrationTest {

    @Test
    fun `SQLite integrable test`() {
        val mainScreenVM = MainScreenViewModel("sqlite")
        mainScreenVM.addGraph("someName", "Directed")
        mainScreenVM.saveGraph("someName", "test")
        val graph = mainScreenVM.getGraph("someName")
        for (i in 1..4) {
            graph.addVertex("$i")
        }

        graph.run {
            this.addEdge("1", "4", 20)
            this.addEdge("1", "2", 2)
            this.addEdge("2", "3", 3)
            this.addEdge("3", "4", 1)
        }
        mainScreenVM.initGraphList("test")
        mainScreenVM.initGraph("someName", "test")
        val loadedGraph = mainScreenVM.getGraph("someName")
        val result = Dijkstra(loadedGraph.model, 4).dijkstra("1", "4")
        val shortestLengthExpected = 6
        var shortestLengthActual = 0
        for (i in result) {
            shortestLengthActual += i.weight
        }
        assertNotNull(shortestLengthActual)
        assertEquals(
            shortestLengthExpected, shortestLengthActual,
            "Dijkstra must return weight of the shortest path"
        )
        val pathExpected = listOf(
            Edge("1", "2", 2),
            Edge("2", "3", 3),
            Edge("3", "4", 1)
        )
        assertContentEquals(
            pathExpected, result,
            "Dijkstra must return shortest path when it is possible to reach destination"
        )
        File("test.db").delete()
    }
}