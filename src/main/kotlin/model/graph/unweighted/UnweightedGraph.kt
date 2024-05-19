package model.graph.unweighted

import graph.Graph
import model.graph.edges.Edge

abstract class UnweightedGraph<V> : Graph<V, Edge<V>>() {
    abstract fun addEdge(from: V, to: V)
}