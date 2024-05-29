package ru.mvrlrd.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mvrlrd.core_api.network.RemoteRepository

class HomeViewModel(private val remoteRepository: RemoteRepository): ViewModel() {
    private var _responseAnswer = MutableLiveData<String>()
    val responseAnswer: LiveData<String> = _responseAnswer
    private var lastRequest: String = ""

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun sendRequest(query: String) {
        if (isNotSameRequest(query)) {
            viewModelScope.launch {
                _isLoading.postValue(true)
                val answer = remoteRepository.getAnswer("", query)
                _responseAnswer.postValue(answer)
                _isLoading.postValue(false)
            }
        }
    }

    private fun isNotSameRequest(query: String): Boolean{
        if (lastRequest == query) return false
        lastRequest = query
        return true
    }

    companion object {
        fun createHomeViewModelFactory(remoteRepository: RemoteRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HomeViewModel(remoteRepository) as T
                }
            }
    }
}