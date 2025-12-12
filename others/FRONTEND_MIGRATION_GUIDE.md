# Frontend JavaScript Migration Guide

## Overview

All Process.php files have been replaced with RESTful API endpoints. The `api-client.js` and `api-services.js` are already loaded in the header.php, so all pages have access to them.

## How to Use the API Services

### Available Services

- `AuthService` - Authentication (login, register, logout, verify)
- `ProductService` - Products (get, create, update, search)
- `CartService` - Shopping cart operations
- `OrderService` - Order management
- `WishlistService` - Wishlist operations
- `UserService` - User profile management
- `CategoryService` - Categories, brands, models

### Migration Pattern

#### OLD WAY (XMLHttpRequest with Process.php):
```javascript
function oldWay() {
    const request = new XMLHttpRequest();
    request.onreadystatechange = function() {
        if (request.readyState == 4 && request.status == 200) {
            const response = request.responseText;
            // handle response
        }
    };
    request.open("POST", "./processes/signInProcess.php", true);
    request.send(formData);
}
```

#### NEW WAY (API Services):
```javascript
async function newWay() {
    try {
        const response = await AuthService.login(email, password, rememberMe);
        if (response.success) {
            // handle success
            console.log(response.data);
        }
    } catch (error) {
        // handle error
        console.error(error.message);
    }
}
```

## File-by-File Migration Examples

### 1. Authentication Files

#### `public/js/index.js` (Sign In)
**OLD:**
```javascript
request.open("POST","./processes/signInProcess.php",true);
request.send(f);
```

**NEW:**
```javascript
try {
    const response = await AuthService.login(email, password, rememberMe);
    if (response.success) {
        window.location = "home";
    }
} catch (error) {
    alert(error.message);
}
```

#### `public/js/signUp.js` (Sign Up)
**OLD:**
```javascript
const response = await fetch("./processes/signupProcess.php", {
    method: "POST",
    body: formData
});
```

**NEW:**
```javascript
const response = await AuthService.register({
    fname, lname, email, mobile, password, gender
});
```

#### `public/js/nav.js` (Sign Out)
**OLD:**
```javascript
request.open("POST","./processes/signoutProcess.php",true);
request.send();
```

**NEW:**
```javascript
const response = await AuthService.logout();
if (response.success) {
    window.location.reload();
}
```

### 2. Product Management Files

#### `public/js/myProducts.js` (Add Product)
**OLD:**
```javascript
$.ajax({
    url: "/processes/addProductProcess.php",
    type: "post",
    data: formData,
    // ...
});
```

**NEW:**
```javascript
try {
    const productData = {
        title, category_id, brand_id, model_id,
        condition_id, price, quantity, description
    };
    const response = await ProductService.createProduct(productData);
    if (response.success) {
        alert("Product added successfully!");
    }
} catch (error) {
    alert(error.message);
}
```

#### `public/js/myProducts.js` (Update Product)
**OLD:**
```javascript
$.ajax({
    url: "/processes/updateProductProcess.php",
    type: "post",
    data: formData,
    // ...
});
```

**NEW:**
```javascript
try {
    const response = await ProductService.updateProduct(productId, {
        title, price, quantity, description
    });
    if (response.success) {
        alert("Product updated!");
    }
} catch (error) {
    alert(error.message);
}
```

#### `public/js/myProducts.js` (Change Product Status)
**OLD:**
```javascript
$.ajax({
    url: `/processes/changeStatusProcess.php?id=${id}`,
    type: "get",
    // ...
});
```

**NEW:**
```javascript
try {
    const response = await ProductService.updateProduct(id, { 
        status: newStatus 
    });
    if (response.success) {
        // Update UI
    }
} catch (error) {
    alert(error.message);
}
```

### 3. Cart Management Files

#### `public/js/cart2.js` (Add to Cart)
**OLD:**
```javascript
$.ajax({
    url: "/processes/addToCartProcess.php",
    type: "post",
    data: { product_id, qty },
    // ...
});
```

**NEW:**
```javascript
try {
    const response = await CartService.addToCart(product_id, qty);
    if (response.success) {
        alert("Added to cart!");
    }
} catch (error) {
    alert(error.message);
}
```

#### `public/js/cart2.js` (Update Cart Quantity)
**OLD:**
```javascript
$.ajax({
    url: "/processes/cartQtyUpdateProcess.php",
    type: "post",
    data: { cart_id, qty },
    // ...
});
```

**NEW:**
```javascript
try {
    const response = await CartService.updateCartItem(cart_id, qty);
    if (response.success) {
        // Update UI
    }
} catch (error) {
    alert(error.message);
}
```

#### `public/js/cart2.js` (Remove from Cart)
**OLD:**
```javascript
$.ajax({
    url: "/processes/removeCartProcess.php?id=" + id,
    type: "get",
    // ...
});
```

