package viewmodel

import androidx.compose.ui.graphics.Color
import model.algos.FindCycle
import model.algos.Prim
import model.algos.findBridges
import model.graph.UndirectedGraph
import model.graph.edges.Edge
import java.sql.DriverManager
import java.sql.SQLException
import kotlin.random.Random

class UndirectedGraphViewModel<V>(
    name: String,
    val graph: UndirectedGraph<V> = UndirectedGraph()
) : AbstractGraphViewModel<V>(name, graph) {
    private val DB_DRIVER = "jdbc:sqlite"
    var inType = initType.Internal
    var initedGraph = false

    override fun addEdge(from: V, to: V, weight: Int) {
        val source: VertexViewModel<V>
        val destination: VertexViewModel<V>
        try {
            source = graphVM[from]!!
            destination = graphVM[to]!!
        } catch (e: Exception) {
            println("Can't add edge between $from and $to: one of them don't exist")
            return
        }
        for (edge in source.edges) if (edge.to == to) return
        for (edge in destination.edges) if (edge.from == from) return

        val edgeFromSource = Edge(from, to, weight)
        val edgeFromDestination = Edge(to, from, weight)
        val edgeFromSourceVM = EdgeViewModel(edgeFromSource, source, destination)
        val edgeFromDestinationVM = EdgeViewModel(edgeFromDestination, destination, source)
        source.edges.add(edgeFromSourceVM)
        destination.edges.add(edgeFromDestinationVM)
        graphModel.addEdge(from, to, weight)
    }

    override fun drawEdges(edges: Collection<Edge<V>>, color: Color) {
        for (edge in edges) {
            for (edgeVM in this.edgesVmOf(edge.from)) {
                if (edgeVM.to == edge.to) edgeVM.color = color
            }
            for (edgeVM in this.edgesVmOf(edge.to)) {
                if (edgeVM.to == edge.from) edgeVM.color = color
            }
        }
    }

    fun findMst() {
        if (size == 0) return
        val startVertex = graphModel.vertices.first()
        val result = Prim.findMst(graphModel as UndirectedGraph<V>, startVertex)
        drawEdges(result, Color.Magenta)
    }

    fun showFindCycles(startVertex: V) {
        val k = FindCycle
        for (i in k.findCycles(graph.matrix, startVertex)) {
            val col =
                Color(Random.nextInt(30, 230), Random.nextInt(30, 230), Random.nextInt(30, 230))
            for (j in i) {
                if (j in graphModel.vertices) {
                    graphVM[j]?.color = col
                    println(graphVM[j]?.color)
                    updateView()
                }
            }
        }
    }

    fun findBridges() {
        val result = findBridges(graphModel as UndirectedGraph<V>)
        drawEdges(result, Color.Yellow)
    }

    fun saveSQLite() {
        var parameterCreate = "( Vertexes String,"
        var parameterInput = "( Vertexes,"
        var create = ("CREATE TABLE $name ")
        val createIndex = ("CREATE TABLE BEBRA_KILLER (name TEXT, type TEXT);")
        val insertIndex = ("INSERT INTO BEBRA_KILLER (name, type) VALUES('$name', 'Undirected');")
        for (i in graph.entries) {
            parameterCreate = "$parameterCreate V${i.key.toString()} INTEGER, "
            parameterInput = "$parameterInput V${i.key.toString()},"
        }
        parameterCreate = parameterCreate.slice(0..parameterCreate.length - 3)
        parameterCreate = "$parameterCreate )"
        parameterInput = parameterInput.slice(0..parameterInput.length - 2)
        parameterInput = "$parameterInput )"
        create = create + parameterCreate + ";"
        val connection = DriverManager.getConnection("$DB_DRIVER:storage.db")
            ?: throw SQLException("Cannot connect to database")
        val delTable = "DROP TABLE $name"
        val delIndexRec = "DELETE FROM BEBRA_KILLER WHERE name='$name';"
        connection.createStatement().also { stmt ->
            try {
                stmt.execute(delTable)
                println("Table deleted")
            } catch (ex: Exception) {
                println("Cannot delete table in database")
                println(ex)
            } finally {
                stmt.close()
            }
        }
        connection.createStatement().also { stmt ->
            try {
                stmt.execute(delIndexRec)
                println("Table deleted")
            } catch (ex: Exception) {
                println("Cannot delete table in database")
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
        for (i in graph.entries) {
            var record = "( 'V${i.key}', "
            val recList = emptyMap<V, String>().toMutableMap()
            for (j in graph.entries) {
                recList[j.key] = "NULL"
            }
            for (j in i.value) {
                recList[j.to] = j.weight.toString()
            }
            for (j in recList) {
                record = "$record ${j.value}, "
            }
            record = record.slice(0..record.length - 3)
            record = "$record ),"
            request = "$request $record"
        }
        request = request.slice(0..request.length - 2)
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
}