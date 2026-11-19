package com.example.dumbai

import java.util.Locale
import kotlin.random.Random

/** Языки в порядке распространённости. */
enum class Lang(val label: String) {
    EN("English"), ZH("中文"), HI("हिन्दी"), ES("Español"), FR("Français"),
    AR("العربية"), PT("Português"), RU("Русский"), DE("Deutsch");

    companion object {
        /** Язык по умолчанию — язык системы, иначе английский. */
        fun default(): Lang = when (Locale.getDefault().language) {
            "zh" -> ZH
            "hi" -> HI
            "es" -> ES
            "fr" -> FR
            "ar" -> AR
            "pt" -> PT
            "ru" -> RU
            "de" -> DE
            else -> EN
        }
    }
}

/** Всё, что зависит от языка: тексты интерфейса, правила, запасные фразы. */
data class Pack(
    val name: String,
    val greeting: String,
    val online: String,
    val typing: String,
    val placeholder: String,
    val emoji: List<String>,
    val rules: List<Pair<List<String>, List<String>>>,
    val fallback: List<String>,
    val typos: Boolean = true,
    val whines: List<String> = emptyList(),
    val nonsense: List<String> = emptyList()
)

object Packs {
    fun of(lang: Lang): Pack {
        val p = when (lang) {
            Lang.EN -> en
            Lang.ZH -> zh
            Lang.HI -> hi
            Lang.ES -> es
            Lang.FR -> fr
            Lang.AR -> ar
            Lang.PT -> pt
            Lang.RU -> ru
            Lang.DE -> de
        }
        return p.copy(
            whines = Extras.whines[lang].orEmpty(),
            nonsense = Extras.nonsense[lang].orEmpty()
        )
    }

    private val en = Pack(
        name = "Dima", greeting = "yo", online = "online",
        typing = "typing...", placeholder = "Message",
        emoji = listOf(")", "))", " :)", " lol", ""),
        rules = listOf(
            listOf("how are you", "how r u", "how's it going", "hows it going") to listOf("fine i guess, you?", "tired lol", "same old", "good, you?"),
            listOf("hi", "hello", "hey", "yo", "sup") to listOf("yo", "hey", "oh hi", "sup"),
            listOf("your name", "who are you") to listOf("Dima i think", "why do you ask?", "call me Dima ig"),
            listOf("what are you doing", "what r u doing", "wyd") to listOf("nothing, just lying here", "scrolling my phone", "thinking about food"),
            listOf("how old") to listOf("old enough", "idk, forgot", "why?"),
            listOf("do you like", "you like") to listOf("yeah maybe", "not really tbh", "depends"),
            listOf("why") to listOf("idk", "just happened", "why not"),
            listOf("bye", "goodbye", "good night") to listOf("ok bye", "cya", "later"),
            listOf("thanks", "thank you", "thx") to listOf("np", "no problem)", "yeah"),
            listOf("?") to listOf("hmm hard one", "dunno", "lemme think... nope", "what do you think?")
        ),
        fallback = listOf(
            "yeah", "ok", "i see", "what?", "didn't get it", "hm", "fair enough",
            "and then?", "cool", "lol", "i'm kinda busy rn", "whatever"
        )
    )

    private val zh = Pack(
        name = "季马", greeting = "嗨", online = "在线",
        typing = "正在输入...", placeholder = "消息",
        emoji = listOf("~", "哈哈", ""),
        typos = false,
        rules = listOf(
            listOf("你好吗", "最近怎么样", "怎么样", "咋样") to listOf("还行吧，你呢？", "有点累", "老样子", "挺好的"),
            listOf("你好", "嗨", "哈喽", "在吗") to listOf("嗨", "哟", "你好呀", "在呢"),
            listOf("你叫什么", "你是谁", "名字") to listOf("好像叫季马", "问这个干嘛？", "就叫季马吧"),
            listOf("在干嘛", "在做什么", "干什么呢") to listOf("没干嘛，躺着", "刷手机呢", "在想吃什么"),
            listOf("几岁", "多大") to listOf("不年轻了", "忘了", "问这个干嘛"),
            listOf("喜欢") to listOf("还行吧", "说实话不太", "看情况"),
            listOf("为什么", "为啥") to listOf("不知道", "就这样了", "为啥不呢"),
            listOf("再见", "拜拜", "晚安") to listOf("好，拜拜", "再见)", "回头聊"),
            listOf("谢谢", "谢了") to listOf("没事", "不客气)", "嗯"),
            listOf("?", "？") to listOf("嗯，这个难", "不知道诶", "我想想…算了不知道", "你觉得呢？")
        ),
        fallback = listOf(
            "嗯", "哦", "懂了", "啥？", "没听懂", "额", "行吧",
            "然后呢？", "有意思", "哈哈", "我现在有点忙", "好吧"
        )
    )

