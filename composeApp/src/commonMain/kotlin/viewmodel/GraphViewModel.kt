package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import graph.AbstractGraph
import lib.graph.UnDigraph

class GraphViewModel(name : String, unDigraph : AbstractGraph = UnDigraph()): ViewModel() {
    val name by mutableStateOf(name)
    val vertices = mutableStateListOf<VertexViewModel>()
    init {
        for (vertex in unDigraph){
            vertices.add(VertexViewModel(vertex.key,vertex.value))
        }
    }
    fun addVertex(){
        vertices.add(VertexViewModel(vertices.size))
    }
}
