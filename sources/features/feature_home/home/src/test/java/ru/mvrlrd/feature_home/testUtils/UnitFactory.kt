package ru.mvrlrd.feature_home.testUtils

import ru.mvrlrd.base_chat_home.model.Chat
import ru.mvrlrd.base_chat_home.model.CompletionOptions
import ru.mvrlrd.base_chat_home.model.Usage
import ru.mvrlrd.core_api.database.chat.entity.ChatEntity
import ru.mvrlrd.core_api.database.chat.entity.CompletionOptionsEntity
import ru.mvrlrd.core_api.database.chat.entity.UsageEntity


object UnitFactory {
    fun getChatEnt(): ChatEntity = ChatEntity(
        id = 1L,
        title = "chat#1",
        roleText = "assistant",
        completionOptions = getCompletionOptionsEntity(),
        modelVer = "1.0",
        usage = getUsageEntity(),
    )
    fun getChat(): Chat = Chat(
        chatId = 1L,
        title = "chat#1",
        roleText = "assistant",
        completionOptions = getCompletionOptions(),
        modelVer = "1.0",
        usage = getUsage(),
    )

    fun getCompletionOptionsEntity() =
         CompletionOptionsEntity(
             stream = true,
             temperature = 0.7,
             maxTokens = 100
         )
    fun getUsageEntity() =
        UsageEntity(
            inputTokens = 1000,
            completionTokens = 500,
            totalTokens = 1500
        )

    fun getCompletionOptions() = CompletionOptions(
        stream = true,
        temperature = 0.7,
        maxTokens = 100
    )

    fun getUsage() = Usage(
        inputTokens = 1000,
        completionTokens = 500,
        totalTokens = 1500
    )

}