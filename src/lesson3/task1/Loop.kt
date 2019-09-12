@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import lesson1.task1.sqr
import kotlin.math.*

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result *= i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
    when {
        n == m -> 1
        n < 10 -> 0
        else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
    }

/**
 * Простая
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    var num = n
    var count = 0
    do {
        num /= 10
        count++
    } while (num != 0)
    return count
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    var a = 1
    var b = 1
    for (i in 2..n) {
        a += b
        a = a xor b
        b = a xor b
        a = a xor b
    }
    return a
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var num1 = m
    var num2 = n
    var k = 1
    var i = 2
    while ((num1 != 1) || (num2 != 1)) {
        var count1 = 0
        while (num1 % i == 0) {
            num1 /= i
            count1++
        }
        var count2 = 0
        while (num2 % i == 0) {
            num2 /= i
            count2++
        }
        for (count in 1..max(count1, count2)) k *= i
        i++
    }
    return k
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    val limit = floor(sqrt(n.toDouble())).toInt() + 1
    for (i in 2..limit) {
        if (n % i == 0) return i
    }
    return n
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int = n / minDivisor(n)

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    var num1 = n
    var num2 = m
    while (num1 != num2) {
        if (num1 > num2) {
            num1 -= num2
        } else {
            num2 -= num1
        }
    }
    return num1 == 1
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    val small = floor(sqrt(m.toDouble())).toInt()
    if (sqr(small) == m) return true
    return sqr(small + 1) in m..n
}

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    if (x == 1) return 0
    return when (x % 2) {
        0 -> 1 + collatzSteps(x / 2)
        else -> 1 + collatzSteps(3 * x + 1)
    }
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю.
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.sin и другие стандартные реализации функции синуса в этой задаче запрещается.
 */

fun coSin(rad: Double, i: Int, x: Double, eps: Double): Double {
    var current = x
    var count = i
    var result = 0.0
    var expression = 1.0
    var minus = 1
    var factorial = 1.0
    do {
        expression = current / factorial
        result += minus * expression
        current *= sqr(rad)
        factorial *= (count + 1) * (count + 2)
        count += 2
        minus *= -1
    } while (eps < abs(expression))
    return result
}

fun sin(x: Double, eps: Double): Double {
    var rad = x
    while (rad > 2 * PI) {
        rad -= 2 * PI
    }
    while (rad < 2 * PI) {
        rad += 2 * PI
    }
    return coSin(rad, 1, rad, eps)
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.cos и другие стандартные реализации функции косинуса в этой задаче запрещается.
 */
fun cos(x: Double, eps: Double): Double {
    var rad = x
    while (rad > 2 * PI) {
        rad -= 2 * PI
    }
    while (rad < 2 * PI) {
        rad += 2 * PI
    }
    return coSin(rad, 0, 1.0, eps)
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var num1 = n
    var num2 = 0
    var digit: Int
    while (num1 > 0) {
        num2 *= 10
        digit = num1 % 10
        num1 /= 10
        num2 += digit
    }
    return num2
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean {
    var num1 = n
    var num2 = revert(n)
    for (i in 1..(digitNumber(n) / 2)) {
        if ((num1 % 10) != (num2 % 10)) return false
        num1 /= 10
        num2 /= 10
    }
    return true
}

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var num = n
    val digit = num % 10
    num /= 10
    while (num > 0) {
        if (num % 10 != digit) return true
        num /= 10
    }
    return false
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    var i = 1
    var count = 0
    var num = 0
    while (count < n) {
        num = sqr(i)
        count += digitNumber(num)
        i++
    }
    for (j in 1..(count - n)) {
        num /= 10
    }
    return num % 10
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    if (n in 1..2) return 1
    var i = 1
    var count = 2
    var fib1 = 1
    var fib2 = 1
    while (count < n) {
        fib2 += fib1
        fib1 = fib1 xor fib2
        fib2 = fib1 xor fib2
        fib1 = fib1 xor fib2
        count += digitNumber(fib1)
        i++
    }
    for (j in 1..(count - n)) {
        fib1 /= 10
    }
    return fib1 % 10
}
