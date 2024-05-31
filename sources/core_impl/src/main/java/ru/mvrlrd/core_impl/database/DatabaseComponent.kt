package ru.mvrlrd.core_impl.database

import dagger.Component
import ru.mvrlrd.core_api.database.DatabaseProvider
import ru.mvrlrd.core_api.mediators.AppProvider
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [AppProvider::class],
    modules = [DatabaseModule::class]
)
interface DatabaseComponent: DatabaseProvider