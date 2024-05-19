package viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import model.graph.edges.Edge

class MainScreenViewModel : ViewModel() {
    val graphs = mutableStateListOf<GraphViewModel<Int, Edge<Int>>>()

    fun addGraph(name: String) {
        val graphVM = GraphViewModel<Int, Edge<Int>>(name)
        graphs.add(graphVM)
    }

    fun getGraph(graphId: Int): GraphViewModel<Int, Edge<Int>> {
        return graphs[graphId]
    }
}