package viewmodel

import Dijkstra
import androidx.compose.ui.graphics.Color
import model.graph.DirectedGraph
import model.graph.edges.Edge

class DirectedGraphViewModel<V>(
    name: String,
    val graph: DirectedGraph<V> = DirectedGraph()
): AbstractGraphViewModel<V>(name, graph){
    val model
        get() = graph
    init {
        for (vertex in graphModel.entries) {
            vertexView[vertex.key] = VertexViewModel(vertex.key, vertex.value)
        }
        for (edge in graphModel.edges) {
            edgesView.add(EdgeViewModel(edge, vertexView[edge.from]!!, vertexView[edge.to]!!))
        }
    }
    fun dijkstraAlgo(start: V, end: V){
        val y = Dijkstra(graph.matrix, graph.size).dijkstra(start, end)
        for (edgeVM in edgesView){
            if (Edge(edgeVM.from, edgeVM.to, edgeVM.weight) in y){
                edgeVM.color = Color.Red
            }
        }
    }

    override fun addEdge(from: V, to: V, weight: Int) {
        if (vertexView[from] == null) return
        for (i in vertexView[from]?.edges!!) if (i.to == to) return
        val edgesCopy = vertexView[from]?.edges?.toMutableList()!!
        val edge = Edge(from, to, weight)
        edgesCopy.add(edge)
        vertexView[from]?.edges = edgesCopy
        edgesView.add(EdgeViewModel(edge, vertexView[edge.from]!!, vertexView[edge.to]!!))
        graphModel.addEdge(from, to, weight)
        updateView()
    }
}