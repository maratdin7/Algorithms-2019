@file:Suppress("UNUSED_PARAMETER")

package lesson1

import java.lang.IllegalArgumentException
import java.lang.StringBuilder
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Сортировка времён
 *
 * Простая
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
 * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
 *
 * Пример:
 *
 * 01:15:19 PM
 * 07:26:57 AM
 * 10:00:03 AM
 * 07:56:14 PM
 * 01:15:19 PM
 * 12:40:31 AM
 *
 * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
 * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
 *
 * 12:40:31 AM
 * 07:26:57 AM
 * 10:00:03 AM
 * 01:15:19 PM
 * 01:15:19 PM
 * 07:56:14 PM
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */

//////////////////////////////////////
// Временнная сложность O(N * logN) //
// Сложность по памяти O(N * logN)  //
//////////////////////////////////////

fun sortTimes(inputName: String, outputName: String) {
    val input = Paths.get(inputName)
    val output = Paths.get(outputName)
    val list = mutableListOf<Int>()
    val reader = Files.newBufferedReader(input)
    reader.use {
        var line = it.readLine()

        while (line != null) {
            val seconds = Times(line).toSeconds()
            list.add(seconds)
            line = it.readLine()
        }

    }
    val intArray = list.toIntArray()

    heapSort(intArray)

    val writer = Files.newBufferedWriter(output)
    writer.use {

        for (i in intArray) {
            it.write(Times.toTimes(i).times)
            it.newLine()
        }

    }
}

class Times(val times: String) {

    companion object ToTimes {
        fun toTimes(seconds: Int): Times {
            val secondsInMinute = 60
            val secondsInHour = secondsInMinute * secondsInMinute
            var times = StringBuilder("")
            var hours = seconds / secondsInHour
            val minutes = (seconds % secondsInHour) / secondsInMinute
            val seconds1 = seconds - secondsInHour * hours - secondsInMinute * minutes
            var pm = "AM"
            if (hours >= 12) {
                hours -= 12
                pm = "PM"
            }
            if (hours == 0) hours += 12
            times = times.append(hours.normalized(), ':', minutes.normalized(), ':', seconds1.normalized(), ' ', pm)
            return Times(times.toString())
        }

        private fun Int.normalized(): String = if (this < 10) "0$this" else this.toString()
    }

    private fun isTimes() {
        val regex = Regex("""((1[0-2])|(0\d)):(\d\d):(\d\d) ((AM)|(PM))""")
        if (!times.matches(regex)) throw IllegalArgumentException()
    }

    fun toSeconds(): Int {
        isTimes()
        val secondsInMinute = 60
        val secondsInHour = secondsInMinute * secondsInMinute
        val list = times.split(':', ' ')
        var seconds = list[1].toInt() * secondsInMinute + list[2].toInt()
        if (list[0] != "12") seconds += list[0].toInt() * secondsInHour
        if (list[3] == "PM") seconds += 12 * secondsInHour
        return seconds
    }
}

/**
 * Сортировка адресов
 *
 * Средняя
 *
 * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
 * где они прописаны. Пример:
 *
 * Петров Иван - Железнодорожная 3
 * Сидоров Петр - Садовая 5
 * Иванов Алексей - Железнодорожная 7
 * Сидорова Мария - Садовая 5
 * Иванов Михаил - Железнодорожная 7
 *
 * Людей в городе может быть до миллиона.
 *
 * Вывести записи в выходной файл outputName,
 * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
 * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
 *
 * Железнодорожная 3 - Петров Иван
 * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
 * Садовая 5 - Сидоров Петр, Сидорова Мария
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortAddresses(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сортировка температур
 *
 * Средняя
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
 * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
 * Например:
 *
 * 24.7
 * -12.6
 * 121.3
 * -98.4
 * 99.5
 * -12.6
 * 11.0
 *
 * Количество строк в файле может достигать ста миллионов.
 * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
 * Повторяющиеся строки сохранить. Например:
 *
 * -98.4
 * -12.6
 * -12.6
 * 11.0
 * 24.7
 * 99.5
 * 121.3
 */
fun sortTemperatures(inputName: String, outputName: String) {

    val input = Paths.get(inputName)
    val output = Paths.get(outputName)
    val list = MutableList<StringBuilder?>(8000) { null }


    val reader = Files.newBufferedReader(input)
    reader.use {
        var line = it.readLine()
        while (line != null) {
            val index = ((line.toDouble() * 10).toInt() + 2730)
            if (list[index] == null) list[index] = StringBuilder("").append(line, '\n')
            else list[index]!!.append(line, '\n')
            line = it.readLine()
        }
    }
    val writer = Files.newBufferedWriter(output)
    writer.use {
        for (i in list) {
            if (i != null) {
                it.write(i.toString())
            }
        }
    }
}

/**
 * Сортировка последовательности
 *
 * Средняя
 * (Задача взята с сайта acmp.ru)
 *
 * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
 *
 * 1
 * 2
 * 3
 * 2
 * 3
 * 1
 * 2
 *
 * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
 * а если таких чисел несколько, то найти минимальное из них,
 * и после этого переместить все такие числа в конец заданной последовательности.
 * Порядок расположения остальных чисел должен остаться без изменения.
 *
 * 1
 * 3
 * 3
 * 1
 * 2
 * 2
 * 2
 */
fun sortSequence(inputName: String, outputName: String) {
    val input = Paths.get(inputName)
    val output = Paths.get(outputName)
    val map = mutableMapOf<Int, Int>()
    val list = mutableListOf<Int>()
    val reader = Files.newBufferedReader(input, StandardCharsets.UTF_8)
    reader.use {
        var line = it.readLine()
        while (line != null) {
            val num = line.toInt()
            list.add(num)
            map[num] = map.getOrDefault(num, 0) + 1
            line = it.readLine()
        }
    }
    var minPair = Pair(Int.MAX_VALUE, 0)

    for ((key, value) in map) {
        when {
            value > minPair.second -> minPair = Pair(key, value)
            value == minPair.second && key < minPair.first -> minPair = Pair(key, value)
        }
    }
    val writer = Files.newBufferedWriter(output, StandardCharsets.UTF_8)
    writer.use {
        for (i in list) {
            if (i != minPair.first) {
                it.write(i.toString())
                it.newLine()
            }
        }
        var i = 0
        while (i < minPair.second) {
            it.write(minPair.first.toString())
            it.newLine()
            i++
        }
    }
}


/**
 * Соединить два отсортированных массива в один
 *
 * Простая
 *
 * Задан отсортированный массив first и второй массив second,
 * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
 * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
 *
 * first = [4 9 15 20 28]
 * second = [null null null null null 1 3 9 13 18 23]
 *
 * Результат: second = [1 3 4 9 9 13 15 20 23 28]
 */
fun <T : Comparable<T>> mergeArrays(first: Array<T>, second: Array<T?>) {
    TODO()
}

