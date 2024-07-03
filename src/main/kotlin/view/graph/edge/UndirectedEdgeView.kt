package view.graph.edge

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import viewmodel.UndirectedGraphViewModel
import viewmodel.graph.EdgeViewModel

@Composable
fun <V> UndirectedEdgeView(
    graphVM: UndirectedGraphViewModel<V>,
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
            strokeWidth = 5f * graphVM.zoom,
            color = edgeVM.color,
        )
        if (isWeighted)
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