package viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import model.graph.edges.Edge
import model.graph.edges.WeightedEdge

class MainScreenViewModel : ViewModel() {
    val graphs = GraphStorage()

    fun addGraph(name: String, type: Pair<String, String>) {
        when (type){
            Pair("undirected", "unweighted") -> {
                graphs.typeList.add(ViewModelType.UU)
                graphs.undirectedUnweightedGraphs.add(UndirectedUnweightedGraphViewModel<Int, Edge<Int>>(name))
            }
            Pair("directed", "unweighted") -> {
                graphs.typeList.add(ViewModelType.DU)
                graphs.directedUnweightedGraphs.add(DirectedUnweightedGraphViewModel<Int, Edge<Int>>(name))
            }
            Pair("undirected", "weighted") -> {
                graphs.typeList.add(ViewModelType.UW)
                graphs.undirectedWeightedGraphs.add(UndirectedWeightedGraphViewModel<Int, WeightedEdge<Int>>(name))
            }
            Pair("directed", "weighted") -> {
                graphs.typeList.add(ViewModelType.DW)
                graphs.directedWeightedGraphs.add(DirectedWeightedGraphViewModel<Int, WeightedEdge<Int>>(name))
            }
        }
    }
    /*
    fun getGraph(graphId: Int): UndirectedUnweightedGraphViewModel<Int, Edge<Int>> {
        return graphs[graphId]
    }
    */
    enum class ViewModelType(){
        UU,
        DU,
        UW,
        DW
    }

    inner class GraphStorage(){

        fun getName(index: Int) : String{
            when(graphs.typeList[index]){
                ViewModelType.UU -> {
                    return graphs.undirectedUnweightedGraphs[index].name
                }
                ViewModelType.DU -> {
                    return graphs.directedUnweightedGraphs[index].name
                }
                ViewModelType.UW -> {
                    return graphs.undirectedWeightedGraphs[index].name
                }
                ViewModelType.DW -> {
                    return graphs.directedWeightedGraphs[index].name
                }
            }
        }
        private fun findGraph(index: Int) : Int{
            var indexAr = 0
            when(graphs.typeList[index]){
                ViewModelType.UU -> {
                    for (i in 0..index) if (graphs.typeList[i] == ViewModelType.UU) indexAr += 1
                }
                ViewModelType.DU -> {
                    for (i in 0..index) if (graphs.typeList[i] == ViewModelType.DU) indexAr += 1
                }
                ViewModelType.UW -> {
                    for (i in 0..index) if (graphs.typeList[i] == ViewModelType.UW) indexAr += 1
                }
                ViewModelType.DW -> {
                    for (i in 0..index) if (graphs.typeList[i] == ViewModelType.DW) indexAr += 1
                }
            }
            return indexAr
        }
        fun removeAt(index: Int){
            when(graphs.typeList[index]){
                ViewModelType.UU -> {
                    graphs.undirectedUnweightedGraphs.removeAt(findGraph(index))
                }
                ViewModelType.DU -> {
                    graphs.directedUnweightedGraphs.removeAt(findGraph(index))
                }
                ViewModelType.UW -> {
                    graphs.undirectedWeightedGraphs.removeAt(findGraph(index))
                }
                ViewModelType.DW -> {
                    graphs.directedWeightedGraphs.removeAt(findGraph(index))
                }
            }
        }
        var undirectedUnweightedGraphs = mutableStateListOf<UndirectedUnweightedGraphViewModel<Int, Edge<Int>>>()
        var directedUnweightedGraphs = mutableStateListOf<DirectedUnweightedGraphViewModel<Int, Edge<Int>>>()
        var undirectedWeightedGraphs = mutableStateListOf<UndirectedWeightedGraphViewModel<Int, WeightedEdge<Int>>>()
        var directedWeightedGraphs = mutableStateListOf<DirectedWeightedGraphViewModel<Int, WeightedEdge<Int>>>()
        var typeList = mutableStateListOf<ViewModelType>()
    }
}