# API Endpoint Mapping

This document maps old `/processes/*.php` files to new REST API endpoints.

## Authentication Endpoints

| Old Process File | New API Endpoint | Method | Notes |
|-----------------|------------------|--------|-------|
| `/processes/signInProcess.php` | `/api/v1/auth/login` | POST | Login user |
| `/processes/signupProcess.php` | `/api/v1/auth/register` | POST | Register new user |
| `/processes/signoutProcess.php` | `/api/v1/auth/logout` | POST | Logout user |
| `/processes/verifyCodeProcess.php` | `/api/v1/auth/verify` | POST | Verify email code |
| `/processes/adminSigninProcess.php` | `/api/v1/admin/login` | POST | Admin login |
| `/processes/adminVerificationProcess.php` | `/api/v1/auth/verify` | POST | Admin verification |

## Product Endpoints

| Old Process File | New API Endpoint | Method | Notes |
|-----------------|------------------|--------|-------|
| `/processes/addProductProcess.php` | `/api/v1/seller/products` | POST | Add new product |
| `/processes/updateProductProcess.php` | `/api/v1/seller/products/{id}` | PUT | Update product |
| `/processes/changeStatusProcess.php` | `/api/v1/seller/products/{id}` | PUT | Change product status |
| `/processes/searchProcess.php` | `/api/v1/search?query={query}` | GET | Search products |
| `/processes/filterProcess.php` | `/api/v1/products?filters` | GET | Filter products |
| `/processes/filterImagesProcess.php` | `/api/v1/products?filters` | GET | Filter products with images |
| `/processes/singleProductWishlistProcess.php` | `/api/v1/wishlist` | POST | Add to wishlist |
| `/processes/sendIdProcess.php` | `/api/v1/products/{id}` | GET | Get product by ID |

## Admin Product Management

| Old Process File | New API Endpoint | Method | Notes |
|-----------------|------------------|--------|-------|
| `/processes/manageProductBlockProcess.php` | `/api/v1/admin/products/{id}/status` | PUT | Block/unblock product |
| `/processes/searchProductsProcess.php` | `/api/v1/admin/products?search={query}` | GET | Search products (admin) |
| `/processes/loadAllProductsProcess.php` | `/api/v1/admin/products` | GET | Load all products |

## Cart Endpoints

| Old Process File | New API Endpoint | Method | Notes |
|-----------------|------------------|--------|-------|
| `/processes/addToCartProcess.php` | `/api/v1/cart` | POST | Add item to cart |
| `/processes/cartQtyUpdateProcess.php` | `/api/v1/cart/{id}` | PUT | Update cart quantity |
| `/processes/removeCartProcess.php` | `/api/v1/cart/{id}` | DELETE | Remove from cart |
| `/processes/updateProductQuantityProcess.php` | `/api/v1/cart/{id}` | PUT | Update quantity |
| `/processes/SendBuyDetailsProcess.php` | `/api/v1/orders/checkout` | POST | Checkout/create order |
| `/processes/invoiceItemAddProcess.php` | `/api/v1/orders/checkout` | POST | Add invoice item |

## Wishlist Endpoints

| Old Process File | New API Endpoint | Method | Notes |
|-----------------|------------------|--------|-------|
| `/processes/removeWatchlistProcess.php` | `/api/v1/wishlist/{id}` | DELETE | Remove from wishlist |

## User Profile Endpoints

| Old Process File | New API Endpoint | Method | Notes |
|-----------------|------------------|--------|-------|
| `/processes/updateProfileProcess.php` | `/api/v1/user/profile` | PUT | Update user profile |
| `/processes/updateProfileAdminProcess.php` | `/api/v1/user/profile` | PUT | Update admin profile |
| `/processes/removeProfileProcess.php` | `/api/v1/user/profile` | PUT | Remove profile image |
| `/processes/removeProfile2Process.php` | `/api/v1/user/profile` | PUT | Remove secondary profile image |
| `/processes/removeAdminProfileProcess.php` | `/api/v1/user/profile` | PUT | Remove admin profile image |

## Location Endpoints

| Old Process File | New API Endpoint | Method | Notes |
|-----------------|------------------|--------|-------|
| `/processes/get_districts.php` | `/api/v1/districts/{province_id}` | GET | Get districts by province |
| `/processes/get_cities.php` | `/api/v1/cities/{district_id}` | GET | Get cities by district |

## Admin User Management

| Old Process File | New API Endpoint | Method | Notes |
|-----------------|------------------|--------|-------|
| `/processes/manageUsersProcess.php` | `/api/v1/admin/users/{email}/status` | PUT | Block/unblock user |
| `/processes/searchUsersProcess.php` | `/api/v1/admin/users?search={query}` | GET | Search users |
| `/processes/loadAllUsersProcess.php` | `/api/v1/admin/users` | GET | Load all users |

## Admin Seller Management

| Old Process File | New API Endpoint | Method | Notes |
|-----------------|------------------|--------|-------|
| `/processes/searchSellersProcess.php` | `/api/v1/admin/users?search={query}&type=seller` | GET | Search sellers |
| `/processes/loadAllSellersProcess.php` | `/api/v1/admin/users?type=seller` | GET | Load all sellers |

## Category Management

| Old Process File | New API Endpoint | Method | Notes |
|-----------------|------------------|--------|-------|
| `/processes/addCategoryProcess.php` | `/api/v1/admin/categories` | POST | Add category |
| `/processes/deleteCategoryProcess.php` | `/api/v1/admin/categories/{id}` | DELETE | Delete category |

## Reviews Management

| Old Process File | New API Endpoint | Method | Notes |
|-----------------|------------------|--------|-------|
| `/processes/writeReviewProcess.php` | `/api/v1/products/{id}/reviews` | POST | Write review |
| `/processes/searchReviewsProcess.php` | `/api/v1/admin/reviews?search={query}` | GET | Search reviews (admin) |
| `/processes/deleteConformProcess.php` | `/api/v1/admin/reviews/{id}` | DELETE | Delete review |

## Order Management

| Old Process File | New API Endpoint | Method | Notes |
|-----------------|------------------|--------|-------|
| `/processes/orderStatusProcess.php` | `/api/v1/orders/{id}/status` | PUT | Update order status |

## Color Management

| Old Process File | New API Endpoint | Method | Notes |
|-----------------|------------------|--------|-------|
| `/processes/addColorProcess.php` | `/api/v1/colors` | POST | Add color (if endpoint exists) |

## Contact Admin

| Old Process File | New API Endpoint | Method | Notes |
|-----------------|------------------|--------|-------|
| `/processes/contactAdminProcess.php` | `/api/v1/contact` | POST | Contact admin (needs implementation) |

## API Base URL

All endpoints should be prefixed with the base URL configured in the application settings.

- Development: `http://localhost:8083/api/v1`
- Production: `https://your-domain.com/api/v1`

## Request Headers

All API requests should include:
```
Content-Type: application/json
Accept: application/json
```

For authenticated endpoints, include:
```
Authorization: Bearer {token}
```
Or use session-based authentication depending on your implementation.

## Response Format

All API responses follow this structure:
```json
{
  "success": true,
  "data": {},
  "message": "Success message"
}
```

Or for errors:
```json
{
  "success": false,
  "message": "Error message",
  "errors": {}
}
```
