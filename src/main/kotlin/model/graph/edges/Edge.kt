package model.graph.edges

open class Edge<V>(val from: V, val to: V) {
    open val weight = 1

    override fun equals(other: Any?): Boolean {
        if (other !is Edge<*>) return false
        if (from == other.from && to == other.to && weight == other.weight) {
            return true
        }
        return false
    }
}
