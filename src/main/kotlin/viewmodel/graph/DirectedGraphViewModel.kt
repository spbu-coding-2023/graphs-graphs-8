package viewmodel

import androidx.compose.ui.graphics.Color
import model.algos.StrongConnections
import de.tudarmstadt.lt.cw.graph.ArrayBackedGraph
import de.tudarmstadt.lt.cw.graph.ArrayBackedGraphCW
import de.tudarmstadt.lt.cw.graph.Graph
import model.algos.BetweenesCentralityDirected
import model.graph.DirectedGraph
import model.graph.Edge
import mu.KotlinLogging
import viewmodel.graph.AbstractGraphViewModel
import viewmodel.graph.EdgeViewModel
import viewmodel.graph.VertexViewModel
import kotlin.random.Random

private val logger = KotlinLogging.logger { }

class DirectedGraphViewModel<V>(
    name: String,
    val graph: DirectedGraph<V> = DirectedGraph()
) : AbstractGraphViewModel<V>(name, graph) {

    private val DB_DRIVER = "jdbc:sqlite"
    var initedGraph = false
    override val graphType = GraphType.Directed

    override fun addEdge(from: V, to: V, weight: Int) {
        val source: VertexViewModel<V>
        val destination: VertexViewModel<V>
        try {
            source = graphVM[from]!!
            destination = graphVM[to]!!
        } catch (e: Exception) {
            println("Can't add edge between $from and $to: one of them don't exist")
            return
        }
        for (edge in source.edges) if (edge.to == to) return

        val edge = Edge(from, to, weight)
        val edgeVM = EdgeViewModel(edge, source, destination)
        source.edges.add(edgeVM)
        graphModel.addEdge(from, to, weight)
    }

    override fun drawEdges(edges: Collection<Edge<V>>, color: Color) {
        for (edge in edges) {
            for (edgeVM in this.edgesVmOf(edge.from)) {
                if (edgeVM.to == edge.to) edgeVM.color = color
            }
        }
    }

    fun chinaWhisperCluster() {
        val comparatorItoV = emptyMap<Int, V>().toMutableMap()
        val comparatorVtoI = emptyMap<V, Int>().toMutableMap()
        for (i in graph.vertices) {
            comparatorItoV[comparatorItoV.size] = i
            comparatorVtoI[i] = comparatorVtoI.size
        }
        val cwGraph: Graph<Int, Float> = ArrayBackedGraph(comparatorVtoI.size, comparatorVtoI.size)
        for (i in comparatorItoV) {
            cwGraph.addNode(i.key)
        }
        for (i in graph.edges) {
            cwGraph.addEdge(comparatorVtoI[i.from], comparatorVtoI[i.to], i.weight.toFloat())
        }

        val cw = ArrayBackedGraphCW(comparatorItoV.size)

        val findClusters = cw.findClusters(cwGraph)
        for (k in findClusters.values) {
            val col =
                Color(Random.nextInt(30, 230), Random.nextInt(30, 230), Random.nextInt(30, 230))
            for (j in k) {

                graphVM[comparatorItoV[j]]?.color = col
            }
        }
    }

    override fun drawBetweennessCentrality() {
        val result = BetweenesCentralityDirected.pagerank(graphModel as DirectedGraph<V>, size)
        for (vertexVM in verticesVM) {
            vertexVM.centrality = (result[vertexVM.vertex] ?: run {
                logger.error { "Can't find centrality value for vertex in graph" }
                0.0
            }) * 100
        }
        this.visibleCentrality = true
    }

    fun drawStrongConnections() {
        val strongConnections = StrongConnections<V>()
        for (component in strongConnections.findStrongConnections(graphModel)) {
            val color =
                Color(Random.nextInt(30, 230), Random.nextInt(30, 230), Random.nextInt(30, 230))
            for (vertex in component) {
                if (vertex in graphModel.vertices) {
                    graphVM[vertex]?.color = color
                }
            }
        }
    }

    fun saveSQLite() {
        graph.saveSQLite(name, "Directed", "storage")
    }
}