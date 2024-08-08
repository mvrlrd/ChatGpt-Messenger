package ru.mvrlrd.companion.navi

import dagger.Component
import ru.mvrlrd.featureapi.FeatureApiProvider

@Component(modules = [FeatureApiModule::class])
interface FeatureApiComponent: FeatureApiProvider {

}