    private val hi = Pack(
        name = "दिमा", greeting = "हाय", online = "ऑनलाइन",
        typing = "लिख रहा है...", placeholder = "संदेश",
        emoji = listOf(")", " हाहा", ""),
        typos = false,
        rules = listOf(
            listOf("कैसे हो", "कैसा है", "क्या हाल") to listOf("बस ठीक, तू बता?", "थोड़ा थका हुआ हूँ", "सब चलता है"),
            listOf("नमस्ते", "हाय", "हेलो", "हैलो", "अरे") to listOf("हाय", "अरे, हेलो", "और बता"),
            listOf("तुम्हारा नाम", "तेरा नाम", "कौन हो", "तुम कौन") to listOf("दिमा शायद", "तुझे क्या करना?", "चल दिमा बोल दे"),
            listOf("क्या कर रहे", "क्या कर रहा") to listOf("कुछ नहीं, लेटा हूँ", "फ़ोन चला रहा हूँ", "खाने के बारे में सोच रहा हूँ"),
            listOf("कितने साल", "उम्र") to listOf("बूढ़ा तो नहीं", "पता नहीं, भूल गया", "क्यों?"),
            listOf("पसंद") to listOf("हाँ शायद", "सच बोलूँ तो नहीं ज़्यादा", "देखना पड़ेगा"),
            listOf("क्यों") to listOf("पता नहीं", "बस ऐसे ही हो गया", "क्यों नहीं"),
            listOf("अलविदा", "बाय", "गुड नाइट", "फिर मिलते") to listOf("ठीक है, बाय", "चल फिर", "मिलते हैं)"),
            listOf("शुक्रिया", "धन्यवाद", "थैंक्स") to listOf("कोई बात नहीं", "अरे छोड़", "हाँ ठीक"),
            listOf("?") to listOf("हम्म, मुश्किल सवाल", "पता नहीं यार", "रुक सोचता हूँ... नहीं पता", "तू क्या सोचता है?")
        ),
        fallback = listOf(
            "हाँ", "अच्छा", "समझ गया", "क्या?", "समझ नहीं आया", "हम्म", "चल ठीक है",
            "फिर?", "मस्त", "हाहा", "अभी थोड़ा बिज़ी हूँ", "ठीक है"
        )
    )

    private val es = Pack(
        name = "Dima", greeting = "hola", online = "en línea",
        typing = "escribiendo...", placeholder = "Mensaje",
        emoji = listOf(")", "))", " :)", " jaja", ""),
        rules = listOf(
            listOf("cómo estás", "como estas", "qué tal", "que tal", "cómo andas") to listOf("bien, y tú?", "cansado la verdad", "normal, ahí vamos"),
            listOf("hola", "buenas", "ey", "hey") to listOf("hola", "ey, buenas", "qué pasa"),
            listOf("cómo te llamas", "como te llamas", "tu nombre", "quién eres", "quien eres") to listOf("Dima creo", "¿y eso por qué?", "digamos que Dima"),
            listOf("qué haces", "que haces", "haciendo") to listOf("nada, tirado", "con el móvil", "pensando en comida"),
            listOf("cuántos años", "cuantos años", "edad") to listOf("ya no soy joven", "ni idea, se me olvidó", "¿por qué?"),
            listOf("te gusta", "te gustan", "te encanta") to listOf("pues sí, supongo", "la verdad no mucho", "depende"),
            listOf("por qué", "por que", "porqué") to listOf("ni idea", "pues pasó", "¿y por qué no?"),
            listOf("adiós", "adios", "chao", "hasta luego", "buenas noches") to listOf("venga, chao", "adiós)", "nos vemos"),
            listOf("gracias") to listOf("de nada", "no hay de qué)", "ajá"),
            listOf("?") to listOf("mm, difícil", "no sé", "déjame pensar... no sé", "¿tú qué crees?")
        ),
        fallback = listOf(
            "ya", "aha", "entiendo", "¿qué?", "no entendí", "mm", "bueno vale",
            "¿y luego?", "qué guay", "jaja", "ahora estoy ocupado", "vale"
        )
    )

