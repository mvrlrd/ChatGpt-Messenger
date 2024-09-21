package ru.mvrlrd.companion.navigation

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import ru.mvrlrd.chat_settings.navigation.FeatureSettingsImpl
import ru.mvrlrd.feature_chat.navigation.FeatureChatImpl
import ru.mvrlrd.feature_home.navigation.FeatureHomeImpl
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

    @Binds
    @IntoMap
    @StringKey("settings")
    fun bindFeatureSettingsApi(featureSettingsImpl: FeatureSettingsImpl): FeatureApi

}