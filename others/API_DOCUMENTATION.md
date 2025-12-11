# BeFit API Documentation

## Base URL
```
http://localhost/api/v1
```

## Authentication
Most endpoints require authentication. After login, a session is created and maintained via cookies.

## Response Format
All responses follow this format:
```json
{
  "success": true|false,
  "message": "Response message",
  "data": {} | []
}
```

## Error Codes
- `200` - Success
- `201` - Created
- `400` - Bad Request
- `401` - Unauthorized
- `403` - Forbidden
- `404` - Not Found
- `422` - Validation Error
- `500` - Server Error

---

## Authentication Endpoints

### Register
`POST /auth/register`

**Body:**
```json
{
  "fname": "John",
  "lname": "Doe",
  "email": "john@example.com",
  "password": "password123",
  "mobile": "0712345678",
  "gender": 1
}
```

### Login
`POST /auth/login`

**Body:**
```json
{
  "email": "john@example.com",
  "password": "password123",
  "rememberMe": true
}
```

### Logout
`POST /auth/logout`

### Check Session
`GET /auth/session`

### Verify Account
`POST /auth/verify`

**Body:**
```json
{
  "email": "john@example.com",
  "code": "verification_code"
}
```

### Forgot Password
`POST /auth/forgot-password`

**Body:**
```json
{
  "email": "john@example.com"
}
```

### Reset Password
`POST /auth/reset-password`

**Body:**
```json
{
  "email": "john@example.com",
  "code": "reset_code",
  "new_password": "newpassword123"
}
```

---

## Product Endpoints

### Get All Products
`GET /products`

**Query Parameters:**
- `page` - Page number (default: 1)
- `limit` - Items per page (default: 20, max: 100)
- `category` - Category ID
- `brand` - Brand ID
- `condition` - Condition ID (1: Brand New, 2: Used)
- `min_price` - Minimum price
- `max_price` - Maximum price
- `search` - Search keyword
- `sort_by` - Sort option (newest, oldest, price_low, price_high)

**Example:**
```
GET /products?category=1&sort_by=price_low&page=1&limit=20
```

### Get Single Product
`GET /products/{id}`

**Response includes:**
- Product details
- Images
- Colors
- Reviews
- Average rating

### Create Product (Seller)
`POST /seller/products`

**Body (multipart/form-data):**
- `title` - Product title
- `description` - Product description
- `price` - Price
- `qty` - Quantity
- `delivery_fee` - Delivery fee
- `category_id` - Category ID
- `model_has_brand_id` - Model-Brand ID
- `condition_id` - Condition ID
- `standard_shipping_fee_id` - Shipping fee ID
- `colors[]` - Array of color IDs
- `image1` - Product image (file)
- `image2` - Product image (file, optional)
- `image3` - Product image (file, optional)

### Update Product (Seller)
`PUT /seller/products/{id}`

### Delete Product (Seller)
`DELETE /seller/products/{id}`

### Get Seller Products
`GET /seller/products`

**Query Parameters:**
- `page` - Page number
- `limit` - Items per page

---

## Cart Endpoints

### Get Cart
`GET /cart`

**Response:**
```json
{
  "success": true,
  "data": {
    "items": [...],
    "totals": {
      "subtotal": 10000,
      "shipping": 500,
      "total": 10500
    }
  }
}
```

### Add to Cart
`POST /cart`

**Body:**
```json
{
  "product_id": 1,
  "qty": 2
}
```

### Update Cart Item
`PUT /cart/{id}`

**Body:**
```json
{
  "qty": 3
}
```

### Remove from Cart
`DELETE /cart/{id}`

### Clear Cart
`DELETE /cart/clear`

---

## Wishlist Endpoints

### Get Wishlist
`GET /wishlist`

### Add to Wishlist
`POST /wishlist`

**Body:**
```json
{
  "product_id": 1
}
```

### Remove from Wishlist
`DELETE /wishlist/{id}`

---

## Order Endpoints

### Get User Orders
`GET /orders`

**Query Parameters:**
- `page` - Page number
- `limit` - Items per page

### Get Order Details
`GET /orders/{id}`

### Checkout
`POST /orders/checkout`

Creates an order from current cart items.

**Response:**
```json
{
  "success": true,
  "data": {
    "order_id": "ORD-12345",
    "invoice_id": 123,
    "total": 10500
  }
}
```

### Update Order Status (Seller)
`PUT /orders/{id}/status`

**Body:**
```json
{
  "product_id": 1,
  "status": 2
}
```

**Status Codes:**
- 1: Order Placed
- 2: Order Success
- 3: Shipped
- 4: Delivery Success

### Get Seller Orders
`GET /seller/orders`

**Query Parameters:**
- `page` - Page number
- `limit` - Items per page

---

## User Profile Endpoints

### Get Profile
`GET /user/profile`

### Update Profile
`PUT /user/profile`

**Body (multipart/form-data):**
- `fname` - First name
- `lname` - Last name
- `mobile` - Mobile number
- `gender` - Gender ID (1: Male, 2: Female)
- `city_id` - City ID
- `line1` - Address line 1
- `line2` - Address line 2
- `postal_code` - Postal code (optional)
- `profile_image` - Profile image (file, optional)

### Get Address
`GET /user/address`

### Update Address
`PUT /user/address`

**Body:**
```json
{
  "city_id": 1,
  "line1": "123 Main St",
  "line2": "Apartment 4B",
  "postal_code": "10400"
}
```

### Change Password
`PUT /user/change-password`

**Body:**
```json
{
  "current_password": "oldpass123",
  "new_password": "newpass123"
}
```

---

## Category & Location Endpoints

### Get All Categories
`GET /categories`

### Get Category Details
`GET /categories/{id}`

### Get Countries
`GET /countries`

### Get Provinces
`GET /provinces/{country_id}`

### Get Districts
`GET /districts/{province_id}`

### Get Cities
`GET /cities/{district_id}`

### Get All Brands
`GET /brands`

### Get Brands by Category
`GET /brands/{category_id}`

### Get Models by Brand
`GET /models/{brand_id}`

### Get Colors
`GET /colors`

### Get Conditions
`GET /conditions`

---

## Example Usage

### JavaScript/Fetch Example
```javascript
// Login
fetch('http://localhost/api/v1/auth/login', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify({
    email: 'user@example.com',
    password: 'password123'
  }),
  credentials: 'include' // Important for cookies
})
.then(response => response.json())
.then(data => console.log(data));

// Get Products
fetch('http://localhost/api/v1/products?category=1&limit=10', {
  credentials: 'include'
})
.then(response => response.json())
.then(data => console.log(data));
```

### Java/Android Example (Using OkHttp)
```java
// Set base URL in Config.java
public static final String BACKEND_API_URL = "http://your-server.com/api/v1/";

// Make request
OkHttpClient client = new OkHttpClient();
Request request = new Request.Builder()
    .url(Config.BACKEND_API_URL + "products")
    .get()
    .build();

client.newCall(request).enqueue(new Callback() {
    @Override
    public void onResponse(Call call, Response response) {
        // Handle response
    }
});
```

---

## Notes
- All POST/PUT requests with JSON should include `Content-Type: application/json` header
- File uploads require `Content-Type: multipart/form-data`
- Session-based authentication requires `credentials: 'include'` in requests
- All timestamps are in format: `YYYY-MM-DD HH:MM:SS`
- Pagination responses include metadata: `total`, `count`, `per_page`, `current_page`, `total_pages`, `has_more`
