<?php
namespace BeFit\Controllers;

use BeFit\Core\Request;
use BeFit\Core\Response;
use BeFit\Core\Validator;

/**
 * Wishlist Controller
 * Handle wishlist operations
 */
class WishlistController {
    
    /**
     * Get wishlist items
     * GET /api/v1/wishlist
     */
    public function index(Request $request) {
        session_start();
        
        if (!isset($_SESSION['user'])) {
            Response::unauthorized();
        }
        
        $userEmail = $_SESSION['user']['email'];
        
        $query = "SELECT w.*, 
                         p.title, p.price, p.qty as available_qty,
                         b.brand_name,
                         m.model_name,
                         c.cat_name,
                         (SELECT img_path FROM product_img WHERE product_id = p.id LIMIT 1) as image
                  FROM wishlist w
                  INNER JOIN product p ON p.id = w.product_id
                  INNER JOIN category c ON c.cat_id = p.category_cat_id
                  INNER JOIN model_has_brand mhb ON mhb.id = p.model_has_brand_id
                  INNER JOIN brand b ON b.brand_id = mhb.brand_brand_id
                  INNER JOIN model m ON m.model_id = mhb.model_model_id
                  WHERE w.user_email = ? AND p.status_status_id = 1
                  ORDER BY w.wId DESC";
        
        $result = \Database::search($query, [$userEmail]);
        $items = [];
        
        while ($row = $result->fetch_assoc()) {
            $items[] = $row;
        }
        
        Response::success(['items' => $items]);
    }
    
    /**
     * Add item to wishlist
     * POST /api/v1/wishlist
     */
    public function store(Request $request) {
        session_start();
        
        if (!isset($_SESSION['user'])) {
            Response::unauthorized();
        }
        
        $data = $request->all();
        $userEmail = $_SESSION['user']['email'];
        
        // Validation
        $validator = new Validator($data);
        if (!$validator->validate([
            'product_id' => 'required|integer'
        ])) {
            Response::validationError($validator->errors());
        }
        
        // Check if already in wishlist
        $existing = \Database::search(
            "SELECT * FROM wishlist WHERE user_email = ? AND product_id = ?",
            [$userEmail, $data['product_id']]
        );
        
        if ($existing->num_rows > 0) {
            Response::error('Product already in wishlist', 400);
        }
        
        // Add to wishlist
        $result = \Database::iud(
            "INSERT INTO wishlist (user_email, product_id, qty) VALUES (?, ?, 1)",
            [$userEmail, $data['product_id']]
        );
        
        if ($result) {
            Response::success(null, 'Added to wishlist successfully', 201);
        }
        
        Response::serverError('Failed to add to wishlist');
    }
    
    /**
     * Remove item from wishlist
     * DELETE /api/v1/wishlist/{id}
     */
    public function destroy(Request $request, $params) {
        session_start();
        
        if (!isset($_SESSION['user'])) {
            Response::unauthorized();
        }
        
        $wishlistId = $params['id'] ?? null;
        $userEmail = $_SESSION['user']['email'];
        
        $result = \Database::iud(
            "DELETE FROM wishlist WHERE wId = ? AND user_email = ?",
            [$wishlistId, $userEmail]
        );
        
        if ($result) {
            Response::success(null, 'Removed from wishlist');
        }
        
        Response::serverError('Failed to remove from wishlist');
    }
}
