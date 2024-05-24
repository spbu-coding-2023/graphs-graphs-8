package viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import model.graph.UndirectedGraph

class MainScreenViewModel : ViewModel() {
    val graphs = GraphStorage()

    fun addGraph(name: String, type: String) {
        when (type) {
            "undirected" -> {
                graphs.typeList.add(ViewModelType.Undirected)
                val graph = UndirectedGraphViewModel<Int>(name)
                fun initGraph() {
                    val comSize = 5
                    val comNumb = 7
                    for (i in 0..<comNumb * comSize) graph.addVertex(i)
                    for (i in 0..comNumb - 1) {
                        for (j in (i * comSize)..<((i + 1) * comSize)) {
                            for (k in (i * comSize)..<((i + 1) * comSize)) {
                                graph.addEdge(j, k)
                            }
                        }
                    }
                }
                initGraph()
                graphs.undirectedGraphs.add(graph)
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