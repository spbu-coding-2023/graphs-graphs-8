import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
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