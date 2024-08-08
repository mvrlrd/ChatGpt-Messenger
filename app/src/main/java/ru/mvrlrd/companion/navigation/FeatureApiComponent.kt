package ru.mvrlrd.companion.navigation

import dagger.Component
import ru.mvrlrd.featureapi.FeatureApiProvider

@Component(modules = [FeatureApiModule::class])
interface FeatureApiComponent: FeatureApiProvider