package lesson1

import org.junit.jupiter.api.Tag
import java.io.File
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
        assertFalse("".matches(regex))

    }

    @Test
    fun testTimes() {
        "02:40:31 AM".testTimes()
        "12:59:59 AM".testTimes()
        "12:59:59 PM".testTimes()
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
        try {
            sortTemperatures("input/temp_in2", "temp.txt")
            assertFileContent(
                "temp.txt",
                """
                    -273.0
                    500.0
                """.trimIndent()
            )

            sortTemperatures("input/temp_in3", "temp.txt")
            assertFileContent(
                "temp.txt",
                """
                """.trimIndent()
            )
        } finally {
            File("temp.txt").delete()
        }
    }

    @Test
    @Tag("Normal")
    fun testSortSequence() {
        sortSequence { inputName, outputName -> sortSequence(inputName, outputName) }
        try {
            sortSequence("input/seq_in6", "temp.txt")
            assertFileContent(
                "temp.txt",
                """
                """.trimIndent()
            )

            sortSequence("input/seq_in7", "temp.txt")
            assertFileContent(
                "temp.txt",
                """
               2
               2
               2
               2
               2
               2
               2
               1
               1
               1
               1
               1
               1
               1
            """.trimIndent()
            )
        } finally {
            File("temp.txt").delete()
        }
    }

    @Test
    @Tag("Easy")
    fun testMergeArrays() {
        mergeArrays { first, second -> mergeArrays(first, second) }
    }
}
