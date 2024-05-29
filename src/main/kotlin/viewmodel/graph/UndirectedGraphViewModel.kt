package viewmodel

import androidx.compose.ui.graphics.Color
import model.algos.BetweenesCentralityDirected
import model.algos.BetweenesCentralityUndirected
import model.algos.Prim
import model.algos.findBridges
import model.graph.DirectedGraph
import model.graph.Edge
import model.graph.UndirectedGraph
import mu.KotlinLogging
import viewmodel.GraphType
import viewmodel.graph.AbstractGraphViewModel
import viewmodel.graph.EdgeViewModel
import viewmodel.graph.VertexViewModel

private val logger = KotlinLogging.logger { }

class UndirectedGraphViewModel<V>(
    name: String,
    val graph: UndirectedGraph<V> = UndirectedGraph()
) : AbstractGraphViewModel<V>(name, graph) {
    override val graphType = GraphType.Undirected

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
        for (edge in destination.edges) if (edge.from == from) return

        val edgeFromSource = Edge(from, to, weight)
        val edgeFromDestination = Edge(to, from, weight)
        val edgeFromSourceVM = EdgeViewModel(edgeFromSource, source, destination)
        val edgeFromDestinationVM = EdgeViewModel(edgeFromDestination, destination, source)
        source.edges.add(edgeFromSourceVM)
        destination.edges.add(edgeFromDestinationVM)
        graphModel.addEdge(from, to, weight)
    }

    override fun drawEdges(edges: Collection<Edge<V>>, color: Color) {
        for (edge in edges) {
            for (edgeVM in this.edgesVmOf(edge.from)) {
                if (edgeVM.to == edge.to) edgeVM.color = color
            }
            for (edgeVM in this.edgesVmOf(edge.to)) {
                if (edgeVM.to == edge.from) edgeVM.color = color
            }
        }
    }

    fun drawMst() {
        if (size == 0) return
        val startVertex = graphModel.vertices.first()
        val result = Prim.findMst(graphModel as UndirectedGraph<V>, startVertex)
        drawEdges(result, Color.Magenta)
    }

    override fun drawBetweennessCentrality() {
        val result = BetweenesCentralityUndirected.compute(graphModel as UndirectedGraph<V>, size)
        for (vertexVM in verticesVM) {
            vertexVM.centrality = (result[vertexVM.vertex] ?: run {
                logger.error { "Can't find centrality value for vertex in graph" }
                0.0
            })
        }
        this.visibleCentrality = true
    }

    fun drawBridges() {
        val result = findBridges(graphModel as UndirectedGraph<V>)
        drawEdges(result, Color.Yellow)
    }

    fun saveSQLite() {
        graph.saveSQLite(name, "Undirected", "storage")
    }
}