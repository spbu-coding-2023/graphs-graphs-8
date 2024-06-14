package view.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.rememberDialogState
import view.screens.settings.localisation
import mu.KotlinLogging
import viewmodel.graph.AbstractGraphViewModel

private val logger = KotlinLogging.logger { }

@Composable
fun DirectedAlgorithmDialog(
    visible: Boolean,
    title: String,
    onCloseRequest: () -> Unit,
    graphVM: AbstractGraphViewModel<String>,
    action: String,
) {
    DialogWindow(
        visible = visible,
        title = title,
        onCloseRequest = onCloseRequest,
        state = rememberDialogState(height = 380.dp, width = 580.dp)
    ) {
        var source by remember { mutableStateOf("") }
        var destination by remember { mutableStateOf("") }
        val textWidth = 90.dp
        Column(modifier = Modifier.padding(30.dp, 24.dp)) {
            Row {
                Text(
                    text = localisation("from"),
                    style = defaultStyle,
                    modifier = Modifier.align(Alignment.CenterVertically).width(textWidth)
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(4.dp, color = Color.Black, shape = RoundedCornerShape(25.dp)),

                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(25.dp),
                    textStyle = defaultStyle,
                    value = source,
                    onValueChange = { newValue -> source = newValue },
                )
            }
            Spacer(modifier = Modifier.height(36.dp))
            Row {
                Text(
                    text = localisation("to"),
                    style = defaultStyle,
                    modifier = Modifier.align(Alignment.CenterVertically).width(textWidth)
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(4.dp, color = Color.Black, shape = RoundedCornerShape(25.dp)),

                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(25.dp),
                    textStyle = defaultStyle,
                    value = destination,
                    onValueChange = { newValue -> destination = newValue },
                )
                Spacer(modifier = Modifier.width(200.dp))
            }
            Spacer(modifier = Modifier.height(36.dp))
            Row {
                val dijkstra = { graphVM.drawDijkstra(source, destination) }
                val fordBellman = { graphVM.drawFordBellman(source, destination) }
                val onClick = when (action) {
                    "Dijkstra" -> dijkstra
                    "FordBellman" -> fordBellman
                    else -> run {
                        logger.warn("Unrecognised action: $action in DirectedAlgorithmDialog")
                        fun() = Unit
                    }
                }
                DefaultButton(onClick, "start", defaultStyle)
                Spacer(modifier = Modifier.width(30.dp))
                DefaultButton(onCloseRequest, "back", defaultStyle, Color.Red)
            }
        }
    }
}
