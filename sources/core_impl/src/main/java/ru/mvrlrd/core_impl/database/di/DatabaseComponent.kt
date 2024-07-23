package ru.mvrlrd.core_impl.database.di

import dagger.Component
import ru.mvrlrd.core_api.database.answer.DatabaseProvider
import ru.mvrlrd.core_api.mediators.AppProvider
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [AppProvider::class],
    modules = [DatabaseModule::class]
)
interface DatabaseComponent: DatabaseProvider