import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import java.awt.Dimension

fun main() = application {
    val state = WindowState(
        width = 1920.dp,
        height = 1080.dp,
        position = WindowPosition(alignment = Alignment.Center),
    )
    Window(
        state = state,
        onCloseRequest = ::exitApplication,
        title = "Graph Visualizer",
    ) {
        window.minimumSize = Dimension(100,100)
        App()
    }
}

@Composable
fun App(){
    MaterialTheme(){
        Navigation()
    }
}
