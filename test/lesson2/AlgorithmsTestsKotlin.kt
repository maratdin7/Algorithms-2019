package lesson2

import org.junit.jupiter.api.Tag
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
    }

    @Test
    @Tag("Hard")
    fun testBaldaSearcher() {
        baldaSearcher { inputName, words -> baldaSearcher(inputName, words) }
    }
}