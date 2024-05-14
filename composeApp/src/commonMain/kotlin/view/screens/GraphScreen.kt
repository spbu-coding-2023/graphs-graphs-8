package view.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import view.DefaultColors
import view.defaultStyle
import view.views.GraphView
import viewmodel.GraphViewModel
import viewmodel.MainScreenViewModel

@Composable
fun GraphScreen(navController: NavController, mainScreenViewModel : MainScreenViewModel, graphId : Int) {
    val graphModel by mutableStateOf(mainScreenViewModel.getGraph(graphId))

    // To MainScreen
    Button(
        onClick = { navController.popBackStack() },
        modifier = Modifier
            .offset (x = 16.dp, y = 16.dp)
            .clip(shape = RoundedCornerShape(45.dp))
            .border(5.dp, color = Color.Black, shape = RoundedCornerShape(45.dp))
            .size(120.dp, 70.dp)
            .zIndex(1f),
        colors = ButtonDefaults.buttonColors(DefaultColors.primary)
    ) {
        Text("Home", style = defaultStyle)
    }

    // Add vertex
    Button(
        onClick = { graphModel.addVertex()},
        modifier = Modifier
            .offset(x = 156.dp, y = 16.dp)
            .clip(shape = RoundedCornerShape(45.dp))
            .border(5.dp, color = Color.Black, shape = RoundedCornerShape(45.dp))
            .size(120.dp, 70.dp)
            .zIndex(1f),
        colors = ButtonDefaults.buttonColors(backgroundColor = DefaultColors.primary)
    ) {
        Text("Add", style = defaultStyle)
    }

    Box(modifier = Modifier.fillMaxSize()){
        GraphView(graphModel)
    }
}