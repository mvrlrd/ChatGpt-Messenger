package ru.mvrlrd.ktor_module.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.mvrlrd.core_api.network.NetworkClient
import ru.mvrlrd.core_api.network.RemoteRepository
import ru.mvrlrd.ktor_module.KtorClient
import ru.mvrlrd.ktor_module.RemoteRepositoryImp

@Module
interface KtorModule {

    @Binds
    fun bindRemoteRepository(repository: RemoteRepositoryImp): RemoteRepository

    @Binds
    fun bindNetworkClient(ktorClient: KtorClient): NetworkClient

    companion object {
        @Provides
        fun provideHttpClient(): HttpClient =
            HttpClient(CIO) {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    })
                }
            }
    }
}