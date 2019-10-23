@file:Suppress("UNUSED_PARAMETER")

package lesson2

import java.io.File

/**
 * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
 * Простая
 *
 * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
 * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
 *
 * 201
 * 196
 * 190
 * 198
 * 187
 * 194
 * 193
 * 185
 *
 * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
 * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
 * Вернуть пару из двух моментов.
 * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
 * Например, для приведённого выше файла результат должен быть Pair(3, 4)
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun optimizeBuyAndSell(inputName: String): Pair<Int, Int> {
    TODO()
}

/**
 * Задача Иосифа Флафия.
 * Простая
 *
 * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
 *
 * 1 2 3
 * 8   4
 * 7 6 5
 *
 * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
 * Человек, на котором остановился счёт, выбывает.
 *
 * 1 2 3
 * 8   4
 * 7 6 х
 *
 * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
 * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
 *
 * 1 х 3
 * 8   4
 * 7 6 Х
 *
 * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
 *
 * 1 Х 3
 * х   4
 * 7 6 Х
 *
 * 1 Х 3
 * Х   4
 * х 6 Х
 *
 * х Х 3
 * Х   4
 * Х 6 Х
 *
 * Х Х 3
 * Х   х
 * Х 6 Х
 *
 * Х Х 3
 * Х   Х
 * Х х Х
 *
 * Общий комментарий: решение из Википедии для этой задачи принимается,
 * но приветствуется попытка решить её самостоятельно.
 */
fun josephTask(menNumber: Int, choiceInterval: Int): Int {
    TODO()
}

/**
 * Наибольшая общая подстрока.
 * Средняя
 *
 * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
 * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
 * Если общих подстрок нет, вернуть пустую строку.
 * При сравнении подстрок, регистр символов *имеет* значение.
 * Если имеется несколько самых длинных общих подстрок одной длины,
 * вернуть ту из них, которая встречается раньше в строке first.
 */

//////////////////////////////////////
// Временнная сложность O(N^2)      //
// Сложность по памяти O(N)         //
//////////////////////////////////////

fun longestCommonSubstring(first: String, second: String): String {
    val list = MutableList(second.length) { 0 }
    var maxSubStrInFirst = -1
    var maxSubStr = 0

    for ((i, charFirst) in first.withIndex()) {
        var previous = 0

        for ((j, charSecond) in second.withIndex()) {
            val temp = previous
            previous = list[j]

            if (charFirst == charSecond) list[j] = temp + 1 else list[j] = 0

            if (list[j] > maxSubStr) {
                maxSubStr = list[j]
                maxSubStrInFirst = i
            }
        }
    }

    return if (maxSubStrInFirst != -1) {
        val startIndex = maxSubStrInFirst - maxSubStr + 1
        first.substring(startIndex, maxSubStrInFirst + 1)
    } else ""
}


/**
 * Число простых чисел в интервале
 * Простая
 *
 * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
 * Если limit <= 1, вернуть результат 0.
 *
 * Справка: простым считается число, которое делится нацело только на 1 и на себя.
 * Единица простым числом не считается.
 */

//////////////////////////////////////
// Временнная сложность O(N*sqrt(N))//
// Сложность по памяти O(1)         //
//////////////////////////////////////

fun calcPrimesNumber(limit: Int): Int {
    var res = 1
    for (i in 2..limit) res += isPrime(i)

    return if (limit < 2) 0 else res
}

fun isPrime(n: Int): Int {
    val sqrt = kotlin.math.sqrt(n.toDouble()).toInt()
    if (n % 2 == 0) return 0
    for (i in 1..sqrt step 2) {
        if (n % i == 0 && i != 1) return 0
    }
    return 1
}

/**
 * Балда
 * Сложная
 *
 * В файле с именем inputName задана матрица из букв в следующем формате
 * (отдельные буквы в ряду разделены пробелами):
 *
 * И Т Ы Н
 * К Р А Н
 * А К В А
 *
 * В аргументе words содержится множество слов для поиска, например,
 * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
 *
 * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
 * и вернуть множество найденных слов. В данном случае:
 * ТРАВА, КРАН, АКВА, НАРТЫ
 *
 * И т Ы Н     И т ы Н
 * К р а Н     К р а н
 * А К в а     А К В А
 *
 * Все слова и буквы -- русские или английские, прописные.
 * В файле буквы разделены пробелами, строки -- переносами строк.
 * Остальные символы ни в файле, ни в словах не допускаются.
 */

////////////////////////////////////
// Временнная сложность O(M*N^2)  // когда длина слова или количество одинаковых букв в массиве N^2
// Сложность по памяти O(N^2)     //
// M = количество слов            //
// N = размеры массива            //
////////////////////////////////////

fun baldaSearcher(inputName: String, words: Set<String>): Set<String> {
    val balda = Balda(inputName)
    return balda.findWords(words)
}

class Balda(val inputName: String) {

    private val matrix: MutableMatrixForBalda = MutableMatrixForBalda(File(inputName).readLines())

    fun findWords(words: Set<String>): Set<String> {
        val set = mutableSetOf<String>()
        var id = 0
        for (word in words) {
            val list = matrix.map[word[0]] ?: continue
            for (cell in list) {
                if (findWord(word, 0, id, cell)) set.add(word)
                id++
            }
        }
        return set
    }

    private fun findWord(word: String, index: Int, number: Int, cell: Cell): Boolean {
        if (word.length == index) return true

        return if (matrix.isCorrectCoordinates(cell.row, cell.column)) {
            val now = matrix[cell.row, cell.column]

            if (now.char == word[index] && now.number != number) {
                matrix[cell.row, cell.column].number = number
                findWord(word, index + 1, number, cell.row to cell.column + 1) ||
                        findWord(word, index + 1, number, cell.row to cell.column - 1) ||
                        findWord(word, index + 1, number, cell.row + 1 to cell.column) ||
                        findWord(word, index + 1, number, cell.row - 1 to cell.column)
            } else false
        } else false
    }


    private class MutableMatrixForBalda(input: List<String>) {
        private val matrix: MutableList<MutableList<CharAndNumber>>

        val map: MutableMap<Char, MutableList<Cell>>

        private var height: Int

        private var width: Int

        init {
            val matrix = mutableListOf<MutableList<CharAndNumber>>()
            val map = mutableMapOf<Char, MutableList<Cell>>()
            for ((i, line) in input.withIndex()) {
                matrix.add(mutableListOf())
                for (j in 0..line.length step 2) {
                    val char = line[j]
                    matrix[i].add(char to -1)
                    map[char] = map.getOrDefault(char, mutableListOf())
                    map[char]!!.add(i to j / 2)
                }
            }
            this.matrix = matrix
            this.height = matrix.size
            width = if (height == 0) 0 else matrix[0].size
            this.map = map
        }

        operator fun get(row: Int, column: Int): CharAndNumber = matrix[row][column]

        operator fun set(row: Int, column: Int, element: CharAndNumber) {
            matrix[row][column] = element
        }

        fun isCorrectCoordinates(row: Int, column: Int): Boolean =
            row in 0 until height && column in 0 until width
    }


    companion object {
        infix fun Char.to(number: Int): CharAndNumber = CharAndNumber(this, number)
        data class CharAndNumber(val char: Char, var number: Int)


        infix fun Int.to(number: Int): Cell = Cell(this, number)
        data class Cell(var row: Int, var column: Int)
    }
}