const express = require("express");
const Stripe = require("stripe");
const router = express.Router();

// Initialize Stripe
const stripe = Stripe(process.env.STRIPE_SECRET);

// Route to create a PaymentIntent
router.post("/create-payment-intent", async (req, res) => {
  const { amount, currency } = req.body;
  try {
    const paymentIntent = await stripe.paymentIntents.create({
      amount,
      currency,
    });
    res.json({ clientSecret: paymentIntent.client_secret });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

module.exports = router;
