package viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MainScreenViewModel : ViewModel() {
    val graphs = GraphStorage()

    fun addGraph(name: String, type: String) {
        when (type) {
            "undirected" -> {
                graphs.typeList.add(ViewModelType.Undirected)
                graphs.undirectedGraphs.add(UndirectedGraphViewModel(name))
            }

            "directed" -> {
                graphs.typeList.add(ViewModelType.Directed)
                graphs.directedGraphs.add(DirectedGraphViewModel(name))
            }

        }
    }

    enum class ViewModelType() {
        Undirected,
        Directed,
    }

    inner class GraphStorage() {
        fun getName(index: Int): String {
            when (graphs.typeList[index]) {
                ViewModelType.Undirected -> {
                    return graphs.undirectedGraphs[findGraph(index)].name
                }

                ViewModelType.Directed -> {
                    return graphs.directedGraphs[findGraph(index)].name
                }
            }
        }

        private fun findGraph(index: Int): Int {
            var indexAr = 0
            when (graphs.typeList[index]) {
                ViewModelType.Undirected -> {
                    for (i in 0..index) if (graphs.typeList[i] == ViewModelType.Undirected) indexAr += 1
                }

                ViewModelType.Directed -> {
                    for (i in 0..index) if (graphs.typeList[i] == ViewModelType.Directed) indexAr += 1
                }
            }
            return indexAr - 1
        }

        fun removeGraph(index: Int) {
            when (graphs.typeList[index]) {
                ViewModelType.Undirected -> {
                    graphs.undirectedGraphs.removeAt(findGraph(index))
                    graphs.typeList.removeAt(index)
                }

                ViewModelType.Directed -> {
                    graphs.directedGraphs.removeAt(findGraph(index))
                    graphs.typeList.removeAt(index)
                }

            }
        }

        fun getUndirected(index: Int): UndirectedGraphViewModel<Int> {
            return undirectedGraphs[findGraph(index)]
        }

        fun getDirected(index: Int): DirectedGraphViewModel<Int> {
            return directedGraphs[findGraph(index)]
        }

        var undirectedGraphs = mutableStateListOf<UndirectedGraphViewModel<Int>>()
        var directedGraphs = mutableStateListOf<DirectedGraphViewModel<Int>>()
        var typeList = mutableStateListOf<ViewModelType>()
    }
}