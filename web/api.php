<?php
/**
 * API Bootstrap
 * Main entry point for API requests
 */

// Error reporting
ini_set('display_errors', 1);
error_reporting(E_ALL);

// Autoloader
spl_autoload_register(function ($class) {
    $prefix = 'BeFit\\';
    $base_dir = __DIR__ . '/src/';
    
    $len = strlen($prefix);
    if (strncmp($prefix, $class, $len) !== 0) {
        return;
    }
    
    $relative_class = substr($class, $len);
    $file = $base_dir . str_replace('\\', '/', $relative_class) . '.php';
    
    if (file_exists($file)) {
        require $file;
    }
});

// Load configuration
$config = require __DIR__ . '/config/app.php';

// Set timezone
date_default_timezone_set($config['timezone']);

// Load database
require_once __DIR__ . '/config/database.php';

// Error handler
set_exception_handler(function ($exception) use ($config) {
    http_response_code(500);
    
    $response = [
        'success' => false,
        'message' => 'Internal server error'
    ];
    
    if ($config['debug']) {
        $response['error'] = $exception->getMessage();
        $response['trace'] = $exception->getTraceAsString();
    }
    
    echo json_encode($response, JSON_PRETTY_PRINT);
    exit;
});

use BeFit\Core\Request;
use BeFit\Core\Router;
use BeFit\Middleware\CorsMiddleware;
use BeFit\Middleware\JsonMiddleware;

// Create request and router
$request = new Request();
$router = new Router();

// Global middleware
$router->middleware([CorsMiddleware::class, 'handle']);
$router->middleware([JsonMiddleware::class, 'handle']);

// Load routes
require __DIR__ . '/routes/api.php';

// Dispatch request
$router->dispatch($request);
