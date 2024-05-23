package view.views

import androidx.compose.runtime.Composable
import view.views.edge.DirectedEdgeView
import view.views.edge.UndirectedEdgeView
import view.views.vertex.DirectedVertexView
import view.views.vertex.UndirectedVertexView
import viewmodel.DirectedGraphViewModel
import viewmodel.UndirectedGraphViewModel

@Composable
fun <V> UndirectedGraphView(graphVM: UndirectedGraphViewModel<V>) {
    for (vertexVM in graphVM.verticesVM) {
        UndirectedVertexView(vertexVM, graphVM)
    }
    for (edgeVM in graphVM.edgesVM) {
        UndirectedEdgeView(edgeVM, graphVM.isWeighted)
    }
}


@Composable
fun <V> DirectedGraphView(graphVM: DirectedGraphViewModel<V>) {
    for (vertexVM in graphVM.verticesVM) {
        DirectedVertexView(vertexVM, graphVM)
    }
    for (edgeVM in graphVM.edgesVM) {
        DirectedEdgeView(edgeVM, graphVM.isWeighted)
    }
}
