package view.views

import androidx.compose.runtime.Composable
import viewmodel.DirectedGraphViewModel
import viewmodel.UndirectedGraphViewModel

@Composable
fun GraphViewUndirect(graphViewModel: UndirectedGraphViewModel<Int>) {
    for (vertexVM in graphViewModel.graphView.values) {
        UndirectedVertexView(vertexVM, graphViewModel)
    }
    for (edge in graphViewModel.edgesView.iterator()) {
        UndirectedEdgeView(edge, graphViewModel)
    }
}


@Composable
fun GraphViewDirect(graphViewModel: DirectedGraphViewModel<Int>) {
    for (vertexVM in graphViewModel.graphView.values) {
        DirectedVertexView(vertexVM, graphViewModel)
    }
    for (edge in graphViewModel.edgesView.iterator()) {
        DirectedEdgeView(edge, graphViewModel)
    }
}
