package view.views.edge

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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import viewmodel.EdgeViewModel
import kotlin.math.atan2

@Composable
fun <V> DirectedEdgeView(edgeVM: EdgeViewModel<V>, isWeighted: Boolean) {

    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = Modifier.fillMaxSize().zIndex(-1f)) {
        drawLine(
            start = Offset(
                edgeVM.fromX + edgeVM.vertexSize / 2,
                edgeVM.fromY + edgeVM.vertexSize / 2
            ),
            end = Offset(
                edgeVM.toX + edgeVM.vertexSize / 2,
                edgeVM.toY + edgeVM.vertexSize / 2
            ),
            strokeWidth = 6f,
            color = edgeVM.color,
        )
        rotate(
            degrees = ((57.2958 * (atan2(
                ((edgeVM.fromY - edgeVM.toY).toDouble()),
                ((edgeVM.fromX - edgeVM.toX).toDouble())
            ))).toFloat()),
            pivot = Offset(
                edgeVM.toX + edgeVM.vertexSize / 2,
                edgeVM.toY + edgeVM.vertexSize / 2
            )
        ) {
            drawRect(
                color = edgeVM.color,
                size = Size(5f, 16f),
                topLeft = Offset(
                    edgeVM.toX + edgeVM.vertexSize / 2 + 65,
                    edgeVM.toY + edgeVM.vertexSize / 2 - 8f
                ),
            )
            drawRect(
                color = edgeVM.color,
                size = Size(5f, 14f),
                topLeft = Offset(
                    edgeVM.toX + edgeVM.vertexSize / 2 + 60,
                    edgeVM.toY + edgeVM.vertexSize / 2 - 7f
                ),
            )
            drawRect(
                color = edgeVM.color,
                size = Size(5f, 12f),
                topLeft = Offset(
                    edgeVM.toX + edgeVM.vertexSize / 2 + 55,
                    edgeVM.toY + edgeVM.vertexSize / 2 - 6f
                ),
            )
            drawRect(
                color = edgeVM.color,
                size = Size(5f, 10f),
                topLeft = Offset(
                    edgeVM.toX + edgeVM.vertexSize / 2 + 50,
                    edgeVM.toY + edgeVM.vertexSize / 2 - 5f
                ),
            )
            drawRect(
                color = edgeVM.color,
                size = Size(5f, 8f),
                topLeft = Offset(
                    edgeVM.toX + edgeVM.vertexSize / 2 + 45,
                    edgeVM.toY + edgeVM.vertexSize / 2 - 4f
                ),
            )
        }
        if (isWeighted) {
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
}