package view.views

import androidx.compose.runtime.Composable
import viewmodel.GraphViewModel
import viewmodel.VertexViewModel

@Composable
fun GraphView(graphViewModel : GraphViewModel) {
    for (vertexVM  in graphViewModel.graphView.values){
        VertexView(vertexVM, graphViewModel)
    }
}