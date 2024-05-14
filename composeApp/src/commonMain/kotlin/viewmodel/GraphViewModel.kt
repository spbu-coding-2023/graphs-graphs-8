package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import lib.graph.Graph.Graph
import lib.graph.Graph.UndirectedGraph

class GraphViewModel(name : String, graph : Graph = UndirectedGraph()): ViewModel() {
    val name by mutableStateOf(name)
    val vertices = mutableStateListOf<VertexViewModel>()
    init {
        for (vertex in graph){
            vertices.add(VertexViewModel(vertex.key,vertex.value))
        }
    }
    fun addVertex(){
        vertices.add(VertexViewModel(vertices.size))
    }
}