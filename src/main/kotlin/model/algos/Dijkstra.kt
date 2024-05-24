import model.graph.edges.Edge
import java.util.*

class Dijkstra<V>(var graph:  MutableMap<V, MutableList<Edge<V>>>, private val totalNodes: Int) {
    private val vertexValues: MutableMap<V, Int> = emptyMap<V, Int>().toMutableMap()
    private val visitedSet: MutableSet<V> = HashSet()
    private val prioraQueue = PriorityQueue<V>(totalNodes)
    private val pathMap: MutableMap<V, MutableList<Edge<V>>> = emptyMap<V, MutableList<Edge<V>>>().toMutableMap()

    fun dijkstra(start: V, end: V) : MutableList<Edge<V>>{
        for (j in graph.keys) {
            vertexValues.put(j, Int.MAX_VALUE)
            pathMap.put(j, emptyList<Edge<V>>().toMutableList())
        }
        prioraQueue.add(start)
        vertexValues[start] = 0

        while (visitedSet.size != totalNodes) {
            println(vertexValues)
            for (i in pathMap){
                println(i)
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
            println(vertexValues)
        }
        return pathMap[end]!!
    }

    private fun refreshSearch(currentVertex: V) {
        var newRange = -1
        for (j in graph[currentVertex]!!) {
            if (!visitedSet.contains(j.to)) {
                newRange = vertexValues[currentVertex]!! + j.weight
                if (newRange < vertexValues[j.to]!!) {
                    vertexValues[j.to] = newRange
                    val k = pathMap[j.from]?.toMutableList()
                    k?.add(j)
                    pathMap[j.to] = k!!
                }
                prioraQueue.add(j.to)
            }
        }
    }
}
