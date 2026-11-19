package com.example.dumbai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class Msg(val text: String, val fromMe: Boolean)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { ChatScreen() } }
    }
}

@Composable
fun ChatScreen() {
    var lang by remember { mutableStateOf(Lang.default()) }
    val ai = remember(lang) { DumbHuman(lang) }
    val messages = remember { mutableStateListOf<Msg>() }
    var input by remember { mutableStateOf("") }
    var typing by remember { mutableStateOf(false) }
    var job by remember { mutableStateOf<Job?>(null) }
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val langScroll = rememberScrollState()

    // при смене языка начинаем чат заново
    LaunchedEffect(lang) {
        job?.cancel()
        typing = false
        messages.clear()
        messages.add(Msg(ai.pack.greeting, false))
    }

    LaunchedEffect(messages.size, typing) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.lastIndex)
    }

    fun send() {
        val text = input.trim()
        if (text.isEmpty() || typing) return
        messages.add(Msg(text, true))
        input = ""
        job = scope.launch {
            val answer = ai.reply(text)
            delay(500)               // "прочитал"
            typing = true            // "печатает..."
            delay(ai.typingDelayMs(answer))
            typing = false
            messages.add(Msg(answer, false))
        }
    }

    Column(Modifier.fillMaxSize().systemBarsPadding().imePadding()) {
        Text(
            "${ai.pack.name} — " + if (typing) ai.pack.typing else ai.pack.online,
            modifier = Modifier.fillMaxWidth().background(Color(0xFF2B5278)).padding(16.dp),
            color = Color.White
        )
        Row(
            Modifier.fillMaxWidth().background(Color(0xFF1F3B57)).horizontalScroll(langScroll).padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Lang.values().forEach { l ->
                Text(
                    l.label,
                    color = Color.White,
                    modifier = Modifier
                        .background(
                            if (l == lang) Color(0xFF4C8BC4) else Color.Transparent,
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { lang = l }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
        LazyColumn(Modifier.weight(1f).padding(horizontal = 8.dp), state = listState) {
            items(messages) { m ->
                Row(
                    Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = if (m.fromMe) Arrangement.End else Arrangement.Start
                ) {
                    Text(
                        m.text,
                        modifier = Modifier
                            .background(
                                if (m.fromMe) Color(0xFFDCF8C6) else Color(0xFFEEEEEE),
                                RoundedCornerShape(12.dp)
                            )
                            .padding(10.dp)
                    )
                }
            }
        }
        Row(Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = input, onValueChange = { input = it },
                modifier = Modifier.weight(1f), placeholder = { Text(ai.pack.placeholder) }
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = { send() }) { Text("➤") }
        }
    }
}
