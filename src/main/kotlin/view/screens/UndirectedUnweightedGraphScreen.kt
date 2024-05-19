package view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import localisation.localisation
import model.graph.edges.Edge
import view.DefaultColors
import view.defaultStyle
import view.views.GraphView
import viewmodel.MainScreenViewModel
import viewmodel.UndirectedUnweightedGraphViewModel

@Composable
fun UndirectedUnweightedGraphScreen(
    navController: NavController,
    mainScreenViewModel: MainScreenViewModel,
    graphId: Int
) {
    val graphVM by mutableStateOf(mainScreenViewModel.graphs.getUU(graphId))

    Box(modifier = Modifier.fillMaxSize()) {
        GraphView(graphVM)
    }

    Column(modifier = Modifier.zIndex(1f).padding(16.dp).width(300.dp)) {
        // To MainScreen
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

        if (isOpenedEdgeMenu) {
            Column(
                modifier = Modifier
                    .background(Color(0xffeeeeee))
                    .border(3.dp, color = Color.Black)
                    .padding(10.dp)

            ) {
                AddUUEdgeMenu(graphVM)
            }

        }
    }
}

@Composable
fun AddUUEdgeMenu(graphModel: UndirectedUnweightedGraphViewModel<Int, Edge<Int>>) {
    var source by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    Row {
        TextField(
            modifier = Modifier
                .width(115.dp)
                .border(3.dp, color = Color.Black),
            textStyle = defaultStyle,
            value = source,
            onValueChange = { newValue -> source = newValue },
        )
        Spacer(modifier = Modifier.width(10.dp))
        TextField(
            modifier = Modifier
                .width(115.dp)
                .border(3.dp, color = Color.Black),
            textStyle = defaultStyle,
            value = destination,
            onValueChange = { newValue -> destination = newValue })
    }

    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = {
            val sourceInt = source.toIntOrNull()
            val destinationInt = destination.toIntOrNull()
            if (sourceInt != null && destinationInt != null) {
                graphModel.addEdge(sourceInt, destinationInt)
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
