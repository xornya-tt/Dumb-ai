# 🤖 Dima — the dumb AI that acts like a human

Meet **Dima** 👋 He's not smart. He doesn't know everything. Sometimes he answers off-topic, makes typos and takes his time to reply. And that's exactly the point 😄

## 💡 The idea

Most chatbots try to be as smart as possible. Dima goes the other way: he tries to feel **human**, not clever.

Real people:
- 🕐 don't answer instantly, they read first and then type
- ✍️ make typos
- 🤷 say "idk" or "lol what?" when they don't get it
- 😴 sometimes reply with something totally random

Dima copies all of that. The goal of this project is to show that "feeling human" is more about small imperfect details than about intelligence 🧠❌

## ✨ Features

- 💬 Telegram-style chat screen
- ⌨️ "typing..." status, and longer messages take longer to type
- 🔤 Random typos (swapped letters)
- 🎲 About 10% of replies are off-topic on purpose
- 😉 Occasional smileys like `)` and `хд`
- 📴 Works fully offline: no internet, no API, no accounts
- 🧩 Simple keyword rules (ELIZA-style), easy to edit

## 🛠️ Tech stack

- **Kotlin**
- **Jetpack Compose** (Material 3)
- **Gradle** (Kotlin DSL)
- **GitHub Actions** for building the APK in the cloud ☁️

The brain lives in `DumbHuman.kt` and is plain Kotlin with no Android code, so it can be reused on desktop too 🖥️

## 📁 Project structure

```
app/src/main/java/com/example/dumbai/
├── DumbHuman.kt     # Dima's brain: rules, typos, delays
└── MainActivity.kt  # Chat UI in Compose

Requires Android 8.0+ (API 26).

## 🎨 Make Dima your own

Open `DumbHuman.kt` and edit the `rules` list. Add your own keywords and replies, change how often he makes typos, or make him ruder, lazier or nicer 😎

## 🗺️ Ideas for the future

- [ ] Desktop version (Compose Desktop)
- [ ] More personalities (lazy, grumpy, friendly)
- [ ] Memory of the last few messages
- [ ] Optional smarter mode with a real AI API

## 🤝 Contributing

Ideas, new phrases and pull requests are welcome! Dima is dumb, but he's open to learning 😉

---

Made with ❤️ and a little bit of silliness
