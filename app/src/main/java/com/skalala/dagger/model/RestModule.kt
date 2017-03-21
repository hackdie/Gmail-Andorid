package com.skalala.dagger.model

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by diegogalindo on 3/18/17.
 *
 */

@Singleton
@Module
class RestModule {

    @Provides
    @Singleton
    @Named("serverUrl")
    fun provideServerUrl(): String {
        return "http://api.androidhive.info/json/"
    }

    @Provides
    @Singleton
    fun provideRetrofit(@Named("serverUrl") serverUrl: String, gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
                .baseUrl(serverUrl)
                .addConverterFactory(gsonConverterFactory)
                .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

}

