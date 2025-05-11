package view.screens.settings

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import view.common.DefaultColors
import view.common.bounceClick
import view.common.defaultStyle
import viewmodel.MainScreenViewModel

@Composable
fun Saving(mainScreenViewModel: MainScreenViewModel) {
    Column {
        Text(
            text = localisation("saving"),
            fontSize = 28.sp,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
        )
        Button(
            onClick = {
                makeSetting(SettingType.BD, "sqlite")
                mainScreenViewModel.saveType = "sqlite"
                mainScreenViewModel.inited = false
                DefaultColors.primaryBright = DefaultColors.pinkBright
                DefaultColors.primaryDark = DefaultColors.pinkDark
            },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .border(
                    width = if (mainScreenViewModel.saveType == "sqlite") boldLine.dp else defaultLine.dp,
                    color = Color.Black
                )
                .bounceClick()
                .size(200.dp, 80.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (mainScreenViewModel.saveType == "sqlite")
                    DefaultColors.pinkBright else DefaultColors.pinkDark
            )
        ) {
            Text("SQLite", style = defaultStyle)
        }
        Button(
            onClick = {
                makeSetting(SettingType.BD, "neo4j")
                mainScreenViewModel.saveType = "neo4j"
                mainScreenViewModel.inited = false
                DefaultColors.primaryBright = DefaultColors.blueBright
                DefaultColors.primaryDark = DefaultColors.blueDark
            },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .border(
                    width = if (mainScreenViewModel.saveType == "neo4j") boldLine.dp else defaultLine.dp,
                    color = Color.Black
                )
                .bounceClick()
                .size(200.dp, 80.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (mainScreenViewModel.saveType == "neo4j")
                    DefaultColors.blueBright else DefaultColors.blueDark
            )
        ) {
            Text("Neo4j", style = defaultStyle)
        }
        Button(
            onClick = {
                makeSetting(SettingType.BD, "local")
                mainScreenViewModel.saveType = "local"
                mainScreenViewModel.inited = false
                DefaultColors.primaryBright = DefaultColors.yellowBright
                DefaultColors.primaryDark = DefaultColors.yellowDark
            },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .border(
                    width = if (mainScreenViewModel.saveType == "local") boldLine.dp else defaultLine.dp,
                    color = Color.Black
                )
                .bounceClick()
                .size(200.dp, 80.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (mainScreenViewModel.saveType == "local")
                    DefaultColors.yellowBright else DefaultColors.yellowDark
            )
        ) {
            Text("Local file", style = defaultStyle)
        }
    }
    Spacer(modifier = Modifier.width(30.dp))
    var uri by remember { mutableStateOf(getSetting(SettingType.NEO4JURI)) }
    var user by remember { mutableStateOf(getSetting(SettingType.NEO4JUSER)) }
    var password by remember { mutableStateOf(getSetting(SettingType.NEO4JPASSWORD)) }
    if (mainScreenViewModel.saveType == "neo4j") {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = localisation("neo4j_data"),
                fontSize = 28.sp,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                modifier = Modifier
                    .width(1000.dp)
                    .border(4.dp, color = Color.Black, shape = RoundedCornerShape(25.dp))
                    .size(400.dp, 80.dp),

                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                placeholder = {
                    Text(
                        "Enter URI: for example, bolt://localhost:7687",
                        fontSize = 32.sp
                    )
                },
                textStyle = TextStyle(fontSize = 32.sp),
                shape = RoundedCornerShape(25.dp),
                value = uri,
                onValueChange = { newValue -> uri = newValue },
            )
            Spacer(modifier = Modifier.height(40.dp))
            TextField(
                modifier = Modifier
                    .width(1000.dp)
                    .border(4.dp, color = Color.Black, shape = RoundedCornerShape(25.dp))
                    .size(300.dp, 80.dp),

                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(25.dp),
                placeholder = {
                    Text(
                        "Enter username:",
                        fontSize = 32.sp
                    )
                },
                textStyle = TextStyle(fontSize = 32.sp),
                value = user,
                onValueChange = { newValue -> user = newValue },
            )
            Spacer(modifier = Modifier.height(40.dp))
            TextField(
                modifier = Modifier
                    .width(1000.dp)
                    .border(4.dp, color = Color.Black, shape = RoundedCornerShape(25.dp))
                    .size(400.dp, 80.dp),

                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                placeholder = {
                    Text(
                        "Enter password",
                        fontSize = 32.sp
                    )
                },
                shape = RoundedCornerShape(25.dp),
                textStyle = TextStyle(fontSize = 32.sp),
                value = password,
                onValueChange = { newValue -> password = newValue },
            )
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = {
                    makeSetting(SettingType.NEO4JURI, uri)
                    makeSetting(SettingType.NEO4JUSER, user)
                    makeSetting(SettingType.NEO4JPASSWORD, password)
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 20.dp)
                    .border(width = 3.dp, color = Color.Black)
                    .bounceClick()
                    .size(200.dp, 80.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = DefaultColors.blueBright
                )
            ) {
                Text("Connect", style = defaultStyle)
            }
        }
    }
}