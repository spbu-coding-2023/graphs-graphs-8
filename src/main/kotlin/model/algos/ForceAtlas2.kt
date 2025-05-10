package model.algos

import height
import kotlinx.coroutines.yield
import viewmodel.graph.AbstractGraphViewModel
import viewmodel.graph.VertexViewModel
import width
import kotlin.math.sign
import kotlin.math.sqrt

const val repulsionK: Double = 150.0
const val attractionK: Double = 250.0
const val gravityK: Double = 5.0

object ForceAtlas2 {
    suspend fun <V> forceDrawing(graphVM: AbstractGraphViewModel<V>) {
        val vertices = graphVM.verticesVM
        while (true) {
            yield()
            val forces = mutableMapOf<VertexViewModel<V>, Pair<Float, Float>>()
            for (vertex in vertices) {
                val edges = vertex.edges
                val isConnected = mutableMapOf<V, Boolean>()
                for (edge in edges) {
                    isConnected[edge.to] = true
                }
                var forceX = 0.0
                var forceY = 0.0

                val gravityForces = getGravity(vertex)
                forceX += gravityForces.first
                forceY += gravityForces.second

                for (vertexInner in vertices) {
                    if (vertexInner == vertex) continue
                    val dx = vertexInner.x.toDouble() - vertex.x.toDouble()
                    val dy = vertexInner.y.toDouble() - vertex.y.toDouble()
                    val repulsion = getRepulsion(dx, dy, vertex.degree, vertexInner.degree)
                    forceX -= sign(dx) * repulsion
                    forceY -= sign(dy) * repulsion

                    if (isConnected[vertexInner.vertex] ?: false) {
                        val attraction = getAttraction(dx, dy)
                        forceX += sign(dx) * attraction
                        forceY += sign(dy) * attraction
                    }
                }
                forces[vertex] = Pair(forceX.toFloat(), forceY.toFloat())
            }
            for (vertex in forces.keys) {
                val forcesPair = forces[vertex]!!
                if (!forcesPair.first.isNaN()) {
                    val newX = (vertex.x + forcesPair.first)
                    vertex.x = newX
                }
                if (!forcesPair.second.isNaN()) {
                    val newY =
                        (vertex.y + forcesPair.second)
                    vertex.y = newY
                }
            }
        }
    }

    private fun getRepulsion(dx: Double, dy: Double, degree1: Int, degree2: Int): Double {
        val distance = getDistance(dx, dy)
//        val repulsion = repulsionK * (degree1 + 1) * (1 + degree2) / distance
        val repulsion = repulsionK / distance
        return repulsion
    }

    private fun getAttraction(dx: Double, dy: Double): Double {
        val distance = getDistance(dx, dy)
        val attraction = distance / attractionK
        return attraction
    }

    private fun getDistance(dx: Double, dy: Double): Double {
        val distance = sqrt(dx * dx + dy * dy)
        return distance
    }

    private fun <V> getGravity(vertex: VertexViewModel<V>): Pair<Double, Double> {
        val x = vertex.x.toDouble()
        val y = vertex.y.toDouble()
        val centerX = (width - 250) / 2
        val centerY = height / 2

        val dx = centerX - x
        val dy = centerY - y
        val forceX = dx.sign * gravityK
        val forceY = dy.sign * gravityK
        return Pair(forceX, forceY)
    }
}