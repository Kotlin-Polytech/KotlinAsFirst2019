@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import lesson3.task1.digitNumber
import lesson3.task1.revert
import java.io.File
import kotlin.math.max

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */

fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val text = File(inputName).readText().toLowerCase()
    val result = mutableMapOf<String, Int>()
    for (i in substrings) {
        result[i] = 0
    }
    for (sub in result.keys) {
        val low = sub.toLowerCase()
        var lastIndex = text.indexOf(low, 0)
        while (lastIndex != -1) {
            result[sub] = result[sub]!! + 1
            lastIndex = text.indexOf(low, lastIndex + 1)
        }
    }
    return result
}

/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val replacement = mapOf('ы' to 'и', 'Ы' to 'И', 'я' to 'а', 'Я' to 'А', 'ю' to 'у', 'Ю' to 'У')
    File(outputName).bufferedWriter().use {
        var trigger = false
        for (i in File(inputName).readText()) {
            if (trigger && i in replacement.keys) {
                it.write(replacement[i].toString())
                trigger = false
            } else {
                it.write(i.toString())
                trigger = i in "ЖжЧчШшЩщ"
            }
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun removeRedundantSpaces(inputLines: List<String>): List<String> {
    val outputLines = mutableListOf<String>()
    for (line in inputLines) {
        var firstIndex = 0
        for (i in line.indices) {
            if (line[i] != ' ') {
                firstIndex = i
                break
            }
        }
        var secondIndex = 0
        for (i in line.indices.reversed()) {
            if (line[i] != ' ') {
                secondIndex = i
                break
            }
        }
        if (firstIndex < secondIndex) outputLines.add(line.slice(firstIndex..secondIndex))
        else outputLines.add("")
    }
    return outputLines
}

fun centerFile(inputName: String, outputName: String) {
    val lines = removeRedundantSpaces(File(inputName).readLines())
    var maxLen = 0
    for (line in lines) {
        if (line.length > maxLen) {
            maxLen = line.length
        }
    }
    File(outputName).bufferedWriter().use {
        for (line in lines) {
            it.write(" ".repeat((maxLen - line.length) / 2) + line)
            it.newLine()
        }
    }
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */

fun alignFileByWidth(inputName: String, outputName: String) {
    val wordsInLines = removeRedundantSpaces(File(inputName).readLines()).map { it.split(Regex("""[ ]+""")) }
    var maxLen = 0
    for (words in wordsInLines) {
        val len = words.joinToString("").length + words.size - 1
        if (len > maxLen) maxLen = len
    }
    File(outputName).bufferedWriter().use {
        for (words in wordsInLines) {
            if (words.isEmpty()) {
                it.newLine()
                continue
            }
            if (words.size == 1) {
                it.write(words.first())
                it.newLine()
                continue
            }
            val spaceCount = maxLen - words.joinToString("").length
            val minSpacesPerWord = spaceCount / (words.size - 1)
            if (minSpacesPerWord * (words.size - 1) == spaceCount) {
                it.write(words.joinToString(" ".repeat(minSpacesPerWord)))
                it.newLine()
            } else {
                val maxSpacesPerWord = minSpacesPerWord + 1
                var count = 1
                while (count * maxSpacesPerWord + (words.size - 1 - count) * minSpacesPerWord != spaceCount) count++
                it.write(
                    words.slice(
                        0..count
                    ).joinToString(" ".repeat(maxSpacesPerWord)) + " ".repeat(minSpacesPerWord) + words.slice(
                        count + 1 until words.size
                    ).joinToString(" ".repeat(minSpacesPerWord))
                )
                it.newLine()
            }
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val wordCount = mutableMapOf<String, Int>()
    val text = File(inputName).readText().toLowerCase()
    val words = text.split(Regex("""[^a-zа-яё]+""")).filter { it.isNotEmpty() }.toList()
    val uniWords = words.toSet()
    for (word in uniWords) {
        wordCount[word] = words.count { it == word }
    }
    if (wordCount.size <= 20) return wordCount
    else {
        val result = mutableMapOf<String, Int>()
        var i = 0
        for ((word, count) in wordCount.toList().sortedByDescending { (_, value) -> value }.toMap()) {
            result[word] = count
            i++
            if (i == 20) {
                return result
            }
        }
    }
    return mapOf()
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val text = File(inputName).readLines().joinToString("\n")
    val lowDictionary = mutableMapOf<Char, String>()
    for ((chr, str) in dictionary) {
        lowDictionary[chr.toLowerCase()] = str.toLowerCase()
    }
    val keySet = lowDictionary.keys
    File(outputName).bufferedWriter().use {
        for (chr in text) {
            val lowChar = chr.toLowerCase()
            if (lowChar in keySet) {
                val replacement = lowDictionary[lowChar]!!
                if (replacement.isNotEmpty()) {
                    if (chr.isUpperCase()) {
                        it.write(replacement.first().toUpperCase() + replacement.slice(1 until replacement.length))
                    } else it.write(replacement)
                }
            } else it.write(chr.toString())
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    var words = File(inputName).readLines()
    words = words.filter { it.toLowerCase().toSet().size == it.length }
    val maxLen = words.map { it.length }.max() ?: -1
    File(outputName).writeText(words.filter { it.length == maxLen }.joinToString(", "))
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {

    val len = mapOf('i' to 1, 'b' to 2, 's' to 2)
    val prefixes = setOf('*', '~')

    fun isTag(line: String, index: Int) =
        when (line[index]) {
            '~' -> if (index + 1 < line.length && line[index + 1] == '~') 's' else null
            '*' -> if (index + 1 < line.length && line[index + 1] == '*') 'b' else 'i'
            else -> null
        }

    fun isValidTag(line: String, index: Int, wantedTag: Char): Boolean {
        var i = index + len[wantedTag]!!
        val stack = mutableListOf<Char>()
        while (i < line.length) {
            val tag = isTag(line, index)
            when (tag) {
                null -> i++
                wantedTag -> return stack.isEmpty()
                in stack -> {
                    stack.remove(tag)
                    i += len[tag] ?: 1
                }
                else -> {
                    if (isValidTag(line, index, tag)) {
                        stack.add(tag)
                        i += len[tag] ?: 1
                    } else i++
                }
            }
        }
        return false
    }

    File(outputName).bufferedWriter().use {
        var trigger = false
        it.write("<html><body>")
        for (line in File(inputName).readLines()) {
            if (line.isEmpty()) {
                if (trigger) {
                    it.write("</p>")
                    trigger = false
                }
            } else {
                if (!trigger) {
                    it.write("<p>")
                    trigger = true
                }
                val stack = mutableListOf<Char>()
                var index = 0
                while (index < line.length) {
                    if (line[index] !in prefixes) {
                        it.write(line[index].toString())
                        index++
                    } else {
                        val tag = isTag(line, index)
                        when (tag) {
                            null -> {
                                it.write(line[index].toString())
                                index++
                            }
                            in stack -> {
                                it.write("</$tag>")
                                stack.remove(tag)
                                index += len[tag] ?: 1
                            }
                            else -> {
                                if (isValidTag(line, index, tag)) {
                                    it.write("<$tag>")
                                    stack.add(tag)
                                    index += len[tag] ?: 1
                                } else {
                                    it.write(line[index].toString())
                                    index++
                                }
                            }
                        }
                    }
                }
            }
        }
        it.write("</p></body></html>")
    }
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Фрукты
<ol>
<li>Бананы</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {

    val result = lhv * rhv
    val len = digitNumber(result) + 1
    val lines = mutableListOf<String>()

    lines.add(" ".repeat(len - digitNumber(lhv)) + lhv.toString())
    lines.add('*' + " ".repeat(len - digitNumber(rhv) - 1) + rhv.toString())
    lines.add("-".repeat(len))

    var num = rhv
    var multipy = num % 10 * lhv
    num /= 10
    lines.add(" ".repeat(len - digitNumber(multipy)) + multipy.toString())

    var spaces = 2
    while (num > 0) {
        multipy = num % 10 * lhv
        num /= 10
        lines.add('+' + " ".repeat(len - digitNumber(multipy) - spaces) + multipy.toString())
        spaces++
    }

    lines.add("-".repeat(len))
    lines.add(" $result")

    File(outputName).writeText(lines.joinToString("\n"))
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {

    var currentNum = 0
    var reversed = revert(lhv)
    val steps = mutableListOf<Int>()
    while (reversed > 0) {
        currentNum = 10 * currentNum + reversed % 10
        reversed /= 10
        steps.add(currentNum)
        steps.add(currentNum - currentNum % rhv)
        currentNum %= rhv
    }

    val pairs = if (steps.chunked(2).any { it[1] != 0 }) {
        steps.chunked(2).drop(steps.chunked(2).indexOfFirst { it[1] != 0 })
    } else listOf(steps)

    File(outputName).bufferedWriter().use {

        val diff = if (digitNumber(pairs[0][0]) > digitNumber(pairs[0][1])) 1 else 0
        it.write(" ".repeat(1 - diff) + "$lhv | $rhv\n")
        var len = digitNumber(pairs[0][1]) + 1
        it.write('-' + pairs[0][1].toString() + " ".repeat(digitNumber(lhv) + 4 - len - diff) + lhv / rhv)
        it.newLine()
        it.write("-".repeat(len))
        it.newLine()

        var isModZero = pairs[0][0] == pairs[0][1]
        var defaultSpaces = len
        if (pairs.isNotEmpty()) {
            for (pair in pairs.drop(1)) {

                defaultSpaces++
                if (isModZero) it.write(" ".repeat(defaultSpaces - digitNumber(pair[0]) - 1) + '0' + pair[0].toString())
                else it.write(" ".repeat(defaultSpaces - digitNumber(pair[0])) + pair[0].toString())
                it.newLine()

                len = digitNumber(pair[1]) + 1
                it.write(" ".repeat(defaultSpaces - len) + '-' + pair[1].toString())
                it.newLine()

                it.write(" ".repeat(defaultSpaces - len) + "-".repeat(len))
                it.newLine()

                isModZero = pair[0] == pair[1]
            }
            val mod = lhv % rhv
            it.write(" ".repeat(defaultSpaces - 1) + mod.toString())
        }
    }
}