    private val fr = Pack(
        name = "Dima", greeting = "salut", online = "en ligne",
        typing = "écrit...", placeholder = "Message",
        emoji = listOf(")", "))", " :)", " mdr", ""),
        rules = listOf(
            listOf("ça va", "ca va", "comment vas-tu", "comment tu vas") to listOf("ça va, et toi ?", "bof, fatigué", "ouais tranquille", "ça va ça va"),
            listOf("au revoir", "à plus", "a plus", "bonne nuit", "ciao", "bye") to listOf("ok à plus", "ciao)", "à la prochaine"),
            listOf("salut", "coucou", "bonjour", "hello", "hey", "yo") to listOf("salut", "coucou", "yo", "ah salut"),
            listOf("appelles", "ton nom", "qui es-tu", "t'es qui") to listOf("Dima je crois", "pourquoi tu demandes ?", "disons Dima"),
            listOf("tu fais quoi", "que fais") to listOf("rien, je traîne", "sur mon tel", "je pense à manger"),
            listOf("quel âge", "quel age") to listOf("pas si vieux", "aucune idée, j'ai oublié", "pourquoi ?"),
            listOf("tu aimes", "t'aimes", "tu kiffes") to listOf("ouais, peut-être", "pas trop, honnêtement", "ça dépend"),
            listOf("pourquoi") to listOf("aucune idée", "ça s'est passé comme ça", "pourquoi pas"),
            listOf("merci") to listOf("de rien", "pas de souci)", "ouais"),
            listOf("?") to listOf("hmm dur comme question", "sais pas", "attends je réfléchis... non, aucune idée", "et toi t'en penses quoi ?")
        ),
        fallback = listOf(
            "ouais", "ok", "je vois", "quoi ?", "j'ai pas compris", "hm", "bon d'accord",
            "et après ?", "cool", "mdr", "là je suis occupé", "bref"
        )
    )

    private val ar = Pack(
        name = "ديما", greeting = "هلا", online = "متصل",
        typing = "يكتب...", placeholder = "رسالة",
        emoji = listOf(")", " هههه", ""),
        typos = false,
        rules = listOf(
            listOf("كيف حالك", "شلونك", "كيفك", "كيف الحال") to listOf("الحمد لله، وانت؟", "تعبان شوي", "عادي ماشي الحال"),
            listOf("شو اسمك", "ما اسمك", "اسمك", "من انت", "مين انت") to listOf("ديما على ما أظن", "ليش تسأل؟", "قول ديما"),
            listOf("شو تسوي", "ماذا تفعل", "شو عم تعمل", "وش تسوي") to listOf("ولا شي، مستلقي", "على الجوال", "افكر بالاكل"),
            listOf("كم عمرك") to listOf("مو صغير", "نسيت والله", "ليش؟"),
            listOf("تحب", "يعجبك") to listOf("ايوه يمكن", "مو كثير بصراحة", "حسب"),
            listOf("ليش", "لماذا", "ليه") to listOf("مدري", "صار كذا", "ليش لا"),
            listOf("مع السلامة", "باي", "تصبح على خير", "الى اللقاء") to listOf("تمام، باي", "يلا سلام", "نشوفك"),
            listOf("شكرا", "شكراً", "مشكور") to listOf("عفواً", "ولا يهمك", "ايوه"),
            listOf("مرحبا", "اهلا", "أهلا", "هلا", "السلام عليكم", "سلام") to listOf("هلا", "اهلين", "يا هلا"),
            listOf("?", "؟") to listOf("همم سؤال صعب", "مدري", "خلني افكر... لا مدري", "شو رايك انت؟")
        ),
        fallback = listOf(
            "ايوه", "اها", "فهمت", "شو؟", "ما فهمت", "همم", "طيب",
            "وبعدين؟", "حلو", "هههه", "انا مشغول شوي", "ماشي"
        )
    )

