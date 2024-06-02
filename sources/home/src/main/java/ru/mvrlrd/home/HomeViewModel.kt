package ru.mvrlrd.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import ru.mvrlrd.core_api.database.entity.Answer
import ru.mvrlrd.core_api.network.RemoteRepository
import ru.mvrlrd.home.domain.api.GetFavoritesAnswersUseCase
import ru.mvrlrd.home.domain.api.SaveAnswerUseCase
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val getFavoritesAnswersUseCase: GetFavoritesAnswersUseCase,
    private val saveAnswerUseCase: SaveAnswerUseCase
) : ViewModel() {
    private var _responseAnswer = MutableLiveData<String>()
    val responseAnswer: LiveData<String> = _responseAnswer
    private var lastRequest: String = ""

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val oneShotEventChannel = Channel<String>()

    fun sendRequest(query: String) {
        if (query.isNotBlank() && isNotSameRequest(query)) {
            viewModelScope.launch {
                _isLoading.postValue(true)
                val responseText = remoteRepository.getAnswer("", query)
                _responseAnswer.postValue(responseText)
                _isLoading.postValue(false)
                val answer = Answer(question =  query, answerText = responseText)
                saveAnswer(answer)
            }
        }else{
            val message = if (query.isBlank()){
                "пустой запрос"
            }else {
                "введите новый запрос"
            }
            viewModelScope.launch {
                oneShotEventChannel.send(message)
            }
        }
    }

    private suspend fun saveAnswer(answer: Answer){
        viewModelScope.launch {
            val id =  saveAnswerUseCase(answer)

            Log.d("TAG", "viewModel_______ saved = $id")
        }
    }
    private suspend fun getFavoriteAnswers(){
        viewModelScope.launch {
            getFavoritesAnswersUseCase()
        }
    }

    private fun isNotSameRequest(query: String): Boolean{
        if (lastRequest == query) return false
        lastRequest = query
        return true
    }

    companion object {
        fun createHomeViewModelFactory(
            remoteRepository: RemoteRepository,
            getFavoritesAnswersUseCase: GetFavoritesAnswersUseCase,
            saveAnswerUseCase: SaveAnswerUseCase
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HomeViewModel(
                        remoteRepository,
                        getFavoritesAnswersUseCase,
                        saveAnswerUseCase
                    ) as T
                }
            }
    }
}