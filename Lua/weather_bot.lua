local telegram_bot = require("telegram-bot")

local bot_token = "YOUR_TELEGRAM_BOT_TOKEN"

local bot = telegram_bot(bot_token)

bot:on_text_message(function(msg)
    local chat_id = tostring(msg.chat.id)
    -- TODO: Получите информацию о погоде для msg.text и отправьте ее в чат.
    bot:send_message(chat_id, "Here is your weather!") -- замените эту строку кодом для запроса погоды
end)

bot:run()
