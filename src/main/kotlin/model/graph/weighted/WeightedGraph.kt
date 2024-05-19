package model.graph.weighted

import graph.Graph
import model.graph.edges.WeightedEdge

abstract class WeightedGraph<V> : Graph<V, WeightedEdge<V>>() {
    abstract fun addEdge(from: V, to: V, weight: Int)
}