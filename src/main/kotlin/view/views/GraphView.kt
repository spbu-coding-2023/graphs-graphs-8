package view.views

import androidx.compose.runtime.Composable
import model.graph.edges.Edge
import model.graph.edges.WeightedEdge
import viewmodel.DirectedUnweightedGraphViewModel
import viewmodel.DirectedWeightedGraphViewModel
import viewmodel.UndirectedUnweightedGraphViewModel

@Composable
fun GraphViewUU(graphViewModel: UndirectedUnweightedGraphViewModel<Int, Edge<Int>>) {
    for (vertexVM in graphViewModel.graphView.values) {
        VertexView(vertexVM, graphViewModel)
    }
}

@Composable
fun GraphViewDU(graphViewModel: DirectedUnweightedGraphViewModel<Int, Edge<Int>>) {
    for (vertexVM in graphViewModel.graphView.values) {
        VertexView(vertexVM, graphViewModel)
    }
}

@Composable
fun GraphViewUW(graphViewModel: UndirectedUnweightedGraphViewModel<Int, Edge<Int>>) {
    for (vertexVM in graphViewModel.graphView.values) {
        VertexView(vertexVM, graphViewModel)
    }
}

@Composable
fun GraphViewDW(graphViewModel: DirectedWeightedGraphViewModel<Int, WeightedEdge<Int>>) {
    for (vertexVM in graphViewModel.graphView.values) {
        VertexView(vertexVM, graphViewModel)
    }
}