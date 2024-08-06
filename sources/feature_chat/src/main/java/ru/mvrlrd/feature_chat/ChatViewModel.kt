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
import ru.mvrlrd.core_api.database.chat.entity.Message
import ru.mvrlrd.feature_chat.domain.api.ClearMessagesUseCase
import ru.mvrlrd.feature_chat.domain.api.DeleteMessageUseCase
import ru.mvrlrd.feature_chat.domain.api.GetAllMessagesForChatUseCase
import ru.mvrlrd.feature_chat.domain.api.GetAnswerUseCase
import ru.mvrlrd.feature_chat.domain.api.SaveMessageToChatUseCase
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val getAnswerUseCase: GetAnswerUseCase,
    private val saveMessageToChatUseCase: SaveMessageToChatUseCase,
    private val getAllMessagesForChatUseCase: GetAllMessagesForChatUseCase,
    private val deleteMessageUseCase: DeleteMessageUseCase,
    private val clearMessagesUseCase: ClearMessagesUseCase,
    private val ioDispatcher: CoroutineDispatcher,
    private val chatId: Long

) : ViewModel() {
    var oneShotEventChannel = Channel<String>()
    private val _messages = mutableStateListOf<Message>()
    val messages : SnapshotStateList <Message> get() = _messages

init {
    getAllMessagesForChatFromDatabase()
}

    fun sendRequest(query: String) {
            viewModelScope.launch (ioDispatcher){
                saveMessageToChatUseCase(
                    Message(
                        holderChatId = chatId,
                        text = query,
                        isReceived = false,
                        date = 1L
                    )
                )
                launch {
                    withContext(ioDispatcher) {
                        getAnswerUseCase(systemRole = "", query = query)
                            .onSuccess {
                                if (it.answer.isNotBlank()) {
                                    val received = Message(
                                        holderChatId = chatId,
                                        text = it.answer,
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

    private fun getAllMessagesForChatFromDatabase() {
        getAllMessagesForChatUseCase(chatId)
            .onEach {
                _messages.clear()
                if(it.isEmpty()){
                    oneShotEventChannel.send("начните с чистого листа!")
                }else{
                    _messages.addAll(it)
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
                        chatId
                    ) as T
                }
            }
    }
}