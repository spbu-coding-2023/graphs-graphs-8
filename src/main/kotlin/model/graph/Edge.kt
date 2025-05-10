package model.graph

data class Edge<V>(val from: V, val to: V, val weight: Int = 1) : Comparable<Edge<V>> {
    override fun compareTo(other: Edge<V>): Int {
        return this.weight - other.weight
    }
}
