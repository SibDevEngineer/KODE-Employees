package com.example.kodeemployees.di

import android.util.Log
import com.example.kodeemployees.data.api.HeaderInterceptor
import com.example.kodeemployees.data.api.KodeApi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://stoplight.io/mocks/kode-education/trainee-test/25143926/" //users

@Module
class ApiModule {
    @Provides
    @Singleton
    fun provideLoggerInterceptor(): Interceptor {
        val logging = HttpLoggingInterceptor { message ->
            Log.d("HTTP LOGGER", message)
        }
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): HeaderInterceptor = HeaderInterceptor()

    @Provides
    @Singleton
    fun provideClient(
        interceptorLogger: Interceptor,
        interceptorHeader: HeaderInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptorLogger)
            .addInterceptor(interceptorHeader)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideKodeApi(retrofit: Retrofit): KodeApi =
        retrofit.create(KodeApi::class.java)

}

