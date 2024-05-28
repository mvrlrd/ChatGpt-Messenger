package ru.mvrlrd.companion

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component()
interface AppComponent {

    companion object {
        fun init(): AppComponent =
            DaggerAppComponent.builder().build()
    }
}