**NEW:**
```javascript
try {
    const response = await CartService.removeFromCart(id);
    if (response.success) {
        // Remove item from UI
    }
} catch (error) {
    alert(error.message);
}
```

#### `public/js/cart2.js` (Checkout)
**OLD:**
```javascript
request.open("POST", "/processes/invoiceItemAddProcess.php", true);
request.send(formData);
```

**NEW:**
```javascript
try {
    const orderData = {
        payment_method,
        delivery_address,
        items: cartItems
    };
    const response = await OrderService.checkout(orderData);
    if (response.success) {
        window.location = `/order-confirmation?id=${response.data.order_id}`;
    }
} catch (error) {
    alert(error.message);
}
```

### 4. Wishlist Files

#### `public/js/wishlist.js` (Remove from Wishlist)
**OLD:**
```javascript
$.ajax({
    url: "/processes/removeWatchlistProcess.php?id=" + id,
    type: "get",
    // ...
});
```

**NEW:**
```javascript
try {
    const response = await WishlistService.removeFromWishlist(id);
    if (response.success) {
        // Remove from UI
    }
} catch (error) {
    alert(error.message);
}
```

#### `public/js/singleProductView.js` (Add to Wishlist)
**OLD:**
```javascript
request.open("POST","./processes/singleProductWishlistProcess.php",true);
request.send(formData);
```

**NEW:**
```javascript
try {
    const response = await WishlistService.addToWishlist(product_id);
    if (response.success) {
        alert("Added to wishlist!");
    }
} catch (error) {
    alert(error.message);
}
```

### 5. User Profile Files

#### `public/js/updateProfile.js` (Update Profile)
**OLD:**
```javascript
const response = await fetch("/processes/updateProfileProcess.php", {
    method: "POST",
    body: formData
});
```

**NEW:**
```javascript
try {
    const response = await UserService.updateProfile({
        fname, lname, mobile, line1, line2,
        province_id, district_id, city_id, postal_code
    });
    if (response.success) {
        alert("Profile updated!");
    }
} catch (error) {
    alert(error.message);
}
```

#### `public/js/updateProfile.js` (Get Districts)
**OLD:**
```javascript
xhr.open("GET", "/processes/get_districts.php?province_id=" + provinceId, true);
xhr.send();
```

**NEW:**
```javascript
try {
    const response = await api.get(`/districts/${provinceId}`);
    if (response.success) {
        // Populate districts dropdown
        response.data.forEach(district => {
            // Add option to select
        });
    }
} catch (error) {
    console.error(error);
}
```

#### `public/js/updateProfile.js` (Get Cities)
**OLD:**
```javascript
xhr.open("GET", "/processes/get_cities.php?district_id=" + districtId, true);
xhr.send();
```

**NEW:**
```javascript
try {
    const response = await api.get(`/cities/${districtId}`);
    if (response.success) {
        // Populate cities dropdown
        response.data.forEach(city => {
            // Add option to select
        });
    }
} catch (error) {
    console.error(error);
}
```

### 6. Search Files

#### `public/js/search.js` (Search Products)
**OLD:**
```javascript
request.open("GET", "./processes/searchProcess.php?query=" + key, true);
request.send();
```

**NEW:**
```javascript
try {
    const response = await api.get('/search', { query: key });
    if (response.success) {
        // Display search results
        displayResults(response.data);
    }
} catch (error) {
    console.error(error);
}
```

#### `public/js/myProductFilter.js` (Filter Products)
**OLD:**
```javascript
request.open("POST","./processes/filterProcess.php",true);
request.send(formData);
```

**NEW:**
```javascript
try {
    const response = await ProductService.getProducts({
        category_id, brand_id, model_id, 
        condition_id, color_id,
        min_price, max_price,
        sort_by, sort_order
    });
    if (response.success) {
        // Display filtered products
        displayProducts(response.data);
    }
} catch (error) {
    console.error(error);
}
```

### 7. Admin Files

#### `public/js/adminSignin.js` (Admin Login)
**OLD:**
```javascript
$.ajax({
    url: "/processes/adminSigninProcess.php",
    type: "post",
    data: formData,
    // ...
});
```

**NEW:**
```javascript
try {
    const response = await api.post('/admin/login', {
        email, password, verification_code
    });
    if (response.success) {
        window.location = "/admin/dashboard";
    }
} catch (error) {
    alert(error.message);
}
```

#### `public/js/manageUsers.js` (Block/Unblock User)
**OLD:**
```javascript
request.open("POST", "./processes/manageUsersProcess.php", true);
request.send(formData);
```

**NEW:**
```javascript
try {
    const response = await api.put(`/admin/users/${email}/status`, {
        status: newStatus
    });
    if (response.success) {
        // Update UI
    }
} catch (error) {
    alert(error.message);
}
```

#### `public/js/manageProduct.js` (Block/Unblock Product)
**OLD:**
```javascript
request.open("POST", "./processes/manageProductBlockProcess.php", true);
request.send(formData);
```

