package com.imdmp.stripelearn.di

import android.util.Log
import com.imdmp.stripelearn.BuildConfig
import com.imdmp.stripelearn.StripeApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    single {
        try {
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(get())
                .build()
        } catch (e: Exception) {
            Log.e("KoinDebug", "Error creating Retrofit instance", e)
            throw e
        }
    }

    single { get<Retrofit>().create(StripeApi::class.java) }
}
