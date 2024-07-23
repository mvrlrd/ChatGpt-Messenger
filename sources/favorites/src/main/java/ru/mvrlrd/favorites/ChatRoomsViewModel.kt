package ru.mvrlrd.favorites

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.mvrlrd.favorites.domain.ChatEntity
import ru.mvrlrd.favorites.domain.api.CreateChatUseCase
import ru.mvrlrd.favorites.domain.api.GetAllChatsUseCase
import ru.mvrlrd.favorites.domain.api.RemoveChatUseCase
import javax.inject.Inject

class ChatRoomsViewModel@Inject constructor(
    private val getAllChatsUseCase: GetAllChatsUseCase,
    private val createChatUseCase: CreateChatUseCase,
    private val removeChatUseCase: RemoveChatUseCase
): ViewModel() {

//    private var _chats = MutableStateFlow<List<ChatEntity>>(emptyList())
//    val chats : StateFlow<List<ChatEntity>> get() = _chats.asStateFlow()
    val items = mutableStateListOf<ChatEntity>()


    init {
        viewModelScope.launch {
            getAllChats()
        }
    }
    fun createChat(chatEntity: ChatEntity){
        viewModelScope.launch {
            createChatUseCase(chatEntity)
        }
    }
     fun getAllChats(){
         viewModelScope.launch {
             getAllChatsUseCase().collect{
                 items.clear()
                 items.addAll(it)
             }
         }
    }
    fun removeChat(id: Long){
        viewModelScope.launch {
            removeChatUseCase(id)
        }
    }

    class MyViewModelFactory(
        private val getAllChatsUseCase: GetAllChatsUseCase,
        private val createChatUseCase: CreateChatUseCase,
        private val removeChatUseCase: RemoveChatUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ChatRoomsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ChatRoomsViewModel(getAllChatsUseCase, createChatUseCase, removeChatUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }



}