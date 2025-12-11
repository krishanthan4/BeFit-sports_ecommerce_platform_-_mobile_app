<?php
namespace BeFit\Middleware;

use BeFit\Core\Request;

/**
 * JSON Middleware
 * Ensure proper JSON response headers
 */
class JsonMiddleware {
    
    public static function handle(Request $request) {
        header('Content-Type: application/json; charset=utf-8');
    }
}
