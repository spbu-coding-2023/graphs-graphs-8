package model.algos

import height
import viewmodel.AbstractGraphViewModel
import viewmodel.VertexViewModel
import width
import kotlin.math.sign
import kotlin.math.sqrt

const val repulsionK: Double = 250.0
const val attractionK: Double = 150.0
const val gravityK: Double = 5.0

object ForceAtlas2 {
    fun <V> forceDrawing(graphVM: AbstractGraphViewModel<V>) {
        val vertices = graphVM.verticesVM

        repeat(50) {
            val forces = mutableMapOf<VertexViewModel<V>, Pair<Float, Float>>()
            for (vertex in vertices) {
                val edges = vertex.edges
                val isConnected = mutableMapOf<V, Boolean>()
                for (edge in edges) {
                    isConnected[edge.to] = true
                }
                var forceX: Double = 0.0
                var forceY: Double = 0.0

                val gravityForces = getGravity(vertex)
                forceX += gravityForces.first
                forceY += gravityForces.second

                for (vertexInner in vertices) {
                    if (vertexInner == vertex) continue
                    val dx = vertexInner.x.toDouble() - vertex.x.toDouble()
                    val dy = vertexInner.y.toDouble() - vertex.y.toDouble()
                    val repulsion = getRepulsion(dx, dy)
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
                vertex.x =
                    (vertex.x + forcesPair.first).coerceIn(100f, width.toFloat() - 200f)
                vertex.y =
                    (vertex.y + forcesPair.second).coerceIn(100f, height.toFloat() - 200f)
            }
        }
    }

    private fun getRepulsion(dx: Double, dy: Double): Double {
        val distance = getDistance(dx, dy)
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