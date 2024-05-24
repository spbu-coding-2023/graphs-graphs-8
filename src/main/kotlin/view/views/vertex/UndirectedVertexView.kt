package view.views.vertex

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import view.common.DefaultColors
import view.views.edge.DirectedEdgeView
import view.views.edge.UndirectedEdgeView
import viewmodel.UndirectedGraphViewModel
import viewmodel.VertexViewModel
import kotlin.math.roundToInt

@Composable
fun <V> UndirectedVertexView(vertexVM: VertexViewModel<V>, graphVM: UndirectedGraphViewModel<V>) {
    val vertex = vertexVM.vertex

    Box(modifier = Modifier
        .offset { IntOffset(vertexVM.x.roundToInt(), vertexVM.y.roundToInt()) }
        .clip(shape = CircleShape)
        .size(vertexVM.vertexSize.dp)
        .background(DefaultColors.primary)
        .border(5.dp, Color.Black, CircleShape)
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consume()
                vertexVM.x += dragAmount.x
                vertexVM.y += dragAmount.y
            }

        }
    ) {
        Text(
            text = "$vertex",
            fontSize = 28.sp,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(),
        )
    }

    for (edgeVM in vertexVM.edges) {
        UndirectedEdgeView(edgeVM, graphVM.isWeighted)
    }
}