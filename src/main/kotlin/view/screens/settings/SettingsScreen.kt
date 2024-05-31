package view.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import viewmodel.MainScreenViewModel
import java.io.File

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

val APPDATA_PATH = "${System.getProperty("user.home")}/.graphs_visualizer"

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

fun resetSettings() {
    File(APPDATA_PATH).mkdirs()
    File(APPDATA_PATH + "/logs/localisation.log").mkdirs()
    File(APPDATA_PATH, "settings.json").run {
        writeText(
            Json.encodeToString(
                SettingsJSON(
                    "en-US",
                    "sqlite"
                )
            )
        )
    }
}

fun makeSetting(type: SettingType, value: String) {
    try {
        val data =
            Json.decodeFromString<SettingsJSON>(File(APPDATA_PATH, "settings.json").readText())
        when (type) {
            SettingType.LANGUAGE -> data.language = value
            SettingType.BD -> data.bd = value
            SettingType.NEO4JURI -> data.neo4jUri = value
            SettingType.NEO4JUSER -> data.neo4jUser = value
            SettingType.NEO4JPASSWORD -> data.neo4jPassword = value
        }
        File(APPDATA_PATH, "settings.json").writeText(Json.encodeToString(data))
    } catch (exception: Exception) {
        resetSettings()
        return
    }
}

fun getSetting(type: SettingType): String {
    try {
        val data =
            Json.decodeFromString<SettingsJSON>(File(APPDATA_PATH, "settings.json").readText())
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
