import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherBot extends TelegramLongPollingBot {
    private static final String TOKEN = "YOUR_TELEGRAM_BOT_TOKEN";
    private static final String BOT_USERNAME = "YourBotUsername";
    private static final String WEATHER_API_KEY = "YOUR_OPENWEATHERMAP_API_KEY";

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String cityName = update.getMessage().getText();
            String weatherData = fetchWeatherData(cityName);
            String response = parseWeatherData(weatherData);

            SendMessage message = new SendMessage()
                    .setChatId(update.getMessage().getChatId())
                    .setText(response);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private String fetchWeatherData(String city) {
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + WEATHER_API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder result = new StringBuilder();
            while (scanner.hasNext()) {
                result.append(scanner.nextLine());
            }
            scanner.close();
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String parseWeatherData(String data) {
        // Здесь вы можете проанализировать JSON ответ и вернуть пользователю нужную информацию о погоде.
        // Просто для демонстрации, мы вернем всю строку.
        return data;
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    public static void main(String[] args) {
        // Запуск бота
    }
}
