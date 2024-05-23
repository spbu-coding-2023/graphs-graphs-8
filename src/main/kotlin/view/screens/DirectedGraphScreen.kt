package view.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.rememberDialogState
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import localisation.localisation
import view.common.AddEdgeDialog
import view.common.DefaultButton
import view.common.defaultStyle
import view.views.GraphViewDirect
import viewmodel.MainScreenViewModel


@Composable
fun DirectedGraphScreen(
    navController: NavController,
    mainScreenViewModel: MainScreenViewModel,
    graphId: Int
) {

    val graphVM by mutableStateOf(mainScreenViewModel.graphs.getDirected(graphId))

    Box(modifier = Modifier.fillMaxSize()) {
        GraphViewDirect(graphVM)
    }

    Column(modifier = Modifier.zIndex(1f).padding(16.dp).width(300.dp)) {
        Text(text = "Directed")
        // To MainScreen
        DefaultButton({ navController.popBackStack() }, "home")

        Spacer(modifier = Modifier.height(16.dp))

        // Add vertex
        DefaultButton({ graphVM.addVertex(graphVM.size) }, "add_vertex")

        Spacer(modifier = Modifier.height(16.dp))

        // Open "add edge" dialog
        var isOpenedEdgeMenu by remember { mutableStateOf(false) }
        DefaultButton({ isOpenedEdgeMenu = !isOpenedEdgeMenu }, "open_edge")

        Spacer(modifier = Modifier.height(10.dp))

        // Open Dijkstra dialog window
        var isDijkstraMenu by remember { mutableStateOf(false) }
        DefaultButton({ isDijkstraMenu = !isDijkstraMenu }, "dijkstra_algorithm")

        Spacer(modifier = Modifier.height(10.dp))

        val onClose = { isOpenedEdgeMenu = false }
        AddEdgeDialog(isOpenedEdgeMenu, onClose, graphVM, isDirected = true)

        // Dijkstra dialog window
        DialogWindow(
            visible = isDijkstraMenu,
            title = "New Edge",
            onCloseRequest = { isDijkstraMenu = false },
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
                    val onClick = { graphVM.dijkstraAlgo(source.toInt(), destination.toInt()) }
                    DefaultButton(onClick, "start")
                }
                Spacer(modifier = Modifier.height(36.dp))
                Row {
                    Spacer(modifier = Modifier.width(30.dp))
                    DefaultButton({ isDijkstraMenu = false }, "back", Color.Red)
                }
            }
        }
    }
}

