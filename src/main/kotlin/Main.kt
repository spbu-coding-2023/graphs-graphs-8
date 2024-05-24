import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import java.awt.Dimension

val width = 1920
val height = 1080

fun main() = application {
    val state = WindowState(
        width = width.dp,
        height = height.dp,
        position = WindowPosition(alignment = Alignment.Center),
    )
    Window(
        state = state,
        onCloseRequest = ::exitApplication,
        title = "Graph Visualizer",
    ) {
        window.minimumSize = Dimension(100, 100)
        App()
    }
}

@Composable
fun App() {
    MaterialTheme() {
        Navigation()
    }
}
