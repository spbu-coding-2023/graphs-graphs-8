package model.graph.edges

class WeightedEdge<V>(source: V, destination: V, _weight: Int) :
    Edge<V>(source, destination) {
    override val weight: Int = _weight
}