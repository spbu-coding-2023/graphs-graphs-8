package model.graph.edges

class Edge<V>(val from: V, val to: V, val weight: Int = 1) {
    override fun equals(other: Any?): Boolean {
        if (other !is Edge<*>) return false
        if (from == other.from && to == other.to && weight == other.weight) {
            return true
        }
        return false
    }
}
