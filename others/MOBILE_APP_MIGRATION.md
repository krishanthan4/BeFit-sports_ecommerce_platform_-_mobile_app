# Mobile App API Migration Guide

## Overview
This document maps old API endpoints to new REST API endpoints for the Android mobile app.

## Configuration Update

### Update Config.java
**Location:** `befit_mobile/app/src/main/java/com/sprinsec/mobile_v2/util/Config.java`

```java
public enum Config {
    INSTANCES;
    // Update these URLs
    public static final String BACKEND_API_URL = "http://your-server.com/api/v1/";
    public static final String BACKEND_URL = "http://your-server.com/";
    // ... rest of config
}
```

## Endpoint Mapping

### Authentication Endpoints

| Old Endpoint | New Endpoint | HTTP Method | Notes |
|-------------|--------------|-------------|-------|
| `signInProcess.php` | `auth/login` | POST | Body as JSON |
| `signupProcess.php` | `auth/register` | POST | Body as JSON |
| `signoutProcess.php` | `auth/logout` | POST | Session required |
| `checkUserSessionProcess.php` | `auth/session` | GET | Returns auth status |
| `verifyCodeProcess.php` | `auth/verify` | POST | Account verification |
| `verifyCode2Process.php` | `auth/verify` | POST | Same endpoint |
| `resetPasswordProcess.php` | `auth/reset-password` | POST | With code |

### Product Endpoints

| Old Endpoint | New Endpoint | HTTP Method | Notes |
|-------------|--------------|-------------|-------|
| `getHomeProductsProcess.php` | `products?sort_by=newest` | GET | Query params |
| `singleProductGetExtraDataProcess.php` | `products/{id}` | GET | Product details |
| `addProductProcess.php` | `seller/products` | POST | Multipart form |
| `updateProductProcess.php` | `seller/products/{id}` | PUT | JSON body |
| `updateProductQuantityProcess.php` | `seller/products/{id}` | PUT | Update qty field |
| `getSellerMyProductsProcess.php` | `seller/products` | GET | With pagination |
| `manageProductBlockProcess.php` | `seller/products/{id}` | DELETE | Soft delete |
| `searchProductsProcess.php` | `products?search={query}` | GET | Search param |
| `filterProcess.php` | `products?category={id}&brand={id}` | GET | Filter params |

### Cart Endpoints

| Old Endpoint | New Endpoint | HTTP Method | Notes |
|-------------|--------------|-------------|-------|
| `getCartProductsProcess.php` | `cart` | GET | Returns items + totals |
| `addToCartProcess.php` | `cart` | POST | JSON: {product_id, qty} |
| `cartQtyUpdateProcess.php` | `cart/{id}` | PUT | JSON: {qty} |
| `removeCartProcess.php` | `cart/{id}` | DELETE | Cart item ID |

### Wishlist Endpoints

| Old Endpoint | New Endpoint | HTTP Method | Notes |
|-------------|--------------|-------------|-------|
| `getWatchlistProcess.php` | `wishlist` | GET | Get all items |
| `singleProductWishlistProcess.php` | `wishlist` | POST | JSON: {product_id} |
| `singleProductRemoveWishlistProcess.php` | `wishlist/{id}` | DELETE | Wishlist item ID |
| `removeWatchlistProcess.php` | `wishlist/{id}` | DELETE | Same as above |

### Order Endpoints

| Old Endpoint | New Endpoint | HTTP Method | Notes |
|-------------|--------------|-------------|-------|
| `getPurchasedHistoryProcess.php` | `orders` | GET | User orders |
| `GetOrderStatusProcess.php` | `orders/{id}` | GET | Order details |
| `SendBuyDetailsProcess.php` | `orders/checkout` | POST | Create order |
| `singleProductBuyNowProcess.php` | `orders/checkout` | POST | Direct checkout |
| `loadAllSellingHistoryProcess.php` | `seller/orders` | GET | Seller's orders |
| `orderStatusProcess.php` | `orders/{id}/status` | PUT | Update status |

### User Profile Endpoints

