package ru.mvrlrd.companion

import android.app.Application
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import ru.mvrlrd.core_api.mediators.AppWithFacade
import ru.mvrlrd.core_api.mediators.ProvidersFacade
import ru.mvrlrd.featureapi.FeatureApi

class App : Application(), AppWithFacade {

    override fun getFacade(): ProvidersFacade {
        return facadeComponent ?: FacadeComponent.init(this).also {
            facadeComponent = it
        }
    }

    companion object {
        private var facadeComponent: FacadeComponent? = null
    }

    override fun onCreate() {
        super.onCreate()
        getFacade()
    }


}