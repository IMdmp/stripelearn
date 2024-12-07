package com.imdmp.stripelearn

import retrofit2.http.Body
import retrofit2.http.POST

interface StripeApi {
    @POST("create-payment-intent")
    suspend fun createPaymentIntent(
        @Body request: PaymentIntentRequest
    ): PaymentIntentResponse
}

data class PaymentIntentRequest(
    val amount: Int,
    val currency: String
)

data class PaymentIntentResponse(
    val clientSecret: String
)
