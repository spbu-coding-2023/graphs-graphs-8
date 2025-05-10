package view.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun DefaultShortButton(
    onClick: () -> Unit,
    localisationCode: String,
    style: TextStyle = defaultStyle,
    color: Color = DefaultColors.primaryBright,

    ) {
    DefaultButton(onClick, localisationCode, style, color, 220.dp, 70.dp)
}