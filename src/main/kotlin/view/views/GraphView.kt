package view.views

import androidx.compose.runtime.Composable
import model.graph.edges.Edge
import viewmodel.GraphViewModel
import viewmodel.VertexViewModel

@Composable
fun GraphView(graphViewModel: GraphViewModel<Int, Edge<Int>>) {
    for (vertexVM in graphViewModel.graphView.values) {
        VertexView(vertexVM, graphViewModel)
    }
}