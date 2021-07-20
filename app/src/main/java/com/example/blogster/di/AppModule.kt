package com.example.blogster.di

import com.example.blogster.data.remote.api.ConduitApi
import com.example.blogster.utils.Constants.CONDUIT_BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideGsonBuilder(): Gson = GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .create()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(CONDUIT_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()



    @Provides
    @Singleton
    fun provideConduitApi(retrofit: Retrofit): ConduitApi =
        retrofit.create(ConduitApi::class.java)


//    @Singleton
//    @Provides
//    fun provideAppDatabase(@ApplicationContext context: Context) =
//        Room.databaseBuilder(
//            context,
//            AppDatabase::class.java,
//            "movie_db"
//        ).build()
//
//    @Singleton
//    @Provides
//    fun provideMovieDao(database: AppDatabase): MovieDao = database.movieDao()

}