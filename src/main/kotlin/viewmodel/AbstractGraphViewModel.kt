package viewmodel

import Dijkstra
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import model.algos.FordBellman
import model.graph.Graph
import model.graph.edges.Edge

abstract class AbstractGraphViewModel<V>(_name: String, graph: Graph<V>) : ViewModel() {
    val name = _name
    protected var graphVM by mutableStateOf(mutableMapOf<V, VertexViewModel<V>>())
    protected val graphModel = graph
    var size = 0
    val isWeighted
        get() = graphModel.isWeighted
    val negativeWeights
        get() = graphModel.negativeWeights
    val model
        get() = graphModel
    val verticesVM
        get() = graphVM.values
    val edgesVM: List<EdgeViewModel<V>>
        get() {
            val result = mutableListOf<EdgeViewModel<V>>()
            for (edgesVM in graphVM.values) {
                for (edgeVM in edgesVM.edges) {
                    result.add(edgeVM)
                }
            }
            return result.toList()
        }

    init {
        for (vertex in graphModel.entries) {
            graphVM[vertex.key] = VertexViewModel(vertex.key)
        }
        for (vertex in graphModel.entries) {
            for (edge in vertex.value) {
                val sourceVertexVM: VertexViewModel<V>
                val destinationVertexVM: VertexViewModel<V>
                try {
                    sourceVertexVM = graphVM[edge.from]!!
                    destinationVertexVM = graphVM[edge.to]!!
                } catch (e: Exception) {
                    println("Can't set edge: source or destination is not exist")
                    break
                }
                val edgeVM = EdgeViewModel(edge, sourceVertexVM, destinationVertexVM)
                sourceVertexVM.edges.add(edgeVM)
            }
        }
    }

    abstract fun addEdge(from: V, to: V, weight: Int = 1)

    abstract fun drawEdges(edges: Collection<Edge<V>>, color: Color)

    fun dijkstraAlgo(start: V, end: V) {
        if (this.negativeWeights) return
        val result = Dijkstra(graphModel.matrix, graphModel.size).dijkstra(start, end)
        drawEdges(result, Color.Red)
    }

    fun fordBellman(from: V, to: V) {
        val path = FordBellman.findShortestPath(from, to, this.graphModel).second ?: emptyList()
        drawEdges(path, Color.Cyan)
    }

    fun vertexVmOf(vertex: V): VertexViewModel<V>? {
        return graphVM[vertex]
    }

    fun edgesVmOf(vertex: V): List<EdgeViewModel<V>> {
        return graphVM[vertex]?.edges?.toList() ?: emptyList()
    }

    fun addVertex(vertex: V) {
        size += 1
        graphVM.putIfAbsent(vertex, VertexViewModel(vertex))
        graphModel.addVertex(vertex)
        updateView()
    }

    fun resetDrawing() {
        for (edge in edgesVM) {
            edge.color = Color.Black
        }
    }

    fun updateView() {
        val keep = graphVM
        graphVM = mutableMapOf<V, VertexViewModel<V>>()
        graphVM = keep
    }
}