import os
import logging
import aiohttp
from aiogram import Bot, Dispatcher, types
from aiogram.types import ParseMode
from aiogram.utils import executor
from dotenv import load_dotenv

load_dotenv()  # загрузка переменных окружения из файла .env

API_TOKEN = os.getenv("TG_TOKEN")
WEATHER_API_KEY = os.getenv("WEATHER_API_KEY")
if not API_TOKEN or not WEATHER_API_KEY:
    raise ValueError("Tokens are not set in the environment variables or .env file!")

logging.basicConfig(level=logging.INFO)
bot = Bot(token=API_TOKEN)
dp = Dispatcher(bot)


async def fetch_weather_data(city):
    url = f'http://api.openweathermap.org/data/2.5/weather?q={city}&appid={WEATHER_API_KEY}'
    async with aiohttp.ClientSession() as session:
        async with session.get(url) as response:
            return await response.json()


@dp.message_handler(commands=['start'])
async def cmd_start(message: types.Message):
    await message.answer("Send me a city name, and I'll provide the weather info!")


@dp.message_handler()
async def handle_city_name(message: types.Message):
    data = await fetch_weather_data(message.text)
    if 'main' in data:
        weather_info = data['main']
        temp = weather_info.get('temp', 'N/A') - 273.15  # Convert Kelvin to Celsius
        feels_like = weather_info.get('feels_like', 'N/A') - 273.15  # Convert Kelvin to Celsius
        weather_desc = data['weather'][0]['description'] if 'weather' in data and data['weather'] else 'N/A'

        weather_response = (f"Weather in {message.text}:\n"
                            f"Temperature: {temp:.2f}°C\n"
                            f"Feels Like: {feels_like:.2f}°C\n"
                            f"Description: {weather_desc.capitalize()}")

        await message.reply(weather_response, parse_mode=ParseMode.HTML)
    else:
        await message.reply("Sorry, I couldn't fetch the weather for that city.")


if __name__ == '__main__':
    from aiogram import executor
    executor.start_polling(dp, skip_updates=True)
