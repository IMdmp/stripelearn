const express = require("express");
const paymentRoutes = require("./routes/paymentRoutes");
const stripe = require("stripe")("sk_test_...");

const app = express();
// Webhook endpoint to handle payment events
app.post("/webhooks", express.raw({ type: "application/json" }), (req, res) => {
  const endpointSecret = process.env.STRIPE_WEBHOOK_SECRET;
  const sig = req.headers["stripe-signature"];

  let event;
  try {
    event = stripe.webhooks.constructEvent(req.body, sig, endpointSecret);
  } catch (err) {
    console.error("Webhook signature verification failed.", err.message);
    return res.status(400).send(`Webhook Error: ${err.message}`);
  }

  // Handle different event types
  switch (event.type) {
    case "payment_intent.succeeded":
      const paymentIntent = event.data.object;
      console.log("Payment succeeded:", paymentIntent);
      // Update order status in database or notify app here
      break;
    case "payment_intent.payment_failed":
      console.log("Payment failed:", event.data.object);
      break;
    default:
      console.log(`Unhandled event type ${event.type}`);
  }

  res.status(200).send("Received");
});
// Middleware
app.use(express.json());
app.use("/api", paymentRoutes);

// Start the server
const PORT = process.env.PORT || 3000;

app.get("/", async (req, res) => {
  res.send(`Hello! My name is ${process.env.NAME}.`);
});

app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
