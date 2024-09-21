package ru.mvrlrd.feature_home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mvrlrd.feature_home.domain.ChatForHome
import ru.mvrlrd.feature_home.domain.api.GetAllChatsUseCase
import ru.mvrlrd.feature_home.domain.api.RemoveChatUseCase
import javax.inject.Inject

class HomeViewModel@Inject constructor(
    private val getAllChatsUseCase: GetAllChatsUseCase,
    private val removeChatUseCase: RemoveChatUseCase
): ViewModel() {

    private val _items = mutableStateListOf<ChatForHome>()
    val items : SnapshotStateList<ChatForHome> get()=_items

    init {
        viewModelScope.launch {
            getAllChats()
        }
    }

     private fun getAllChats(){
         viewModelScope.launch {
             getAllChatsUseCase().collect{
                 _items.clear()
                 _items.addAll(it)
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
        private val removeChatUseCase: RemoveChatUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(getAllChatsUseCase, removeChatUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}