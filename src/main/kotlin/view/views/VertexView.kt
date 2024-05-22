package view.views

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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import view.DefaultColors
import viewmodel.DirectedGraphViewModel
import viewmodel.EdgeViewModel
import viewmodel.UndirectedGraphViewModel
import viewmodel.VertexViewModel
import kotlin.math.atan2
import kotlin.math.roundToInt

@Composable
fun DirectedVertexView(vertexVM: VertexViewModel<Int>, graphVM: DirectedGraphViewModel<Int>) {
    val vertex = vertexVM.vertex
    Box(modifier = Modifier
        .offset { IntOffset(vertexVM.offsetX.roundToInt(), vertexVM.offsetY.roundToInt()) }
        .clip(shape = CircleShape)
        .size(vertexVM.vertexSize.dp)
        .background(DefaultColors.primary)
        .border(5.dp, Color.Black, CircleShape)
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consume()
                for (edge in graphVM.edgesView.iterator()) {
                    if(edge.to == vertexVM.vertex){
                        edge.offsetXTo+= dragAmount.x
                        edge.offsetYTo+= dragAmount.y
                    }
                }
                for (edge in graphVM.edgesView.iterator()) {
                    if(edge.from == vertexVM.vertex){
                        edge.offsetXFrom+= dragAmount.x
                        edge.offsetYFrom+= dragAmount.y
                    }
                }
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
    for (edge in graphVM.edgesView.iterator()) {
        DirectedEdgeView(edge, graphVM)
    }
}

@Composable
fun DirectedEdgeView(edgeVM: EdgeViewModel<Int>, graphVM: DirectedGraphViewModel<Int>){

    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = Modifier.fillMaxSize().zIndex(-1f)) {
        drawLine(
            start = Offset(
                edgeVM.offsetXFrom + edgeVM.vertexSize / 2,
                edgeVM.offsetYFrom + edgeVM.vertexSize / 2
            ),
            end = Offset(edgeVM.offsetXTo + edgeVM.vertexSize / 2, edgeVM.offsetYTo + edgeVM.vertexSize / 2),
            strokeWidth = 6f,
            color = Color.Black,
        )
        rotate(
            degrees = ((57.2958 * (atan2(
                ((edgeVM.offsetYFrom - edgeVM.offsetYTo).toDouble()),
                ((edgeVM.offsetXFrom - edgeVM.offsetXTo).toDouble())
            ))).toFloat()),
            pivot = Offset(edgeVM.offsetXTo + edgeVM.vertexSize / 2, edgeVM.offsetYTo + edgeVM.vertexSize / 2)
        ) {
            drawRect(
                color = Color.Black,
                size = Size(5f, 16f),
                topLeft = Offset(
                    edgeVM.offsetXTo + edgeVM.vertexSize / 2 + 70,
                    edgeVM.offsetYTo + edgeVM.vertexSize / 2 - 8f
                ),
            )
            drawRect(
                color = Color.Black,
                size = Size(5f, 14f),
                topLeft = Offset(
                    edgeVM.offsetXTo + edgeVM.vertexSize / 2 + 65,
                    edgeVM.offsetYTo + edgeVM.vertexSize / 2 - 7f
                ),
            )
            drawRect(
                color = Color.Black,
                size = Size(5f, 12f),
                topLeft = Offset(
                    edgeVM.offsetXTo + edgeVM.vertexSize / 2 + 60,
                    edgeVM.offsetYTo + edgeVM.vertexSize / 2 - 6f
                ),
            )
            drawRect(
                color = Color.Black,
                size = Size(5f, 10f),
                topLeft = Offset(
                    edgeVM.offsetXTo + edgeVM.vertexSize / 2 + 55,
                    edgeVM.offsetYTo + edgeVM.vertexSize / 2 - 5f
                ),
            )
            drawRect(
                color = Color.Black,
                size = Size(5f, 8f),
                topLeft = Offset(
                    edgeVM.offsetXTo + edgeVM.vertexSize / 2 + 50,
                    edgeVM.offsetYTo + edgeVM.vertexSize / 2 - 4f
                ),
            )
        }
        if(graphVM.graph.state)
            drawText(textMeasurer,  edgeVM.weight.toString(),
                topLeft = Offset((edgeVM.offsetXFrom + edgeVM.vertexSize + edgeVM.offsetXTo)/2 - edgeVM.weight.toString().length * 5.5f, (edgeVM.offsetYFrom + edgeVM.vertexSize + edgeVM.offsetYTo)/2 - 9),
                style = TextStyle(background = Color.White, fontSize = 18.sp)
            )
    }
}

@Composable
fun UndirectedEdgeView(edgeVM: EdgeViewModel<Int>, graphVM: UndirectedGraphViewModel<Int>){

    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = Modifier.fillMaxSize().zIndex(-1f)) {
        drawLine(
            start = Offset(
                edgeVM.offsetXFrom + edgeVM.vertexSize / 2,
                edgeVM.offsetYFrom + edgeVM.vertexSize / 2
            ),
            end = Offset(edgeVM.offsetXTo + edgeVM.vertexSize / 2, edgeVM.offsetYTo + edgeVM.vertexSize / 2),
            strokeWidth = 6f,
            color = Color.Black,
        )
        if (graphVM.graph.state)
            drawText(
                textMeasurer, edgeVM.weight.toString(),
                topLeft = Offset(
                    (edgeVM.offsetXFrom + edgeVM.vertexSize + edgeVM.offsetXTo) / 2 - edgeVM.weight.toString().length * 5.5f,
                    (edgeVM.offsetYFrom + edgeVM.vertexSize + edgeVM.offsetYTo) / 2 - 9
                ),
                style = TextStyle(background = Color.White, fontSize = 18.sp)
            )
    }
}

@Composable
fun UndirectedVertexView(vertexVM: VertexViewModel<Int>, graphVM: UndirectedGraphViewModel<Int>) {
    val vertex = vertexVM.vertex

    Box(modifier = Modifier
        .offset { IntOffset(vertexVM.offsetX.roundToInt(), vertexVM.offsetY.roundToInt()) }
        .clip(shape = CircleShape)
        .size(vertexVM.vertexSize.dp)
        .background(DefaultColors.primary)
        .border(5.dp, Color.Black, CircleShape)
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consume()
                for (edge in graphVM.edgesView.iterator()) {
                    if(edge.to == vertexVM.vertex){
                        edge.offsetXTo+= dragAmount.x
                        edge.offsetYTo+= dragAmount.y
                    }
                }
                for (edge in graphVM.edgesView.iterator()) {
                    if(edge.from == vertexVM.vertex){
                        edge.offsetXFrom+= dragAmount.x
                        edge.offsetYFrom+= dragAmount.y
                    }
                }
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
}