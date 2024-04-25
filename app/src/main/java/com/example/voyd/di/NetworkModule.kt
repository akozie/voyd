package com.example.voyd.di

import com.example.voyd.data.remote.apiServices.VoydApiService
import com.example.voyd.data.remote.interceptors.AuthInterceptor
import com.example.voyd.utils.AppConstants.BASE_URL
import com.example.voyd.utils.AppConstants.STRING_AUTH_INTERCEPTOR_TAG
import com.example.voyd.utils.AppConstants.STRING_LOGGING_INTERCEPTOR_TAG
import com.example.voyd.utils.AppConstants.TIME_OUT_15
import com.example.voyd.utils.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesBaseUrl(): String = BASE_URL

    @Singleton
    @Provides
    @Named(STRING_LOGGING_INTERCEPTOR_TAG)
    fun providesLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    @Named(STRING_AUTH_INTERCEPTOR_TAG)
    fun providesAuthInterceptor(authInterceptor: AuthInterceptor): Interceptor = authInterceptor

    @Singleton
    @Provides
    fun providesOKHTTPClient(
        @Named(STRING_LOGGING_INTERCEPTOR_TAG) loggingInterceptor: Interceptor,
        @Named(STRING_AUTH_INTERCEPTOR_TAG) authInterceptor: Interceptor,
    ): OkHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(TIME_OUT_15, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT_15, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT_15, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    @Singleton
    @Provides
    fun providesRetrofitForGoogleMapsNetworkCall(
        okHttpClient: OkHttpClient,
        baseUrl: String,
    ): Retrofit =
        Retrofit.Builder().baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun providesVoydApiService(
        retrofit: Retrofit
    ): VoydApiService = retrofit.create(VoydApiService::class.java)

    @Singleton
    @Provides
    fun providesNetworkUtil(): NetworkUtils = NetworkUtils()
}