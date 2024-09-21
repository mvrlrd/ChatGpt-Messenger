package ru.mvrlrd.featureapi

interface FeatureApiProvider {
    fun provideMediatorsMap(): Map<String,@JvmSuppressWildcards  FeatureApi>
}