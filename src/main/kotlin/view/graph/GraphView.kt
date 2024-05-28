package view.graph

import androidx.compose.runtime.Composable
import view.graph.vertex.DirectedVertexView
import view.graph.vertex.UndirectedVertexView
import viewmodel.DirectedGraphViewModel
import viewmodel.UndirectedGraphViewModel

@Composable
fun <V> UndirectedGraphView(graphVM: UndirectedGraphViewModel<V>) {
    for (vertexVM in graphVM.verticesVM) {
        UndirectedVertexView(vertexVM, graphVM)

    }
}


@Composable
fun <V> DirectedGraphView(graphVM: DirectedGraphViewModel<V>) {
    for (vertexVM in graphVM.verticesVM) {
        DirectedVertexView(vertexVM, graphVM)
    }
}
