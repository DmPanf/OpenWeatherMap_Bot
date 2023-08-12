import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

class WeatherBot : TelegramLongPollingBot() {
    override fun getBotUsername(): String {
        return "YOUR_BOT_USERNAME"
    }

    override fun getBotToken(): String {
        return "YOUR_BOT_TOKEN"
    }

    override fun onUpdateReceived(update: Update?) {
        update?.let {
            if (it.hasMessage() && it.message.hasText()) {
                // TODO: Get the weather information for it.message.text and send it to the chat.
                val message = "Here is your weather!"
                val response = SendMessage()
                response.chatId = it.message.chatId.toString()
                response.text = message
                try {
                    execute(response)
                } catch (e: TelegramApiException) {
                    e.printStackTrace()
                }
            }
        }
    }
}

fun main() {
    // TODO: Initialize and start the bot
}
