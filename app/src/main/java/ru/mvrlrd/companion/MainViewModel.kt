package ru.mvrlrd.companion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mvrlrd.core_api.network.RemoteRepository

class MainViewModel(private val remoteRepository: RemoteRepository): ViewModel() {
    private var _responseAnswer = MutableLiveData<String>()
    val responseAnswer: LiveData<String> = _responseAnswer


    fun sendRequest(query: String){
        viewModelScope.launch {
            val answer = remoteRepository.getAnswer("", query)
            _responseAnswer.postValue(answer)
        }
    }

    companion object {
        fun createMainViewModelFactory(remoteRepository: RemoteRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(remoteRepository) as T
                }
            }
    }

}