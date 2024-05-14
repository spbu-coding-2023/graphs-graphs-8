package viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MainScreenViewModel: ViewModel() {
    val graphs = mutableStateListOf<GraphViewModel>()

    fun addGraph(name : String) {
        graphs.add(GraphViewModel(name))
    }

    fun getGraph(graphId: Int): GraphViewModel {
        return graphs[graphId]
    }
}