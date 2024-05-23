package view.views

import androidx.compose.runtime.Composable
import viewmodel.DirectedGraphViewModel
import viewmodel.UndirectedGraphViewModel

@Composable
fun GraphViewUndirect(graphViewModel: UndirectedGraphViewModel<String>) {
    for (vertexVM in graphViewModel.vertexView.values) {
        UndirectedVertexView(vertexVM, graphViewModel)
    }
    for (edge in graphViewModel.edgesView.iterator()) {
        UndirectedEdgeView(edge, graphViewModel)
    }
}


@Composable
fun GraphViewDirect(graphViewModel: DirectedGraphViewModel<String>) {
    for (vertexVM in graphViewModel.vertexView.values) {
        DirectedVertexView(vertexVM, graphViewModel)
    }
    for (edge in graphViewModel.edgesView.iterator()) {
        DirectedEdgeView(edge, graphViewModel)
    }
}
