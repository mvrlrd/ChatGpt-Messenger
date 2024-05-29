package ru.mvrlrd.core_android

import android.content.Context

interface AppProvider {
    fun provideContext(): Context
}