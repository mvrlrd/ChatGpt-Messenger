package ru.mvrlrd.core_impl.network.di

import dagger.Component
import ru.mvrlrd.core_api.network.NetworkClientProvider

@Component(
    modules = [KtorModule::class])
interface KtorComponent : NetworkClientProvider