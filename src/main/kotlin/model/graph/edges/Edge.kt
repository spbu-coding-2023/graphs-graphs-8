package model.graph.edges

open class Edge<V>(val from: V, val to: V) {
    open val weight = 1
}
