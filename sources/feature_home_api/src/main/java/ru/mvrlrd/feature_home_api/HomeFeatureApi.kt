package ru.mvrlrd.feature_home_api

import ru.mvrlrd.featureapi.FeatureApi

interface HomeFeatureApi : FeatureApi{
    val homeRoute: String
}