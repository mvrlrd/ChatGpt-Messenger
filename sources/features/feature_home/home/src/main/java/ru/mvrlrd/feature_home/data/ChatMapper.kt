package ru.mvrlrd.feature_home.data

import ru.mvrlrd.core_api.database.chat.entity.ChatEntity
import ru.mvrlrd.feature_home.domain.ChatForHome
import javax.inject.Inject

class ChatMapper@Inject constructor() {

    fun mapChatEntityToChatForHome(chat: ChatEntity, color: Int): ChatForHome{
        return with(chat){
            ChatForHome(
                chatId = id,
                title = title,
                role = roleText,
                prompt = prompt,
                color = color
            )
        }
    }
}