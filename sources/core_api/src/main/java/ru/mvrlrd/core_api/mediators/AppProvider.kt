package ru.mvrlrd.core_api.mediators

import android.content.Context

interface AppProvider {

    fun provideContext(): Context
}