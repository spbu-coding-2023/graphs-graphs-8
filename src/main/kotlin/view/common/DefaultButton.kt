package view.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import localisation.localisation

@Composable
fun DefaultButton(
    onClick: () -> Unit,
    localisationCode: String,
    style: TextStyle = defaultStyle,
    color: Color = DefaultColors.primary,
    width: androidx.compose.ui.unit.Dp = 240.dp,
    height: androidx.compose.ui.unit.Dp = 80.dp,
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(45.dp))
            .border(5.dp, color = Color.Black, shape = RoundedCornerShape(45.dp))
            .size(width, height),
        colors = ButtonDefaults.buttonColors(backgroundColor = color)
    ) {
        Text(localisation(localisationCode), style = style)
    }
}