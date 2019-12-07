@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson5

import lesson5.impl.GraphBuilder
import java.lang.IllegalArgumentException
import java.util.*

/**
 * Эйлеров цикл.
 * Средняя
 *
 * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
 * Если в графе нет Эйлеровых циклов, вернуть пустой список.
 * Соседние дуги в списке-результате должны быть инцидентны друг другу,
 * а первая дуга в списке инцидентна последней.
 * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
 * Веса дуг никак не учитываются.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
 *
 * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
 * связного графа ровно по одному разу
 */
fun Graph.findEulerLoop(): List<Graph.Edge> {
    TODO()
}

/**
 * Минимальное остовное дерево.
 * Средняя
 *
 * Дан граф (получатель). Найти по нему минимальное остовное дерево.
 * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
 * вернуть любое из них. Веса дуг не учитывать.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ:
 *
 *      G    H
 *      |    |
 * A -- B -- C -- D
 * |    |    |
 * E    F    I
 * |
 * J ------------ K
 */
fun Graph.minimumSpanningTree(): Graph {
    TODO()
}

/**
 * Максимальное независимое множество вершин в графе без циклов.
 * Сложная
 *
 * Дан граф без циклов (получатель), например
 *
 *      G -- H -- J
 *      |
 * A -- B -- D
 * |         |
 * C -- F    I
 * |Boolean
 * E
 *
 * Найти в нём самое большое независимое множество вершин и вернуть его.
 * Никакая пара вершин в независимом множестве не должна быть связана ребром.
 *
 * Если самых больших множеств несколько, приоритет имеет то из них,
 * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
 *
 * В данном случае ответ (A, E, F, D, G, J)
 *
 * Если на входе граф с циклами, бросить IllegalArgumentException
 *
 * Эта задача может быть зачтена за пятый и шестой урок одновременно
 */

//////////////////////////////////////
// Временнная сложность O(n^2)      //  n кол-во вершин
// Сложность по памяти O(n^2)       //
//////////////////////////////////////

fun Graph.largestIndependentVertexSet(): Set<Graph.Vertex> =
    LargestIndependentVertexSet(this).findIndependentVertex()


class LargestIndependentVertexSet(private val graph: Graph) {
    private val vertices = graph.vertices
    private val size = vertices.size
    private val visited: MutableMap<Graph.Vertex, Sum> = mutableMapOf()
    private val set = mutableSetOf<Graph.Vertex>()

    private fun dfs(vertex: Graph.Vertex, prev: Graph.Vertex) {
        if (visited[vertex] != null && visited[vertex]!!.info == Info.UNKNOWN) throw IllegalArgumentException()
        visited[vertex] = Sum(mutableSetOf(), Info.NOW)

        for (v in graph.getNeighbors(vertex)) {
            if (v != prev)
                dfs(v, vertex)

            visited[vertex]!!.info = Info.UNKNOWN
        }
    }

    fun findIndependentVertex(): Set<Graph.Vertex> {
        if (vertices.isEmpty()) return setOf()
        for (vertex in vertices) {
            if (!visited.containsKey(vertex)) {
                dfs(vertex, vertex)
                findIndependentVertex(vertex)

                if (visited[vertex]!!.info != Info.UNKNOWN) {
                    if (visited[vertex]!!.info != Info.CHILD) set.add(vertex)
                    addToSet(vertex, vertex)
                }
            }
        }
        return set
    }

    private fun findIndependentVertex(v: Graph.Vertex): Int {

        if (visited[v]!!.info != Info.UNKNOWN) return visited[v]!!.sum.size

        visited[v]!!.info = Info.NOW
        var childSum = 0
        val childSet = mutableSetOf<Graph.Vertex>()
        var grandChildSum = 0
        val grandChildSet = mutableSetOf<Graph.Vertex>()

        for (child in graph.getNeighbors(v)) {      /*тк кол-во ребер = n - 1 (n кол-во вершин) в дереве с корнем в v,
                                                                     то слложность findIndependentVertex по времени n^2*/
            var previousSum = findIndependentVertex(child)

            if (visited[child]!!.info != Info.NOW) {
                childSum += previousSum
                childSet.add(child)

                for (grandChild in graph.getNeighbors(child)) {
                    previousSum = findIndependentVertex(grandChild)

                    if (visited[child]!!.info != Info.NOW) {
                        grandChildSum += previousSum
                        grandChildSet.add(grandChild)
                    }

                }
            }
        }

        return if ((grandChildSum + 1) >= childSum) {
            grandChildSet.add(v)
            visited[v] = Sum(grandChildSet, Info.VISIT)
            grandChildSum + 1
        } else {
            visited[v] = Sum(childSet, Info.CHILD)
            childSum
        }
    }

    private fun addToSet(vertex: Graph.Vertex, prev: Graph.Vertex) {
        for (v in visited[vertex]!!.sum) {
            if (v != prev) {
                if (visited[v]!!.info != Info.CHILD)
                    set.add(v)
                addToSet(v, vertex)
            }
        }
        visited[vertex]!!.info = Info.UNKNOWN
    }

    data class Sum(var sum: Set<Graph.Vertex>, var info: Info = Info.UNKNOWN)
    enum class Info {
        UNKNOWN,
        NOW,
        VISIT,
        GRANDCHILD,
        CHILD
    }
}


/**
 * Наидлиннейший простой путь.
 * Сложная
 *
 * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
 * Простым считается путь, вершины в котором не повторяются.
 * Если таких путей несколько, вернуть любой из них.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ: A, E, J, K, D, C, H, G, B, F, I
 */
fun Graph.longestSimplePath(): Path {
    TODO()
}