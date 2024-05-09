package mainscreen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import bounceClick
import visualizer.styling.bigStyle
import visualizer.styling.defaultStyle

sealed class Screen(val route: String){
    object MainScreen: Screen("main_screen")
    object GraphScreen: Screen("graph_screen")
    object SettingsScreen: Screen("settings_screen")
}

@Composable
fun MainScreen(navController: NavController){
    val graphs = remember { mutableStateListOf<String>() }
    var search by  remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth().height(100.dp)) {
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
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(10.dp)
                    ),
                shape = RoundedCornerShape(10.dp),
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
                    .clip(shape = RoundedCornerShape(25.dp))
                    .clickable { }
                    .border(
                        width = 5.dp,
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(25.dp)
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
                    .padding(horizontal = 20.dp)
                    .size(100.dp)
                    .clip(shape = RoundedCornerShape(25.dp))
                    .clickable { }
                    .border(
                        width = 5.dp,
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(25.dp)
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

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(graphs) { graph ->
                if ( !graph.startsWith(search) ) return@items
                Row(modifier = Modifier.padding(30.dp)) {
                    Text(
                        graph,
                        style = bigStyle,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(45.dp))
                            .border(
                            width = 5.dp,
                            color = MaterialTheme.colors.primary,
                                shape = RoundedCornerShape(45.dp)
                        )
                            .padding(vertical = 16.dp, horizontal = 30.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun GraphScreen(navController: NavController){
    Text("Здесь будут графы", style= defaultStyle)
}

@Composable
fun SettingsScreen(navController: NavController){
    Column{
        Text(text = "Тут наверно будут настройки", fontSize = 28.sp)
        Button(onClick = {navController.navigate(Screen.MainScreen.route)},
            modifier = Modifier
                .padding(16.dp)
                .border(width = 3.dp, color = Color.Black)
                .bounceClick()) {
            Text("Назад", style = defaultStyle)
        }
    }

}