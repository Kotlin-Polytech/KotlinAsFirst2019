@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth
import lesson5.task1.canBuildFrom
import java.lang.NumberFormatException

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
val months = listOf(
    "",
    "января",
    "февраля",
    "марта",
    "апреля",
    "мая",
    "июня",
    "июля",
    "августа",
    "сентября",
    "октября",
    "ноября",
    "декабря"
)
fun dateStrToDigit(str: String): String {
    try {
        val parts = str.split(" ")
        if (parts.size != 3) {
            return ""
        }
        val day = parts[0].toIntOrNull()
        val month = parts[1]
        val year = parts[2].toIntOrNull()
        if (day == null || year == null) {
            return ""
        }
        val monthNum = months.indexOf(month)
        if (monthNum == -1) {
            return ""
        }
        if ((day < 1) || (day > daysInMonth(monthNum, year))) {
            return ""
        }
        return String.format("%02d.%02d.%d", day, monthNum, year)
    } catch (e: Error) {
        return ""
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    try {
        val parts = digital.split(".")
        if (parts.size != 3) {
            return ""
        }
        val day = parts[0].toIntOrNull()
        val month = parts[1].toIntOrNull()
        val year = parts[2].toIntOrNull()
        if (day == null || month == null || year == null) {
            return ""
        }
        if (month !in 1..12) {
            return ""
        }
        if (day > daysInMonth(month, year)) {
            return ""
        }
        return String.format("%d %s %d", day, months[month], year)
    } catch (e: Error) {
        return ""
    }
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    val clearPhone = phone.filter { it != ' ' }
    val plusAndDigit = """(\+\d)?"""
    val minusOrDigit = """(\d|((?<=\d)-+(?=\d)))"""
    val brackets = '(' + """\(""" + minusOrDigit + '+' + """\)""" + ')' + '?'
    val pattern = plusAndDigit + minusOrDigit + '*' + brackets + minusOrDigit + '+'
    return if (clearPhone.matches(
            Regex(pattern)
        )
    )
        clearPhone.filter { it !in listOf('-', '(', ')') }
    else ""
}


/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    if (!jumps.matches(Regex("""((\d+|[-%]) )*(\d+|[-%])"""))) {
        return -1
    }
    val parts = jumps.split(' ')
    var max = -1
    for (elem in parts) {
        val height = elem.toIntOrNull()
        if (height != null && height > max) {
            max = height
        }
    }
    return max
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    if (!jumps.matches(Regex("""((\d+ [+%-]+ )*(\d+ [+%-]+))"""))) {
        return -1
    }
    val parts = jumps.split(' ')
    var max = -1
    for (i in parts.indices step 2) {
        if ((parts[i].toInt() > max) && ('+' in parts[i + 1])) {
            max = parts[i].toInt()
        }
    }
    return max
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    require(expression.matches(Regex("""(\d+ [+-] )*\d+""")))
    val parts = expression.split(' ')
    var result = parts[0].toInt()
    for (i in 2 until parts.size step 2) {
        if (parts[i - 1] == "+") {
            result += parts[i].toInt()
        } else {
            result -= parts[i].toInt()
        }
    }
    return result
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    var count = -1
    var prevWord = ""
    for (word in str.split(' ')) {
        val currentWord = word.toLowerCase()
        if (currentWord == prevWord)
            return count
        count += prevWord.length
        prevWord = currentWord
        count++
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    if (!description.toLowerCase().matches(Regex("""(([^\s;]+ \d*.?\d+; )*([^\s;]+ \d*.?\d+))"""))) {
        return ""
    }
    val parts = description.split("; ")
    var max = -1.0
    var maxName = ""
    for (line in parts) {
        val stuff = line.split(' ')
        if (stuff[1].toDouble() < 0) {
            return ""
        }
        if (stuff[1].toDouble() > max) {
            max = stuff[1].toDouble()
            maxName = stuff[0]
        }
    }
    return maxName
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    if (roman.isEmpty()) return -1
    if (!roman.matches(Regex("""M*(CM|DC{0,3}|CD|C{0,3})?(XC|LX{0,3}|XL|X{0,3})?(IX|VI{0,3}|IV|I{0,3})?"""))) {
        return -1
    }
    val romans = listOf("I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M")
    val numbers = listOf(1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000)
    var index = roman.length - 1
    var result = 0
    while (index >= 0) {
        val doubleRoman = if (index > 0) {
            romans.indexOf(roman.slice(index - 1..index))
        } else -1
        if (doubleRoman != -1) {
            result += numbers[doubleRoman]
            index -= 2
        } else {
            result += numbers[romans.indexOf(roman[index].toString())]
            index -= 1
        }
    }
    return result
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun findNextBracket(line: String, pos: Int): Int {
    var count = 0
    for (i in pos until line.length) {
        when (line[i]) {
            '[' -> count++
            ']' -> count--
        }
        if (count == 0) return i
    }
    return -1
}

fun checkBrackets(line: String): Boolean {
    var count = 0
    for (chr in line) {
        when (chr) {
            '[' -> count++
            ']' -> count--
        }
        if (count < 0) return false
    }
    return count == 0
}

fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    require(canBuildFrom(listOf('-', '+', '<', '>', '[', ']', ' '), commands))
    require(checkBrackets(commands))
    val grid = mutableListOf<Int>()
    for (i in 1..cells) {
        grid.add(0)
    }
    var position = cells / 2
    var oPosition = 0
    var count = 0
    val order = mutableListOf<Int>()
    while ((oPosition < commands.length) && (count < limit)) {
        check(position in 0 until cells)
        when (commands[oPosition]) {
            '<' -> position--
            '>' -> position++
            '+' -> grid[position]++
            '-' -> grid[position]--
            '[' -> if (grid[position] == 0) oPosition = findNextBracket(commands, oPosition)
            else order.add(oPosition)
            ']' -> if (grid[position] != 0) oPosition = order.last()
            else order.remove(order.last())
        }
        count++
        oPosition++
    }
    check(position in 0 until cells)
    return grid
}
