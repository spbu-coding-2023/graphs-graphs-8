import model.graph.Edge
import model.graph.Graph
import java.util.*

class Dijkstra<V>(val graph: Graph<V>, private val totalNodes: Int) {
    private val vertexValues: MutableMap<V, Int> = emptyMap<V, Int>().toMutableMap()
    private val visitedSet: MutableSet<V> = HashSet()
    private val prioraQueue = PriorityQueue<V>(totalNodes)
    private val pathMap: MutableMap<V, MutableList<Edge<V>>> =
        emptyMap<V, MutableList<Edge<V>>>().toMutableMap()

    fun dijkstra(start: V, end: V): MutableList<Edge<V>> {
        for (vertex in graph.vertices) {
            vertexValues.put(vertex, Int.MAX_VALUE)
            pathMap.put(vertex, emptyList<Edge<V>>().toMutableList())
        }
        prioraQueue.add(start)
        vertexValues[start] = 0

        while (visitedSet.size != totalNodes) {
            for (i in pathMap) {
            }
            if (prioraQueue.isEmpty()) {
                return pathMap[end]!!
            }
            val ux = prioraQueue.remove()
            if (visitedSet.contains(ux)) {
                continue
            }
            if (ux != null) {
                visitedSet.add(ux)
                refreshSearch(ux)
            }
        }
        return pathMap[end]!!
    }

    private fun refreshSearch(currentVertex: V) {
        var newRange = -1
        for (edge in graph.edgesOf(currentVertex)) {
            if (!visitedSet.contains(edge.to)) {
                newRange = vertexValues[currentVertex]!! + edge.weight
                if (newRange < vertexValues[edge.to]!!) {
                    vertexValues[edge.to] = newRange
                    val k = pathMap[edge.from]?.toMutableList()
                    k?.add(edge)
                    pathMap[edge.to] = k!!
                }
                prioraQueue.add(edge.to)
            }
        }
    }
}
