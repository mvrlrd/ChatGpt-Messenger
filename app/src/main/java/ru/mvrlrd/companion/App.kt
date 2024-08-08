package ru.mvrlrd.companion

import android.app.Application
import ru.mvrlrd.core_api.mediators.AppWithFacade
import ru.mvrlrd.core_api.mediators.ProvidersFacade

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