# VK-bot

Бот отвечает на сообщения вида *<Текст сообщения пользователя>* следующим образом: 

*Вы сказали: <Текст сообщения пользователя>*

## Запуск приложения
1. В директории *TestBot/src/main/resources/* создать файл **config.properties**
2. Внести в файл **config.properties** следующие параметры конфигурации: **groupId**, равный id вк-сообщества, и **accessToken**, равный ключу доступа сообщества
3. Запустить **TestBot/src/main/java/com/ladalada/Server**
