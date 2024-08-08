package ru.mvrlrd.settings_api

import ru.mvrlrd.featureapi.FeatureApi

interface FeatureSettingsApi: FeatureApi {
    val settingsRoute: String
}