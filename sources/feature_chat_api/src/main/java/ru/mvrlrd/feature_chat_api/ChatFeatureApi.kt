package ru.mvrlrd.feature_chat_api


import ru.mvrlrd.featureapi.FeatureApi

interface ChatFeatureApi: FeatureApi {
    val chatRoute: String
}