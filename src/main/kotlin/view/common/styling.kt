package view.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import view.screens.SettingType
import view.screens.getSetting

val defaultStyle = TextStyle(fontSize = 28.sp)
val microStyle = TextStyle(fontSize = 18.sp, textAlign = TextAlign.Center)
val smallStyle = TextStyle(fontSize = 22.sp, textAlign = TextAlign.Center)
val mediumStyle = TextStyle(fontSize = 26.sp, textAlign = TextAlign.Center)

val bigStyle = TextStyle(fontSize = 50.sp)

object DefaultColors {
    val greenBright = Color(0xff00E400)

    val yellowBright = Color(0xffFFF14A)
    val yellowDark = Color(0xffCFC007)
    val blueBright = Color(0xff00BDFF)
    val blueDark = Color(0xff076FBE)
    val pinkBright = Color(0xffFFD3D3)
    val pinkDark = Color(0xffFCABAB)
    val background = Color.White

    var primaryBright by mutableStateOf(
        when (getSetting(SettingType.BD)) {
            "sqlite" -> pinkBright
            "neo4j" -> blueBright
            "local" -> yellowBright
            else -> throw IllegalStateException("BD Setting is invalid")
        }
    )
    var primaryDark by mutableStateOf(
        when (getSetting(SettingType.BD)) {
            "sqlite" -> pinkDark
            "neo4j" -> blueDark
            "local" -> yellowDark
            else -> throw IllegalStateException("BD Setting is invalid")
        }
    )
}