import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import java.awt.Dimension
import java.awt.GraphicsEnvironment

val width = try {
    GraphicsEnvironment.getLocalGraphicsEnvironment().maximumWindowBounds.width
} catch (e: Exception) {
    100
}
val height = try {
    GraphicsEnvironment.getLocalGraphicsEnvironment().maximumWindowBounds.height
} catch (e: Exception) {
    100
}

fun main() {
    application {
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
}

@Composable
fun App() {

    MaterialTheme() {
        Navigation()
    }
}
