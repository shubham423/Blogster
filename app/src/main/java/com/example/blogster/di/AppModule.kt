package com.example.blogster.di

import com.example.blogster.data.remote.api.ConduitApi
import com.example.blogster.data.remote.api.ConduitAuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClientBuilder(): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
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

//    @Provides
//    @Singleton
//    fun provideRetrofit(): Retrofit =
//        Retrofit.Builder()
//            .baseUrl("https://conduit.productionready.io/api/")
//            .addConverterFactory(MoshiConverterFactory.create())
//            .build()


    @Provides
    @Singleton
    fun provideRetrofitForAuth(okHttpClient: OkHttpClient,authInterceptor: Interceptor): Retrofit =
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