package com.example.dumbai

import kotlin.random.Random

/** Логика "глупого человека". Чистый Kotlin, без Android, работает и на ПК (JVM). */
class DumbHuman {

    private val rules: List<Pair<List<String>, List<String>>> = listOf(
        listOf("привет", "здаров", "хай", "ку") to listOf("ну привет", "о, здарова", "хай)", "ку"),
        listOf("как дела", "как ты", "как жизнь") to listOf("да норм, а у тя?", "ну так, живу", "устал чето", "нормально, ты как?"),
        listOf("как зовут", "твоё имя", "твое имя") to listOf("Дима вроде", "а тебе зачем?", "ну допустим Саня"),
        listOf("что делаешь", "чем занят") to listOf("да ничего, лежу", "в телефоне сижу", "думаю о еде"),
        listOf("сколько", "лет") to listOf("мне? ну не молодой уже", "а какая разница", "хз, забыл"),
        listOf("любишь", "нравится") to listOf("ну да, наверное", "не особо если честно", "смотря что"),
        listOf("почему") to listOf("хз", "ну так вышло", "а почему бы и нет"),
        listOf("пока", "до свидания", "спокойной") to listOf("ну давай", "пока)", "ага, бывай"),
        listOf("спасибо", "thx") to listOf("да не за что", "обращайся)", "ага"),
        listOf("?") to listOf("хм, сложный вопрос", "не знаю даже", "ща подумаю... не, не знаю", "а ты как думаешь?")
    )

    private val fallback = listOf(
        "ну да", "ага", "понятно", "чего?", "не понял", "хм", "ну допустим",
        "а потом?", "прикольно", "лол", "я щас занят вообще", "ладно"
    )

    private val emoji = listOf(")", "))", " :)", " хд", "")

    fun reply(input: String): String {
        val text = input.lowercase().trim()
        // иногда отвечаем невпопад
        if (Random.nextInt(100) < 10) return fallback.random()

        val match = rules.firstOrNull { (keys, _) -> keys.any { text.contains(it) } }
        val base = match?.second?.random() ?: fallback.random()
        return maybeTypo(base) + if (Random.nextInt(100) < 25) emoji.random() else ""
    }

    /** Чем длиннее ответ, тем дольше "печатает". */
    fun typingDelayMs(reply: String): Long =
        (700L + reply.length * 70L + Random.nextLong(0, 800)).coerceAtMost(4000)

    private fun maybeTypo(s: String): String {
        if (s.length < 4 || Random.nextInt(100) > 15) return s
        val i = Random.nextInt(1, s.length - 1)
        // меняем две соседние буквы местами
        val chars = s.toCharArray()
        val t = chars[i]; chars[i] = chars[i + 1]; chars[i + 1] = t
        return String(chars)
    }
}