| Old Endpoint | New Endpoint | HTTP Method | Notes |
|-------------|--------------|-------------|-------|
| `getUserProfileDataProcess.php` | `user/profile` | GET | Full profile |
| `updateProfileProcess.php` | `user/profile` | PUT | Multipart form |
| `updateProfileAdminProcess.php` | `user/profile` | PUT | Same endpoint |
| `removeProfileProcess.php` | `user/profile` | DELETE | Remove image |
| `removeProfile2Process.php` | `user/profile` | DELETE | Same |

### Category & Metadata Endpoints

| Old Endpoint | New Endpoint | HTTP Method | Notes |
|-------------|--------------|-------------|-------|
| `getHomeCategoryProcess.php` | `categories` | GET | All categories |
| `loadAllCategoryProcess.php` | `categories` | GET | Same |
| `get_cities.php` | `cities/{district_id}` | GET | By district |
| `get_districts.php` | `districts/{province_id}` | GET | By province |
| `getAddProductInitialDataProcess.php` | Multiple endpoints | GET | See below |

### Reviews/Feedback Endpoints

| Old Endpoint | New Endpoint | HTTP Method | Notes |
|-------------|--------------|-------------|-------|
| `giveFeedbackToProductProcess.php` | `products/{id}/reviews` | POST | Add review |
| `writeReviewProcess.php` | `products/{id}/reviews` | POST | Same |
| `loadAllFeedbacksAndReviewsProcess.php` | `products/{id}/reviews` | GET | Get reviews |

### Admin Endpoints

| Old Endpoint | New Endpoint | HTTP Method | Notes |
|-------------|--------------|-------------|-------|
| `adminSigninProcess.php` | `admin/login` | POST | Admin auth |
| `adminSignoutProcess.php` | `admin/logout` | POST | Admin logout |
| `adminVerificationProcess.php` | `admin/verify` | POST | Admin verify |
| `loadAdminDashboardOverviewProcess.php` | `admin/dashboard` | GET | Stats |
| `loadAllUsersProcess.php` | `admin/users` | GET | User list |
| `loadAllProductsProcess.php` | `admin/products` | GET | Product list |
| `loadAllSellersProcess.php` | `admin/sellers` | GET | Seller list |
| `manageUsersProcess.php` | `admin/users/{email}/status` | PUT | Block/unblock |
| `searchUsersProcess.php` | `admin/users?search={query}` | GET | Search users |

## Request Format Changes

### Old Format (Form Data)
```java
RequestBody formBody = new FormBody.Builder()
    .add("email", email)
    .add("password", password)
    .build();
```

### New Format (JSON)
```java
JSONObject jsonBody = new JSONObject();
jsonBody.put("email", email);
jsonBody.put("password", password);

RequestBody body = RequestBody.create(
    MediaType.parse("application/json"),
    jsonBody.toString()
);

Request request = new Request.Builder()
    .url(Config.BACKEND_API_URL + "auth/login")
    .post(body)
    .addHeader("Content-Type", "application/json")
    .build();
```

## Response Format Changes

### Old Format
```php
echo "success";
// or
echo "error message";
```

### New Format (JSON)
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "user": {...},
    "is_seller": true
  }
}
```

### Parsing New Response
```java
JSONObject response = new JSONObject(responseBody);
boolean success = response.getBoolean("success");
String message = response.getString("message");

