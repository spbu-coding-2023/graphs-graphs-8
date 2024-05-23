package viewmodel

import androidx.compose.ui.graphics.Color
import model.algos.FindCycle
import model.algos.Prim
import model.algos.findBridges
import model.graph.UndirectedGraph
import model.graph.edges.Edge

class UndirectedGraphViewModel<V>(
    name: String,
    val graph: UndirectedGraph<V> = UndirectedGraph()
) : AbstractGraphViewModel<V>(name, graph) {

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

        if (weight != 1) isWeighted = true
        updateView()
    }

    private fun drawEdges(edges: Collection<Edge<V>>, color: Color) {
        for (edge in edges) {
            for (edgeVM in this.edgesVmOf(edge.from)) {
                if (edgeVM.to == edge.to) edgeVM.color = color
            }
            for (edgeVM in this.edgesVmOf(edge.to)) {
                if (edgeVM.to == edge.from) edgeVM.color = color
            }
        }
    }

    fun findMst() {
        if (size == 0) return
        val startVertex = graphModel.vertices.first()
        val result = Prim.findMst(graphModel as UndirectedGraph<V>, startVertex)
        drawEdges(result, Color.Magenta)
    }

    fun findCycles() {
        if (size == 0) return
        val startVertex = graph.vertices.first()
        val result = FindCycle.findCycle(graphModel as UndirectedGraph<V>, startVertex)
            ?: emptyList()
        drawEdges(result, Color.Magenta)
    }

    fun findBridges() {
        val result = findBridges(graphModel as UndirectedGraph<V>)
        drawEdges(result, Color.Yellow)
    }
}