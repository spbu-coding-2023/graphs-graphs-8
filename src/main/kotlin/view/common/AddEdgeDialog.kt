package view.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.rememberDialogState
import localisation.localisation
import viewmodel.AbstractGraphViewModel

@Composable
fun AddEdgeDialog(
    _visible: Boolean,
    onClose: () -> Unit,
    graphVM: AbstractGraphViewModel<Int>,
    isDirected: Boolean = false
) {
    var visible by mutableStateOf(_visible)
    DialogWindow(
        visible = visible,
        title = "New Edge",
        onCloseRequest = onClose,
        state = rememberDialogState(height = 600.dp, width = 880.dp)
    ) {
        var source by remember { mutableStateOf("") }
        var destination by remember { mutableStateOf("") }
        var checkedState by remember { mutableStateOf(true) }
        var weight by remember { mutableStateOf("1") }
        Column(modifier = Modifier.padding(30.dp, 24.dp)) {
            val textWidth = 90.dp
            val rightPadding = 200.dp
            Row {
                Text(
                    text = localisation(if (isDirected) "from" else "1st"),
                    style = defaultStyle,
                    modifier = Modifier.align(Alignment.CenterVertically).width(textWidth),
                )
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
                Spacer(modifier = Modifier.width(rightPadding))
            }
            Spacer(modifier = Modifier.height(36.dp))
            Row {
                Text(
                    text = localisation(if (isDirected) "to" else ("2nd")),
                    style = defaultStyle,
                    modifier = Modifier.align(Alignment.CenterVertically).width(textWidth)
                )
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
                Spacer(modifier = Modifier.width(rightPadding))
            }
            Spacer(modifier = Modifier.height(36.dp))
            Row {
                if (!checkedState) {
                    Text(
                        text = localisation("weight"),
                        style = defaultStyle,
                        modifier = Modifier.align(Alignment.CenterVertically)
                            .width(textWidth + 30.dp)
                    )
                    TextField(
                        enabled = !checkedState,
                        modifier = Modifier
                            .weight(1f)
                            .width(115.dp)
                            .border(
                                3.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(10.dp),
                            )
                            .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
                        shape = RoundedCornerShape(10.dp),

                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        textStyle = defaultStyle,
                        value = weight,
                        onValueChange = { value ->
                            if (value.length < 10) weight =
                                value.filter { it.isDigit() || (it == '-' && it == value.first()) }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                }
                Checkbox(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    checked = checkedState,
                    onCheckedChange = {
                        checkedState = it;
                        weight = if (checkedState) "1" else ""
                    }
                )
                Text(
                    text = localisation("unweighted"),
                    style = defaultStyle,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(rightPadding))
            }
            Spacer(modifier = Modifier.height(36.dp))
            Row {
                val onClick = {
                    val sourceInt = source.toIntOrNull()
                    val destinationInt = destination.toIntOrNull()
                    if (sourceInt != null && destinationInt != null) {
                        if (weight == "") weight = "1"
                        graphVM.addEdge(sourceInt, destinationInt, weight.toInt())
                        visible = false
                    }
                }
                DefaultButton(onClick, "add_edge")
            }
            Spacer(modifier = Modifier.height(36.dp))
            Row {
                DefaultButton(onClose, "back", Color.Red)
            }
        }
    }
}