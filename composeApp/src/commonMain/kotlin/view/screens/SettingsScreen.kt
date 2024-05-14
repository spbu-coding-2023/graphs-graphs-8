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
import view.DefaultColors
import view.bounceClick
import view.defaultStyle

@Composable
fun SettingsScreen(navController: NavController){
    Column{
        Text(text = "Тут наверно будут настройки", fontSize = 28.sp)
        Button(onClick = {navController.popBackStack()},
            modifier = Modifier
                .padding(16.dp)
                .border(width = 3.dp, color = Color.Black)
                .bounceClick(),
            colors = ButtonDefaults.buttonColors(backgroundColor = DefaultColors.primary)) {
            Text("Назад", style = defaultStyle)
        }
    }

}