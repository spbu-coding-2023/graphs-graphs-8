package viewmodel.io

import viewmodel.DirectedGraphViewModel
import viewmodel.GraphType
import viewmodel.MainScreenViewModel
import viewmodel.UndirectedGraphViewModel
import viewmodel.graph.AbstractGraphViewModel
import java.sql.DriverManager
import java.sql.SQLException

object SQLiteRepository {
    private val DB_DRIVER = "jdbc:sqlite"
    fun initGraphList(source: String, mainScreenVM: MainScreenViewModel) {
        val DB_DRIVER = "jdbc:sqlite"
        val connection = DriverManager.getConnection("$DB_DRIVER:$source.db")
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
                mainScreenVM.addGraph(resSet.getString("name"), "Directed")
            } else if (resSet.getString("type") == "Undirected") {
                mainScreenVM.addGraph(resSet.getString("name"), "Undirected")
            }
        }
        connection.close()
    }

    fun initGraph(graphVM: AbstractGraphViewModel<String>, source: String) {
        if (graphVM.graphType == GraphType.Directed) {
            val graphVM = graphVM as DirectedGraphViewModel<String>
            val connection = DriverManager.getConnection("$DB_DRIVER:$source.db")
            val getGraphs by lazy { connection.prepareStatement("SELECT * FROM ${graphVM.name}") }
            val getVertex by lazy { connection.prepareStatement("SELECT Vertexes FROM ${graphVM.name}") }
            val resVertex = getVertex.executeQuery()
            val resEdges = getGraphs.executeQuery()
            while (resVertex.next()) {
                var vertexName = resVertex.getString("Vertexes")
                if (vertexName.length > 1) vertexName =
                    vertexName.slice(1..vertexName.length - 1)
                graphVM.addVertex(vertexName)
            }
            while (resEdges.next()) {
                for (i in graphVM.graph.vertices) {
                    val weight = resEdges.getString("V$i")
                    var to = resEdges.getString("Vertexes")
                    to = to.slice(1..<to.length)
                    println(weight)
                    if (weight != null) {
                        graphVM.addEdge(to, i, weight.toInt())
                    }
                }
            }
        }
        if (graphVM.graphType == GraphType.Undirected) {
            val graph = graphVM as UndirectedGraphViewModel<String>
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

    fun removeGraph(name: String) {
        val DB_DRIVER = "jdbc:sqlite"
        val delTable = "DROP TABLE $name"
        val delIndexRec = "DELETE FROM BEBRA_KILLER WHERE name='$name';"
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
    }
}