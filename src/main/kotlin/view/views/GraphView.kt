package view.views

import androidx.compose.runtime.Composable
import model.graph.edges.Edge
import viewmodel.DirectedGraphViewModel
import viewmodel.UndirectedGraphViewModel

@Composable
fun GraphViewUndirect(graphViewModel: UndirectedGraphViewModel<Int, Edge<Int>>) {
    for (vertexVM in graphViewModel.graphView.values) {
        UndirectedVertexView(vertexVM, graphViewModel)
    }
}


@Composable
fun GraphViewDirect(graphViewModel: DirectedGraphViewModel<Int, Edge<Int>>) {
    for (vertexVM in graphViewModel.graphView.values) {
        DirectedVertexView(vertexVM, graphViewModel)
    }
}
