package view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.rememberDialogState
import androidx.navigation.NavController
import localisation.localisation
import view.DefaultColors
import view.bigStyle
import view.bounceClick
import view.defaultStyle
import viewmodel.MainScreenViewModel

@Composable
fun MainScreen(navController: NavController, mainScreenViewModel: MainScreenViewModel){
    var search by remember { mutableStateOf("") }
    var graphName by remember { mutableStateOf("") }
    val dialogState = remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize().background(DefaultColors.background).padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth().height(100.dp)) {
            // Search tab
            TextField(
                value = search,
                textStyle = bigStyle,
                placeholder = { Text(text = localisation("enter_graph_name"), style = bigStyle) },
                onValueChange = { search = it },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .border(
                        width = 4.dp,
                        color = DefaultColors.primary,
                        shape = RoundedCornerShape(45.dp)
                    ),
                shape = RoundedCornerShape(45.dp),
                trailingIcon = {
                    Icon(
                        Icons.Filled.Search, contentDescription = "SearchIcon", modifier = Modifier
                            .size(100.dp)
                            .padding(10.dp)
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            )

            Spacer(modifier = Modifier.width(40.dp))

            // Add graph
            IconButton(
                onClick = {
                    dialogState.value = true
                },
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .size(100.dp)
                    .clip(shape = RoundedCornerShape(45.dp))
                    .clickable { }
                    .background(DefaultColors.primary)
                    .border(
                        width = 5.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(45.dp)
                    )
                    .bounceClick(),
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Add graph",
                    modifier = Modifier.size(100.dp)
                )
            }

            // To settings
            IconButton(
                onClick = { navController.navigate(Screen.SettingsScreen.route) },
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .size(100.dp)
                    .clip(shape = RoundedCornerShape(45.dp))
                    .clickable { }
                    .background(DefaultColors.primary)
                    .border(
                        width = 5.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(45.dp)
                    )
                    .bounceClick(),

                ) {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = "Settings",
                    modifier = Modifier.size(100.dp)
                )
            }
        }

        DialogWindow(visible = dialogState.value, title = "New Graph",onCloseRequest = { dialogState.value = false }, state = rememberDialogState(size = DpSize(960.dp, 640.dp))) {
            Text(text = localisation("enter_new_graph_name"), modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp), style = defaultStyle)
            TextField(
                value = graphName,
                textStyle = bigStyle,
                placeholder = { Text(text = localisation("write_name"), style = bigStyle) },
                onValueChange = { graphName = it },
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 70.dp)
                    .size(800.dp, 90.dp)
                    .weight(1f)
                    .border(
                        width = 4.dp,
                        color = Color.Cyan,
                        shape = RoundedCornerShape(25.dp)
                    ),
                shape = RoundedCornerShape(25.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            )
            Button(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 20.dp, vertical = 180.dp)
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(25.dp)
                )
                .width(300.dp)
                .height(60.dp),
                shape = RoundedCornerShape(25.dp),
                colors = if(graphName != "") ButtonDefaults.buttonColors(backgroundColor = DefaultColors.simpleGreen) else ButtonDefaults.buttonColors(backgroundColor = DefaultColors.darkGreen),
                onClick = {
                    if(graphName != ""){
                        mainScreenViewModel.addGraph(graphName)
                        dialogState.value = false
                    }
                },
            ) {
                Text(text= localisation("add"), color = if(graphName != "") Color.White else Color.Black, fontSize = 28.sp)
            }
            Button(modifier = Modifier

                .padding(horizontal = 20.dp, vertical = 260.dp)
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(25.dp)
                )
                .width(300.dp)
                .height(60.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                onClick = {
                    dialogState.value = false
                },
            ) {
                Text(text= localisation("back"), color = Color.White, fontSize = 28.sp)
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(mainScreenViewModel.graphs) { index, graph ->
                if (!graph.name.startsWith(search)) return@itemsIndexed

                // To GraphScreen
                Row(modifier = Modifier.padding(vertical = 15.dp)) {
                    Button(
                        onClick = { navController.navigate("${Screen.GraphScreen.route}/$index") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .weight(1f)
                            .clip(shape = RoundedCornerShape(45.dp))
                            .border(
                                width = 5.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(45.dp)
                            ),
                        colors = ButtonDefaults.buttonColors(backgroundColor = DefaultColors.primary)
                    ) {
                        Text(text = graph.name, style = bigStyle, modifier = Modifier.clip(RoundedCornerShape(45.dp)))
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    // Remove Graph
                    IconButton(
                        onClick = { mainScreenViewModel.graphs.removeAt(index) },
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(100.dp)
                            .clip(shape = RoundedCornerShape(45.dp))
                            .background(Color(0xe8,0x08,0x3e))
                            .border(5.dp , color = Color.Black, shape = RoundedCornerShape(45.dp))
                            .bounceClick(),
                    ){
                        Icon(
                            Icons.Filled.Delete, contentDescription = "Remove graph", modifier = Modifier
                            .padding(5.dp)
                            .fillMaxSize())
                    }
                }
            }
        }
    }
}