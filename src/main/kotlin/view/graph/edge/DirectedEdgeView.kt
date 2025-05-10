package view.graph.edge

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import viewmodel.DirectedGraphViewModel
import viewmodel.graph.EdgeViewModel
import kotlin.math.atan2

@Composable
fun <V> DirectedEdgeView(
    graphVM: DirectedGraphViewModel<V>,
    edgeVM: EdgeViewModel<V>,
    isWeighted: Boolean,
) {

    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = Modifier.fillMaxSize().zIndex(-1f)) {
        val vertexSizeZoomed = edgeVM.fromVM.vertexSize * graphVM.zoom
        val first = edgeVM.fromVM
        val second = edgeVM.toVM
        drawLine(
            start = Offset(
                (first.offsetX + vertexSizeZoomed / 2).dp.toPx(),
                (first.offsetY + vertexSizeZoomed / 2).dp.toPx()
            ),
            end = Offset(
                (second.offsetX + vertexSizeZoomed / 2).dp.toPx(),
                (second.offsetY + vertexSizeZoomed / 2).dp.toPx()
            ),
            strokeWidth = 6f * graphVM.zoom,
            color = edgeVM.color,
        )
        rotate(
            degrees = ((57.2958 * (atan2(
                ((first.offsetY - second.offsetY).toDouble()).dp.toPx(),
                ((first.offsetX - second.offsetX).toDouble()).dp.toPx()
            ))).toFloat()),
            pivot = Offset(
                (second.offsetX + vertexSizeZoomed / 2).dp.toPx(),
                (second.offsetY + vertexSizeZoomed / 2).dp.toPx()
            )
        ) {
            drawRect(
                color = edgeVM.color,
                size = Size(5f * graphVM.zoom, 16f * graphVM.zoom),
                topLeft = Offset(
                    (second.offsetX + vertexSizeZoomed / 2 + 65 * graphVM.zoom).dp.toPx(),
                    (second.offsetY + vertexSizeZoomed / 2 - 8f * graphVM.zoom).dp.toPx()
                ),
            )
            drawRect(
                color = edgeVM.color,
                size = Size(5f * graphVM.zoom, 14f * graphVM.zoom),
                topLeft = Offset(
                    (second.offsetX + vertexSizeZoomed / 2 + 60 * graphVM.zoom).dp.toPx(),
                    (second.offsetY + vertexSizeZoomed / 2 - 7f * graphVM.zoom).dp.toPx()
                ),
            )
            drawRect(
                color = edgeVM.color,
                size = Size(5f * graphVM.zoom, 12f * graphVM.zoom),
                topLeft = Offset(
                    (second.offsetX + vertexSizeZoomed / 2 + 55 * graphVM.zoom).dp.toPx(),
                    (second.offsetY + vertexSizeZoomed / 2 - 6f * graphVM.zoom).dp.toPx()
                ),
            )
            drawRect(
                color = edgeVM.color,
                size = Size(5f * graphVM.zoom, 10f * graphVM.zoom),
                topLeft = Offset(
                    (second.offsetX + vertexSizeZoomed / 2 + 50 * graphVM.zoom).dp.toPx(),
                    (second.offsetY + vertexSizeZoomed / 2 - 5f * graphVM.zoom).dp.toPx()
                ),
            )
            drawRect(
                color = edgeVM.color,
                size = Size(5f * graphVM.zoom, 8f * graphVM.zoom),
                topLeft = Offset(
                    (second.offsetX + vertexSizeZoomed / 2 + 45 * graphVM.zoom).dp.toPx(),
                    (second.offsetY + vertexSizeZoomed / 2 - 4f * graphVM.zoom).dp.toPx()
                ),
            )
        }
        if (isWeighted) {
            drawText(
                textMeasurer, edgeVM.weight.toString(),
                topLeft = Offset(
                    ((first.offsetX + vertexSizeZoomed + second.offsetX) / 2 - edgeVM.weight.toString().length * 5.5f * graphVM.zoom).dp.toPx(),
                    ((first.offsetY + vertexSizeZoomed + second.offsetY) / 2 - 9 * graphVM.zoom).dp.toPx()
                ),
                style = TextStyle(background = Color.White, fontSize = 20.sp)
            )
        }
    }
}