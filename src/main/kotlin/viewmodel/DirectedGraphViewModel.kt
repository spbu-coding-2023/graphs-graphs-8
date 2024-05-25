package viewmodel

import androidx.compose.ui.graphics.Color
import model.algos.StrongConnections
import de.tudarmstadt.lt.cw.graph.ArrayBackedGraph
import de.tudarmstadt.lt.cw.graph.ArrayBackedGraphCW
import de.tudarmstadt.lt.cw.graph.Graph
import model.graph.DirectedGraph
import model.graph.Edge
import java.sql.DriverManager
import java.sql.SQLException
import kotlin.random.Random

class DirectedGraphViewModel<V>(
    name: String,
    val graph: DirectedGraph<V> = DirectedGraph()
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

        val edge = Edge(from, to, weight)
        val edgeVM = EdgeViewModel(edge, source, destination)
        source.edges.add(edgeVM)
        graphModel.addEdge(from, to, weight)
    }

    override fun drawEdges(edges: Collection<Edge<V>>, color: Color) {
        for (edge in edges) {
            for (edgeVM in this.edgesVmOf(edge.from)) {
                if (edgeVM.to == edge.to) edgeVM.color = color
            }
        }
    }

    fun chinaWhisperCluster() {
        val comparatorItoV = emptyMap<Int, V>().toMutableMap()
        val comparatorVtoI = emptyMap<V, Int>().toMutableMap()
        for (i in graph.vertices) {
            comparatorItoV[comparatorItoV.size] = i
            comparatorVtoI[i] = comparatorVtoI.size
        }
        val cwGraph: Graph<Int, Float> = ArrayBackedGraph(comparatorVtoI.size, comparatorVtoI.size)
        for (i in comparatorItoV) {
            cwGraph.addNode(i.key)
        }
        for (i in graph.edges) {
            cwGraph.addEdge(comparatorVtoI[i.from], comparatorVtoI[i.to], i.weight.toFloat())
        }

        val cw = ArrayBackedGraphCW(comparatorItoV.size)

        val findClusters = cw.findClusters(cwGraph)
        for (k in findClusters.values) {
            val col =
                Color(Random.nextInt(30, 230), Random.nextInt(30, 230), Random.nextInt(30, 230))
            for (j in k) {

                graphVM[comparatorItoV[j]]?.color = col
            }
        }
    }

    fun drawStrongConnections() {
        val strongConnections = StrongConnections<V>()
        for (component in strongConnections.findStrongConnections(graphModel)) {
            val col =
                Color(Random.nextInt(30, 230), Random.nextInt(30, 230), Random.nextInt(30, 230))
            for (vertex in component) {
                if (vertex in graphModel.vertices) {
                    graphVM[vertex]?.color = col
                }
            }
        }
    }

    fun saveSQLite() {
        var parameterCreate = "( Vertexes String,"
        var parameterInput = "( Vertexes,"
        var create = ("CREATE TABLE $name ")
        val createIndex = ("CREATE TABLE BEBRA_KILLER (name TEXT, type TEXT);")
        val insertIndex = ("INSERT INTO BEBRA_KILLER (name, type) VALUES('$name', 'Directed');")
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