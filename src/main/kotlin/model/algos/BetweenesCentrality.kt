package model.algos

import model.graph.UndirectedGraph

//object BetweenesCentrality {
//    fun <V> betweennessCentrality(graph: UndirectedGraph<V>): Map<V, Double> {
//        val centrality = mutableMapOf<V, Double>()
//        for (v in graph.vertices) {
//            centrality[v] = 0.0
//        }
//
//        for (s in graph.vertices) {
//            val stack = Stack<V>()
//            val predecessors = mutableMapOf<V, MutableList<V>>()
//            val shortestPaths = mutableMapOf<V, Int>()
//            val distance = mutableMapOf<V, Int>()
//            val dependency = mutableMapOf<V, Double>()
//
//            for (v in graph.vertices) {
//                predecessors[v] = mutableListOf()
//                shortestPaths[v] = 0
//                distance[v] = -1
//                dependency[v] = 0.0
//            }
//
//            shortestPaths[s] = 1
//            distance[s] = 0
//            val queue: Queue<V> = LinkedList()
//            queue.add(s)
//
//            while (queue.isNotEmpty()) {
//                val v = queue.poll()
//                stack.push(v)
//                for (edge in graph.edgesOf(v)) {
//                    val w = edge.to
//                    if (distance[w]!! < 0) {
//                        queue.add(w)
//                        distance[w] = distance[v]!! + 1
//                    }
//                    if (distance[w] == distance[v]!! + 1) {
//                        shortestPaths[w] = shortestPaths[w]!! + shortestPaths[v]!!
//                        predecessors[w]!!.add(v)
//                    }
//                }
//            }
//
//            while (stack.isNotEmpty()) {
//                val w = stack.pop()
//                for (v in predecessors[w]!!) {
//                    dependency[v] = dependency[v]!! + (shortestPaths[v]!!.toDouble() / shortestPaths[w]!!) * (1 + dependency[w]!!)
//                }
//                if (w != s) {
//                    centrality[w] = centrality[w]!! + dependency[w]!!
//                }
//            }
//        }
//
//        return centrality
//    }
//}

/*
* to view the centrality value for each vertex use:
* `val centrality = graph.betweennessCentrality()
*  centrality.forEach { (vertex, value) ->
*   println("Vertex: $vertex, Betweenness Centrality: $value") }`
* */
object BetweenesCentrality {
    fun <V> hits(iterations: Int = 100, graph: UndirectedGraph<V>): Pair<Map<V, Double>, Map<V, Double>> {
        val authority = mutableMapOf<V, Double>()
        val hub = mutableMapOf<V, Double>()

        // Инициализация всех авторитетов и хабов значением 1.0
        for (v in graph.vertices) {
            authority[v] = 1.0
            hub[v] = 1.0
        }

        for (i in 0 until iterations) {
            val newAuthority = mutableMapOf<V, Double>()
            val newHub = mutableMapOf<V, Double>()

            // Обновление авторитетов
            for (v in graph.vertices) {
                newAuthority[v] = graph.edgesOf(v).sumOf { edge -> hub[edge.to] ?: 0.0 }
            }

            // Обновление хабов
            for (v in graph.vertices) {
                newHub[v] = graph.edgesOf(v).sumOf { edge -> newAuthority[edge.to] ?: 0.0 }
            }

            // Нормализация
            val normAuthority = Math.sqrt(newAuthority.values.sumOf { it * it })
            val normHub = Math.sqrt(newHub.values.sumOf { it * it })

            for (v in graph.vertices) {
                authority[v] = newAuthority[v]!! / normAuthority
                hub[v] = newHub[v]!! / normHub
            }
        }

        return Pair(authority, hub)
    }
}