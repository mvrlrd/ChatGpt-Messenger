package ru.mvrlrd.feature_chat.pullrefresh


enum class RefreshIndicatorState( val messageRes: String) {
    Default("R.string.pull_to_refresh_complete_label"),
    PullingDown("R.string.pull_to_refresh_pull_label"),
    ReachedThreshold("R.string.pull_to_refresh_release_label"),
    Refreshing("R.string.pull_to_refresh_refreshing_label")
}