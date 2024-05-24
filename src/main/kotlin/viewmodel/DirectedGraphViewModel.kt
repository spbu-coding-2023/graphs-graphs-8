package viewmodel

import Dijkstra
import StrongConnections
import androidx.compose.ui.graphics.Color
import model.graph.DirectedGraph
import model.graph.edges.Edge
import java.sql.DriverManager
import java.sql.SQLException
import kotlin.random.Random

class DirectedGraphViewModel<V>(
    name: String,
    val graph: DirectedGraph<V> = DirectedGraph()
): AbstractGraphViewModel<V>(name, graph){
    val model
        get() = graph
    var inType = initType.Internal
    var initedGraph = false

    private val DB_DRIVER = "jdbc:sqlite"
    init {
        for (vertex in graphModel.entries) {
            vertexView[vertex.key] = VertexViewModel(vertex.key, vertex.value)
        }
        for (edge in graphModel.edges) {
            edgesView.add(EdgeViewModel(edge, vertexView[edge.from]!!, vertexView[edge.to]!!))
        }
    }


    fun dijkstraAlgo(start: V, end: V){
        val y = Dijkstra(graph.matrix, graph.size).dijkstra(start, end)
        for (edgeVM in edgesView){
            if (Edge(edgeVM.from, edgeVM.to, edgeVM.weight) in y){
                edgeVM.color = Color.Red
            }
        }
    }

    fun showStrongConnections(){
        val k = StrongConnections<V>()
        for (i in k.findStrongConnections(graph.matrix)) {
            val col = Color(Random.nextInt(30, 230), Random.nextInt(30, 230),Random.nextInt(30, 230))
            for (j in i) {
                if (j in graphModel.vertices) {
                    vertexView[j]?.color = col
                    updateView()
                }
            }
        }
    }

    fun saveSQLite(){
        var parameterCreate = "( Vertexes String,"
        var parameterInput = "( Vertexes,"
        var create = ("CREATE TABLE $name ")
        val createIndex = ("CREATE TABLE BEBRA_KILLER (name TEXT, type TEXT);")
        val insertIndex = ("INSERT INTO BEBRA_KILLER (name, type) VALUES('$name', 'Directed');")
        for (i in graph.entries){
            parameterCreate = "$parameterCreate V${i.key.toString()} INTEGER, "
            parameterInput = "$parameterInput V${i.key.toString()},"
        }
        parameterCreate = parameterCreate.slice(0.. parameterCreate.length - 3)
        parameterCreate = "$parameterCreate )"
        parameterInput = parameterInput.slice(0.. parameterInput.length - 2)
        parameterInput = "$parameterInput )"
        create = create + parameterCreate + ";"
        val connection = DriverManager.getConnection("$DB_DRIVER:storage.db")
            ?: throw SQLException("Cannot connect to database")
        val delTable = "DROP TABLE $name"
        val delIndexRec = "DELETE FROM BEBRA_KILLER WHERE name='$name';"
        connection.createStatement().also { stmt ->
            try {
                stmt.execute(delTable)
                println("Tables created or already exists")
            } catch (ex: Exception) {
                println("Cannot create table in database")
                println(ex)
            } finally {
                stmt.close()
            }
        }
        connection.createStatement().also { stmt ->
            try {
                stmt.execute(delIndexRec)
                println("Tables created or already exists")
            } catch (ex: Exception) {
                println("Cannot create table in database")
                println(ex)
            } finally {
                stmt.close()
            }
        }
        connection.createStatement().also { stmt ->
            try {
                stmt.execute(create)
                stmt.execute(createIndex)
                println("Tables created or already exists")
            } catch (ex: Exception) {
                println("Cannot create table in database")
                println(ex)
            } finally {
                stmt.close()
            }
        }
        connection.createStatement().also { stmt ->
            try {
                stmt.execute(insertIndex)
            } catch (ex: Exception) {
                println("Unsuccessful")
                println(ex)
            } finally {
                stmt.close()
            }
        }

        var request = "INSERT INTO $name $parameterInput VALUES "
        for (i in graph.entries){
            var record = "( 'V${i.key}', "
            val recList = emptyMap<V, String>().toMutableMap()
            for (j in graph.entries){
                recList[j.key] = "NULL"
            }
            for (j in i.value){
                recList[j.to] = j.weight.toString()
            }
            for (j in recList){
                record = "$record ${j.value}, "
            }
            record = record.slice(0.. record.length - 3)
            record = "$record ),"
            request = "$request $record"
        }
        request = request.slice(0.. request.length - 2)
        connection.createStatement().also { stmt ->
            try {
                stmt.execute(request)
            } catch (ex: Exception) {
                println("Unsuccessful")
                println(ex)
            } finally {
                stmt.close()
            }
        }
        println(request)
    }

    override fun addEdge(from: V, to: V, weight: Int) {
        if (vertexView[from] == null) return
        for (i in vertexView[from]?.edges!!) if (i.to == to) return
        val edgesCopy = vertexView[from]?.edges?.toMutableList()!!
        val edge = Edge(from, to, weight)
        edgesCopy.add(edge)
        vertexView[from]?.edges = edgesCopy
        edgesView.add(EdgeViewModel(edge, vertexView[edge.from]!!, vertexView[edge.to]!!))
        graphModel.addEdge(from, to, weight)
        updateView()
    }
}