package ru.mvrlrd.home

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.mvrlrd.core_api.database.answer.entity.Answer
import ru.mvrlrd.core_api.database.chat.entity.Message
import ru.mvrlrd.core_api.network.RemoteRepository
import ru.mvrlrd.core_api.network.dto.ServerResponse
import ru.mvrlrd.home.domain.api.ClearMessagesUseCase
import ru.mvrlrd.home.domain.api.DeleteMessageUseCase
import ru.mvrlrd.home.domain.api.GetAllMessagesForChatUseCase
import ru.mvrlrd.home.domain.api.GetAnswerUseCase
import ru.mvrlrd.home.domain.api.SaveMessageToChatUseCase
import ru.mvrlrd.home.pullrefresh.ColorItemDataSource
import ru.mvrlrd.home.pullrefresh.RefreshIndicatorState
import ru.mvrlrd.home.pullrefresh.Result
import ru.mvrlrd.main.pullrefresh.PullToRefreshLayoutState
import java.util.Date
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getAnswerUseCase: GetAnswerUseCase,
    private val saveMessageToChatUseCase: SaveMessageToChatUseCase,
    private val getAllMessagesForChatUseCase: GetAllMessagesForChatUseCase,
    private val deleteMessageUseCase: DeleteMessageUseCase,
    private val clearMessagesUseCase: ClearMessagesUseCase,
    private val chatId: Long

) : ViewModel() {
    val oneShotEventChannel = Channel<String>()
    private val _messages = mutableStateListOf<Message>()
    val messages : SnapshotStateList <Message> get() = _messages

init {
    getAllMessagesForChat(chatId)
}

    fun sendRequest(query: String) {
            viewModelScope.launch {
                saveMessageToChat(
                    Message(
                        holderChatId = chatId,
                        text = query,
                        isReceived = false
                    )
                )
                launch {
                    getAnswerUseCase(systemRole = "", query =  query)
                        .onSuccess {

                            val text = (it as ServerResponse).result.alternatives[0].message.text
                            saveMessageToChat(
                                Message(
                                    holderChatId = chatId,
                                    text = text,
                                    isReceived = true
                                )
                            )
                        }.onFailure {
                            oneShotEventChannel.send(it.message.toString())
                        }
                }
            }
    }

   private fun saveMessageToChat(message: Message){
        viewModelScope.launch {
            Log.d("TAG","message saved ${message.text}")
            saveMessageToChatUseCase(message)
        }
    }
    private fun getAllMessagesForChat(chatId: Long){
        viewModelScope.launch {
//            _isLoading.postValue(true)
            getAllMessagesForChatUseCase(chatId).collect{
                _messages.clear()
                _messages.addAll(it)
//                delay(300)
//                _isLoading.postValue(false)
            }
        }
    }

    fun deleteMessage(messageId: Long){
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
                    return HomeViewModel(
                        getAnswerUseCase,
                        saveMessageToChatUseCase,
                        getAllMessagesForChatUseCase,
                        deleteMessageUseCase,
                        clearMessagesUseCase,
                        chatId
                    ) as T
                }
            }
    }


//////


    private val colorItemDataSource = ColorItemDataSource()

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    val pullToRefreshState = PullToRefreshLayoutState(
        onTimeUpdated = { timeElapsed ->
            convertElapsedTimeIntoText(timeElapsed)
        },
    )



    val scrollState = LazyListState()

    init {
//        fetchData(UiState.LoadingType.INITIAL_LOAD)
        viewModelScope.launch {
            _uiState.emit(UiState.Initial)
        }


    }

    private fun fetchData(loadType: UiState.LoadingType) {
        viewModelScope.launch {
            colorItemDataSource.getColorList().map { result ->
                Log.d("TAG", "fetchData     result= $result  loadType = $loadType")
                when (result) {

                    is Result.Loading -> {
                        if (loadType == UiState.LoadingType.INITIAL_LOAD) {
                            UiState.Loading
                        } else {
                            _uiState.value
                            UiState.Success(emptyList())
                        }
                    }

                    is Result.Error -> {
                        pullToRefreshState.updateRefreshState(RefreshIndicatorState.Default)
                        UiState.Error
                    }
                    is Result.Success -> {
                        Log.d("TAG","SUCCESSSSS")
//                            if (loadType == UiState.LoadingType.PULL_REFRESH) {
//                                pullToRefreshState.updateRefreshState(RefreshIndicatorState.Default)
//                                scrollState.scrollToItem(0)
//                            }

                        UiState.Success(listOf(
                            Answer(1L,"hello?","Lalalala"),
                            Answer(2L,"question?","answer") ))
                    }
                }
            }.collect() { result ->
                Log.d("TAG","___ collectLatest =  $result")
                _uiState.value = result
            }
        }
    }

    fun refresh() {
        Log.d("TAG", "__refresh")
        fetchData(UiState.LoadingType.PULL_REFRESH)
    }

    fun convertElapsedTimeIntoText(timeElapsed: Long): String {
        return "convertElapsedTimeIntoText"
    }
}