**NEW:**
```javascript
try {
    const response = await api.put(`/admin/products/${product_id}/status`, {
        status: newStatus
    });
    if (response.success) {
        // Update UI
    }
} catch (error) {
    alert(error.message);
}
```

#### `public/js/manage_category.gui.php` (Add Category)
**OLD:**
```javascript
request.open("POST", "/processes/addCategoryProcess.php", true);
request.send(formData);
```

**NEW:**
```javascript
try {
    const response = await api.post('/admin/categories', {
        name, description, image
    });
    if (response.success) {
        alert("Category added!");
        loadCategories();
    }
} catch (error) {
    alert(error.message);
}
```

#### `public/js/manage_category.gui.php` (Delete Category)
**OLD:**
```javascript
request.open("POST", "/processes/deleteCategoryProcess.php", true);
request.send(formData);
```

**NEW:**
```javascript
try {
    const response = await api.delete(`/admin/categories/${category_id}`);
    if (response.success) {
        alert("Category deleted!");
        loadCategories();
    }
} catch (error) {
    alert(error.message);
}
```

### 8. Reviews Files

#### `public/js/writeReviews.js` (Write Review)
**OLD:**
```javascript
request.open("POST","/processes/writeReviewProcess.php",true);
request.send(formData);
```

**NEW:**
```javascript
try {
    const response = await api.post(`/products/${product_id}/reviews`, {
        rating, comment
    });
    if (response.success) {
        alert("Review submitted!");
    }
} catch (error) {
    alert(error.message);
}
```

#### `public/js/manageReviews.js` (Delete Review)
**OLD:**
```javascript
request.open("POST", "/processes/deleteConformProcess.php", true);
request.send(formData);
```

**NEW:**
```javascript
try {
    const response = await api.delete(`/admin/reviews/${review_id}`);
    if (response.success) {
        // Remove from UI
    }
} catch (error) {
    alert(error.message);
}
```

### 9. Order Files

#### `public/js/orderStatus.js` (Update Order Status)
**OLD:**
```javascript
request.open("POST", "./processes/orderStatusProcess.php", true);
request.send(formData);
```

**NEW:**
```javascript
try {
    const response = await OrderService.updateOrderStatus(order_id, new_status);
    if (response.success) {
        alert("Order status updated!");
    }
} catch (error) {
    alert(error.message);
}
```

## Common Patterns

### Converting FormData to Object
**OLD:**
```javascript
const formData = new FormData();
formData.append("key", value);
request.send(formData);
```

**NEW:**
```javascript
const data = {
    key: value
};
const response = await api.post('/endpoint', data);
```

### Converting jQuery $.ajax
**OLD:**
```javascript
$.ajax({
    url: "/processes/file.php",
    type: "post",
    data: { key: value },
    success: function(response) {
        // handle success
    },
    error: function(error) {
        // handle error
    }
});
```

**NEW:**
```javascript
try {
    const response = await api.post('/endpoint', { key: value });
    if (response.success) {
        // handle success
    }
} catch (error) {
    // handle error
}
```

### Converting XMLHttpRequest
**OLD:**
```javascript
const request = new XMLHttpRequest();
request.onreadystatechange = function() {
    if (request.readyState == 4 && request.status == 200) {
        const response = request.responseText;
        // handle
    }
};
request.open("POST", "/processes/file.php", true);
request.send(formData);
```

**NEW:**
```javascript
try {
    const response = await api.post('/endpoint', data);
    if (response.success) {
        // handle response.data
    }
} catch (error) {
    // handle error
}
```

## Response Format

All API responses follow this structure:

**Success:**
```json
{
    "success": true,
    "data": {},
    "message": "Success message"
}
```

**Error:**
```json
{
    "success": false,
    "message": "Error message",
    "errors": {}
}
```

## Testing After Migration

1. Open browser console (F12)
2. Check for errors
3. Test the functionality
4. Verify API calls in Network tab
5. Check response structure

## Files That Need Migration

See `API_ENDPOINT_MAPPING.md` for the complete list of process files and their corresponding API endpoints.

### JavaScript Files to Update:
- index.js (signin)
- signUp.js
- nav.js (signout)
- myProducts.js
- cart.js
- cart2.js
- wishlist.js
- singleProductView.js
- updateProfile.js
- search.js
- myProductFilter.js
- myProductFilter2.js
- writeReviews.js
- orderStatus.js
- adminSignin.js
- adminSettings.js
- manageUsers.js
- manageSellers.js
- manageProduct.js
- manageReviews.js
- contactAdmin.js

### PHP Files to Update (inline scripts):
- guis/admin/manage_category.gui.php
- guis/admin/manage_reviews.gui.php

## Need Help?

If you encounter issues:
1. Check the browser console for errors
2. Verify the API endpoint exists in `routes/api.php`
3. Check the controller method implementation
4. Ensure the request data matches what the API expects
