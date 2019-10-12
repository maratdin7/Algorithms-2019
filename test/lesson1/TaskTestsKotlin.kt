package lesson1

import org.junit.jupiter.api.Tag
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TaskTestsKotlin : AbstractTaskTests() {

    @Test
    @Tag("Easy")
    fun testSortTimes() {
        sortTimes { inputName, outputName -> sortTimes(inputName, outputName) }
    }

    @Test
    fun testRegex() {
        val regex = Regex("""((1[0-2])|(0\d)):(\d\d):(\d\d) ((AM)|(PM))""")
        assertTrue("02:40:31 AM".matches(regex))
        assertFalse("13:40:31 PM".matches(regex))
        assertFalse("02:40:31 AM fdkjfkdj".matches(regex))
    }

    @Test
    fun testTimes() {
        "02:40:31 AM".testTimes()
        "12:00:00 AM".testTimes()
        "12:00:00 PM".testTimes()
    }

    private fun String.testTimes() {
        val seconds = Times(this).toSeconds()
        assertEquals(this, Times.toTimes(seconds).times)
    }

    @Test
    @Tag("Normal")
    fun testSortAddresses() {
        sortAddresses { inputName, outputName -> sortAddresses(inputName, outputName) }
    }

    @Test
    @Tag("Normal")
    fun testSortTemperatures() {
        sortTemperatures { inputName, outputName -> sortTemperatures(inputName, outputName) }
    }

    @Test
    @Tag("Normal")
    fun testSortSequence() {
        sortSequence { inputName, outputName -> sortSequence(inputName, outputName) }
    }

    @Test
    @Tag("Easy")
    fun testMergeArrays() {
        mergeArrays { first, second -> mergeArrays(first, second) }
    }
}
