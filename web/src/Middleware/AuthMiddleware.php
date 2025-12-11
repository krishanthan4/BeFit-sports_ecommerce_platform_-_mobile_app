<?php
namespace BeFit\Middleware;

use BeFit\Core\Request;
use BeFit\Core\Response;

/**
 * Authentication Middleware
 * Verify user authentication
 */
class AuthMiddleware {
    
    public static function handle(Request $request) {
        session_start();
        
        if (!isset($_SESSION['user'])) {
            Response::unauthorized('Authentication required');
        }
    }
    
    public static function optional(Request $request) {
        if (session_status() === PHP_SESSION_NONE) {
            session_start();
        }
    }
}
