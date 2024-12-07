package com.imdmp.stripelearn.di

import com.imdmp.stripelearn.PaymentRepository
import com.imdmp.stripelearn.main.PaymentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { PaymentRepository() }
    viewModel { PaymentViewModel(get()) }
}