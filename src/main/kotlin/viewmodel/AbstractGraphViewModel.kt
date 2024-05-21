package viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel

abstract class AbstractGraphViewModel<V, G>(graph: G) : ViewModel() {
    val graphView = mutableStateMapOf<V, VertexViewModel<V>>()
    val graphModel = graph
    var size = 0
}