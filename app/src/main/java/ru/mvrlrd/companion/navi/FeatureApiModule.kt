package ru.mvrlrd.companion.navi

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import ru.mvrlrd.feature_chat.navi.FeatureChatImpl
import ru.mvrlrd.feature_home.navi.FeatureHomeImpl
import ru.mvrlrd.featureapi.FeatureApi

@Module
interface FeatureApiModule {
    @Binds
    @IntoMap
    @StringKey("chat")
    fun bindFeatureChatApi(featureChatImpl: FeatureChatImpl): FeatureApi
    @Binds
    @IntoMap
    @StringKey("home")
    fun bindFeatureHomeApi(featureHomeImpl: FeatureHomeImpl): FeatureApi

}