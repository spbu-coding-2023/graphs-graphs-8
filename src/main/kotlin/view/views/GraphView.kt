package view.views

import androidx.compose.runtime.Composable
import model.graph.edges.Edge
import viewmodel.UndirectedUnweightedGraphViewModel

@Composable
fun GraphView(graphViewModel: UndirectedUnweightedGraphViewModel<Int, Edge<Int>>) {
    for (vertexVM in graphViewModel.graphView.values) {
        VertexView(vertexVM, graphViewModel)
    }
}