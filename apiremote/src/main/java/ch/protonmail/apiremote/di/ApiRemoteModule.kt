package ch.protonmail.apiremote.di

import ch.protonmail.apiremote.TaskApi
import ch.protonmail.common.AppConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit

private const val APPLICATION_JSON = "application/json"
private const val FAKE_BASE_URL =
    "https://proton-android-testcloud.europe-west1.firebasedatabase.app"

@Module
@InstallIn(SingletonComponent::class)
class ApiRemoteModule {

    @Provides
    fun provideJson(): Converter.Factory {
        val contentType = APPLICATION_JSON.toMediaType()
        return Json
            .apply {
                configuration.run {
                }
            }
            .asConverterFactory(contentType)
    }

    @Provides
    fun provideLoggingInterceptor(appConfig: AppConfig) = HttpLoggingInterceptor().apply {
        if (appConfig.debug) level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    fun provideRetrofit(
        converterFactory: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(FAKE_BASE_URL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(converterFactory)
        .client(okHttpClient)
        .build()

    @Provides
    fun provideTaskApi(retrofit: Retrofit): TaskApi = retrofit.create(TaskApi::class.java)
}