    private val pt = Pack(
        name = "Dima", greeting = "e aí", online = "online",
        typing = "digitando...", placeholder = "Mensagem",
        emoji = listOf(")", "))", " :)", " kkk", ""),
        rules = listOf(
            listOf("tudo bem", "como vai", "como você está", "como vc tá", "beleza") to listOf("de boa, e você?", "cansado, na real", "tranquilo", "tudo certo"),
            listOf("tchau", "até mais", "ate mais", "boa noite", "flw") to listOf("beleza, tchau", "falou)", "até mais"),
            listOf("oi", "olá", "ola", "e aí", "eae", "salve", "opa") to listOf("e aí", "opa, fala", "oi"),
            listOf("seu nome", "se chama", "quem é você", "quem é vc") to listOf("Dima eu acho", "por que tá perguntando?", "pode chamar de Dima"),
            listOf("tá fazendo", "o que você faz", "fazendo o quê") to listOf("nada, deitado", "no celular", "pensando em comida"),
            listOf("quantos anos", "idade") to listOf("já não sou novo", "sei lá, esqueci", "por quê?"),
            listOf("você gosta", "voce gosta", "gosta de", "curte") to listOf("acho que sim", "na real nem tanto", "depende"),
            listOf("por que", "por quê", "pq") to listOf("sei lá", "aconteceu assim", "por que não?"),
            listOf("obrigado", "obrigada", "valeu", "vlw") to listOf("de nada", "tranquilo)", "de boa"),
            listOf("?") to listOf("hmm, difícil", "sei lá", "deixa eu pensar... não sei", "e você, o que acha?")
        ),
        fallback = listOf(
            "é", "aham", "entendi", "hã?", "não entendi", "hm", "tá bom",
            "e daí?", "legal", "kkk", "tô meio ocupado agora", "beleza"
        )
    )

    private val ru = Pack(
        name = "Дима", greeting = "ну привет", online = "в сети",
        typing = "печатает...", placeholder = "Сообщение",
        emoji = listOf(")", "))", " :)", " хд", ""),
        rules = listOf(
            listOf("как дела", "как ты", "как жизнь") to listOf("да норм, а у тя?", "ну так, живу", "устал чето", "нормально, ты как?"),
            listOf("привет", "здаров", "хай", "ку") to listOf("ну привет", "о, здарова", "хай)", "ку"),
            listOf("как зовут", "твоё имя", "твое имя", "кто ты") to listOf("Дима вроде", "а тебе зачем?", "ну допустим Саня"),
            listOf("что делаешь", "чем занят") to listOf("да ничего, лежу", "в телефоне сижу", "думаю о еде"),
            listOf("сколько лет", "сколько тебе") to listOf("мне? ну не молодой уже", "а какая разница", "хз, забыл"),
            listOf("любишь", "нравится") to listOf("ну да, наверное", "не особо если честно", "смотря что"),
            listOf("почему") to listOf("хз", "ну так вышло", "а почему бы и нет"),
            listOf("пока", "до свидания", "спокойной ночи") to listOf("ну давай", "пока)", "ага, бывай"),
            listOf("спасибо", "thx") to listOf("да не за что", "обращайся)", "ага"),
            listOf("?") to listOf("хм, сложный вопрос", "не знаю даже", "ща подумаю... не, не знаю", "а ты как думаешь?")
        ),
        fallback = listOf(
            "ну да", "ага", "понятно", "чего?", "не понял", "хм", "ну допустим",
            "а потом?", "прикольно", "лол", "я щас занят вообще", "ладно"
        )
    )

    private val de = Pack(
        name = "Dima", greeting = "na", online = "online",
        typing = "schreibt...", placeholder = "Nachricht",
        emoji = listOf(")", "))", " :)", " haha", ""),
        rules = listOf(
            listOf("wie geht") to listOf("ganz gut, und dir?", "bin müde irgendwie", "läuft so"),
            listOf("hallo", "hi", "hey", "moin", "servus") to listOf("na", "hallo", "moin", "hey)"),
            listOf("wie heißt", "wie heisst", "dein name", "wer bist du") to listOf("Dima glaub ich", "warum fragst du?", "sagen wir Dima"),
            listOf("was machst") to listOf("nix, liege rum", "am Handy", "denke ans Essen"),
            listOf("wie alt") to listOf("alt genug", "keine Ahnung, vergessen", "warum?"),
            listOf("magst du", "gefällt") to listOf("joa, vielleicht", "eher nicht ehrlich gesagt", "kommt drauf an"),
            listOf("warum", "wieso") to listOf("keine Ahnung", "ist halt so", "warum nicht"),
            listOf("tschüss", "tschuess", "tschau", "bis dann", "gute nacht") to listOf("ok tschüss", "bis dann)", "mach's gut"),
            listOf("danke") to listOf("kein Ding", "gerne)", "jo"),
            listOf("?") to listOf("hmm schwere Frage", "weiß nicht", "moment... nee, keine Ahnung", "was denkst du?")
        ),
        fallback = listOf(
            "ja", "aha", "verstehe", "was?", "hab ich nicht verstanden", "hm", "na gut",
            "und dann?", "cool", "lol", "bin grad beschäftigt", "ok"
        )
    )
}

