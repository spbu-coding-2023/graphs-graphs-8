import model.graph.edges.Edge
import java.util.*

class Dijkstra<V>(var graph:  MutableMap<V, MutableList<Edge<V>>>, private val totalNodes: Int) {
    private val vertexValues: MutableMap<V, Int> = emptyMap<V, Int>().toMutableMap()
    private val visitedSet: MutableSet<V> = HashSet()
    private val prioraQueue = PriorityQueue<V>(totalNodes)

    fun dijkstra(start: V) {
        for (j in graph.keys) {
            vertexValues.put(j, Int.MAX_VALUE)
        }
        prioraQueue.add(start)
        vertexValues[start] = 0

        while (visitedSet.size != totalNodes) {
            println(vertexValues)
            if (prioraQueue.isEmpty()) {
                return
            }
            val ux = prioraQueue.remove()
            if (visitedSet.contains(ux)) {
                continue
            }
            if (ux != null) {
                visitedSet.add(ux)
                refreshSearch(ux)
            }
            println()
        }
    }

    private fun refreshSearch(currentVertex: V) {
        var newRange = -1
        for (j in graph[currentVertex]!!) {
            if (!visitedSet.contains(j.to)) {
                newRange = vertexValues[currentVertex]!! + j.weight
                if (newRange < vertexValues[j.to]!!) {
                    vertexValues[j.to] = newRange
                }
                prioraQueue.add(j.to)
            }
        }
    }
}
