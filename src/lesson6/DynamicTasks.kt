@file:Suppress("UNUSED_PARAMETER")

package lesson6

import java.io.File

/**
 * Наибольшая общая подпоследовательность.
 * Средняя
 *
 * Дано две строки, например "nematode knowledge" и "empty bottle".
 * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
 * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
 * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
 * Если общей подпоследовательности нет, вернуть пустую строку.
 * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
 * При сравнении подстрок, регистр символов *имеет* значение.
 */
fun longestCommonSubSequence(first: String, second: String): String {
    TODO()
}

/**
 * Наибольшая возрастающая подпоследовательность
 * Сложная
 *
 * Дан список целых чисел, например, [2 8 5 9 12 6].
 * Найти в нём самую длинную возрастающую подпоследовательность.
 * Элементы подпоследовательности не обязаны идти подряд,
 * но должны быть расположены в исходном списке в том же порядке.
 * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
 * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
 * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
 */

//////////////////////////////////////
// Временнная сложность O(n^2)      //
// Сложность по памяти O(n)         //
//////////////////////////////////////
fun longestIncreasingSubSequence(list: List<Int>): List<Int> {
    data class Node(var index: Int, var size: Int)

    val maxNodes = mutableListOf<Node>()
    for ((i, value) in list.withIndex()) {
        var maxNode = Node(i, 0)

        for (j in 0 until i)
            if (list[j] < value && maxNode.size < maxNodes[j].size)
                maxNode = Node(j, maxNodes[j].size)
        maxNode.size++
        maxNodes.add(maxNode)
    }
    var last = 0
    var current = maxNodes.firstOrNull() ?: return listOf()
    for ((i, node) in maxNodes.withIndex()) {
        if (current.size < node.size) {
            last = i
            current = node
        }
    }
    val ans = mutableListOf(list[last])
    while (current.size - 1 != 0) {
        ans.add(list[current.index])
        current = maxNodes[current.index]
    }
    return ans.asReversed()
}


/**
 * Самый короткий маршрут на прямоугольном поле.
 * Средняя
 *
 * В файле с именем inputName задано прямоугольное поле:
 *
 * 0 2 3 2 4 1
 * 1 5 3 4 6 2
 * 2 6 2 5 1 3
 * 1 4 3 2 6 2
 * 4 2 3 1 5 0
 *
 * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
 * В каждой клетке записано некоторое натуральное число или нуль.
 * Необходимо попасть из верхней левой клетки в правую нижнюю.
 * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
 * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
 *
 * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
 */
fun shortestPathOnField(inputName: String): Int {
    val list = File(inputName).readLines()
    val matrix: MutableMap<Int, MutableMap<Int, Int>> = mutableMapOf()
    val len = list.size
    var width = 0
    for (i in 0 until len) {
        matrix[i] = mutableMapOf()
        val listNum = list[i].trimIndent().split(" ")
        width = listNum.size
        for ((j, num) in listNum.withIndex()) {
            val inCell = num.toInt()
            var sumUp = Int.MAX_VALUE
            var sumLeft = Int.MAX_VALUE
            var sumAngle = Int.MAX_VALUE
            if (i != 0 && j != 0) sumAngle = inCell + (matrix[i - 1]!![j - 1] ?: 0)
            if (i != 0) sumUp = inCell + (matrix[i - 1]!![j] ?: 0)
            if (j != 0) sumLeft = inCell + (matrix[i]!![j - 1] ?: 0)
            matrix[i]!![j] = minOf(sumAngle, sumLeft, sumUp)
            if (matrix[i]!![j] == Int.MAX_VALUE) matrix[i]!![j] = inCell
        }
    }

    return if (len != 0 && width != 0) matrix[len - 1]!![width - 1]!! else 0
}

// Задачу "Максимальное независимое множество вершин в графе без циклов"
// смотрите в уроке 5