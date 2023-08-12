package main

import (
	"log"
	"github.com/go-telegram-bot-api/telegram-bot-api"
)

func main() {
	botToken := "YOUR_TELEGRAM_BOT_TOKEN"
	bot, err := tgbotapi.NewBotAPI(botToken)
	if err != nil {
		log.Panic(err)
	}

	updateConfig := tgbotapi.NewUpdate(0)
	updateConfig.Timeout = 60
	updates, err := bot.GetUpdatesChan(updateConfig)
	if err != nil {
		log.Panic(err)
	}

	for update := range updates {
		if update.Message == nil {
			continue
		}
		// TODO: Получите информацию о погоде для update.Message.Text и отправьте ее в чат.
		msg := tgbotapi.NewMessage(update.Message.Chat.ID, "Here is your weather!") // замените эту строку кодом для запроса погоды
		bot.Send(msg)
	}
}
