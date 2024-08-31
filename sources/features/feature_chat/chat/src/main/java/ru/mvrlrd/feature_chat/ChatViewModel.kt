package ru.mvrlrd.feature_chat

import android.database.sqlite.SQLiteException
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mvrlrd.core_api.database.chat.entity.MessageEntity
import ru.mvrlrd.feature_chat.domain.api.ClearMessagesUseCase
import ru.mvrlrd.feature_chat.domain.api.DeleteMessageUseCase
import ru.mvrlrd.feature_chat.domain.api.GetAllMessagesForChatUseCase
import ru.mvrlrd.feature_chat.domain.api.GetAnswerUseCase
import ru.mvrlrd.feature_chat.domain.api.GetChatSettingsUseCase
import ru.mvrlrd.feature_chat.domain.api.SaveMessageToChatUseCase
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val getAnswerUseCase: GetAnswerUseCase,
    private val saveMessageToChatUseCase: SaveMessageToChatUseCase,
    private val getAllMessagesForChatUseCase: GetAllMessagesForChatUseCase,
    private val deleteMessageUseCase: DeleteMessageUseCase,
    private val clearMessagesUseCase: ClearMessagesUseCase,
    private val ioDispatcher: CoroutineDispatcher,
    private val getChatSettingsUseCase: GetChatSettingsUseCase,
    private val chatId: Long

) : ViewModel() {
    var oneShotEventChannel = Channel<String>()
    private val _messageEntities = mutableStateListOf<MessageEntity>()
    val messageEntities : SnapshotStateList <MessageEntity> get() = _messageEntities



init {
    getAllMessagesForChatFromDatabase()
}

    fun sendRequest(query: String) {
            viewModelScope.launch (ioDispatcher){
                saveMessageToChatUseCase(
                    MessageEntity(
                        holderChatId = chatId,
                        text = query,
                        isReceived = false,
                        date = 1L
                    )
                )
                launch {
                    withContext(ioDispatcher) {
                        getAnswerUseCase(chatId = chatId, query = query)
                            .onSuccess {
                                if (it.text.isNotBlank()) {
                                    val received = MessageEntity(
                                        holderChatId = chatId,
                                        text = it.text,
                                        isReceived = true,
                                        date = it.date
                                    )
                                   saveMessageToChatUseCase(
                                        received
                                    )
                                }
                            }.onFailure {
                                oneShotEventChannel.send("сообщение не загружено ${it.message.toString()}")
                            }
                    }
                }
            }
    }

    private fun getChatSettings(){
        viewModelScope.launch(ioDispatcher) {
            getChatSettingsUseCase(chatId = chatId)
        }
    }

   private fun getAllMessagesForChatFromDatabase() {
        getAllMessagesForChatUseCase(chatId)
            .onEach {
                _messageEntities.clear()
                if (it.isNotEmpty()) {
                    _messageEntities.addAll(it)
                }
            }
            .catch {
                handleDatabaseError(it)
            }
            .launchIn(
                viewModelScope
            )
    }

    private suspend fun handleDatabaseError(exception: Throwable) {
        when (exception) {
            is SQLiteException -> {
                oneShotEventChannel.send("_error: ${exception.message}")
            }
            else -> {
                oneShotEventChannel.send("_error: ${exception.message}")
            }
        }
    }


    fun deleteMessageFromDatabase(messageId: Long){
        viewModelScope.launch {
            deleteMessageUseCase(messageId)
        }
    }

    companion object {
        fun createHomeViewModelFactory(
            getAnswerUseCase: GetAnswerUseCase,
            saveMessageToChatUseCase: SaveMessageToChatUseCase,
            getAllMessagesForChatUseCase: GetAllMessagesForChatUseCase,
            deleteMessageUseCase: DeleteMessageUseCase,
            clearMessagesUseCase: ClearMessagesUseCase,
            getChatSettingsUseCase: GetChatSettingsUseCase,
            chatId: Long
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ChatViewModel(
                        getAnswerUseCase,
                        saveMessageToChatUseCase,
                        getAllMessagesForChatUseCase,
                        deleteMessageUseCase,
                        clearMessagesUseCase,
                        Dispatchers.IO,
                        getChatSettingsUseCase,
                        chatId
                    ) as T
                }
            }
    }
}