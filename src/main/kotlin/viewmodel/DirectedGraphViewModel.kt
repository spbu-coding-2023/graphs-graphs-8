package viewmodel

import Dijkstra
import androidx.compose.ui.graphics.Color
import model.graph.DirectedGraph
import model.graph.edges.Edge
import java.sql.DriverManager
import java.sql.SQLException

class DirectedGraphViewModel<V>(
    name: String,
    val graph: DirectedGraph<V> = DirectedGraph()
): AbstractGraphViewModel<V>(name, graph){
    val model
        get() = graph
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

    fun saveSQLite(){
        val DB_DRIVER = "jdbc:sqlite"
        var parameterCreate = "( Vertexes String,"
        var parameterInput = "( Vertexes,"
        var create = ("CREATE TABLE if not exists $name ")
        for (i in graph.entries){
            parameterCreate = "$parameterCreate V${i.key.toString()} INTEGER, "
            parameterInput = "$parameterInput V${i.key.toString()},"
        }
        parameterCreate = parameterCreate.slice(0.. parameterCreate.length - 3)
        parameterCreate = "$parameterCreate )"
        parameterInput = parameterInput.slice(0.. parameterInput.length - 2)
        parameterInput = "$parameterInput )"
        create = create + parameterCreate + ";"
        println(create)
        val connection = DriverManager.getConnection("$DB_DRIVER:$name.db")
            ?: throw SQLException("Cannot connect to database")
        connection.createStatement().also { stmt ->
            try {
                stmt.execute(create)
                println("Tables created or already exists")
            } catch (ex: Exception) {
                println("Cannot create table in database")
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
                println("YES")
            } catch (ex: Exception) {
                println("NOPE")
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