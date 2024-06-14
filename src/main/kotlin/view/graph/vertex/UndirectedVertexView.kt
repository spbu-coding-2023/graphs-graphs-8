package view.graph.vertex

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import view.graph.edge.UndirectedEdgeView
import viewmodel.graph.UndirectedGraphViewModel
import viewmodel.graph.VertexViewModel
import kotlin.math.min

@Composable
fun <V> UndirectedVertexView(vertexVM: VertexViewModel<V>, graphVM: UndirectedGraphViewModel<V>) {
    val vertex = vertexVM.vertex

    Box(
        modifier = Modifier
            .offset(
                vertexVM.offsetX.dp,
                vertexVM.offsetY.dp
            )
            .clip(shape = CircleShape)
            .size((vertexVM.vertexSize * graphVM.zoom).dp)
            .background(vertexVM.color)
            .border((5 * graphVM.zoom).dp, Color.Black, CircleShape)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    vertexVM.x += dragAmount.x / graphVM.zoom
                    vertexVM.y += dragAmount.y / graphVM.zoom
                }

            }
    ) {
        Text(
            text = "$vertex",
            fontSize = (graphVM.zoom * 28).sp,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize()
                .offset(y = (-vertexVM.vertexSize / 10).dp),
        )
        if (graphVM.visibleCentrality) {
            Text(
                "${vertexVM.centrality}", fontSize = (28 * graphVM.zoom).sp,
                modifier = Modifier.wrapContentSize().offset(y = (-vertexVM.vertexSize - 10).dp)
            )
        }
    }
    if (graphVM.visibleCentrality) {
        Box(modifier = Modifier.zIndex(-3f)) {
            var centrality = vertexVM.centrality.toString()
            centrality = centrality.substring(0, min(4, centrality.length))
            Text(
                centrality, fontSize = (28 * graphVM.zoom).sp,
                color = Color.LightGray,
                modifier = Modifier
                    .wrapContentSize()
                    .offset(
                        x = (vertexVM.offsetX + vertexVM.vertexSize / 7 * graphVM.zoom).dp,
                        y = (vertexVM.offsetY - vertexVM.vertexSize * 0.7 * graphVM.zoom).dp
                    )
            )
        }
    }

    for (edgeVM in vertexVM.edges) {
        UndirectedEdgeView(graphVM, edgeVM, graphVM.isWeighted)
    }
}