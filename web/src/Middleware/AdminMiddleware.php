<?php
namespace BeFit\Middleware;

use BeFit\Core\Request;
use BeFit\Core\Response;

/**
 * Admin Middleware
 * Verify admin authentication
 */
class AdminMiddleware {
    
    public static function handle(Request $request) {
        session_start();
        
        if (!isset($_SESSION['admin'])) {
            Response::forbidden('Admin access required');
        }
    }
}
