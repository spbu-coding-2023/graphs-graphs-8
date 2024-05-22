package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import model.graph.edges.Edge

class EdgeViewModel<V>(edge: Edge<V>, vertexFromVM: VertexViewModel<V>, vertexToVM: VertexViewModel<V>,) :
    ViewModel() {
    var offsetXFrom by mutableStateOf(vertexFromVM.offsetX)
    var offsetYFrom by mutableStateOf(vertexFromVM.offsetY)
    var offsetXTo by mutableStateOf(vertexToVM.offsetX)
    var offsetYTo by mutableStateOf(vertexToVM.offsetY)
    var vertexSize by mutableStateOf(vertexFromVM.vertexSize)
    var weight by mutableStateOf(edge.weight)
    var from by mutableStateOf(edge.from)
    var to by mutableStateOf(edge.to)
    var color by mutableStateOf(Color.Black)
}