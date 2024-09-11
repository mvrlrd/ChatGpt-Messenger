package ru.mvrlrd.feature_chat.pullrefresh

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshLayout(
    modifier: Modifier = Modifier,
    pullRefreshLayoutState: PullToRefreshLayoutState,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit,
) {
    val refreshIndicatorState by pullRefreshLayoutState.refreshIndicatorState
    val timeElapsedSinceLastRefresh by pullRefreshLayoutState.lastRefreshText




    val pullToRefreshState = androidx.compose.material3.pulltorefresh.rememberPullToRefreshState(
        positionalThreshold = 120.dp,
    ) {
        onRefresh()
        pullRefreshLayoutState.refresh()
        true
    }


//
//    val pullToRefreshState =  rememberPullRefreshState(
//        refreshing = refreshIndicatorState == RefreshIndicatorState.Refreshing,
//        refreshThreshold = 120.dp,
//        onRefresh = {
//            onRefresh()
//            pullRefreshLayoutState.refresh()
//        },
//    )

    LaunchedEffect(key1 = pullToRefreshState.progress) {
        when {
            pullToRefreshState.progress >= 1 -> {
                pullRefreshLayoutState.updateRefreshState(RefreshIndicatorState.ReachedThreshold)
            }
            pullToRefreshState.progress > 0 -> {
                pullRefreshLayoutState.updateRefreshState(RefreshIndicatorState.PullingDown)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
//            .pullRefresh(pullToRefreshState)
            .background(MaterialTheme.colorScheme.onPrimary),
    ) {
        PullToRefreshIndicator(
            indicatorState = refreshIndicatorState,
            pullToRefreshProgress = pullToRefreshState.progress,
            timeElapsed = timeElapsedSinceLastRefresh,
        )
        Box(modifier = Modifier.weight(1f),) {
            content()
        }
    }
}
