package viewmodel.io


import mu.KotlinLogging
import org.neo4j.driver.AuthTokens
import org.neo4j.driver.GraphDatabase
import org.neo4j.driver.TransactionContext
import viewmodel.DirectedGraphViewModel
import viewmodel.GraphType
import viewmodel.MainScreenViewModel
import viewmodel.UndirectedGraphViewModel
import viewmodel.graph.AbstractGraphViewModel
import java.io.Closeable

// penguin-carlo-ceramic-invite-wheel-2163

private val logger = KotlinLogging.logger { }

class Neo4jRepository<V>(uri: String, user: String, password: String) : Closeable {

    val driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password))
    val session = driver.session()

    fun saveGraph(graphVM: AbstractGraphViewModel<V>) {
        val graphName = graphVM.name
        val graphType = graphVM.graphType
        // remove old graph if exist
        session.executeWrite { tx ->
            removeGraph(tx, graphName)
        }
        // create graph
        session.executeWrite { tx ->
            tx.run("CREATE (graph: Graph {name: '$graphName', type: '$graphType'});")
        }
        session.executeWrite { tx ->
            tx.run("CREATE CONSTRAINT uniqueGraphName IF NOT EXISTS FOR (graph: Graph) REQUIRE (graph.value) IS UNIQUE;")
            tx.run("CREATE INDEX IF NOT EXISTS FOR (graph: Graph) ON (graph.name);")
        }
        // create vertices in a graph
        session.executeWrite { tx ->
            for (vertexVM in graphVM.verticesVM) {
                tx.run(
                    "CREATE (v: `$graphName` {value : \$vertexValue});",
                    mapOf("vertexValue" to vertexVM.vertex.toString())
                )
            }
        }
        session.executeWrite { tx ->
            tx.run("CREATE CONSTRAINT IF NOT EXISTS FOR (n: `$graphName`) REQUIRE (n.value) IS UNIQUE;")
            tx.run("CREATE INDEX IF NOT EXISTS FOR (n: `$graphName`) ON (n.value);")
        }
        // create edges in a graph
        session.executeWrite { tx ->
            for (edgeVM in graphVM.edgesVM) {
                tx.run(
                    "MATCH (v1: `$graphName`) WHERE v1.value = \$vertex1 \n" +
                            "MATCH (v2: `$graphName`) WHERE v2.value = \$vertex2 \n" +
                            "CREATE (v1)-[:Edge {weight: \$edgeWeight}]->(v2)",
                    mapOf(
                        "vertex1" to edgeVM.from.toString(),
                        "vertex2" to edgeVM.to.toString(),
                        "edgeWeight" to edgeVM.weight.toString()
                    )
                )
            }
        }
    }

    fun removeGraph(name: String) {
        session.executeWrite { tx ->
            removeGraph(tx, name)
        }
    }

    private fun removeGraph(
        tx: TransactionContext,
        name: String,
    ) {
        val check = tx.run(
            "MATCH (graph: Graph) WHERE graph.name = '$name' RETURN graph.name as name;"
        ).list()

        if (check.size == 0) return
        if (check.size > 1) {
            logger.error { "More than 1 graph with same name and type exist in neo4j" }
            return
        }
        tx.run(
            "OPTIONAL MATCH (n: `$name`)" +
                    "OPTIONAL MATCH (graph: Graph) WHERE graph.name = '$name'" +
                    "DETACH DELETE n, graph;"
        )
    }

    fun getAllGraphs(): List<AbstractGraphViewModel<String>> {
        val graphs = mutableListOf<AbstractGraphViewModel<String>>()
        val names = session.executeRead { tx ->
            val result = tx.run("MATCH (graph: Graph) RETURN graph.name as name")
            return@executeRead result.list() { it.asMap()["name"].toString() }
        }
        for (name in names) {
            graphs.add(getGraph(name))
        }
        return graphs
    }

    fun getGraph(graphName: String): AbstractGraphViewModel<String> {
        val graphData = session.executeRead() { tx ->
            val result =
                tx.run("MATCH (graph: Graph) WHERE graph.name = '$graphName' RETURN graph.name as name, graph.type as type")
            return@executeRead result.list().first().asMap()
        }
        val graph: AbstractGraphViewModel<String>
        if (graphData["type"] == "Undirected") {
            graph = UndirectedGraphViewModel(graphData["name"].toString())
        } else if (graphData["type"] == "Directed") {
            graph = DirectedGraphViewModel(graphData["name"].toString())
        } else throw IllegalArgumentException("graph type in db isn't correct")
        val vertices = session.executeRead() { tx ->
            val result = tx.run("MATCH (v: `$graphName`) RETURN v.value as value")
            return@executeRead result.list() { it.asMap()["value"].toString() }
        }
        for (vertex in vertices) {
            graph.addVertex(vertex)
        }
        session.executeRead { tx ->
            for (vertex in vertices) {
                val destinations =
                    tx.run("MATCH (v: `$graphName` {value: '$vertex'})-[:Edge]->(n) RETURN n.value as destination")
                        .list() { it.asMap()["destination"].toString() }
                for (destination in destinations) {
                    graph.addEdge(vertex, destination)
                }
            }
        }
        return graph
    }

    fun initGraphList(mainScreenViewModel: MainScreenViewModel) {
        val graphs = session.executeRead { tx ->
            val result =
                tx.run("MATCH (graph: Graph) RETURN graph.name as name, graph.type as type")
            return@executeRead result.list() { it.asMap() }
        }
        for (graph in graphs) {
            val graphType =
                if (graph["type"] == "Undirected") GraphType.Undirected else GraphType.Directed
            mainScreenViewModel.addGraph(graph["name"].toString(), graphType)
        }
    }

    fun clearDB() {
        session.executeWrite { tx ->
            tx.run("MATCH (n) DETACH DELETE n")
        }
    }

    override fun close() {
        session.close()
        driver.close()
    }
}