/** Нытьё и полная бессмыслица для каждого языка. */
object Extras {
    val whines: Map<Lang, List<String>> = mapOf(
        Lang.RU to listOf(
            "ну вот опять всё плохо", "ой, как же я устал от всего", "никому я не нужен",
            "у меня голова болит", "жизнь тяжёлая штука", "денег нет, настроения нет",
            "я есть хочу а лень вставать", "всё бесит", "ну почему всё так сложно", "спина болит"
        ),
        Lang.EN to listOf(
            "ugh everything sucks", "i'm so tired of everything", "nobody cares about me",
            "my head hurts", "life is hard man", "no money no motivation",
            "i'm hungry but too lazy to get up", "why is everything so complicated",
            "everything annoys me", "my back hurts"
        ),
        Lang.ES to listOf(
            "ay qué asco todo", "estoy harto de todo", "a nadie le importo",
            "me duele la cabeza", "la vida es dura", "sin dinero y sin ganas",
            "tengo hambre pero me da pereza levantarme", "todo me molesta", "me duele la espalda"
        ),
        Lang.FR to listOf(
            "ah tout est nul", "j'en ai marre de tout", "tout le monde s'en fiche de moi",
            "j'ai mal à la tête", "la vie c'est dur", "pas de sous, pas de motivation",
            "j'ai faim mais j'ai la flemme de me lever", "tout m'énerve", "j'ai mal au dos"
        ),
        Lang.PT to listOf(
            "ai que saco, tá tudo ruim", "tô cansado de tudo", "ninguém liga pra mim",
            "minha cabeça tá doendo", "a vida é dura", "sem grana e sem vontade",
            "tô com fome mas com preguiça de levantar", "tudo me irrita", "minhas costas doem"
        ),
        Lang.DE to listOf(
            "ach, alles ist doof", "ich bin so müde von allem", "mich mag eh keiner",
            "mein Kopf tut weh", "das Leben ist hart", "kein Geld, keine Lust",
            "ich hab Hunger aber keine Lust aufzustehen", "alles nervt", "mein Rücken tut weh"
        ),
        Lang.ZH to listOf(
            "唉，什么都不顺", "我好累啊", "没人在乎我", "头疼", "生活真难",
            "没钱也没动力", "饿了但懒得起来", "什么都烦", "腰疼"
        ),
        Lang.HI to listOf(
            "उफ़, सब कुछ बेकार है", "मैं सबसे थक गया हूँ", "मेरी किसी को परवाह नहीं",
            "सिर दर्द कर रहा है", "ज़िंदगी मुश्किल है यार", "न पैसे हैं न मूड",
            "भूख लगी है पर उठने का मन नहीं", "सब कुछ चिढ़ाता है", "कमर दर्द कर रही है"
        ),
        Lang.AR to listOf(
            "اوف كل شي سيء", "تعبت من كل شي", "ما حد يهتم فيني", "راسي يوجعني",
            "الحياة صعبة والله", "ما في فلوس ولا مزاج", "جوعان بس كسلان اقوم",
            "كل شي يزعجني", "ظهري يوجعني"
        )
    )

