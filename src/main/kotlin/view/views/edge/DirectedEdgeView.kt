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
import viewmodel.DirectedGraphViewModel
import viewmodel.EdgeViewModel
import kotlin.math.atan2

@Composable
fun <V> DirectedEdgeView(edgeVM: EdgeViewModel<V>, graphVM: DirectedGraphViewModel<V>) {

    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = Modifier.fillMaxSize().zIndex(-1f)) {
        drawLine(
            start = Offset(
                edgeVM.xFrom + edgeVM.vertexSize / 2,
                edgeVM.yFrom + edgeVM.vertexSize / 2
            ),
            end = Offset(
                edgeVM.xTo + edgeVM.vertexSize / 2,
                edgeVM.yTo + edgeVM.vertexSize / 2
            ),
            strokeWidth = 6f,
            color = edgeVM.color,
        )
        rotate(
            degrees = ((57.2958 * (atan2(
                ((edgeVM.yFrom - edgeVM.yTo).toDouble()),
                ((edgeVM.xFrom - edgeVM.xTo).toDouble())
            ))).toFloat()),
            pivot = Offset(
                edgeVM.xTo + edgeVM.vertexSize / 2,
                edgeVM.yTo + edgeVM.vertexSize / 2
            )
        ) {
            drawRect(
                color = edgeVM.color,
                size = Size(5f, 16f),
                topLeft = Offset(
                    edgeVM.xTo + edgeVM.vertexSize / 2 + 65,
                    edgeVM.yTo + edgeVM.vertexSize / 2 - 8f
                ),
            )
            drawRect(
                color = edgeVM.color,
                size = Size(5f, 14f),
                topLeft = Offset(
                    edgeVM.xTo + edgeVM.vertexSize / 2 + 60,
                    edgeVM.yTo + edgeVM.vertexSize / 2 - 7f
                ),
            )
            drawRect(
                color = edgeVM.color,
                size = Size(5f, 12f),
                topLeft = Offset(
                    edgeVM.xTo + edgeVM.vertexSize / 2 + 55,
                    edgeVM.yTo + edgeVM.vertexSize / 2 - 6f
                ),
            )
            drawRect(
                color = edgeVM.color,
                size = Size(5f, 10f),
                topLeft = Offset(
                    edgeVM.xTo + edgeVM.vertexSize / 2 + 50,
                    edgeVM.yTo + edgeVM.vertexSize / 2 - 5f
                ),
            )
            drawRect(
                color = edgeVM.color,
                size = Size(5f, 8f),
                topLeft = Offset(
                    edgeVM.xTo + edgeVM.vertexSize / 2 + 45,
                    edgeVM.yTo + edgeVM.vertexSize / 2 - 4f
                ),
            )
        }
        if (graphVM.graph.state)
            drawText(
                textMeasurer, edgeVM.weight.toString(),
                topLeft = Offset(
                    (edgeVM.xFrom + edgeVM.vertexSize + edgeVM.xTo) / 2 - edgeVM.weight.toString().length * 5.5f,
                    (edgeVM.yFrom + edgeVM.vertexSize + edgeVM.yTo) / 2 - 9
                ),
                style = TextStyle(background = Color.White, fontSize = 18.sp)
            )
    }
}