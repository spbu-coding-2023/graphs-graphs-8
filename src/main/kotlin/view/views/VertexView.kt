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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import view.DefaultColors
import viewmodel.GraphViewModel
import viewmodel.VertexViewModel
import kotlin.math.atan2
import kotlin.math.roundToInt

@Composable
fun VertexView(vertexVM: VertexViewModel, graphVM: GraphViewModel) {
    val number = vertexVM.number

    Box(modifier = Modifier
        .offset {IntOffset(vertexVM.offsetX.roundToInt(), vertexVM.offsetY.roundToInt())}
        .clip(shape = CircleShape)
        .size(100.dp)
        .background(DefaultColors.primary)
        .border(5.dp, Color.Black, CircleShape)
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consume()
                vertexVM.offsetX += dragAmount.x
                vertexVM.offsetY += dragAmount.y
            }
        }
    ){
        Text(text = "$number",
            fontSize = 40.sp,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(),)
    }

    vertexVM.edges.forEach{ otherNumber ->
        val otherVM = graphVM.graphView[otherNumber]!!
        val otherX = otherVM.offsetX
        val otherY = otherVM.offsetY

        Canvas(modifier = Modifier.fillMaxSize().zIndex(-1f)){
            drawLine(
                start = Offset(vertexVM.offsetX + vertexVM.vertexSize/2, vertexVM.offsetY + vertexVM.vertexSize/2),
                end = Offset( otherX + vertexVM.vertexSize/2, otherY + vertexVM.vertexSize/2),
                strokeWidth = if(graphVM.graphView[otherNumber]?.edges?.contains(vertexVM.number) != true) 6f else 12f,
                color = Color.Black,
            )
            rotate(
                    degrees = ((57.2958 * (atan2(((vertexVM.offsetY - otherY).toDouble()), ((vertexVM.offsetX - otherX).toDouble())))).toFloat()),
                pivot = Offset( otherX + vertexVM.vertexSize/2, otherY + vertexVM.vertexSize/2)
            ){
                if(graphVM.graphView[otherNumber]?.edges?.contains(vertexVM.number) != true) {
                    drawRect(
                        color = Color.Black,
                        size = Size(5f, 16f),
                        topLeft = Offset(otherX + vertexVM.vertexSize / 2 + 70, otherY + vertexVM.vertexSize / 2 - 8f),
                    )
                    drawRect(
                        color = Color.Black,
                        size = Size(5f, 14f),
                        topLeft = Offset(otherX + vertexVM.vertexSize / 2 + 65, otherY + vertexVM.vertexSize / 2 - 7f),
                    )
                    drawRect(
                        color = Color.Black,
                        size = Size(5f, 12f),
                        topLeft = Offset(otherX + vertexVM.vertexSize / 2 + 60, otherY + vertexVM.vertexSize / 2 - 6f),
                    )
                    drawRect(
                        color = Color.Black,
                        size = Size(5f, 10f),
                        topLeft = Offset(otherX + vertexVM.vertexSize / 2 + 55, otherY + vertexVM.vertexSize / 2 - 5f),
                    )
                    drawRect(
                        color = Color.Black,
                        size = Size(5f, 8f),
                        topLeft = Offset(otherX + vertexVM.vertexSize / 2 + 50, otherY + vertexVM.vertexSize / 2 - 4f),
                    )
                }
            }

        }
    }
}