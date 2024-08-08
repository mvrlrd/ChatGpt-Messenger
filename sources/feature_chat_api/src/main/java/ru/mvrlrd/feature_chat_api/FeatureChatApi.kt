package ru.mvrlrd.feature_chat_api


import ru.mvrlrd.featureapi.FeatureApi

interface FeatureChatApi: FeatureApi {
    val chatRoute: String
}