package ru.mvrlrd.ktor_module.di

import dagger.Component
import ru.mvrlrd.core_api.mediators.NetworkClientProvider

@Component(
    modules = [KtorModule::class])
interface KtorComponent : NetworkClientProvider