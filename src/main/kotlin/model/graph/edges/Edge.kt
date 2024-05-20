package model.graph.edges

data class Edge<V>(val from: V, val to: V, val weight: Int = 1)