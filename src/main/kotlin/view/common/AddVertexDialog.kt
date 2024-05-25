package view.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
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
fun AddVertexDialog(
    _visible: Boolean,
    onClose: () -> Unit,
    graphVM: AbstractGraphViewModel<String>,
) {
    var visible by mutableStateOf(_visible)
    var centerCoordinates by remember { mutableStateOf(true) }
    DialogWindow(
        visible = visible,
        title = "New Vertices",
        onCloseRequest = onClose,
        state = rememberDialogState(height = 320.dp, width = 570.dp)
    ) {
        var verticesNumber by remember { mutableStateOf("1") }
        val textWidth = 130.dp
        Column(modifier = Modifier.padding(30.dp, 24.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = localisation("number"),
                    style = defaultStyle,
                    modifier = Modifier.align(Alignment.CenterVertically).width(textWidth),
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
                    value = verticesNumber,
                    onValueChange = { newValue ->
                        if (newValue.length < 6) {
                            verticesNumber = newValue.filter { it.isDigit() }
                        }
                    },
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Checkbox(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    checked = centerCoordinates,
                    onCheckedChange = { centerCoordinates = it }
                )
                Text(
                    text = localisation("center_coordinates"),
                    style = defaultStyle,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                val onClick = {
                    if (verticesNumber == "") verticesNumber = "1"
                    repeat(verticesNumber.toInt()) {
                        graphVM.addVertex(graphVM.size.toString(), centerCoordinates)
                    }
                    graphVM.updateView()
                    visible = false

                }
                DefaultButton(onClick, "add_edge")
                Spacer(modifier = Modifier.width(30.dp))
                DefaultButton(onClose, "back", Color.Red)
            }
        }
    }
}