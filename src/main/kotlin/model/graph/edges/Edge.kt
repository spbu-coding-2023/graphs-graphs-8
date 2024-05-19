package model.graph

open class Edge<out V>(val from: V, val to: V) {
    open val weight = 1
}