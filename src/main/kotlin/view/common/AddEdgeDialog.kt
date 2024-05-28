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
import viewmodel.graph.AbstractGraphViewModel

@Composable
fun AddEdgeDialog(
    _visible: Boolean,
    onClose: () -> Unit,
    graphVM: AbstractGraphViewModel<String>,
    isDirected: Boolean = false
) {
    var visible by mutableStateOf(_visible)
    DialogWindow(
        visible = visible,
        title = "New Edge",
        onCloseRequest = onClose,
        state = rememberDialogState(height = 520.dp, width = 580.dp)
    ) {
        var source by remember { mutableStateOf("") }
        var destination by remember { mutableStateOf("") }
        var notWeighted by remember { mutableStateOf(true) }
        var weight by remember { mutableStateOf("1") }
        Column(modifier = Modifier.padding(30.dp, 24.dp).fillMaxSize()) {
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
                Spacer(modifier = Modifier.width(rightPadding))
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                if (!notWeighted) {
                    Text(
                        text = localisation("weight"),
                        style = defaultStyle,
                        modifier = Modifier.align(Alignment.CenterVertically)
                            .width(textWidth + 30.dp)
                    )
                    TextField(
                        enabled = !notWeighted,
                        modifier = Modifier
                            .fillMaxWidth()
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
            }
            Spacer(modifier = Modifier.weight(1f))
            Row {
                Checkbox(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    checked = notWeighted,
                    onCheckedChange = {
                        notWeighted = it;
                        weight = if (notWeighted) "1" else ""
                    }
                )
                Text(
                    text = localisation("unweighted"),
                    style = defaultStyle,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(rightPadding))
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                val onClick = {
                    if (weight == "") weight = "1"
                    graphVM.addEdge(source, destination, weight.toInt())
                    visible = false

                }
                DefaultButton(onClick, "add_edge")
                Spacer(modifier = Modifier.width(30.dp))
                DefaultButton(onClose, "back", Color.Red)
            }
        }
    }
}