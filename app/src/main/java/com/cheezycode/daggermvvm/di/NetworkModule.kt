package com.cheezycode.daggermvvm.di

import com.cheezycode.daggermvvm.BuildConfig
import com.cheezycode.daggermvvm.retrofit.FakerAPI
import com.cheezycode.daggermvvm.utils.Constants
import com.cheezycode.daggermvvm.utils.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): Interceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient{
        val okhttpBuilder = OkHttpClient.Builder() //and every other method after it except build() would return a Builder (Builder pattern)
        if(BuildConfig.DEBUG){
            okhttpBuilder.addInterceptor(interceptor)
        }
        return okhttpBuilder.build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient:OkHttpClient) : Retrofit{
            return Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    @Singleton
    @Provides
    fun providesFakerAPI(retrofit: Retrofit) : FakerAPI{
        return retrofit.create(FakerAPI::class.java)
    }
    }


