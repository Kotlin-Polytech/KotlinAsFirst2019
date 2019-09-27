package lesson5.task1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class Tests {
    @Test
    @Tag("Example")
    fun shoppingListCostTest() {
        val itemCosts = mapOf(
            "Хлеб" to 50.0,
            "Молоко" to 100.0
        )
        assertEquals(
            150.0,
            shoppingListCost(
                listOf("Хлеб", "Молоко"),
                itemCosts
            )
        )
        assertEquals(
            150.0,
            shoppingListCost(
                listOf("Хлеб", "Молоко", "Кефир"),
                itemCosts
            )
        )
        assertEquals(
            0.0,
            shoppingListCost(
                listOf("Хлеб", "Молоко", "Кефир"),
                mapOf()
            )
        )
    }

    @Test
    @Tag("Example")
    fun filterByCountryCode() {
        val phoneBook = mutableMapOf(
            "Quagmire" to "+1-800-555-0143",
            "Adam's Ribs" to "+82-000-555-2960",
            "Pharmakon Industries" to "+1-800-555-6321"
        )

        filterByCountryCode(phoneBook, "+1")
        assertEquals(2, phoneBook.size)

        filterByCountryCode(phoneBook, "+1")
        assertEquals(2, phoneBook.size)

        filterByCountryCode(phoneBook, "+999")
        assertEquals(0, phoneBook.size)
    }

    @Test
    @Tag("Example")
    fun removeFillerWords() {
        assertEquals(
            "Я люблю Котлин".split(" "),
            removeFillerWords(
                "Я как-то люблю Котлин".split(" "),
                "как-то"
            )
        )
        assertEquals(
            "Я люблю Котлин".split(" "),
            removeFillerWords(
                "Я как-то люблю таки Котлин".split(" "),
                "как-то",
                "таки"
            )
        )
        assertEquals(
            "Я люблю Котлин".split(" "),
            removeFillerWords(
                "Я люблю Котлин".split(" "),
                "как-то",
                "таки"
            )
        )
    }

    @Test
    @Tag("Example")
    fun buildWordSet() {
        assertEquals(
            buildWordSet("Я люблю Котлин".split(" ")),
            mutableSetOf("Я", "люблю", "Котлин")
        )
        assertEquals(
            buildWordSet("Я люблю люблю Котлин".split(" ")),
            mutableSetOf("Котлин", "люблю", "Я")
        )
        assertEquals(
            buildWordSet(listOf()),
            mutableSetOf<String>()
        )
    }

    @Test
    @Tag("Easy")
    fun buildGrades() {
        assertEquals(
            mapOf<Int, List<String>>(),
            buildGrades(mapOf())
        )
        assertEquals(
            mapOf(5 to listOf("Михаил", "Семён"), 3 to listOf("Марат")),
            buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
                .mapValues { (_, v) -> v.sorted() }
        )
        assertEquals(
            mapOf(3 to listOf("Марат", "Михаил", "Семён")),
            buildGrades(mapOf("Марат" to 3, "Семён" to 3, "Михаил" to 3))
                .mapValues { (_, v) -> v.sorted() }
        )
    }

    @Test
    @Tag("Easy")
    fun containsIn() {
        assertTrue(containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")))
        assertFalse(containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")))
        assertFalse(containsIn(mapOf("a" to "z"), mapOf("b" to "sweet")))
    }

    @Test
    @Tag("Easy")
    fun subtractOf() {
        val from = mutableMapOf("a" to "z", "b" to "c")

        subtractOf(from, mapOf())
        assertEquals(from, mapOf("a" to "z", "b" to "c"))

        subtractOf(from, mapOf("b" to "z"))
        assertEquals(from, mapOf("a" to "z", "b" to "c"))

        subtractOf(from, mapOf("a" to "z"))
        assertEquals(from, mapOf("b" to "c"))
    }

    @Test
    @Tag("Easy")
    fun whoAreInBoth() {
        assertEquals(
            emptyList<String>(),
            whoAreInBoth(emptyList(), emptyList())
        )
        assertEquals(
            listOf("Marat"),
            whoAreInBoth(listOf("Marat", "Mikhail"), listOf("Marat", "Kirill"))
        )
        assertEquals(
            emptyList<String>(),
            whoAreInBoth(listOf("Marat", "Mikhail"), listOf("Sveta", "Kirill"))
        )
    }

    @Test
    @Tag("Normal")
    fun mergePhoneBooks() {
        assertEquals(
            mapOf("Emergency" to "112"),
            mergePhoneBooks(
                mapOf("Emergency" to "112"),
                mapOf("Emergency" to "112")
            )
        )
        assertEquals(
            mapOf("Emergency" to "112", "Police" to "02"),
            mergePhoneBooks(
                mapOf("Emergency" to "112"),
                mapOf("Emergency" to "112", "Police" to "02")
            )
        )
        assertEquals(
            mapOf("Emergency" to "112, 911", "Police" to "02"),
            mergePhoneBooks(
                mapOf("Emergency" to "112"),
                mapOf("Emergency" to "911", "Police" to "02")
            )
        )
        assertEquals(
            mapOf("Emergency" to "112, 911", "Fire department" to "01", "Police" to "02"),
            mergePhoneBooks(
                mapOf("Emergency" to "112", "Fire department" to "01"),
                mapOf("Emergency" to "911", "Police" to "02")
            )
        )
    }

    @Test
    @Tag("Normal")
    fun averageStockPrice() {
        assertEquals(
            mapOf<String, Double>(),
            averageStockPrice(listOf())
        )
        assertEquals(
            mapOf("MSFT" to 100.0, "NFLX" to 40.0),
            averageStockPrice(listOf("MSFT" to 100.0, "NFLX" to 40.0))
        )
        assertEquals(
            mapOf("MSFT" to 150.0, "NFLX" to 40.0),
            averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
        )
        assertEquals(
            mapOf("MSFT" to 150.0, "NFLX" to 45.0),
            averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0, "NFLX" to 50.0))
        )
    }

    @Test
    @Tag("Normal")
    fun findCheapestStuff() {
        assertNull(
            findCheapestStuff(
                mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
                "торт"
            )
        )
        assertEquals(
            "Мария",
            findCheapestStuff(
                mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
                "печенье"
            )
        )
    }

    @Test
    @Tag("Normal")
    fun canBuildFrom() {
        assertFalse(canBuildFrom(emptyList(), "foo"))
        assertTrue(canBuildFrom(listOf('a', 'b', 'o'), "baobab"))
        assertFalse(canBuildFrom(listOf('a', 'm', 'r'), "Marat"))
    }

    @Test
    @Tag("Normal")
    fun extractRepeats() {
        assertEquals(
            emptyMap<String, Int>(),
            extractRepeats(emptyList())
        )
        assertEquals(
            mapOf("a" to 2),
            extractRepeats(listOf("a", "b", "a"))
        )
        assertEquals(
            emptyMap<String, Int>(),
            extractRepeats(listOf("a", "b", "c"))
        )
    }

    @Test
    @Tag("Normal")
    fun hasAnagrams() {
        assertFalse(hasAnagrams(emptyList()))
        assertTrue(hasAnagrams(listOf("рот", "свет", "тор")))
        assertFalse(hasAnagrams(listOf("рот", "свет", "код", "дверь")))
    }

    @Test
    @Tag("Hard")
    fun propagateHandshakes() {
        assertEquals(
            mapOf(
                "Marat" to setOf("Mikhail", "Sveta"),
                "Sveta" to setOf("Mikhail"),
                "Mikhail" to setOf()
            ),
            propagateHandshakes(
                mapOf(
                    "Marat" to setOf("Sveta"),
                    "Sveta" to setOf("Mikhail")
                )
            )
        )
        assertEquals(
            mapOf(
                "Marat" to setOf("Mikhail", "Sveta"),
                "Sveta" to setOf("Marat", "Mikhail"),
                "Mikhail" to setOf("Sveta", "Marat")
            ),
            propagateHandshakes(
                mapOf(
                    "Marat" to setOf("Mikhail", "Sveta"),
                    "Sveta" to setOf("Marat"),
                    "Mikhail" to setOf("Sveta")
                )
            )
        )
    }

    @Test
    @Tag("Hard")
    fun findSumOfTwo() {
        assertEquals(
            Pair(1, 2),
            findSumOfTwo(listOf(1, 0, 0, 0, 0, 0), 0)
        )
        assertEquals(
            Pair(-1, -1),
            findSumOfTwo(listOf(1, 2, 3), 6)
        )
        assertEquals(
            Pair(-1, -1),
            findSumOfTwo(emptyList(), 1)
        )
        assertEquals(
            Pair(0, 2),
            findSumOfTwo(listOf(1, 2, 3), 4)
        )
        assertEquals(
            Pair(0, 1),
            findSumOfTwo(listOf(0, 0), 0)
        )
        assertEquals(
            Pair(-1, -1),
            findSumOfTwo(
                listOf
                    (
                    0,
                    0,
                    0,
                    2,
                    0,
                    0,
                    0,
                    0,
                    0,
                    19982,
                    0,
                    0,
                    0,
                    0,
                    609,
                    48410,
                    0,
                    17750,
                    0,
                    40700,
                    0,
                    21006,
                    36550,
                    40699,
                    13094,
                    12153,
                    5,
                    49186,
                    414,
                    40700,
                    0,
                    40699,
                    13151,
                    0,
                    0,
                    0,
                    15080,
                    33290,
                    12194,
                    20522,
                    40699,
                    7100,
                    0,
                    0,
                    7972,
                    32346,
                    19591,
                    0,
                    1,
                    16470,
                    40699,
                    37179,
                    40700,
                    18170,
                    25832,
                    40699,
                    9032,
                    40700,
                    21386,
                    0,
                    40699,
                    40700,
                    40699,
                    40699,
                    40700,
                    0,
                    0,
                    40700,
                    43028,
                    39865,
                    13285,
                    1,
                    40700,
                    0,
                    26959,
                    28293,
                    40700,
                    14459,
                    27557,
                    1,
                    717,
                    40700,
                    45394,
                    40700,
                    42862,
                    40700,
                    0,
                    40699,
                    17107,
                    0,
                    10670,
                    46018,
                    40700,
                    1,
                    14247,
                    45696,
                    1,
                    40700,
                    1,
                    40699,
                    40699,
                    25798,
                    25564,
                    26565,
                    40699,
                    14967,
                    12879,
                    27362,
                    40699,
                    49528,
                    29902,
                    40699,
                    40700,
                    17388,
                    4938,
                    24136,
                    40700,
                    0,
                    40699,
                    0,
                    1,
                    40699,
                    1,
                    40700,
                    26518,
                    20040,
                    40700,
                    0,
                    40700,
                    0,
                    40699,
                    0,
                    21139,
                    0,
                    40700,
                    35028,
                    1,
                    1,
                    35114,
                    40699,
                    40700,
                    34320,
                    40699,
                    0,
                    40700,
                    40699,
                    1,
                    0,
                    0,
                    40699,
                    40699,
                    0,
                    40700,
                    24205,
                    38742,
                    19143,
                    25165,
                    2733,
                    45447,
                    40700,
                    0,
                    0,
                    21607,
                    1,
                    40699,
                    1,
                    2812,
                    34984,
                    40700,
                    1,
                    40699,
                    1,
                    1,
                    40699,
                    20925,
                    48541,
                    0,
                    0,
                    40699,
                    40700,
                    1,
                    0,
                    40700,
                    19058,
                    40699,
                    37022,
                    1,
                    40699,
                    39299,
                    16775,
                    33603,
                    40699,
                    0,
                    40700,
                    12955,
                    11673,
                    0,
                    2826,
                    40700,
                    25744,
                    18663,
                    40699,
                    45786,
                    1,
                    8004,
                    40700,
                    760,
                    27150,
                    49337,
                    1,
                    0,
                    1,
                    18612,
                    0,
                    17392,
                    1,
                    40699,
                    7213,
                    1,
                    36097,
                    40700,
                    31148,
                    1,
                    49851,
                    0,
                    40700,
                    40700,
                    37701,
                    49554,
                    37272,
                    4534,
                    41518,
                    40700,
                    0,
                    20388,
                    40699,
                    1995,
                    40700,
                    40699,
                    36418,
                    40700,
                    27233,
                    0,
                    0,
                    18260,
                    40699,
                    46285,
                    28214,
                    40700,
                    30734,
                    20235,
                    19551,
                    4523,
                    49643,
                    42425,
                    0,
                    1,
                    40699,
                    49203,
                    10852,
                    0,
                    33870,
                    19510,
                    40699,
                    24440,
                    0,
                    40699,
                    0,
                    8327,
                    2215,
                    26959,
                    40699,
                    21418,
                    20439,
                    0,
                    0,
                    12427,
                    1,
                    20970,
                    42524,
                    1,
                    8898,
                    17478,
                    29706,
                    0,
                    40700,
                    1,
                    40700,
                    1,
                    42834,
                    0,
                    40699,
                    20805,
                    7982,
                    40700,
                    18919,
                    28954,
                    0,
                    40699,
                    40700,
                    40700
                ), 65544
            )
        )
    }

    @Test
    @Tag("Impossible")
    fun bagPacking() {
        assertEquals(
            setOf("3", "2", "1", "0"),
            bagPacking(
                mapOf("0" to (1 to 1), "1" to (149 to 2), "2" to (1 to 1), "3" to (1 to 1), "4" to (321 to 1)),
                457
            )
        )
        assertEquals(
            setOf("0"),
            bagPacking(
                mapOf("0" to (1 to 1)),
                2
            )
        )
        assertEquals(
            setOf("Кубок"),
            bagPacking(
                mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000), "Алмаз" to (1000 to 50000)),
                850
            )
        )
        assertEquals(
            setOf("0", "1"),
            bagPacking(
                mapOf("0" to (1 to 1), "1" to (1 to 1)),
                2
            )
        )
        assertEquals(
            setOf("Кубок"),
            bagPacking(
                mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
                850
            )
        )
        assertEquals(
            emptySet<String>(),
            bagPacking(
                mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
                450
            )
        )
    }

    // TODO: map task tests
}
