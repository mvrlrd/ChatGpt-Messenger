package ru.mvrlrd.feature_home_api

import ru.mvrlrd.featureapi.FeatureApi

interface FeatureHomeApiProvider {
    fun provideFeatureHomeApi():FeatureApi
}