package ru.mvrlrd.home

import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mvrlrd.core_api.database.answer.entity.Answer
import ru.mvrlrd.core_api.database.chat.entity.Message
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
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getAnswerUseCase: GetAnswerUseCase,
    private val saveMessageToChatUseCase: SaveMessageToChatUseCase,
    private val getAllMessagesForChatUseCase: GetAllMessagesForChatUseCase,
    private val deleteMessageUseCase: DeleteMessageUseCase,
    private val clearMessagesUseCase: ClearMessagesUseCase,
    private val ioDispatcher: CoroutineDispatcher,
    private val chatId: Long

) : ViewModel() {
    val oneShotEventChannel = Channel<String>()
    private val _messages = mutableStateListOf<Message>()
    val messages : SnapshotStateList <Message> get() = _messages



init {
    getAllMessagesForChatFromDatabase(chatId)
}

    fun sendRequest(query: String) {

            viewModelScope.launch (ioDispatcher){
                saveMessageToChatInDatabase(
                    Message(
                        holderChatId = chatId,
                        text = query,
                        isReceived = false
                    )
                )
                launch {
                    withContext(ioDispatcher){
                        getAnswerUseCase(systemRole = "", query =  query)
                            .onSuccess {
                                if (it.answer.isNotBlank()){
                                    val recieved =  Message(
                                        holderChatId = chatId,
                                        text = it.answer,
                                        isReceived = true,

                                    )
                                    //как протестировать что в этом месте получили то что нужно?
                                    saveMessageToChatInDatabase(
                                       recieved
                                    )
                                }
                            }.onFailure {
                                oneShotEventChannel.send("сообщение не загружено ${it.message.toString()}")
                            }
                    }

                }
            }
    }

   private fun saveMessageToChatInDatabase(message: Message){
        viewModelScope.launch {
//            Log.d("TAG","message saved ${message.text}")
            saveMessageToChatUseCase(message)
        }
    }
    private fun getAllMessagesForChatFromDatabase(chatId: Long) {
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
//                Log.e("ChatViewModel", "SQLiteException: ${exception.message}")
            }
            else -> {
                oneShotEventChannel.send("_error: ${exception.message}")
//                Log.e("ChatViewModel", "An unexpected error occurred: ${exception.message}")
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
                    return HomeViewModel(
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