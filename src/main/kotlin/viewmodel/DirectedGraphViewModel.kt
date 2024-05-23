package viewmodel

import Dijkstra
import androidx.compose.ui.graphics.Color
import model.graph.DirectedGraph
import model.graph.edges.Edge

class DirectedGraphViewModel<V>(
    name: String,
    val graph: DirectedGraph<V> = DirectedGraph()
) : AbstractGraphViewModel<V>(name, graph) {
    val model
        get() = graph

    init {
        for (vertex in graphModel.entries) {
            graphView[vertex.key] = VertexViewModel(vertex.key, vertex.value)
        }
        for (edge in graphModel.edges) {
            edgesView.add(EdgeViewModel(edge, graphView[edge.from]!!, graphView[edge.to]!!))
        }
    }

    fun dijkstraAlgo(start: V, end: V) {
        val y = Dijkstra(graph.matrix, graph.size).dijkstra(start, end)
        for (edgeVM in edgesView) {
            if (Edge(edgeVM.from, edgeVM.to, edgeVM.weight) in y) {
                edgeVM.color = Color.Red
            }
        }
    }

    override fun addEdge(from: V, to: V, weight: Int) {
        val source: VertexViewModel<V>
        val destination: VertexViewModel<V>
        try {
            source = graphView[from]!!
            destination = graphView[to]!!
        } catch (e: Exception) {
            println("Can't add edge between $from and $to: one of them don't exist")
            return
        }
        for (edge in source.edges) if (edge.to == to) return

        val edge = Edge(from, to, weight)
        source.edges.add(edge)
        edgesView.add(EdgeViewModel(edge, source, destination))
        graphModel.addEdge(from, to, weight)
        updateView()
    }
}