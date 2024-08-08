package ru.mvrlrd.chat_settings

data class SettingsState(
    val name: String="",
    val systemRole: String="",
    val maxTokens: MaxTokens= MaxTokens(""),//устанавливает ограничение на выход модели в токенах.
    val stream: Boolean=false,// включает потоковую передачу частично сгенерированного текста.
    val temperature: Temperature= Temperature(0.3f)//чем выше значение этого параметра, тем более креативными и случайными будут ответы модели.

    //role — роль отправителя сообщения:
    //
    //user — предназначена для отправки пользовательских сообщений к модели.
    //system — позволяет задать контекст запроса и определить поведение модели.
    //assistant — используется для ответов, которые генерирует модель. При работе в режиме чата ответы модели, помеченные ролью assistant, включаются в состав сообщения для сохранения контекста беседы. Не передавайте сообщения пользователя с этой ролью.
    //text — текстовое содержимое сообщения.
)