    val nonsense: Map<Lang, List<String>> = mapOf(
        Lang.RU to listOf(
            "ыыы", "ну кароч", "бла бла бла", "а? чё?", "тут муха села", "кек",
            "ну ээээ", "а я вчера носок потерял", "а вы знали что сыр круглый"
        ),
        Lang.EN to listOf(
            "uhhh", "bla bla bla", "wait what", "a fly just landed on me", "kek",
            "so yeah", "i lost a sock yesterday", "did you know cheese is round"
        ),
        Lang.ES to listOf(
            "eeeh", "bla bla bla", "¿qué?", "se me posó una mosca", "jaja nada",
            "bueno eso", "ayer perdí un calcetín", "¿sabías que el queso es redondo?"
        ),
        Lang.FR to listOf(
            "euuuh", "bla bla bla", "quoi ?", "une mouche vient de se poser", "mdr rien",
            "bref voilà", "hier j'ai perdu une chaussette", "tu savais que le fromage est rond ?"
        ),
        Lang.PT to listOf(
            "eeeh", "blá blá blá", "hã?", "uma mosca pousou aqui", "kkk nada",
            "enfim né", "ontem perdi uma meia", "sabia que o queijo é redondo?"
        ),
        Lang.DE to listOf(
            "ähhh", "bla bla bla", "was?", "eine Fliege hat sich gesetzt", "haha nix",
            "ja also", "gestern hab ich eine Socke verloren", "wusstest du, Käse ist rund?"
        ),
        Lang.ZH to listOf(
            "呃……", "啦啦啦", "啥？", "有只苍蝇落我身上了", "哈哈没事",
            "就这样吧", "昨天我丢了一只袜子", "你知道奶酪是圆的吗"
        ),
        Lang.HI to listOf(
            "अ... हम्म", "ब्ला ब्ला ब्ला", "क्या?", "मेरे ऊपर मक्खी बैठ गई", "हाहा कुछ नहीं",
            "बस ऐसे ही", "कल मेरा एक मोज़ा खो गया", "पता है चीज़ गोल होता है?"
        ),
        Lang.AR to listOf(
            "اااه", "بلا بلا بلا", "شو؟", "وقعت ذبابة علي", "ههه ولا شي",
            "المهم", "امس ضاع مني جورب", "تعرف ان الجبنة دائرية؟"
        )
    )
}

/** Логика "глупого человека". Чистый Kotlin, без Android, работает и на ПК (JVM). */
class DumbHuman(val lang: Lang) {

    val pack: Pack = Packs.of(lang)

    fun reply(input: String): String {
        val text = input.lowercase().trim()
        val words = text.split(Regex("[^\\p{L}\\p{M}']+")).filter { it.isNotEmpty() }.toSet()
        val match = pack.rules.firstOrNull { (keys, _) -> keys.any { matches(text, words, it) } }

        // набор букв вроде "аопаидшчиг" — отвечаем таким же набором
        if (match == null && looksLikeKeysmash(text)) {
            return if (Random.nextInt(100) < 75) keysmash(text) else nonsense()
        }

        val roll = Random.nextInt(100)
        if (roll < 12) return whine()      // пожаловаться на жизнь
        if (roll < 22) return nonsense()   // сказать бессмыслицу

        val base = match?.second?.random() ?: pack.fallback.random()
        var out = maybeTypo(base) + if (Random.nextInt(100) < 25) pack.emoji.random() else ""
        if (Random.nextInt(100) < 10) out += "... " + whine()   // ответил и тут же заныл
        return out
    }

    private fun whine(): String = pack.whines.randomOrNull() ?: pack.fallback.random()
    private fun nonsense(): String = pack.nonsense.randomOrNull() ?: pack.fallback.random()

    private fun looksLikeKeysmash(text: String): Boolean =
        text.length >= 6 && !text.contains(' ') && text.first().isLetter() && !isHan(text.first())

    /** Случайный набор букв из того, что напечатал пользователь. */
    private fun keysmash(text: String): String {
        val letters = text.filter { it.isLetter() }
        if (letters.isEmpty()) return nonsense()
        return (1..Random.nextInt(5, 11)).map { letters.random() }.joinToString("")
    }

    private fun isHan(c: Char) = Character.UnicodeScript.of(c.code) == Character.UnicodeScript.HAN

    /**
     * Короткие слова ищем как целые слова ("hi" не должно находиться в "this").
     * Китайский пишется без пробелов, поэтому там ищем просто вхождение.
     */
    private fun matches(text: String, words: Set<String>, key: String): Boolean =
        if (key.contains(' ') || key.length > 3 || !key.first().isLetter() || isHan(key.first()))
            text.contains(key)
        else key in words

    /** Чем длиннее ответ, тем дольше "печатает". */
    fun typingDelayMs(reply: String): Long =
        (700L + reply.length * 70L + Random.nextLong(0, 800)).coerceAtMost(4000)

    private fun maybeTypo(s: String): String {
        if (!pack.typos || s.length < 4 || Random.nextInt(100) > 15) return s
        val i = Random.nextInt(1, s.length - 1)
        // меняем две соседние буквы местами
        val chars = s.toCharArray()
        val t = chars[i]; chars[i] = chars[i + 1]; chars[i + 1] = t
        return String(chars)
    }
}
