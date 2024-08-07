package ru.mvrlrd.base_chat_home.model

import ru.mvrlrd.core_api.database.chat.entity.ChatEntity
import ru.mvrlrd.core_api.database.chat.entity.CompletionOptionsEntity
import ru.mvrlrd.core_api.database.chat.entity.UsageEntity
import ru.mvrlrd.base_chat_home.model.Chat
import ru.mvrlrd.base_chat_home.model.CompletionOptions
import ru.mvrlrd.base_chat_home.model.Usage
import javax.inject.Inject

class ChatMapper @Inject constructor() {
    fun mapChatToChatEntity(entity: Chat): ChatEntity {
        return with(entity) {
            ChatEntity(
                id = chatId,
                title = title,
                role = role,
                completionOptions = mapCompletionOptionsToCompletionOptionsEntity(completionOptions = completionOptions),
                modelVer = modelVer,
                usage = mapUsageToUsageEntity(usage = usage),
                date = date,
            )
        }
    }

    private fun mapCompletionOptionsToCompletionOptionsEntity(completionOptions: CompletionOptions): CompletionOptionsEntity {
        return with(completionOptions) {
            CompletionOptionsEntity(
                stream = stream,
                temperature = temperature,
                maxTokens = maxTokens
            )
        }
    }

    private fun mapUsageToUsageEntity(usage: Usage): UsageEntity {
        return with(usage) {
            UsageEntity(
                inputTokens = inputTokens,
                completionTokens = completionTokens,
                totalTokens = totalTokens
            )
        }
    }

    private fun mapCompletionOptionsEntityToCompletionOptions(completionOptionsEntity: CompletionOptionsEntity): CompletionOptions {
        return with(completionOptionsEntity) {
            CompletionOptions(
                stream = stream,
                temperature = temperature,
                maxTokens = maxTokens
            )
        }
    }

    private fun mapUsageEntityToUsage(usageEntity: UsageEntity): Usage {
        return with(usageEntity) {
            Usage(
                inputTokens = inputTokens,
                completionTokens = completionTokens,
                totalTokens = totalTokens
            )
        }
    }

    fun mapChatEntityToChat(chatEntity: ChatEntity): Chat {
        return with(chatEntity) {
            Chat(
                chatId = id,
                title = title,
                role = role,
                completionOptions = mapCompletionOptionsEntityToCompletionOptions(
                    completionOptionsEntity = completionOptions
                ),
                modelVer = modelVer,
                usage = mapUsageEntityToUsage(usageEntity = usage),
                date = date,
            )
        }
    }
    fun mapChatEntitiesToChats(chatEntities: List<ChatEntity>):List<Chat>{
        return chatEntities.map { chat-> mapChatEntityToChat(chat) }
    }
}