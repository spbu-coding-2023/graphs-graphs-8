import model.graph.edges.Edge

class StrongConnections<V>(){
    private val comparatorIV = emptyMap<Int, V>().toMutableMap()
    private val comparatorVI = emptyMap<V, Int>().toMutableMap()
    fun dfs(
        curr: Int, des: Int, adj: MutableMap<V, MutableList<V>>,
        vis: MutableList<Int>
    ): Boolean {
        if (curr == des) {
            return true
        }
        vis[curr] = 1
        for (x in adj[comparatorIV[curr]]!!) {
            if (vis[comparatorVI[x]!!] == 0) {
                if (dfs(comparatorVI[x]!!, des, adj, vis)) {
                    return true
                }
            }
        }
        return false
    }

    fun isPath(src: Int, des: Int, adj: MutableMap<V, MutableList<V>>): Boolean {
        val vis: MutableList<Int> = ArrayList(adj.size + 1)
        for (i in 0..adj.size) {
            vis.add(0)
        }
        return dfs(src, des, adj, vis)
    }

    fun findStrongConnections(graph:  MutableMap<V, MutableList<Edge<V>>>): List<List<V>> {
        for (i in graph.keys){
            comparatorIV[comparatorIV.size] = i
            comparatorVI[i] = comparatorVI.size
        }
        println(comparatorVI)

        println(comparatorIV)
        val adj = emptyMap<V, MutableList<V>>().toMutableMap()
        val n = comparatorIV.size
        val ans: MutableList<List<V>> = ArrayList()
        val is_scc: MutableList<Int> = ArrayList(comparatorIV.size + 1)
        for (i in 0..n) {
            is_scc.add(0)
        }
        for (i in comparatorVI.keys) {
            adj[i] = emptyList<V>().toMutableList()
        }

        for (edge in graph) {
            for (j in edge.value) {
                adj[j.from]?.add(j.to)
            }
        }

        for (i in 0..<n) {
            if (is_scc[i] == 0) {
                val scc: MutableList<V> = ArrayList()
                scc.add(comparatorIV[i]!!)

                for (j in i + 1..<n) {
                    if (is_scc[j] == 0 && isPath(i, j, adj)
                        && isPath(j, i, adj)
                    ) {
                        is_scc[j] = 1
                        scc.add(comparatorIV[j]!!)
                    }
                }
                ans.add(scc)
            }
        }
        return ans
    }
}