package view.views.edge

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import viewmodel.EdgeViewModel

@Composable
fun <V> UndirectedEdgeView(edgeVM: EdgeViewModel<V>, isWeighted: Boolean) {

    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = Modifier.fillMaxSize().zIndex(-1f)) {
        drawLine(
            start = Offset(
                edgeVM.fromX + edgeVM.vertexSize / 2,
                edgeVM.fromY + edgeVM.vertexSize / 2
            ),
            end = Offset(edgeVM.toX + edgeVM.vertexSize / 2, edgeVM.toY + edgeVM.vertexSize / 2),
            strokeWidth = 8f,
            color = edgeVM.color,
        )
        if (isWeighted)
            drawText(
                textMeasurer, edgeVM.weight.toString(),
                topLeft = Offset(
                    (edgeVM.fromX + edgeVM.vertexSize + edgeVM.toX) / 2 - edgeVM.weight.toString().length * 5.5f,
                    (edgeVM.fromY + edgeVM.vertexSize + edgeVM.toY) / 2 - 9
                ),
                style = TextStyle(background = Color.White, fontSize = 20.sp)
            )
    }
}