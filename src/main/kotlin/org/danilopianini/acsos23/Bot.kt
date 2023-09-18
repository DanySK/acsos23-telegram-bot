@file:JvmName("Bot")

package org.danilopianini.acsos23

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.newChatMembers
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId

/**
 * Bot Entrypoint.
 */
fun main() {
    val bot = bot {
        token = checkNotNull(System.getenv("BOT_TOKEN")) {
            "Please set the BOT_TOKEN environment variable"
        }
        dispatch {
            newChatMembers {
                val username = message.from?.apply {
                    username?.let { "@$it" } ?: "$firstName $lastName"
                }
                if (username != null) {
                    bot.sendMessage(ChatId.fromId(message.chat.id), text = "Welcome to ACSOS 2023, $username!")
                }
            }
            text {
                if (message.text.orEmpty().contains("@acsos_23_bot")) {
                    bot.sendMessage(ChatId.fromId(message.chat.id), text = "I have been mentioned!")
                }
            }
        }
    }
    bot.startPolling()
}
