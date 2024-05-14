package view.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import localisation
import view.DefaultColors
import view.bounceClick
import view.defaultStyle
import java.io.File


@Serializable
class SettingsJSON(var language: String)
enum class SettingType{
    LANGUAGE
}

fun resetSettings(){
    File("src/settings.json").writeText(Json.encodeToString(SettingsJSON("en-US")))
}

fun makeSetting(name: SettingType, value: String){
    try{
        val data = Json.decodeFromString<SettingsJSON>(File("src/settings.json").readText())
        when (name){
            SettingType.LANGUAGE -> data.language = value;
        }
        File("src/settings.json").writeText(Json.encodeToString(data))
    }
    catch(exception: Exception){
        resetSettings()
        return
    }
}

@Composable
fun SettingsScreen(navController: NavController){
    Column{
        Text(text = localisation("settings"), fontSize = 28.sp, modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp))
        Button(onClick = {makeSetting(SettingType.LANGUAGE, "cn-CN")},
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .border(width = 3.dp, color = Color.Black)
                .bounceClick(),
            colors = ButtonDefaults.buttonColors(backgroundColor = DefaultColors.primary)) {
            Text("汉语", style = defaultStyle)
        }
        Button(onClick = {makeSetting(SettingType.LANGUAGE, "ru-RU")},
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .border(width = 3.dp, color = Color.Black)
                .bounceClick(),
            colors = ButtonDefaults.buttonColors(backgroundColor = DefaultColors.primary)) {
            Text("Русский", style = defaultStyle)
        }
        Button(onClick = {makeSetting(SettingType.LANGUAGE, "en-US")},
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .border(width = 3.dp, color = Color.Black)
                .bounceClick(),
            colors = ButtonDefaults.buttonColors(backgroundColor = DefaultColors.primary)) {
            Text("English", style = defaultStyle)
        }
        Button(onClick = {navController.navigate(Screen.MainScreen.route)},
            modifier = Modifier
                .padding(16.dp)
                .border(width = 3.dp, color = Color.Black)
                .bounceClick(),
            colors = ButtonDefaults.buttonColors(backgroundColor = DefaultColors.error)) {
            Text(localisation("back"), style = defaultStyle, color = Color.White)
        }

    }
}