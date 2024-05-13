package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import lib.graph.Graph

class GraphViewModel(graph : Graph = Graph()): ViewModel() {
    val vertices = mutableStateListOf<VertexViewModel>()
    init {
        for (vertex in graph.vertices) {
            vertices.add(VertexViewModel(vertex))
        }
    }

    fun addVertex() = vertices.add(VertexViewModel(vertices.size))
}