package com.example.thong.di

import android.util.Log
import com.example.thong.utils.Constants
import com.example.thong.utils.Constants.BASE_URL
import com.example.thong.utils.Constants.INIT_TRYOUT
import com.example.thong.utils.Constants.MAX_TRYOUTS
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = module {

    single() {
        createRetrofit(
            okHttpClient = get()
        )
    }

    single() {
        provideOkHttpClient()
    }

    single<Converter.Factory> {
        GsonConverterFactory.create(
            GsonBuilder()
                .setLenient()
                .create()
        )
    }
}

private fun createRetrofit(
    okHttpClient: OkHttpClient,
): Retrofit {
    val gson = GsonBuilder()
        .setLenient()
        .create()
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

private fun provideOkHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder().apply {

        addInterceptor(HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        })
        addInterceptor { chain ->
            val defaultRequest = chain.request()

            val defaultHttpUrl = defaultRequest.url()

            val httpUrl = defaultHttpUrl.newBuilder().build()

            val requestBuilder = defaultRequest.newBuilder()
                .url(httpUrl)

            chain.proceed(requestBuilder.build())
        }
        addInterceptor { chain ->
            val request = chain.request()
            var response = chain.proceed(request)
            var tryOuts = INIT_TRYOUT

            while (!response.isSuccessful && tryOuts < MAX_TRYOUTS) {
                Log.d(
                    this.javaClass.simpleName, "intercept: timeout/connection failure, " +
                            "performing automatic retry ${(tryOuts + 1)}"
                )
                tryOuts++
                response = chain.proceed(request)
            }

            response
        }
        addInterceptor(interceptor)

        connectTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
        readTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)

    }.build()
}