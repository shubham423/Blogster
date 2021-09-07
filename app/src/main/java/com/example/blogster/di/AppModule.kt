package com.example.blogster.di

import android.content.Context
import android.content.SharedPreferences
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.blogster.ConduitApp
import com.example.blogster.data.remote.api.ConduitApi
import com.example.blogster.data.remote.api.ConduitAuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideHttpClientBuilder(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(ChuckerInterceptor(ConduitApp.applicationContext()))
            .addInterceptor(loggingInterceptor)
            .build()


    @Provides
    @Singleton
    fun authInterceptor(): Interceptor {
        return Interceptor { chain ->
            var req = chain.request()
            authToken?.let {
                req = req.newBuilder()
                    .header("Authorization", "Token $it")
                    .build()
            }
            chain.proceed(req)
        }

    }

    var authToken: String? = null

    @Provides
    @Singleton
    fun provideRetrofitForAuth(okHttpClient: OkHttpClient, authInterceptor: Interceptor): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://conduit.productionready.io/api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient.newBuilder().addInterceptor(authInterceptor).build())
            .build()


    @Provides
    @Singleton
    fun provideConduitApi(retrofit: Retrofit): ConduitApi =
        retrofit
            .create(ConduitApi::class.java)


    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): ConduitAuthApi =
        retrofit
            .create(ConduitAuthApi::class.java)


}