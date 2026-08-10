#!/bin/bash
# Production Smoke Test Script

# 1. Change this to your live Render URL
API_URL="https://order-management-service-k2ek.onrender.com/api/orders"

echo "🚀 Starting Production Smoke Test..."
echo "Target URL: $API_URL"
echo "---------------------------------------"

# 2. Ping the endpoint with a test order payload
RESPONSE=$(curl -s -X POST "$API_URL" \
  -H "Content-Type: application/json" \
  -d '{
    "productName": "MacBook Pro",
    "price": 2500.00,
    "quantity": 1,
    "customerNotes": "Standard delivery please. Love the product!"
  }')

# 3. Check if the response contains the database ID and Claude's analysis
if [[ "$RESPONSE" == *"\"id\":"* ]] && [[ "$RESPONSE" == *"\"aiSentimentAnalysis\":"* ]]; then
  echo "✅ SUCCESS: Order processed, Claude analyzed intent, and database persisted the record!"
  echo "Response Data: $RESPONSE"
  exit 0
else
  echo "❌ FAILURE: Unexpected production response or connection timeout."
  echo "Response Data: $RESPONSE"
  exit 1
fi
