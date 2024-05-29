package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import mu.KotlinLogging
import view.screens.SettingType
import view.screens.getSetting
import viewmodel.graph.AbstractGraphViewModel
import viewmodel.io.Neo4jRepository
import viewmodel.io.SQLiteRepository
import kotlin.math.log

enum class GraphType() {
    Undirected,
    Directed,
}

private val logger = KotlinLogging.logger { }

class MainScreenViewModel(val saveType: String = getSetting(SettingType.BD)) : ViewModel() {
    val graphs by mutableStateOf(mutableMapOf<String, AbstractGraphViewModel<String>>())
    val graphsNames = mutableStateListOf<String>()
    internal var inited = false

    fun addGraph(name: String, type: String) {
        if (graphsNames.contains(name)) {
            return
        }
        val graphVM: AbstractGraphViewModel<String>
        when (type) {
            "undirected" -> {
                graphVM = UndirectedGraphViewModel(name)

            }

            else -> {
                graphVM = DirectedGraphViewModel(name)
            }
        }
        graphs[name] = graphVM
        graphsNames.add(name)
    }

    fun saveGraph(name: String, bdName: String = "storage") {
        try {

            val graphVM = getGraph(name)
            if (saveType == "sqlite") {
                graphVM.model.saveSQLite(name, graphVM.graphType.toString(), bdName)
            } else if (saveType == "neo4j") {
                val rep = Neo4jRepository<String>(
                    getSetting(SettingType.NEO4JURI),
                    getSetting(SettingType.NEO4JUSER),
                    getSetting(SettingType.NEO4JPASSWORD)
                )
                rep.saveGraph(graphVM)
            }
        } catch (e: Exception) {
            logger.error { "Can't save graph: $name" }
        }
    }

    fun getGraph(name: String): AbstractGraphViewModel<String> {
        return graphs[name]
            ?: throw IllegalStateException("Can't find graph with name $name")
    }

    fun initGraph(name: String, sourceSQLite: String) {
        val graphVM = getGraph(name)
        if (graphVM.isInited) return
        if (saveType == "sqlite") {
            SQLiteRepository.initGraph(graphVM, sourceSQLite)
        } else if (saveType == "neo4j") {
            val rep = Neo4jRepository<String>(
                getSetting(SettingType.NEO4JURI),
                getSetting(SettingType.NEO4JUSER),
                getSetting(SettingType.NEO4JPASSWORD)
            )
            graphs[name] = rep.getGraph(name)
        }
        graphVM.isInited = true

    }

    fun initGraphList(sourceSQLite: String) {
        if (saveType == "sqlite") {
            SQLiteRepository.initGraphList("storage", this)
        } else if (saveType == "neo4j") {
            val rep = try {
                Neo4jRepository<String>(
                    getSetting(SettingType.NEO4JURI),
                    getSetting(SettingType.NEO4JUSER),
                    getSetting(SettingType.NEO4JPASSWORD)
                )
            } catch (e: Exception) {
                logger.info { "Could not start a neo4j session in repository with given data" }
                return
            }
            rep.initGraphList(this)
        }
        inited = true
    }

    fun removeGraph(name: String) {
        if (saveType == "sqlite") {
            try {
                SQLiteRepository.removeGraph(name)
            } catch (e: Exception) {
            }

        } else if (saveType == "neo4j") {
            val rep = Neo4jRepository<String>(
                getSetting(SettingType.NEO4JURI),
                getSetting(SettingType.NEO4JUSER),
                getSetting(SettingType.NEO4JPASSWORD)
            )
            rep.removeGraph(name)
        }
        graphs.remove(name)
        graphsNames.remove(name)
    }
}