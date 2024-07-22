package ru.mvrlrd.main

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canlioya.pullrefreshcomposesample.pullrefresh.PullToRefreshLayoutState
import com.canlioya.pullrefreshcomposesample.pullrefresh.RefreshIndicatorState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.mvrlrd.core_api.database.entity.Answer
import ru.mvrlrd.main.pullrefresh.ColorItemDataSource
import ru.mvrlrd.main.pullrefresh.Result
import javax.inject.Inject

class MainViewModel @Inject constructor() :
    ViewModel() {


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

                        UiState.Success(listOf(Answer(1L,"hello?","Lalalala"),Answer(2L,"question?","answer") ))
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