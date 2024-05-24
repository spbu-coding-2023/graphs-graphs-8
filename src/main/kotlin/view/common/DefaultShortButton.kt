package view.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DefaultShortButton(
    onClick: () -> Unit,
    localisationCode: String,
    color: Color = DefaultColors.primary,
) {
    DefaultButton(onClick, localisationCode, color, 220.dp, 70.dp)
}