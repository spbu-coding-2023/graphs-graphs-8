package view.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import model.graph.edges.Edge
import view.DefaultColors
import viewmodel.GraphViewModel
import viewmodel.VertexViewModel
import kotlin.math.roundToInt

@Composable
fun VertexView(vertexVM: VertexViewModel<Int>, graphVM: GraphViewModel<Int, Edge<Int>>) {
    val vertex = vertexVM.vertex

    Box(modifier = Modifier
        .offset { IntOffset(vertexVM.offsetX.roundToInt(), vertexVM.offsetY.roundToInt()) }
        .clip(shape = CircleShape)
        .size(120.dp)
        .background(DefaultColors.primary)
        .border(5.dp, Color.Black, CircleShape)
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consume()
                vertexVM.offsetX += dragAmount.x
                vertexVM.offsetY += dragAmount.y
            }
        }
    ) {
        Text(
            text = "$vertex",
            fontSize = 40.sp,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(),
        )
    }

    vertexVM.edges.forEach { otherNumber ->
        val otherVM = graphVM.graphView[otherNumber]!!
        val otherX = otherVM.offsetX
        val otherY = otherVM.offsetY
        Canvas(modifier = Modifier.fillMaxSize().zIndex(-1f)) {
            drawLine(
                start = Offset(
                    vertexVM.offsetX + vertexVM.vertexSize / 2,
                    vertexVM.offsetY + vertexVM.vertexSize / 2
                ),
                end = Offset(otherX + vertexVM.vertexSize / 2, otherY + vertexVM.vertexSize / 2),
                strokeWidth = 10f,
                color = Color.Black
            )
        }
    }
}