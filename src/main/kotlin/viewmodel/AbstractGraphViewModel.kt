package viewmodel

import Dijkstra
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import model.algos.FindCycle
import model.algos.FordBellman
import model.graph.Graph
import model.graph.Edge
import view.common.DefaultColors
import javax.swing.text.StyledEditorKit.BoldAction
import kotlin.random.Random

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

    fun updateView() {
        val keep = graphVM
        graphVM = mutableMapOf<V, VertexViewModel<V>>()
        graphVM = keep
    }

    fun drawDijkstra(start: V, end: V) {
        if (this.negativeWeights) return
        val result = Dijkstra(graphModel, graphModel.size).dijkstra(start, end)
        drawEdges(result, Color.Red)
    }

    fun drawFordBellman(from: V, to: V) {
        val path = FordBellman.findShortestPath(from, to, this.graphModel).second ?: emptyList()
        drawEdges(path, Color.Cyan)
    }

    fun drawCycles(startVertex: V) {
        val findCycle = FindCycle
        for (cycle in findCycle.findCycles(graphModel, startVertex)) {
            val col =
                Color(Random.nextInt(30, 230), Random.nextInt(30, 230), Random.nextInt(30, 230))
            for (edge in cycle) {
                if (edge in graphModel.vertices) {
                    graphVM[edge]?.color = col
                }
            }
        }
    }

    fun vertexVmOf(vertex: V): VertexViewModel<V>? {
        return graphVM[vertex]
    }

    fun edgesVmOf(vertex: V): List<EdgeViewModel<V>> {
        return graphVM[vertex]?.edges?.toList() ?: emptyList()
    }

    fun addVertex(vertex: V, centerCoordinates: Boolean = false) {
        size += 1
        graphVM.putIfAbsent(
            vertex,
            VertexViewModel(vertex, centerCoordinates = centerCoordinates),
        )
        graphModel.addVertex(vertex)
    }

    fun resetColors() {
        for (edgeVM in edgesVM) {
            edgeVM.color = Color.Black
        }
        for (vertexVM in verticesVM) {
            vertexVM.color = DefaultColors.primary
        }
    }
}