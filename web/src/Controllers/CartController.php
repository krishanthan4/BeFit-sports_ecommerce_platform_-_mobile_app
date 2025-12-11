<?php
namespace BeFit\Controllers;

use BeFit\Core\Request;
use BeFit\Core\Response;
use BeFit\Core\Validator;
use BeFit\Models\Cart;
use BeFit\Models\Product;

/**
 * Cart Controller
 * Handle shopping cart operations
 */
class CartController {
    
    private $cartModel;
    private $productModel;
    
    public function __construct() {
        $this->cartModel = new Cart();
        $this->productModel = new Product();
    }
    
    /**
     * Get cart items
     * GET /api/v1/cart
     */
    public function index(Request $request) {
        session_start();
        
        if (!isset($_SESSION['user'])) {
            Response::unauthorized();
        }
        
        $userEmail = $_SESSION['user']['email'];
        $items = $this->cartModel->getCartItems($userEmail);
        $totals = $this->cartModel->getCartTotal($userEmail);
        
        Response::success([
            'items' => $items,
            'totals' => $totals
        ]);
    }
    
    /**
     * Add item to cart
     * POST /api/v1/cart
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
            'product_id' => 'required|integer',
            'qty' => 'required|integer'
        ])) {
            Response::validationError($validator->errors());
        }
        
        // Check product exists and has sufficient quantity
        $product = $this->productModel->find($data['product_id']);
        
        if (!$product) {
            Response::notFound('Product not found');
        }
        
        if ($product['status_status_id'] != 1) {
            Response::error('Product is not available', 400);
        }
        
        if ($product['qty'] < $data['qty']) {
            Response::error('Insufficient stock available', 400);
        }
        
        // Add to cart
        $result = $this->cartModel->addItem($userEmail, $data['product_id'], $data['qty']);
        
        if ($result) {
            Response::success(null, 'Item added to cart successfully', 201);
        }
        
        Response::serverError('Failed to add item to cart');
    }
    
    /**
     * Update cart item quantity
     * PUT /api/v1/cart/{id}
     */
    public function update(Request $request, $params) {
        session_start();
        
        if (!isset($_SESSION['user'])) {
            Response::unauthorized();
        }
        
        $cartId = $params['id'] ?? null;
        $data = $request->all();
        
        // Validation
        $validator = new Validator($data);
        if (!$validator->validate([
            'qty' => 'required|integer'
        ])) {
            Response::validationError($validator->errors());
        }
        
        if ($data['qty'] <= 0) {
            Response::error('Quantity must be greater than 0', 400);
        }
        
        $result = $this->cartModel->updateQuantity($cartId, $data['qty']);
        
        if ($result) {
            Response::success(null, 'Cart updated successfully');
        }
        
        Response::serverError('Failed to update cart');
    }
    
    /**
     * Remove item from cart
     * DELETE /api/v1/cart/{id}
     */
    public function destroy(Request $request, $params) {
        session_start();
        
        if (!isset($_SESSION['user'])) {
            Response::unauthorized();
        }
        
        $cartId = $params['id'] ?? null;
        $userEmail = $_SESSION['user']['email'];
        
        $result = $this->cartModel->removeItem($cartId, $userEmail);
        
        if ($result) {
            Response::success(null, 'Item removed from cart');
        }
        
        Response::serverError('Failed to remove item from cart');
    }
    
    /**
     * Clear entire cart
     * DELETE /api/v1/cart/clear
     */
    public function clear(Request $request) {
        session_start();
        
        if (!isset($_SESSION['user'])) {
            Response::unauthorized();
        }
        
        $userEmail = $_SESSION['user']['email'];
        $result = $this->cartModel->clearCart($userEmail);
        
        if ($result) {
            Response::success(null, 'Cart cleared successfully');
        }
        
        Response::serverError('Failed to clear cart');
    }
}
