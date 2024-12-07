package com.imdmp.stripelearn

import android.app.Application
import com.imdmp.stripelearn.di.networkModule
import com.imdmp.stripelearn.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class StripeLearn : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@StripeLearn)
            modules(listOf(networkModule, viewModelModule))
        }
    }
}