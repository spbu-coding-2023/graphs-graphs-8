package view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.lifecycle.ViewModel
import lib.graph.Graph
import view.views.GraphView
import viewmodel.GraphViewModel

sealed class Screen(val route: String){
    object MainScreen: Screen("main_screen")
    object GraphScreen: Screen("graph_screen")
    object SettingsScreen: Screen("settings_screen")
}

class MainScreenViewModel : ViewModel(){
    val graphs = mutableStateOf(arrayListOf<Graph>())
    val searchText = mutableStateOf("")

    fun addGraph(graph: Graph){
        graphs.value.add(graph)
    }
    fun removeGraph(index : Int){
        if (index in graphs.value.indices){
            graphs.value.removeAt(index)
        }
        else throw IllegalArgumentException("graph index out of range")
    }

    fun changeSearchText(text: String){
        searchText.value = text
    }
}

@Composable
fun MainScreen(navController: NavController){
    val graphs = remember { mutableStateListOf<String>() }
    var search by  remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize().background(DefaultColors.background).padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth().height(100.dp)) {
            // Search tab
            TextField(
                value = search,
                textStyle = bigStyle,
                placeholder = { Text(text = "Enter graph name", style = bigStyle) },
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

            // add graph
            IconButton(
                onClick = { graphs.add(search) },
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

            // to settings
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
            itemsIndexed(graphs) { index, graph ->
                if (!graph.startsWith(search)) return@itemsIndexed
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
                        Text(text = graph, style = bigStyle, modifier = Modifier.clip(RoundedCornerShape(45.dp)))
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    IconButton(
                        onClick = { graphs.removeAt(index) },
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(100.dp)
                            .clip(shape = RoundedCornerShape(45.dp))
                            .background(Color(0xe8,0x08,0x3e))
                            .border(5.dp , color = Color.Black, shape = RoundedCornerShape(45.dp))
                            .bounceClick(),
                    ){
                        Icon(Icons.Filled.Delete, contentDescription = "Remove graph", modifier = Modifier
                            .padding(5.dp)
                            .fillMaxSize())
                    }
                }
            }
        }
    }
}

@Composable
fun GraphScreen(navController: NavController, graphViewModel : GraphViewModel = GraphViewModel()) {
    Button(
        onClick = { navController.navigate(Screen.MainScreen.route) },
        modifier = Modifier
            .offset (x = 16.dp, y = 16.dp)
            .clip(shape = RoundedCornerShape(45.dp))
            .border(5.dp, color = Color.Black, shape = RoundedCornerShape(45.dp))
            .background(DefaultColors.primary)
            .size(120.dp, 70.dp)
            .zIndex(1f)
    ) {
        Text("Home", style = defaultStyle)
    }
    Button(
        onClick = { graphViewModel.addVertex()},
        modifier = Modifier
            .offset(x = 156.dp, y = 16.dp)
            .clip(shape = RoundedCornerShape(45.dp))
            .border(5.dp, color = Color.Black, shape = RoundedCornerShape(45.dp))
            .background(DefaultColors.primary)
            .size(120.dp, 70.dp)
            .zIndex(1f),
        colors = ButtonDefaults.buttonColors(backgroundColor = DefaultColors.primary)
    ) {
        Text("Add", style = defaultStyle)
    }

    Box(modifier = Modifier.fillMaxSize()){
        GraphView(graphViewModel)
    }   
}

@Composable
fun SettingsScreen(navController: NavController){
    Column{
        Text(text = "Тут наверно будут настройки", fontSize = 28.sp)
        Button(onClick = {navController.navigate(Screen.MainScreen.route)},
            modifier = Modifier
                .padding(16.dp)
                .border(width = 3.dp, color = Color.Black)
                .bounceClick(),
            colors = ButtonDefaults.buttonColors(backgroundColor = DefaultColors.background)) {
            Text("Назад", style = defaultStyle)
        }
    }

}