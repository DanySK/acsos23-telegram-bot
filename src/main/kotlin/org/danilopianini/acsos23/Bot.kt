@file:JvmName("Bot")

package org.danilopianini.acsos23

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
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
            text {
                bot.sendMessage(ChatId.fromId(message.chat.id), text = text)
                bot.sendMessage(ChatId.fromId(message.chat.id), text = "custom message")
            }
        }
    }
    bot.startPolling()
}
