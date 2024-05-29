package view.common

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

val defaultStyle = TextStyle(fontSize = 28.sp)
val microSize = TextStyle(fontSize = 18.sp, textAlign = TextAlign.Center)
val smallSize = TextStyle(fontSize = 22.sp, textAlign = TextAlign.Center)
val mediumSize = TextStyle(fontSize = 26.sp, textAlign = TextAlign.Center)

val bigStyle = TextStyle(fontSize = 50.sp)

object DefaultColors {
    val primary = Color(0xff, 0xf1, 0x4a)
    val primarySelected = Color(0xcf, 0xc0, 0x07)
    val darkGreen = Color(0x00, 0x64, 0x00)
    val simpleGreen = Color(0x00, 0xe4, 0x00)
    val background = Color.White
}