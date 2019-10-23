package lesson2

import org.junit.jupiter.api.Tag
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals


class AlgorithmsTestsKotlin : AbstractAlgorithmsTests() {
    @Test
    @Tag("Easy")
    fun testOptimizeBuyAndSell() {
        optimizeBuyAndSell { optimizeBuyAndSell(it) }
    }

    @Test
    @Tag("Easy")
    fun testJosephTask() {
        josephTask { menNumber, choiceInterval -> josephTask(menNumber, choiceInterval) }
    }

    @Test
    @Tag("Normal")
    fun testLongestCommonSubstring() {
        longestCommonSubstring { first, second -> longestCommonSubstring(first, second) }

        assertEquals("werty", longestCommonSubstring("Ewerty", "Qwerty"))
        assertEquals("y", longestCommonSubstring("y", "yfdsjfkldsjflk"))
        assertEquals("", longestCommonSubstring("", ""))
        assertEquals("haa", longestCommonSubstring("haah", "haaah"))
        assertEquals("haa", longestCommonSubstring("haaah", "haahah"))
    }


    @Test
    @Tag("Easy")
    fun testCalcPrimesNumber() {
        calcPrimesNumber { calcPrimesNumber(it) }

        assertEquals(1, calcPrimesNumber(2))
        assertEquals(7, calcPrimesNumber(17))
    }

    @Test
    @Tag("Hard")
    fun testBaldaSearcher() {
        baldaSearcher { inputName, words -> baldaSearcher(inputName, words) }

        val str = setForBalda(98, 'T')
        val set = setOf(
            str,
            "A$str",
            "${str}A"
        )
        val words = mutableSetOf(
            str,
            "A$str",
            "${str}A",
            "${str}T"
        )

        assertEquals(
            set,
            baldaSearcher("input/balda_in4", words)
        )
    }

    private fun setForBalda(n: Int, char: Char): String {
        val str = StringBuilder("")
        for (i in 0 until n) str.append(char)
        return str.toString()
    }
}