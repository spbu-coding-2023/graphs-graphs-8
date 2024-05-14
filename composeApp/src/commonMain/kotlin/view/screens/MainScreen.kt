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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import localisation
import view.DefaultColors
import view.bigStyle
import view.bounceClick
import viewmodel.MainScreenViewModel

@Composable
fun MainScreen(navController: NavController, mainScreenViewModel: MainScreenViewModel = MainScreenViewModel()){
    var search by remember { mutableStateOf("") }
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
                onClick = { mainScreenViewModel.addGraph(search) },
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

        Spacer(modifier = Modifier.height(30.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(mainScreenViewModel.graphs) { index, graph ->
                if (!graph.name.startsWith(search)) return@itemsIndexed

                // To GraphScreen
                Row(modifier = Modifier.padding(vertical = 15.dp)) {
                    Button(
                        onClick = { navController.navigate(Screen.GraphScreen.route) },
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