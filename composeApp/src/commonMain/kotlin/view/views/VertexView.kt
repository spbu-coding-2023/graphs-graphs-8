package view.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import view.DefaultColors
import viewmodel.VertexViewModel
import kotlin.math.roundToInt

@Composable
fun VertexView(vertexViewModel: VertexViewModel) {
    val index = vertexViewModel.index
    var offsetX by remember { mutableStateOf(1000f) }
    var offsetY by remember { mutableStateOf(540f) }
    Box(modifier = Modifier
        .offset {IntOffset(offsetX.roundToInt(), offsetY.roundToInt())}
        .clip(shape = CircleShape)
        .size(120.dp)
        .background(DefaultColors.background)
        .border(5.dp, Color.Black, CircleShape)
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consume()
                offsetX += dragAmount.x
                offsetY += dragAmount.y
            }
        }
    ){
        Text(text = "$index",
            fontSize = 40.sp,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(),)
    }
}