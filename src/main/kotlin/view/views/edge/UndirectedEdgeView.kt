package view.views.edge

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
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
import viewmodel.UndirectedGraphViewModel

@Composable
fun <V> UndirectedEdgeView(edgeVM: EdgeViewModel<V>, isWeighted: Boolean) {

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
        if (isWeighted)
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