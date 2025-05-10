package viewmodel.graph

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import model.graph.Edge

class EdgeViewModel<V>(
    edge: Edge<V>,
    vertexFromVM: VertexViewModel<V>,
    vertexToVM: VertexViewModel<V>,
) :
    ViewModel() {
    val fromVM = vertexFromVM
    val toVM = vertexToVM
    
    val weight by mutableStateOf(edge.weight)
    val from by mutableStateOf(edge.from)
    val to by mutableStateOf(edge.to)
    var color by mutableStateOf(Color.Black)
}