package ru.mvrlrd.feature_home_api

import ru.mvrlrd.featureapi.FeatureApi

interface FeatureHomeApi : FeatureApi{
     val homeRoute: String
}