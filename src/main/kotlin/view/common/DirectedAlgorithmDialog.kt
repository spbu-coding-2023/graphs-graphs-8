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
import localisation.localisation
import viewmodel.AbstractGraphViewModel


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
        state = rememberDialogState(height = 600.dp, width = 880.dp)
    ) {
        var source by remember { mutableStateOf("") }
        var destination by remember { mutableStateOf("") }
        Column {
            Spacer(modifier = Modifier.height(24.dp))
            Row {
                Spacer(modifier = Modifier.width(30.dp))
                Text(
                    text = localisation("from"),
                    style = defaultStyle,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(26.dp))
                TextField(
                    modifier = Modifier
                        .weight(1f)
                        .width(115.dp)
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
                Spacer(modifier = Modifier.width(200.dp))
            }
            Spacer(modifier = Modifier.height(36.dp))
            Row {
                Spacer(modifier = Modifier.width(30.dp))
                Text(
                    text = localisation("to"),
                    style = defaultStyle,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(62.dp))
                TextField(
                    modifier = Modifier
                        .weight(1f)
                        .width(115.dp)
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
                Spacer(modifier = Modifier.width(30.dp))
                val dijkstra = { graphVM.dijkstraAlgo(source, destination) }
                val fordBellman = { graphVM.fordBellman(source, destination) }
                val onClick = if (action == "Dijkstra") {
                    dijkstra
                } else if (action == "FordBellman") {
                    fordBellman
                } else {
                    {}
                }
                DefaultButton(onClick, "start")
            }
            Spacer(modifier = Modifier.height(36.dp))
            Row {
                Spacer(modifier = Modifier.width(30.dp))
                DefaultButton(onCloseRequest, "back", Color.Red)
            }
        }
    }
}
