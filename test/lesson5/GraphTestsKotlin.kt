package lesson5

import lesson5.impl.GraphBuilder
import org.junit.jupiter.api.Tag
import java.lang.IllegalArgumentException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GraphTestsKotlin : AbstractGraphTests() {

    @Test
    @Tag("Normal")
    fun testFindEulerLoop() {
        findEulerLoop { findEulerLoop() }
    }

    @Test
    @Tag("Normal")
    fun testMinimumSpanningTree() {
        minimumSpanningTree { minimumSpanningTree() }
    }

    @Test
    @Tag("Hard")
    fun testLargestIndependentVertexSet() {
        largestIndependentVertexSet { largestIndependentVertexSet() }
    }

    @Test
    fun test() {
        val set = mutableSetOf<Graph.Vertex>()
        val graph = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            val d = addVertex("D")
            val e = addVertex("e")
            val f = addVertex("f")
            val g = addVertex("g")
            val h = addVertex("h")
            val i = addVertex("i")
            val j = addVertex("j")
            addConnection(a, j)
            addConnection(j, b)
            addConnection(a, c)
            addConnection(a, d)
            addConnection(a, e)
            addConnection(e, f)
            addConnection(b, g)
            addConnection(b, h)
            addConnection(b, i)
            set.addAll(listOf(c, i, g, h, e, d, j))
        }.build()
        assertEquals(set, graph.largestIndependentVertexSet())
        val graphWithCycle = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            val d = addVertex("D")

            addConnection(a, b)
            addConnection(b, c)
            addConnection(c, d)
            addConnection(b, d)
        }.build()
        assertFailsWith<IllegalArgumentException> { graphWithCycle.largestIndependentVertexSet()}
    }

    @Test
    @Tag("Hard")
    fun testLongestSimplePath() {
        longestSimplePath { longestSimplePath() }
    }
}