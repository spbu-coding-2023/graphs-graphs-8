package viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MainScreenViewModel : ViewModel() {
    val graphs = GraphStorage()

    fun addGraph(name: String, type: String) {
        when (type){
            "undirected" -> {
                graphs.typeList.add(ViewModelType.Undirect)
                graphs.undirectedGraphs.add(UndirectedGraphViewModel(name))
            }
            "directed" -> {
                graphs.typeList.add(ViewModelType.Direct)
                graphs.directedGraphs.add(DirectedGraphViewModel(name))
            }

        }
    }
    enum class ViewModelType(){
        Undirect,
        Direct,
    }

    inner class GraphStorage(){
        fun getName(index: Int) : String{
            when(graphs.typeList[index]){
                ViewModelType.Undirect -> {
                    return graphs.undirectedGraphs[findGraph(index)].name
                }
                ViewModelType.Direct -> {
                    return graphs.directedGraphs[findGraph(index)].name
                }
            }
        }
        private fun findGraph(index: Int) : Int{
            var indexAr = 0
            when(graphs.typeList[index]){
                ViewModelType.Undirect -> {
                    for (i in 0..index) if (graphs.typeList[i] == ViewModelType.Undirect) indexAr += 1
                }
                ViewModelType.Direct -> {
                    for (i in 0..index) if (graphs.typeList[i] == ViewModelType.Direct) indexAr += 1
                }
            }
            return indexAr - 1
        }
        fun removeGraph(index: Int){
            when(graphs.typeList[index]){
                ViewModelType.Undirect -> {
                    graphs.undirectedGraphs.removeAt(findGraph(index))
                    graphs.typeList.removeAt(index)
                }
                ViewModelType.Direct -> {
                    graphs.directedGraphs.removeAt(findGraph(index))
                    graphs.typeList.removeAt(index)
                }

            }
        }
        fun getUndirect(index: Int) : UndirectedGraphViewModel<Int>{
            return undirectedGraphs[findGraph(index)]
        }
        fun getDirect(index: Int) : DirectedGraphViewModel<Int>{
            return directedGraphs[findGraph(index)]
        }

        var undirectedGraphs = mutableStateListOf<UndirectedGraphViewModel<Int>>()
        var directedGraphs = mutableStateListOf<DirectedGraphViewModel<Int>>()
        var typeList = mutableStateListOf<ViewModelType>()
    }
}