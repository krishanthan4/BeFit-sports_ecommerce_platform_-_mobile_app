<?php
namespace BeFit\Middleware;

use BeFit\Core\Request;
use BeFit\Core\Response;

/**
 * CORS Middleware
 * Handle Cross-Origin Resource Sharing
 */
class CorsMiddleware {
    
    public static function handle(Request $request) {
        $config = require __DIR__ . '/../../config/app.php';
        $cors = $config['cors'];
        
        if (!$cors['enabled']) {
            return;
        }
        
        // Set CORS headers
        $origin = $_SERVER['HTTP_ORIGIN'] ?? '*';
        
        if ($cors['origins'] === ['*'] || in_array($origin, $cors['origins'])) {
            header("Access-Control-Allow-Origin: $origin");
        }
        
        header("Access-Control-Allow-Methods: " . implode(', ', $cors['methods']));
        header("Access-Control-Allow-Headers: " . implode(', ', $cors['headers']));
        
        if ($cors['credentials']) {
            header("Access-Control-Allow-Credentials: true");
        }
        
        header("Access-Control-Max-Age: 3600");
    }
}
