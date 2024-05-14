package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel

class VertexViewModel(number: Int, edges: MutableList<Int> = mutableListOf()): ViewModel() {
    val number : Int = number
    val edges = edges
    var offsetX by mutableStateOf(1000f)
    var offsetY by mutableStateOf(540f)
    val vertexSize = 120f
}