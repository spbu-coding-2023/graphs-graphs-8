package view.views

import androidx.compose.runtime.Composable
import viewmodel.GraphViewModel
import viewmodel.VertexViewModel

@Composable
fun GraphView(graphViewModel : GraphViewModel) {
    graphViewModel.vertices.forEach{ vertexvm ->
        VertexView(vertexvm)
    }
}