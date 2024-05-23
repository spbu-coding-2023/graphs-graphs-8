package view.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.serialization.Serializable
import localisation.getLocalisation
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import localisation.localisation
import view.common.DefaultColors
import view.common.bounceClick
import view.common.defaultStyle
import java.io.File

const val pathToSettings = "src/main/kotlin/settings.json"

@Serializable
class SettingsJSON(var language: String)
enum class SettingType {
    LANGUAGE
}

fun resetSettings() {
    File(pathToSettings).writeText(Json.encodeToString(SettingsJSON("en-US")))
}

fun makeSetting(name: SettingType, value: String) {
    try {
        val data = Json.decodeFromString<SettingsJSON>(File(pathToSettings).readText())
        when (name) {
            SettingType.LANGUAGE -> data.language = value
        }
        File(pathToSettings).writeText(Json.encodeToString(data))
    } catch (exception: Exception) {
        resetSettings()
        return
    }
}

@Composable
fun SettingsScreen(navController: NavController) {
    var language by mutableStateOf(getLocalisation())
    Column {
        Text(
            text = localisation("settings"),
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
                .border(width = if (language == "cn-CN") 5.dp else 3.dp, color = Color.Black)
                .bounceClick(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor =
                if (language == "cn-CN") DefaultColors.primarySelected else DefaultColors.primary
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
                .border(width = if (language == "ru-RU") 5.dp else 3.dp, color = Color.Black)
                .bounceClick(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor =
                if (language == "ru-RU") DefaultColors.primarySelected else DefaultColors.primary
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
                .border(width = if (language == "en-US") 5.dp else 3.dp, color = Color.Black)
                .bounceClick(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor =
                if (language == "en-US") DefaultColors.primarySelected else DefaultColors.primary
            )
        ) {
            Text("English", style = defaultStyle)
        }
        Button(
            onClick = { navController.navigate(Screen.MainScreen.route) },
            modifier = Modifier
                .padding(16.dp)
                .border(width = 3.dp, color = Color.Black)
                .bounceClick(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
        ) {
            Text(localisation("back"), style = defaultStyle, color = Color.White)
        }
    }
}