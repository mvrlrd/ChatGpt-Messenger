package ru.mvrlrd.base_chat_home.model

import ru.mvrlrd.core_api.database.chat.entity.ChatEntity
import ru.mvrlrd.core_api.database.chat.entity.CompletionOptionsEntity
import ru.mvrlrd.core_api.database.chat.entity.UsageEntity
import javax.inject.Inject

class ChatMapper @Inject constructor() {


     fun mapCompletionOptionsToCompletionOptionsEntity(completionOptions: CompletionOptions): CompletionOptionsEntity {
        return with(completionOptions) {
            CompletionOptionsEntity(
                stream = stream,
                temperature = temperature,
                maxTokens = maxTokens
            )
        }
    }

     fun mapCompletionOptionsEntityToCompletionOptions(completionOptionsEntity: CompletionOptionsEntity): CompletionOptions {
        return with(completionOptionsEntity) {
            CompletionOptions(
                stream = stream,
                temperature = temperature,
                maxTokens = maxTokens
            )
        }
    }

     fun mapUsageEntityToUsage(usageEntity: UsageEntity): Usage {
        return with(usageEntity) {
            Usage(
                inputTokens = inputTokens,
                completionTokens = completionTokens,
                totalTokens = totalTokens
            )
        }
    }

    fun mapUsageToUsageEntity(usage: Usage): UsageEntity {
        return with(usage) {
            UsageEntity(
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
                roleText = roleText,
                prompt = prompt,
                completionOptions = mapCompletionOptionsEntityToCompletionOptions(
                    completionOptionsEntity = completionOptions
                ),
                modelVer = modelVer,
                usage = mapUsageEntityToUsage(usageEntity = usage),
                date = date,
            )
        }
    }

    fun mapChatToChatEntity(entity: Chat): ChatEntity {
        return with(entity) {
            ChatEntity(
                id = chatId,
                title = title,
                roleText= roleText,
                prompt = prompt,
                completionOptions = mapCompletionOptionsToCompletionOptionsEntity(completionOptions = completionOptions),
                modelVer = modelVer,
                usage = mapUsageToUsageEntity(usage = usage),
                date = date,
            )
        }
    }

    fun mapChatEntitiesToChats(chatEntities: List<ChatEntity>):List<Chat>{
        return chatEntities.map { chat-> mapChatEntityToChat(chat) }
    }
}