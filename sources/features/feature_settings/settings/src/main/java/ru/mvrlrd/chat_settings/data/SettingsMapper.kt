package ru.mvrlrd.chat_settings.data

import ru.mvrlrd.chat_settings.MaxTokens
import ru.mvrlrd.chat_settings.Temperature
import ru.mvrlrd.chat_settings.domain.ChatSettings
import ru.mvrlrd.core_api.database.chat.entity.ChatEntity
import ru.mvrlrd.core_api.database.chat.entity.CompletionOptionsEntity
import ru.mvrlrd.core_api.database.chat.entity.UsageEntity
import javax.inject.Inject
import kotlin.math.roundToInt

class SettingsMapper @Inject constructor() {
    fun mapSettingsToChatEntity(chatSettings: ChatSettings): ChatEntity {
        return with(chatSettings) {
            ChatEntity(
                id = chatId,
                title = name,
                roleText = systemRole,
                prompt = prompt,
                completionOptions = CompletionOptionsEntity(
                    stream,
                    (temperature.value.toDouble()),
                    maxTokens.value.toInt()
                ),
                modelVer = "", //
                usage = UsageEntity(0,0,0),//
                date = 1L//
            )
        }
    }
    fun mapChatEntityToSettings(chat: ChatEntity): ChatSettings{
        return with(chat){
            ChatSettings(
                chatId = 0L, //
                name = title,
                systemRole = roleText,
                prompt = prompt,
                stream=completionOptions.stream,
                temperature= Temperature(completionOptions.temperature.toFloat()),
                maxTokens= MaxTokens(completionOptions.maxTokens.toString())
            )
        }
    }
}