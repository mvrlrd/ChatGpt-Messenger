package ru.mvrlrd.companion

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.mvrlrd.core_android.AppProvider
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent : AppProvider {

    companion object {

        private var appComponent: AppProvider? = null
        fun create(application: Application): AppProvider =
            appComponent ?: DaggerAppComponent
                .builder()
                .application(application.applicationContext)
                .build()
    }

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(context: Context): Builder

        fun build(): AppComponent
    }
}