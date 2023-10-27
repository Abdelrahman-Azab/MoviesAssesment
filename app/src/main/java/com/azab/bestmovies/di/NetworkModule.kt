package com.azab.bestmovies.di

import android.content.Context
import com.azab.bestmovies.data.remote.api.MoviesApi
import com.azab.bestmovies.utils.constants.Constants
import com.azab.bestmovies.utils.extensions.ConnectivityExtensions
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module{
    fun provideApiService(): MoviesApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor { it ->
                val original: Request = it.request()
                val originalHttpUrl: HttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter(Constants.API_KEY, "2ad2bb6b")
                    .build()

                val requestBuilder: Request.Builder = original.newBuilder()
                    .url(url)

                val request: Request = requestBuilder.build()
                it.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .baseUrl(Constants.SERVER_URL)
            .build()
            .create(MoviesApi::class.java)
    }

    fun provideConnectivityExtensions(context: Context): ConnectivityExtensions {
        return ConnectivityExtensions(context)
    }

    single { provideApiService() }
    single { provideConnectivityExtensions(get()) }

}