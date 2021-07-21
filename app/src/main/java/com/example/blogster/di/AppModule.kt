package com.example.blogster.di

import com.example.blogster.data.remote.api.ConduitApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Provides
//    @Singleton
//    fun provideHttpClientBuilder(): OkHttpClient = OkHttpClient.Builder()
//        .readTimeout(60, TimeUnit.SECONDS)
//        .connectTimeout(60, TimeUnit.SECONDS)
//        .build()



    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://conduit.productionready.io/api/")
            .addConverterFactory(MoshiConverterFactory.create())
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