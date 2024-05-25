package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import height
import width
import view.common.DefaultColors
import kotlin.random.Random

class VertexViewModel<V>(
    _vertex: V,
    _edges: MutableList<EdgeViewModel<V>> = mutableListOf(),
    centerCoordinates: Boolean = false
) :
    ViewModel() {
    val vertex: V = _vertex
    var edges = mutableStateListOf<EdgeViewModel<V>>()
    var x by mutableStateOf(0f)
    var y by mutableStateOf(0f)

    init {
        for (edge in _edges) {
            edges.add(edge)
        }
        if (centerCoordinates) {
            x = Random.nextInt(width / 2 - 300, width / 2 + 300).toFloat()
            y = Random.nextInt(height / 2 - 300, height / 2 + 300).toFloat()
        } else {
            x = Random.nextInt(0, 30000).toFloat()
            y = Random.nextInt(0, 30000).toFloat()
        }
    }

    val vertexSize = 60f
    var color by mutableStateOf(DefaultColors.primary)
    val degree
        get() = edges.size
}