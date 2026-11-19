package com.example.dumbai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
    val ai = remember { DumbHuman() }
    val messages = remember { mutableStateListOf(Msg("ну привет", false)) }
    var input by remember { mutableStateOf("") }
    var typing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size, typing) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.lastIndex)
    }

    fun send() {
        val text = input.trim()
        if (text.isEmpty() || typing) return
        messages.add(Msg(text, true))
        input = ""
        scope.launch {
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
            if (typing) "Дима — печатает..." else "Дима — в сети",
            modifier = Modifier.fillMaxWidth().background(Color(0xFF2B5278)).padding(16.dp),
            color = Color.White
        )
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
                modifier = Modifier.weight(1f), placeholder = { Text("Сообщение") }
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = { send() }) { Text("➤") }
        }
    }
}
