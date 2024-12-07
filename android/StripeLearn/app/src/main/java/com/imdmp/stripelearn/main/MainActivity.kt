package com.imdmp.stripelearn.main

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.imdmp.stripelearn.BuildConfig
import com.imdmp.stripelearn.R
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val paymentViewModel: PaymentViewModel by viewModel()
    lateinit var btnTestStripe: Button
    lateinit var btnTestConfirm : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val stripe = Stripe(
            context = this,
            publishableKey = BuildConfig.STRIPE_KEY
        )
        PaymentConfiguration.init(
            this,
            publishableKey = BuildConfig.STRIPE_KEY,
        )
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnTestStripe = findViewById(R.id.btnTestStripe)
        btnTestConfirm = findViewById(R.id.btnTestConfirm)
        val paymentSheet = PaymentSheet(this) { paymentResult ->
            when (paymentResult) {
                is PaymentSheetResult.Completed -> {
                    // Payment successful
                    Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show()
                }
                is PaymentSheetResult.Canceled -> {
                    // Payment canceled
                    Toast.makeText(this, "Payment Canceled", Toast.LENGTH_SHORT).show()
                }
                is PaymentSheetResult.Failed -> {
                    // Payment failed
                    Toast.makeText(this, "Payment Failed: ${paymentResult.error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }


        btnTestStripe.setOnClickListener {
            paymentViewModel.createPaymentIntent(1000, "usd"){ clientSecret->
                btnTestConfirm.visibility = View.VISIBLE
                btnTestConfirm.setOnClickListener {
                    paymentSheet.presentWithPaymentIntent(
                        paymentViewModel.clientSecret,
                        PaymentSheet.Configuration(
                            merchantDisplayName = "Danny's Business"
                        )
                    )
                }
            }
        }


    }
}