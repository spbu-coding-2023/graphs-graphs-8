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
import view.common.AddEdgeDialog
import view.common.DefaultButton
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
        DefaultButton({ graphVM.addVertex(graphVM.size) }, "add_vertex")

        Spacer(modifier = Modifier.height(16.dp))

        // Open "add edge" dialog window
        var isOpenedEdgeMenu by remember { mutableStateOf(false) }
        DefaultButton({ isOpenedEdgeMenu = !isOpenedEdgeMenu }, "open_edge")

        Spacer(modifier = Modifier.height(10.dp))
        
        val onClose = { isOpenedEdgeMenu = false }
        AddEdgeDialog(isOpenedEdgeMenu, onClose, graphVM)
    }
}
