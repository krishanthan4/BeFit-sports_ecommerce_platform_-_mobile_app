<?php
/**
 * API Routes
 * Define all API endpoints
 */

use BeFit\Controllers\AuthController;
use BeFit\Controllers\ProductController;
use BeFit\Controllers\CartController;
use BeFit\Controllers\UserController;
use BeFit\Controllers\CategoryController;
use BeFit\Controllers\OrderController;
use BeFit\Controllers\WishlistController;

// ============================================
// Public Routes (No Authentication Required)
// ============================================

// Authentication
$router->post('/auth/register', [AuthController::class, 'register']);
$router->post('/auth/login', [AuthController::class, 'login']);
$router->post('/auth/logout', [AuthController::class, 'logout']);
$router->get('/auth/session', [AuthController::class, 'checkSession']);
$router->post('/auth/verify', [AuthController::class, 'verify']);
$router->post('/auth/forgot-password', [AuthController::class, 'forgotPassword']);
$router->post('/auth/reset-password', [AuthController::class, 'resetPassword']);

// Products (Public)
$router->get('/products', [ProductController::class, 'index']);
$router->get('/products/{id}', [ProductController::class, 'show']);

// Categories
$router->get('/categories', [CategoryController::class, 'index']);
$router->get('/categories/{id}', [CategoryController::class, 'show']);

// Locations
$router->get('/countries', [CategoryController::class, 'getCountries']);
$router->get('/provinces/{country_id}', [CategoryController::class, 'getProvinces']);
$router->get('/districts/{province_id}', [CategoryController::class, 'getDistricts']);
$router->get('/cities/{district_id}', [CategoryController::class, 'getCities']);

// Brands & Models
$router->get('/brands', [CategoryController::class, 'getBrands']);
$router->get('/brands/{category_id}', [CategoryController::class, 'getBrandsByCategory']);
$router->get('/models/{brand_id}', [CategoryController::class, 'getModelsByBrand']);

// Colors & Conditions
$router->get('/colors', [CategoryController::class, 'getColors']);
$router->get('/conditions', [CategoryController::class, 'getConditions']);

// ============================================
// Protected Routes (Authentication Required)
// ============================================

// User Profile
$router->get('/user/profile', [UserController::class, 'profile']);
$router->put('/user/profile', [UserController::class, 'updateProfile']);
$router->get('/user/address', [UserController::class, 'getAddress']);
$router->put('/user/address', [UserController::class, 'updateAddress']);
$router->put('/user/change-password', [UserController::class, 'changePassword']);

// Shopping Cart
$router->get('/cart', [CartController::class, 'index']);
$router->post('/cart', [CartController::class, 'store']);
$router->put('/cart/{id}', [CartController::class, 'update']);
$router->delete('/cart/{id}', [CartController::class, 'destroy']);
$router->delete('/cart/clear', [CartController::class, 'clear']);

// Wishlist
$router->get('/wishlist', [WishlistController::class, 'index']);
$router->post('/wishlist', [WishlistController::class, 'store']);
$router->delete('/wishlist/{id}', [WishlistController::class, 'destroy']);

// Orders
$router->get('/orders', [OrderController::class, 'index']);
$router->get('/orders/{id}', [OrderController::class, 'show']);
$router->post('/orders/checkout', [OrderController::class, 'checkout']);
$router->put('/orders/{id}/status', [OrderController::class, 'updateStatus']);

// Seller Operations
$router->get('/seller/products', [ProductController::class, 'sellerProducts']);
$router->post('/seller/products', [ProductController::class, 'store']);
$router->put('/seller/products/{id}', [ProductController::class, 'update']);
$router->delete('/seller/products/{id}', [ProductController::class, 'destroy']);
$router->get('/seller/orders', [OrderController::class, 'sellerOrders']);

// Reviews
$router->post('/products/{id}/reviews', [ProductController::class, 'addReview']);
$router->get('/products/{id}/reviews', [ProductController::class, 'getReviews']);

// Search
$router->get('/search', [ProductController::class, 'search']);

// ============================================
// Admin Routes
// ============================================

$router->group('/admin', function ($router) {
    // Admin Auth
    $router->post('/login', [AdminController::class, 'login']);
    $router->post('/logout', [AdminController::class, 'logout']);
    
    // Dashboard
    $router->get('/dashboard', [AdminController::class, 'dashboard']);
    
    // User Management
    $router->get('/users', [AdminController::class, 'getUsers']);
    $router->put('/users/{email}/status', [AdminController::class, 'updateUserStatus']);
    
    // Product Management
    $router->get('/products', [AdminController::class, 'getProducts']);
    $router->put('/products/{id}/status', [AdminController::class, 'updateProductStatus']);
    
    // Category Management
    $router->post('/categories', [AdminController::class, 'createCategory']);
    $router->put('/categories/{id}', [AdminController::class, 'updateCategory']);
    $router->delete('/categories/{id}', [AdminController::class, 'deleteCategory']);
    
    // Order Management
    $router->get('/orders', [AdminController::class, 'getOrders']);
    
    // Reviews Management
    $router->get('/reviews', [AdminController::class, 'getReviews']);
    $router->delete('/reviews/{id}', [AdminController::class, 'deleteReview']);
});
