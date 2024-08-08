package ru.mvrlrd.chat_settings.data

import ru.mvrlrd.base_chat_home.model.Chat
import ru.mvrlrd.base_chat_home.model.CompletionOptions
import ru.mvrlrd.base_chat_home.model.Usage
import ru.mvrlrd.chat_settings.domain.ChatSettings
import ru.mvrlrd.core_api.database.chat.entity.ChatEntity
import ru.mvrlrd.core_api.database.chat.entity.CompletionOptionsEntity
import ru.mvrlrd.core_api.database.chat.entity.UsageEntity
import javax.inject.Inject
import kotlin.math.roundToInt

class SettingsMapper@Inject constructor() {
    fun mapSettingsToChat(chatSettings: ChatSettings): ChatEntity{
        return with(chatSettings) {
            ChatEntity(
                id = 0L, //
                title = name,
                roleText = systemRole,
                completionOptions = CompletionOptionsEntity(
                    stream,
                    (temperature.value.toDouble() * 10).roundToInt() / 10.0,
                    maxTokens.value.toInt()
                ),
                modelVer = "", //
                usage = UsageEntity(0,0,0),//
                date = 1L //
            )
        }
    }
}