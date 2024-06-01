package ru.mvrlrd.companion

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canlioya.pullrefreshcomposesample.pullrefresh.PullToRefreshLayoutState
import com.canlioya.pullrefreshcomposesample.pullrefresh.RefreshIndicatorState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.mvrlrd.companion.pull.pullrefresh.ColorItemDataSource
import javax.inject.Inject
import ru.mvrlrd.companion.pull.pullrefresh.Result.*

//class MainViewModel(private val remoteRepository: RemoteRepository): ViewModel() {
//    private var _responseAnswer = MutableLiveData<String>()
//    val responseAnswer: LiveData<String> = _responseAnswer
//
//
//    fun sendRequest(query: String){
//        viewModelScope.launch {
//            val answer = remoteRepository.getAnswer("", query)
//            _responseAnswer.postValue(answer)
//        }
//    }
//
//    companion object {
//        fun createMainViewModelFactory(remoteRepository: RemoteRepository): ViewModelProvider.Factory =
//            object : ViewModelProvider.Factory {
//                override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                    return MainViewModel(remoteRepository) as T
//                }
//            }
//    }
//}

class MainViewModel @Inject constructor(
) : ViewModel() {

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

                    Loading -> {
                        if (loadType == UiState.LoadingType.INITIAL_LOAD) {
                            UiState.Loading
                        } else {

                            _uiState.value
                            UiState.Success
                        }
                    }

                    is Error -> {
                        pullToRefreshState.updateRefreshState(RefreshIndicatorState.Default)
                        UiState.Error
                    }
                    is Success -> {
                        Log.d("TAG","SUCCESSSSS")
                            if (loadType == UiState.LoadingType.PULL_REFRESH) {
                                pullToRefreshState.updateRefreshState(RefreshIndicatorState.Default)
                                scrollState.scrollToItem(0)
                            }
                            UiState.Success

                    }
                }
            }.collectLatest { result ->
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