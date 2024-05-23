package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import model.graph.Graph

abstract class AbstractGraphViewModel<V>(_name: String, graph: Graph<V>) : ViewModel() {
    val name = _name
    protected var graphVM by mutableStateOf(mutableMapOf<V, VertexViewModel<V>>())
    var edgesView by mutableStateOf(mutableListOf<EdgeViewModel<V>>())
    protected val graphModel = graph
    var size = 0
    var isWeighted by mutableStateOf(false)
        protected set
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

    fun edgesVmOf(vertex: V): List<EdgeViewModel<V>> {
        return graphVM[vertex]?.edges?.toList() ?: emptyList()
    }

    fun addVertex(vertex: V) {
        size += 1
        graphVM.putIfAbsent(vertex, VertexViewModel(vertex))
        graphModel.addVertex(vertex)
        updateView()
    }

    fun updateView() {
        val keep = graphVM
        graphVM = mutableMapOf<V, VertexViewModel<V>>()
        graphVM = keep
    }

    // возможно надо будет переработать состояния, поэтому понадобится
    fun updateEdgesView(vertex: V) {
        try {
            val keep = graphVM[vertex]?.edges!!
            graphVM[vertex]?.edges = mutableListOf()
            graphVM[vertex]?.edges = keep
        } catch (e: Exception) {
            println("Can't find vertex for update view of their edges")
        }
    }

    abstract fun addEdge(from: V, to: V, weight: Int = 1)
}