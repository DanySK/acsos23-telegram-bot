@file:JvmName("Bot")

package org.danilopianini.acsos23

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.newChatMembers
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId
import java.lang.management.ManagementFactory
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

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
                val chatId = ChatId.fromId(message.chat.id)
                val username: String? = message.from?.run {
                    username?.let { "@$it" } ?: "$firstName $lastName"
                }
                if (username != null) {
                    bot.sendMessage(chatId, text = "Welcome to ACSOS 2023, $username!")
                }
            }
            text {
                val chatId = ChatId.fromId(message.chat.id)
                val receivedText = message.text.orEmpty()
                if (receivedText.contains("MAINTENANCE_TEST")) {
                    bot.sendMessage(
                        ChatId.fromId(message.chat.id),
                        text = "I have been mentioned!",
                        replyToMessageId = message.messageId,
                    )
                    bot.sendMessage(ChatId.fromId(message.chat.id), text = text)
                    if (receivedText.contains("committees")) {
                        bot.sendMessage(ChatId.fromId(message.chat.id), text = Committees.committes().toString())
                    }
                    if (receivedText.contains("uptime")) {
                        bot.sendMessage(
                            chatId,
                            text = """
                                I have been up for ${ManagementFactory.getRuntimeMXBean().uptime} ms, since ${
                                ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT)
                            }
                            """.trimIndent(),
                        )
                    }
                }
            }
        }
    }
    bot.startPolling()
}