if (success) {
    JSONObject data = response.getJSONObject("data");
    // Process data
}
```

## Pagination Changes

### Old Approach
Limited or no pagination

### New Approach
All list endpoints support pagination:

```
GET /products?page=1&limit=20
```

**Response:**
```json
{
  "success": true,
  "data": [...],
  "pagination": {
    "total": 100,
    "count": 20,
    "per_page": 20,
    "current_page": 1,
    "total_pages": 5,
    "has_more": true
  }
}
```

## Filter & Search Changes

### Old Approach
```
filterProcess.php?category=1&brand=2
```

### New Approach
```
GET /products?category=1&brand=2&min_price=1000&max_price=5000&sort_by=price_low
```

**Supported Filters:**
- `category` - Category ID
- `brand` - Brand ID
- `condition` - Condition ID (1: New, 2: Used)
- `min_price` - Minimum price
- `max_price` - Maximum price
- `search` - Search keyword
- `sort_by` - newest, oldest, price_low, price_high

## Migration Checklist

### Step 1: Update Config
- [ ] Update `Config.java` with new base URL
- [ ] Test connection to new API

### Step 2: Update Services (One by One)
- [ ] SignInService → auth/login
- [ ] SignUpService → auth/register
- [ ] GetHomeProductsService → products
- [ ] GetCartProductsService → cart
- [ ] AddToCartService → cart (POST)
- [ ] Continue with others...

### Step 3: Update Request Format
- [ ] Change from FormBody to JSON where applicable
- [ ] Add Content-Type: application/json header
- [ ] Update parameter names if changed

### Step 4: Update Response Parsing
- [ ] Parse new JSON format
- [ ] Handle success/error states
- [ ] Extract data from data object
- [ ] Handle pagination metadata

### Step 5: Test Each Feature
- [ ] Authentication flow
- [ ] Product browsing
- [ ] Cart operations
- [ ] Checkout process
- [ ] Profile management
- [ ] Seller operations
- [ ] Admin features

## Example: Complete Migration

### Old Code
```java
// SignInService.java
RequestBody formBody = new FormBody.Builder()
    .add("email", email)
    .add("password", password)
    .add("rememberMe", String.valueOf(rememberMe))
    .build();

Request request = new Request.Builder()
    .url(Config.BACKEND_API_URL + "signInProcess.php")
    .post(formBody)
    .build();

// Response parsing
String responseBody = response.body().string();
if (responseBody.equals("success")) {
    // Success
} else {
    // Error
}
```

### New Code
```java
// SignInService.java
JSONObject jsonBody = new JSONObject();
jsonBody.put("email", email);
jsonBody.put("password", password);
jsonBody.put("rememberMe", rememberMe);

RequestBody body = RequestBody.create(
    MediaType.parse("application/json"),
    jsonBody.toString()
);

Request request = new Request.Builder()
    .url(Config.BACKEND_API_URL + "auth/login")
    .post(body)
    .addHeader("Content-Type", "application/json")
    .build();

// Response parsing
String responseBody = response.body().string();
JSONObject jsonResponse = new JSONObject(responseBody);

if (jsonResponse.getBoolean("success")) {
    JSONObject data = jsonResponse.getJSONObject("data");
    JSONObject user = data.getJSONObject("user");
    boolean isSeller = data.getBoolean("is_seller");
    // Process user data
} else {
    String errorMessage = jsonResponse.getString("message");
    // Show error
}
```

## Testing Tools

### Postman Collection
Import the API to Postman for testing:
1. Create new collection
2. Add requests for each endpoint
3. Test with different parameters
4. Verify responses

### cURL Commands
```bash
# Test login
curl -X POST http://localhost/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@test.com","password":"password"}'

# Test products
curl http://localhost/api/v1/products?category=1&limit=10
```

## Common Issues & Solutions

### Issue 1: 404 Not Found
**Solution:** Check .htaccess is working and URL is correct

### Issue 2: CORS Error
**Solution:** Server sends CORS headers automatically, ensure credentials are included

### Issue 3: Session Not Maintained
**Solution:** Use cookie manager in OkHttp:
```java
OkHttpClient client = new OkHttpClient.Builder()
    .cookieJar(new JavaNetCookieJar(new CookieManager()))
    .build();
```

### Issue 4: Image Upload Fails
**Solution:** Use multipart/form-data:
```java
MultipartBody.Builder builder = new MultipartBody.Builder()
    .setType(MultipartBody.FORM)
    .addFormDataPart("title", title)
    .addFormDataPart("image1", file.getName(),
        RequestBody.create(MediaType.parse("image/*"), file));
```

## Support

For questions or issues during migration:
1. Check API_DOCUMENTATION.md
2. Test endpoint with Postman/cURL
3. Review response format
4. Check server logs

---

**Note:** Complete one service at a time and test thoroughly before moving to the next. Keep old code commented until new implementation is verified.
