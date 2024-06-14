package view.screens

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
import androidx.navigation.NavController
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import localisation.localisation
import view.common.DefaultColors
import view.common.bounceClick
import view.common.defaultStyle
import viewmodel.MainScreenViewModel
import java.io.File

const val pathToSettings = "src/main/kotlin/settings.json"
const val boldLine = 8
const val defaultLine = 3

@Serializable
class SettingsJSON(
    var language: String,
    var bd: String,
    var neo4jUri: String = "",
    var neo4jUser: String = "",
    var neo4jPassword: String = "",
)

enum class SettingType {
    LANGUAGE,
    BD,
    NEO4JURI,
    NEO4JUSER,
    NEO4JPASSWORD,
}

fun resetSettings() {
    File(pathToSettings).writeText(
        Json.encodeToString(
            SettingsJSON(
                "en-US",
                "sqlite"
            )
        )
    )
}

fun makeSetting(type: SettingType, value: String) {
    try {
        val data = Json.decodeFromString<SettingsJSON>(File(pathToSettings).readText())
        when (type) {
            SettingType.LANGUAGE -> data.language = value
            SettingType.BD -> data.bd = value
            SettingType.NEO4JURI -> data.neo4jUri = value
            SettingType.NEO4JUSER -> data.neo4jUser = value
            SettingType.NEO4JPASSWORD -> data.neo4jPassword = value
        }
        File(pathToSettings).writeText(Json.encodeToString(data))
    } catch (exception: Exception) {
        resetSettings()
        return
    }
}

fun getSetting(type: SettingType): String {
    try {
        val data = Json.decodeFromString<SettingsJSON>(File(pathToSettings).readText())
        return when (type) {
            SettingType.LANGUAGE -> data.language
            SettingType.BD -> data.bd
            SettingType.NEO4JURI -> data.neo4jUri
            SettingType.NEO4JUSER -> data.neo4jUser
            SettingType.NEO4JPASSWORD -> data.neo4jPassword
        }
    } catch (e: Exception) {
        resetSettings()
        return getSetting(type)
    }
}

@Composable
fun SettingsScreen(navController: NavController, mainScreenViewModel: MainScreenViewModel) {
    Column(modifier = Modifier.padding(20.dp, 10.dp)) {
        Row(modifier = Modifier.fillMaxSize()) {
            Language(navController)
            Spacer(Modifier.width(10.dp))
            Saving(mainScreenViewModel)
            Spacer(Modifier.width(10.dp))
        }
    }
}

@Composable
fun Language(navController: NavController) {
    Column {
        var language by mutableStateOf(getSetting(SettingType.LANGUAGE))
        Text(
            text = localisation("language"),
            fontSize = 28.sp,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
        )
        Button(
            onClick = {
                makeSetting(SettingType.LANGUAGE, "cn-CN")
                language = "cn-CN"
            },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .border(
                    width = if (language == "cn-CN") boldLine.dp else defaultLine.dp,
                    color = Color.Black
                )
                .bounceClick()
                .size(200.dp, 80.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor =
                if (language == "cn-CN") DefaultColors.primaryBright else DefaultColors.primaryDark
            )
        ) {
            Text("汉语", style = defaultStyle)
        }
        Button(
            onClick = {
                makeSetting(SettingType.LANGUAGE, "ru-RU")
                language = "ru-RU"
            },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .border(
                    width = if (language == "ru-RU") boldLine.dp else defaultLine.dp,
                    color = Color.Black
                )
                .bounceClick()
                .size(200.dp, 80.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor =
                if (language == "ru-RU") DefaultColors.primaryBright else DefaultColors.primaryDark
            )
        ) {
            Text("Русский", style = defaultStyle)
        }
        Button(
            onClick = {
                makeSetting(SettingType.LANGUAGE, "en-US")
                language = "en-US"
            },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .border(
                    width = if (language == "en-US") boldLine.dp else defaultLine.dp,
                    color = Color.Black
                )
                .bounceClick()
                .size(200.dp, 80.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor =
                if (language == "en-US") DefaultColors.primaryBright else DefaultColors.primaryDark
            )
        ) {
            Text("English", style = defaultStyle)
        }
        Button(
            onClick = { navController.navigate(Screen.MainScreen.route) },
            modifier = Modifier
                .padding(16.dp)
                .border(width = defaultLine.dp, color = Color.Black)
                .bounceClick()
                .size(200.dp, 80.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
        ) {
            Text(localisation("back"), style = defaultStyle, color = Color.White)
        }
    }
}

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