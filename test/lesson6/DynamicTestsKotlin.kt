package lesson6

import org.junit.jupiter.api.Tag
import kotlin.test.Test
import kotlin.test.assertEquals

class DynamicTestsKotlin : AbstractDynamicTests() {

    @Test
    @Tag("Normal")
    fun testLongestCommonSubSequence() {
        longestCommonSubSequence { first, second -> longestCommonSubSequence(first, second) }
    }

    @Test
    @Tag("Hard")
    fun testLongestIncreasingSubSequence() {
        longestIncreasingSubSequence { longestIncreasingSubSequence(it) }
        assertEquals(
            listOf(10, 13, 14, 15, 16, 17),
            longestIncreasingSubSequence(listOf(10, 5, 13, 14, 15, 6, 22, 15, 16, 17))
        )
        assertEquals(
            listOf(10),
            longestIncreasingSubSequence(listOf(10, 10, 10, 10))
        )
        assertEquals(
            listOf(10, 12, 13, 30),
            longestIncreasingSubSequence(listOf(10, 12, 13, 10, 11, 13, 30))
        )
    }

    @Test
    @Tag("Normal")
    fun testShortestPathOnField() {
        shortestPathOnField { shortestPathOnField(it) }
    }

}