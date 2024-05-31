package view.screens.settings

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import view.common.DefaultColors
import view.common.bounceClick
import view.common.defaultStyle
import view.screens.Screen

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