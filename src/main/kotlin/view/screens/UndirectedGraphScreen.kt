package view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.rememberDialogState
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import localisation.localisation
import view.DefaultColors
import view.defaultStyle
import view.views.GraphViewUndirect
import viewmodel.MainScreenViewModel

@Composable
fun UndirectedGraphScreen(
    navController: NavController,
    mainScreenViewModel: MainScreenViewModel,
    graphId: Int
) {
    val graphVM by mutableStateOf(mainScreenViewModel.graphs.getUndirected(graphId))

    Box(modifier = Modifier.fillMaxSize()) {
        GraphViewUndirect(graphVM)
    }

    Column(modifier = Modifier.zIndex(1f).padding(16.dp).width(300.dp)) {
        // To MainScreen
        Text(text = "Undirected")
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .clip(shape = RoundedCornerShape(45.dp))
                .border(5.dp, color = Color.Black, shape = RoundedCornerShape(45.dp))
                .size(240.dp, 80.dp),
            colors = ButtonDefaults.buttonColors(DefaultColors.primary)
        ) {
            Text(localisation("home"), style = defaultStyle)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add vertex
        Button(
            onClick = { graphVM.addVertex(graphVM.size) },
            modifier = Modifier
                .clip(shape = RoundedCornerShape(45.dp))
                .border(5.dp, color = Color.Black, shape = RoundedCornerShape(45.dp))
                .size(240.dp, 80.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = DefaultColors.primary)
        ) {
            Text(localisation("add_vertex"), style = defaultStyle)
        }

        Spacer(modifier = Modifier.height(16.dp))
        var isOpenedEdgeMenu by remember { mutableStateOf(false) }

        Button(
            onClick = { isOpenedEdgeMenu = !isOpenedEdgeMenu },
            modifier = Modifier
                .clip(shape = RoundedCornerShape(45.dp))
                .border(5.dp, color = Color.Black, shape = RoundedCornerShape(45.dp))
                .size(240.dp, 80.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = DefaultColors.primary)
        ) {
            Text(localisation("open_edge"), style = defaultStyle)
        }

        Spacer(modifier = Modifier.height(10.dp))

        DialogWindow(
            visible = isOpenedEdgeMenu,
            title = "New Edge",
            onCloseRequest = { isOpenedEdgeMenu = false },
            state = rememberDialogState(height = 600.dp, width = 880.dp)
        ) {
            var source by remember { mutableStateOf("") }
            var destination by remember { mutableStateOf("") }
            var checkedState by remember { mutableStateOf(false) }
            var weight by remember { mutableStateOf("") }
            Column {
                Spacer(modifier = Modifier.height(24.dp))
                Row {
                    Spacer(modifier = Modifier.width(30.dp))
                    Text(text = localisation("1st"), style = defaultStyle,modifier = Modifier.align(Alignment.CenterVertically))
                    Spacer(modifier = Modifier.width(62.dp))
                    TextField(
                        modifier = Modifier
                            .weight(1f)
                            .width(115.dp)
                            .border(4.dp, color = Color.Black,shape = RoundedCornerShape(25.dp),),

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
                    Text(text = localisation("2nd"), style = defaultStyle,modifier = Modifier.align(Alignment.CenterVertically))
                    Spacer(modifier = Modifier.width(54.dp))
                    TextField(
                        modifier = Modifier
                            .weight(1f)
                            .width(115.dp)
                            .border(4.dp, color = Color.Black,shape = RoundedCornerShape(25.dp),),

                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        shape = RoundedCornerShape(25.dp),
                        textStyle = defaultStyle,
                        value = destination,
                        onValueChange = { newValue -> destination = newValue },)
                    Spacer(modifier = Modifier.width(200.dp))
                }
                Spacer(modifier = Modifier.height(36.dp))
                Row {
                    Spacer(modifier = Modifier.width(30.dp))
                    if(!checkedState) {
                        Text(
                            text = localisation("weight"),
                            style = defaultStyle,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(30.dp))
                        TextField(
                            enabled = !checkedState,
                            modifier = Modifier
                                .weight(1f)
                                .width(115.dp)
                                .border(3.dp, color = Color.Black, shape = RoundedCornerShape(10.dp),)
                                .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
                            shape = RoundedCornerShape(10.dp),

                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            ),
                            textStyle = defaultStyle,
                            value = weight,
                            onValueChange = { value -> if (value.length < 10) weight = value.filter { it.isDigit() } },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                    }
                    Checkbox(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        checked = checkedState,
                        onCheckedChange = { checkedState = it;
                            weight = if(checkedState) "1" else "" }
                    )
                    Text(text = localisation("unweighted"), style = defaultStyle,modifier = Modifier.align(Alignment.CenterVertically))
                    Spacer(modifier = Modifier.width(200.dp))
                }
                Spacer(modifier = Modifier.height(36.dp))
                Row {
                    Spacer(modifier = Modifier.width(30.dp))
                    Button(
                        onClick = {
                            val sourceInt = source.toIntOrNull()
                            val destinationInt = destination.toIntOrNull()
                            if (sourceInt != null && destinationInt != null) {
                                graphVM.addEdge(sourceInt, destinationInt, weight.toInt())
                                isOpenedEdgeMenu = false
                            }
                        }, modifier = Modifier
                            .clip(shape = RoundedCornerShape(45.dp))
                            .border(5.dp, color = Color.Black, shape = RoundedCornerShape(45.dp))
                            .size(240.dp, 80.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = DefaultColors.primary)
                    ) {
                        Text(localisation("add_edge"), style = defaultStyle)
                    }
                }
                Spacer(modifier = Modifier.height(36.dp))
                Row {
                    Spacer(modifier = Modifier.width(30.dp))
                    Button(
                        onClick = { isOpenedEdgeMenu = false }, modifier = Modifier
                            .clip(shape = RoundedCornerShape(45.dp))
                            .border(5.dp, color = Color.Black, shape = RoundedCornerShape(45.dp))
                            .size(240.dp, 80.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                    ) {
                        Text(localisation("back"), style = defaultStyle)
                    }
                }
            }
        }
    }
}
