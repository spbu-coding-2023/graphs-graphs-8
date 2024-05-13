package lib.graph


class Graph(){
    val vertices = arrayListOf<Int>()
    var size = vertices.size
        private set

    fun addVertex(){
        vertices.add(vertices.size)
        size+=1
    }

    fun forEach(action : (Int) -> Unit ) = vertices.forEach(action)

    operator fun iterator() : Iterator<Int> {
        return vertices.iterator()
    }
}
