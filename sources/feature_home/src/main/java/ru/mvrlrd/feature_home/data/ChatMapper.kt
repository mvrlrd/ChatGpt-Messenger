package ru.mvrlrd.feature_home.data

import ru.mvrlrd.core_api.database.chat.entity.ChatDto
import ru.mvrlrd.core_api.database.chat.entity.CompletionOptionsDto
import ru.mvrlrd.core_api.database.chat.entity.UsageDto
import ru.mvrlrd.feature_home.domain.ChatEntity
import ru.mvrlrd.feature_home.domain.CompletionOptionsEntity
import ru.mvrlrd.feature_home.domain.UsageEntity
import javax.inject.Inject

class ChatMapper @Inject constructor() {
    fun mapEntityToDto(entity: ChatEntity): ChatDto {
        return with(entity) {
            ChatDto(
                id = chatId,
                title = title,
                role = role,
                completionOptions = mapCompletionOptionsEntityToDto(completionOptionsEntity = completionOptions),
                modelVer = modelVer,
                usage = mapUsageEntityToDto(usageEntity = usageEntity),
                date = date,
            )
        }
    }

    private fun mapCompletionOptionsEntityToDto(completionOptionsEntity: CompletionOptionsEntity): CompletionOptionsDto {
        return with(completionOptionsEntity) {
            CompletionOptionsDto(
                stream = stream,
                temperature = temperature,
                maxTokens = maxTokens
            )
        }
    }

    private fun mapUsageEntityToDto(usageEntity: UsageEntity): UsageDto {
        return with(usageEntity) {
            UsageDto(
                inputTokens = inputTokens,
                completionTokens = completionTokens,
                totalTokens = totalTokens
            )
        }
    }

    private fun mapCompletionOptionsDtoToEntity(dto: CompletionOptionsDto): CompletionOptionsEntity {
        return with(dto) {
            CompletionOptionsEntity(
                stream = stream,
                temperature = temperature,
                maxTokens = maxTokens
            )
        }
    }

    private fun mapUsageDtoToEntity(dto: UsageDto): UsageEntity {
        return with(dto) {
            UsageEntity(
                inputTokens = inputTokens,
                completionTokens = completionTokens,
                totalTokens = totalTokens
            )
        }
    }

    fun mapDtoToEntity(chatDto: ChatDto): ChatEntity{
        return with(chatDto) {
            ChatEntity(
                chatId = id,
                title = title,
                role = role,
                completionOptions = mapCompletionOptionsDtoToEntity(dto = completionOptions),
                modelVer = modelVer,
                usageEntity = mapUsageDtoToEntity(dto = usage),
                date = date,
            )
        }
    }
    fun mapDtosToEntities(chatDtos: List<ChatDto>):List<ChatEntity>{
        return chatDtos.map { chat-> mapDtoToEntity(chat) }
    }
}