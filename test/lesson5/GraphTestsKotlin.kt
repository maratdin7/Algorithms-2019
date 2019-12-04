package lesson5

import lesson5.impl.GraphBuilder
import org.junit.jupiter.api.Tag
import kotlin.test.Test
import kotlin.test.assertEquals

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
       // largestIndependentVertexSet { largestIndependentVertexSet() }
    }

    @Test
    fun test() {
        val cross = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            val d = addVertex("D")
            val e = addVertex("e")
            val f = addVertex("f")
            val g = addVertex("g")
            val h = addVertex("h")
            val i = addVertex("i")
            //val j = addVertex("j")

            addConnection(a, b)
            addConnection(a, c)
            addConnection(c, d)
            addConnection(c, e)
            addConnection(e, f)
            addConnection(b, g)
            addConnection(b, h)
            addConnection(b, i)
        }.build()
        assertEquals(6, cross.largestIndependentVertexSet())
    }

    @Test
    @Tag("Hard")
    fun testLongestSimplePath() {
        longestSimplePath { longestSimplePath() }
    }
}