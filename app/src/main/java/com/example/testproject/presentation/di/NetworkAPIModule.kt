package com.example.testproject.presentation.di

import android.content.Context
import android.util.Log
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.Logger
import com.ihsanbal.logging.LoggingInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SportsAPIModule {

    private val baseUrl = "https://reqres.in/api/"

    @Provides
    @Singleton
    fun provideAccountApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton
    fun providesAccountApiOkHttpClient(
        loggingInterceptorBuilder: LoggingInterceptor.Builder,
        @ApplicationContext context: Context
    ): OkHttpClient {

        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)

        client.apply {
            addInterceptor(
                loggingInterceptorBuilder
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .build()
            )
            addInterceptor(InterceptorX(context))
        }

        return client.build()
    }

    @Provides
    @Singleton
    fun providesAccountApiRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        coroutineCallAdapterFactory: CoroutineCallAdapterFactory
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(coroutineCallAdapterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): LoggingInterceptor.Builder {
        return LoggingInterceptor.Builder()
            .setLevel(Level.BASIC)
            .log(Platform.INFO)
            .logger(LoggerX())
            .request("Request")
            .response("Response")
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun providesCoroutineCallAdapterFactory(): CoroutineCallAdapterFactory = CoroutineCallAdapterFactory()

}

class LoggerX @Inject constructor() : Logger {
    override fun log(level: Int, tag: String?, msg: String?) {
        try {
            val newMsg = URLDecoder.decode(msg, StandardCharsets.UTF_8.toString())
            Log.i(tag, newMsg)
        } catch (e: Exception) {
            println()
        }
    }
}

class InterceptorX @Inject constructor(
    @ApplicationContext val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val headers = request.headers.newBuilder()

//        RefreshTokenDelegate().getTokens(context)?.let { token ->
//            headers.add("Authorization", "${token.access}")
//        }

        request = request
            .newBuilder()
            .headers(headers.build())
            .build()

        val response = chain.proceed(request)
        return response
    }
}