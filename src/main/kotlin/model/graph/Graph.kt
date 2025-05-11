package model.graph

import java.sql.DriverManager
import java.sql.SQLException

abstract class Graph<V>() {
    protected val graph = mutableMapOf<V, MutableList<Edge<V>>>()
    private val DB_DRIVER = "jdbc:sqlite"

    val entries
        get() = graph.entries
    var isWeighted = false
        protected set
    var negativeWeights = false
        protected set

    val vertices
        get() = graph.keys

    val edges: List<Edge<V>>
        get() {
            val edges = mutableListOf<Edge<V>>()
            for (vertex in vertices) {
                val edgesOf = edgesOf(vertex)
                edges.addAll(edgesOf)
            }
            return edges.toList()
        }

    var size = graph.size
        private set

    fun addVertex(vertex: V) {
        graph.putIfAbsent(vertex, mutableListOf<Edge<V>>())
        size++
    }

    fun degreeOfVertex(vertex: V): Int {
        return graph[vertex]?.size ?: 0
    }

    fun saveSQLite(name: String, type: String, bdName: String) {
        var parameterCreate = "( Vertexes String,"
        var parameterInput = "( Vertexes,"
        var create = ("CREATE TABLE '$name'")
        val createIndex = ("CREATE TABLE IF NOT EXISTS Graphs (name TEXT, type TEXT);")
        val insertIndex = ("INSERT INTO Graphs (name, type) VALUES('$name', '$type');")
        for (i in graph.entries) {
            parameterCreate = "$parameterCreate V${i.key.toString()} INTEGER, "
            parameterInput = "$parameterInput V${i.key.toString()},"
        }
        parameterCreate = parameterCreate.slice(0..parameterCreate.length - 3)
        parameterCreate = "$parameterCreate )"
        parameterInput = parameterInput.slice(0..parameterInput.length - 2)
        parameterInput = "$parameterInput )"
        create = create + parameterCreate + ";"
        val connection = DriverManager.getConnection("$DB_DRIVER:$bdName.db")
            ?: throw SQLException("Cannot connect to database")
        val delTable = "DROP TABLE IF EXISTS '$name';"
        val delIndexRec = "DELETE FROM Graphs WHERE name='$name';"
        connection.createStatement().also { stmt ->
            try {
                stmt.execute(delTable)
            } catch (ex: Exception) {
                println("Can't delete old table of graph")
                println(ex)
            } finally {
                stmt.close()
            }
        }
        connection.createStatement().also { stmt ->
            try {
                stmt.execute(delIndexRec)
            } catch (ex: Exception) {
                println("Can't delete graph entry from Graphs")
                println(ex)
            } finally {
                stmt.close()
            }
        }
        connection.createStatement().also { stmt ->
            try {
                stmt.execute(createIndex)
                stmt.execute(create)
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
                println("Unsuccessful insert vertices in sqlite graph table")
                println(ex)
            } finally {
                stmt.close()
            }
        }

        var request = "INSERT INTO '$name' $parameterInput VALUES "
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

    }

    abstract fun addEdge(from: V, to: V, weight: Int = 1)

    fun edgesOf(from: V): MutableList<Edge<V>> {
        return graph[from] ?: mutableListOf()
    }

    fun forEach(action: (MutableList<Edge<V>>) -> Unit) {
        graph.forEach { number, list -> action(list) }
    }

    operator fun iterator() = graph.entries.iterator()
}
