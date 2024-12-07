package com.imdmp.stripelearn.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imdmp.stripelearn.PaymentRepository
import kotlinx.coroutines.launch

class PaymentViewModel(private val repository: PaymentRepository) : ViewModel() {
    public var clientSecret = ""

    fun createPaymentIntent(amount: Int, currency: String, callback: (String)->Unit) {
        viewModelScope.launch {
            try {
                val clientSecret = repository.createPaymentIntent(amount, currency)
                Log.d("Danny Tag", "client secret: ${clientSecret}")
                this@PaymentViewModel.clientSecret = clientSecret
                callback(clientSecret)
                // Use clientSecret with Stripe SDK
            } catch (e: Exception) {
                // Handle errors
                Log.e("Danny Tag","error! $e")
            }
        }
    }
}
