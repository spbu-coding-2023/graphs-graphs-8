package viewmodel

import model.graph.UndirectedGraph
import model.graph.edges.Edge

class UndirectedGraphViewModel<V>(
    name: String,
    val graph: UndirectedGraph<V> = UndirectedGraph()
) : AbstractGraphViewModel<V>(name, graph) {

    val model
        get() = graph

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
        for (edge in destination.edges) if (edge.from == from) return

        val edgeFromSource = Edge(from, to, weight)
        val edgeFromDestination = Edge(to, from, weight)
        source.edges.add(edgeFromSource)
        destination.edges.add(edgeFromDestination)
        edgesView.add(EdgeViewModel(edgeFromSource, source, destination))
        edgesView.add(EdgeViewModel(edgeFromDestination, destination, source))
        graphModel.addEdge(from, to, weight)
        updateView()
    }
}