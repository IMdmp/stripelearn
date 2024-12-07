package com.imdmp.stripelearn

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PaymentRepository : KoinComponent {
    private val api: StripeApi by inject()

    suspend fun createPaymentIntent(amount: Int, currency: String): String {
        val response = api.createPaymentIntent(
            PaymentIntentRequest(amount, currency)
        )
        return response.clientSecret
    }
}