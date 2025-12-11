<?php
/**
 * Application Configuration
 */

return [
    // Application settings
    'app_name' => 'BeFit',
    'app_url' => 'http://localhost',
    'environment' => 'development', // development, production
    'debug' => true,
    'timezone' => 'Asia/Colombo',
    
    // Session settings
    'session' => [
        'name' => 'BEFIT_SESSION',
        'lifetime' => 7200, // 2 hours
        'path' => '/',
        'secure' => false, // Set true for HTTPS
        'httponly' => true,
        'samesite' => 'Lax'
    ],
    
    // CORS settings
    'cors' => [
        'enabled' => true,
        'origins' => ['*'], // Allow all origins in development
        'methods' => ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS'],
        'headers' => ['Content-Type', 'Authorization', 'X-Requested-With'],
        'credentials' => true
    ],
    
    // API settings
    'api' => [
        'version' => 'v1',
        'rate_limit' => 100, // requests per minute
        'pagination' => [
            'default_limit' => 20,
            'max_limit' => 100
        ]
    ],
    
    // File upload settings
    'upload' => [
        'max_size' => 5242880, // 5MB in bytes
        'allowed_types' => ['image/jpeg', 'image/png', 'image/jpg', 'image/gif'],
        'paths' => [
            'products' => __DIR__ . '/../public/images/product_images/',
            'profiles' => __DIR__ . '/../resources/profile_images/',
            'categories' => __DIR__ . '/../resources/'
        ]
    ],
    
    // Email settings (PHPMailer)
    'email' => [
        'from_email' => 'noreply@befit.com',
        'from_name' => 'BeFit',
        'smtp_host' => 'smtp.gmail.com',
        'smtp_port' => 587,
        'smtp_username' => '',
        'smtp_password' => '',
        'smtp_encryption' => 'tls'
    ],
    
    // Payment gateway settings
    'payment' => [
        'payhere' => [
            'merchant_id' => '',
            'merchant_secret' => '',
            'sandbox' => true
        ]
    ],
    
    // Company information
    'company' => [
        'name' => 'BeFit',
        'email' => 'info@befit.com',
        'phone' => '',
        'address' => '',
        'latitude' => 6.912059362912562,
        'longitude' => 79.85130333558227
    ]
];
