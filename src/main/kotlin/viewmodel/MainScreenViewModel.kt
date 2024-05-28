package viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import viewmodel.graph.DirectedGraphViewModel
import viewmodel.graph.UndirectedGraphViewModel
import java.sql.DriverManager
import java.sql.SQLException

enum class SaveType {
    SQLite,
    CSV,
    Neo4j,
    Internal
}

enum class GraphType() {
    Undirected,
    Directed,
}

class MainScreenViewModel : ViewModel() {
    val graphs = GraphStorage()
    internal var inited = false
    private val DB_DRIVER = "jdbc:sqlite"
    fun addGraph(name: String, type: String, saveType: SaveType) {
        when (type) {
            "undirected" -> {
                graphs.typeList.add(GraphType.Undirected)
                val graphVM = UndirectedGraphViewModel<String>(name)
                graphVM.saveType = saveType
                graphs.undirectedGraphs.add(graphVM)
            }

            "directed" -> {
                graphs.typeList.add(GraphType.Directed)
                val graphVM = DirectedGraphViewModel<String>(name)
                graphVM.saveType = saveType
                graphs.directedGraphs.add(graphVM)
            }

        }
    }

    fun initModel(index: Int) {
        if (graphs.typeList[index] == GraphType.Directed) {
            val graph = graphs.getDirected(index)
            if (graph.initedGraph) return
            else graph.initedGraph = true
            if (graph.saveType == SaveType.SQLite) {
                val connection = DriverManager.getConnection("$DB_DRIVER:storage.db")
                val getGraphs by lazy { connection.prepareStatement("SELECT * FROM ${graph.name}") }
                val getVertex by lazy { connection.prepareStatement("SELECT Vertexes FROM ${graph.name}") }
                val resVertex = getVertex.executeQuery()
                val resEdges = getGraphs.executeQuery()
                while (resVertex.next()) {
                    var vertexName = resVertex.getString("Vertexes")
                    if (vertexName.length > 1) vertexName =
                        vertexName.slice(1..vertexName.length - 1)
                    graph.addVertex(vertexName)
                }
                while (resEdges.next()) {
                    for (i in graph.graph.vertices) {
                        val weight = resEdges.getString("V$i")
                        var to = resEdges.getString("Vertexes")
                        to = to.slice(1..<to.length)
                        println(weight)
                        if (weight != null) {
                            graph.addEdge(to, i, weight.toInt())
                        }
                    }
                }
            }
        }
        if (graphs.typeList[index] == GraphType.Undirected) {
            val graph = graphs.getUndirected(index)
            if (graph.initedGraph) return
            else graph.initedGraph = true
            if (graph.saveType == SaveType.SQLite) {
                val connection = DriverManager.getConnection("$DB_DRIVER:storage.db")
                val getGraphs by lazy { connection.prepareStatement("SELECT * FROM ${graph.name}") }
                val getVertex by lazy { connection.prepareStatement("SELECT Vertexes FROM ${graph.name}") }
                val resVertex = getVertex.executeQuery()
                val resEdges = getGraphs.executeQuery()
                while (resVertex.next()) {
                    var vertexName = resVertex.getString("Vertexes")
                    if (vertexName.length > 1) vertexName =
                        vertexName.slice(1..vertexName.length - 1)
                    graph.addVertex(vertexName)
                }
                while (resEdges.next()) {
                    for (i in graph.graph.vertices) {
                        val weight = resEdges.getString("V$i")
                        var to = resEdges.getString("Vertexes")
                        to = to.slice(1..<to.length)
                        if (weight != null) {
                            graph.addEdge(to, i, weight.toInt())
                        }
                    }
                }
            }
        }
    }

    fun graphInit() {
        val DB_DRIVER = "jdbc:sqlite"
        val connection = DriverManager.getConnection("$DB_DRIVER:storage.db")
            ?: throw SQLException("Cannot connect to database")
        val createIndex = ("CREATE TABLE BEBRA_KILLER (name TEXT, type TEXT);")

        connection.createStatement().also { stmt ->
            try {
                stmt.execute(createIndex)
                println("Tables created or already exists")
            } catch (ex: Exception) {
                println("Cannot create table in database")
                println(ex)
            } finally {
                stmt.close()
            }
        }
        val getGraphs by lazy { connection.prepareStatement("SELECT * FROM BEBRA_KILLER") }
        val resSet = getGraphs.executeQuery()
        while (resSet.next()) {
            if (resSet.getString("type") == "Directed") {
                addGraph(resSet.getString("name"), "directed", SaveType.SQLite)
            } else if (resSet.getString("type") == "Undirected") {
                addGraph(resSet.getString("name"), "undirected", SaveType.SQLite)
            }
        }
        connection.close()
    }

    inner class GraphStorage() {
        fun getName(index: Int): String {
            when (graphs.typeList[index]) {
                GraphType.Undirected -> {
                    return graphs.undirectedGraphs[findGraph(index)].name
                }

                GraphType.Directed -> {
                    return graphs.directedGraphs[findGraph(index)].name
                }
            }
        }

        internal fun findGraph(index: Int): Int {
            var indexAr = 0
            when (graphs.typeList[index]) {
                GraphType.Undirected -> {
                    for (i in 0..index) if (graphs.typeList[i] == GraphType.Undirected) indexAr += 1
                }

                GraphType.Directed -> {
                    for (i in 0..index) if (graphs.typeList[i] == GraphType.Directed) indexAr += 1
                }
            }
            return indexAr - 1
        }

        fun removeGraph(index: Int) {
            val DB_DRIVER = "jdbc:sqlite"
            val delTable = "DROP TABLE ${getName(index)}"
            val delIndexRec = "DELETE FROM BEBRA_KILLER WHERE name='${getName(index)}';"
            val connection = DriverManager.getConnection("$DB_DRIVER:storage.db")
                ?: throw SQLException("Cannot connect to database")
            connection.createStatement().also { stmt ->
                try {
                    stmt.execute(delTable)
                    stmt.execute(delIndexRec)
                    println("Tables created or already exists")
                } catch (ex: Exception) {
                    println("Cannot create table in database")
                    println(ex)
                } finally {
                    stmt.close()
                }
            }
            when (graphs.typeList[index]) {
                GraphType.Undirected -> {
                    graphs.undirectedGraphs.removeAt(findGraph(index))
                    graphs.typeList.removeAt(index)
                }

                GraphType.Directed -> {
                    graphs.directedGraphs.removeAt(findGraph(index))
                    graphs.typeList.removeAt(index)
                }
            }

        }

        fun getUndirected(index: Int): UndirectedGraphViewModel<String> {
            return undirectedGraphs[findGraph(index)]
        }

        fun getDirected(index: Int): DirectedGraphViewModel<String> {
            return directedGraphs[findGraph(index)]
        }

        var undirectedGraphs = mutableStateListOf<UndirectedGraphViewModel<String>>()
        var directedGraphs = mutableStateListOf<DirectedGraphViewModel<String>>()
        var typeList = mutableStateListOf<GraphType>()
    }
}