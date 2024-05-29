package view.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogState
import androidx.compose.ui.window.DialogWindow
import androidx.navigation.NavController
import localisation.localisation
import view.common.DefaultColors
import view.common.bigStyle
import view.common.bounceClick
import view.common.defaultStyle
import viewmodel.GraphType
import viewmodel.MainScreenViewModel
import java.io.File


@Composable
fun MainScreen(navController: NavController, mainScreenViewModel: MainScreenViewModel) {
    var search by remember { mutableStateOf("") }
    var graphName by remember { mutableStateOf("") }
    val dialogState = remember { mutableStateOf(false) }
    val expandedDropDown = remember { mutableStateOf(false) }
    var selectedOptionTextDropDown = remember { GraphType.Undirected }

    if (!mainScreenViewModel.inited) {
        mainScreenViewModel.initGraphList()
    }
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
                        color = DefaultColors.primaryBright,
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
                    .background(DefaultColors.primaryBright)
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
                    .background(DefaultColors.primaryBright)
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

        DialogWindow(
            visible = dialogState.value,
            title = "New Graph",
            resizable = false,
            onCloseRequest = { dialogState.value = false },
            state = DialogState(size = DpSize(960.dp, 680.dp))
        )
        {
            Text(
                text = localisation("enter_new_graph_name"),
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp),
                style = defaultStyle
            )
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
            Button(
                modifier = Modifier
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
                colors = if (graphName != "") ButtonDefaults.buttonColors(backgroundColor = DefaultColors.pinkBright) else ButtonDefaults.buttonColors(
                    backgroundColor = DefaultColors.pinkDark
                ),
                onClick = {
                    if (graphName != "") {
                        mainScreenViewModel.addGraph(
                            graphName,
                            selectedOptionTextDropDown,
                        )
                        mainScreenViewModel.saveGraph(graphName)
                        graphName = ""
                        dialogState.value = false
                    }
                },
            ) {
                Text(
                    text = localisation("add"),
                    color = if (graphName != "") Color.White else Color.Black,
                    fontSize = 28.sp
                )
            }

            Button(
                modifier = Modifier
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
                Text(text = localisation("back"), color = Color.White, fontSize = 28.sp)
            }
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .padding(horizontal = 350.dp, vertical = 180.dp)
                    .width(300.dp)
                    .height(60.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .border(BorderStroke(2.dp, Color.LightGray), RoundedCornerShape(25.dp))
                    .clickable { expandedDropDown.value = !expandedDropDown.value },
            ) {
                Text(
                    text = selectedOptionTextDropDown.toString(),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 20.dp)
                )
                Icon(
                    Icons.Filled.ArrowDropDown, "contentDescription",
                    Modifier.align(Alignment.CenterEnd)
                )
                DropdownMenu(
                    expanded = expandedDropDown.value,
                    onDismissRequest = { expandedDropDown.value = false }
                ) {
                    GraphType.entries.forEach { selectedOption ->
                        DropdownMenuItem(
                            onClick = {
                                selectedOptionTextDropDown = selectedOption
                                expandedDropDown.value = false
                            }
                        ) {
                            Text(text = localisation(selectedOption.toString().lowercase()))
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(mainScreenViewModel.graphNames) { _, name ->
                if (!name.startsWith(search)) return@itemsIndexed
                // To GraphScreen
                val graphVM = mainScreenViewModel.getGraph(name)
                Row(modifier = Modifier.padding(vertical = 15.dp)) {
                    Button(
                        onClick = {
                            mainScreenViewModel.loadGraph(name, "storage")
                            if (graphVM.graphType == GraphType.Undirected) {
                                navController.navigate(
                                    "${Screen.UndirectedGraphScreen.route}/$name"
                                )
                            } else navController.navigate(
                                "${Screen.DirectedGraphScreen.route}/$name"
                            )
                        },
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
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = DefaultColors.primaryBright
                        )
                    ) {
                        Row {
                            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                                Image(
                                    bitmap = if (graphVM.graphType == GraphType.Directed) loadImageBitmap(
                                        File("src/main/resources/directed.png").inputStream()
                                    )
                                    else loadImageBitmap(File("src/main/resources/undirected.png").inputStream()),
                                    contentDescription = "Type",

                                    modifier = Modifier
                                        .padding(15.dp)
                                        .align(Alignment.End),
                                )
                            }
                            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                                Text(
                                    text = name,
                                    style = bigStyle,
                                    modifier = Modifier.clip(RoundedCornerShape(45.dp))
                                )
                            }
                        }

                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    // Remove Graph
                    IconButton(
                        onClick = { mainScreenViewModel.removeGraph(name) },
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(100.dp)
                            .clip(shape = RoundedCornerShape(45.dp))
                            .background(Color(0xe8, 0x08, 0x3e))
                            .border(5.dp, color = Color.Black, shape = RoundedCornerShape(45.dp))
                            .bounceClick(),
                    ) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Remove graph",
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}