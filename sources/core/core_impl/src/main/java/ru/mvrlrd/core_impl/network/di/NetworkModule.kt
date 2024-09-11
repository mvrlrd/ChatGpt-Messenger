package ru.mvrlrd.core_impl.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import ru.mvrlrd.core_api.network.NetworkClient
import ru.mvrlrd.core_api.network.RemoteRepository
import ru.mvrlrd.core_impl.BuildConfig
import ru.mvrlrd.core_impl.network.RemoteRepositoryImp
import ru.mvrlrd.core_impl.network.ApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import ru.mvrlrd.core_impl.network.RetrofitClient
import javax.inject.Named

@Module
interface NetworkModule {
    @Binds
    fun bindRemoteRepository(repository: RemoteRepositoryImp): RemoteRepository

    @Binds
    fun bindNetworkClient(retrofit: RetrofitClient): NetworkClient

    companion object{
        @Provides
        @Named("modelUrl")
        fun provideModelUrl() = "gpt://${BuildConfig.FOLDER_ID}/yandexgpt-lite"

        @Provides
        fun provideClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient{
           return OkHttpClient.Builder()
               .addInterceptor{
                   chain ->
                   val original = chain.request()
                   val request = original.newBuilder()
                       .header("Authorization", "Api-Key ${BuildConfig.API_KEY}")
                       .header("Content-Type", "application/json")
                       .header("x-folder-id", BuildConfig.FOLDER_ID)
                       .method(original.method, original.body)
                       .build()
                   chain.proceed(request)
               }
               .addInterceptor(loggingInterceptor)
               .build()
       }

        @Provides
        fun provideLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        @Provides
        fun provideRetrofit(client: OkHttpClient, converterFactory: Converter.Factory,): Retrofit{
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(converterFactory)
                .build()
        }

        @Provides
        fun provideConverterFactory(): Converter.Factory{
            val contentType = "application/json".toMediaType()
            return Json{ignoreUnknownKeys = true}.asConverterFactory(contentType)
        }

        @Provides
        fun provideApiService(retrofit: Retrofit): ApiService{
            return retrofit.create(ApiService::class.java)
        }
    }
}