package viewmodel

import androidx.compose.ui.graphics.Color
import model.algos.FindCycle
import model.algos.Prim
import model.graph.UndirectedGraph
import model.graph.edges.Edge

class UndirectedGraphViewModel<V>(
    name: String,
    val graph: UndirectedGraph<V> = UndirectedGraph()
) : AbstractGraphViewModel<V>(name, graph) {

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

    fun primAlgo(startVertex: V) {
        val mst = Prim.findMST(graph, startVertex)
        for (edgeVM in edgesView) {
            if (Edge(edgeVM.from, edgeVM.to, edgeVM.weight) in mst) {
                edgeVM.color = Color.Magenta
            }
        }
    }

    fun cycleFind(startVertex: V) {
        val cycle = FindCycle.findCycle(graph, startVertex)
        for (edgeVM in edgesView) {
            if (cycle != null) {
                if (Edge(edgeVM.from, edgeVM.to) in cycle) {
                    edgeVM.color = Color.Green
                }
            }
        }
    }

    override fun addEdge(from: V, to: V, weight: Int) {
        if (vertexView[from] == null) return
        for (i in vertexView[from]?.edges!!) if (i.to == to) return
        val edgesCopy = vertexView[from]?.edges?.toMutableList()!!
        val edgeTo = Edge(from, to, weight)
        val edgeFrom = Edge(from, to, weight)
        edgesCopy.add(edgeTo)
        edgesCopy.add(edgeFrom)
        vertexView[from]?.edges = edgesCopy
        edgesView.add(EdgeViewModel(edgeTo, vertexView[edgeTo.from]!!, vertexView[edgeTo.to]!!))
        edgesView.add(EdgeViewModel(edgeFrom, vertexView[edgeFrom.from]!!, vertexView[edgeFrom.to]!!))
        graphModel.addEdge(from, to, weight)
        graphModel.addEdge(to, from, weight)
        updateView()
    }
}