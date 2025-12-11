<?php
namespace BeFit\Controllers;

use BeFit\Core\Request;
use BeFit\Core\Response;
use BeFit\Core\Validator;

/**
 * Order Controller
 * Handle order and checkout operations
 */
class OrderController {
    
    /**
     * Get user's orders
     * GET /api/v1/orders
     */
    public function index(Request $request) {
        session_start();
        
        if (!isset($_SESSION['user'])) {
            Response::unauthorized();
        }
        
        $userEmail = $_SESSION['user']['email'];
        $page = max(1, (int)$request->input('page', 1));
        $limit = min(100, max(1, (int)$request->input('limit', 20)));
        $offset = ($page - 1) * $limit;
        
        $query = "SELECT i.*, 
                         COUNT(ihp.product_id) as item_count
                  FROM invoice i
                  INNER JOIN invoice_has_products ihp ON ihp.invoice_id = i.invoice_id
                  WHERE i.user_email = ?
                  GROUP BY i.invoice_id
                  ORDER BY i.date DESC
                  LIMIT ? OFFSET ?";
        
        $result = \Database::search($query, [$userEmail, $limit, $offset]);
        $orders = [];
        
        while ($row = $result->fetch_assoc()) {
            $orders[] = $row;
        }
        
        // Get total count
        $countResult = \Database::search(
            "SELECT COUNT(*) as count FROM invoice WHERE user_email = ?",
            [$userEmail]
        );
        $countRow = $countResult->fetch_assoc();
        $total = $countRow['count'];
        
        Response::paginated($orders, $total, $page, $limit);
    }
    
    /**
     * Get order details
     * GET /api/v1/orders/{id}
     */
    public function show(Request $request, $params) {
        session_start();
        
        if (!isset($_SESSION['user'])) {
            Response::unauthorized();
        }
        
        $orderId = $params['id'] ?? null;
        $userEmail = $_SESSION['user']['email'];
        
        // Get invoice
        $invoiceResult = \Database::search(
            "SELECT * FROM invoice WHERE invoice_id = ? AND user_email = ?",
            [$orderId, $userEmail]
        );
        
        if ($invoiceResult->num_rows === 0) {
            Response::notFound('Order not found');
        }
        
        $invoice = $invoiceResult->fetch_assoc();
        
        // Get order items
        $itemsQuery = "SELECT ihp.*, 
                              p.title, p.price,
                              os.order_status_name,
                              (SELECT img_path FROM product_img WHERE product_id = p.id LIMIT 1) as image
                       FROM invoice_has_products ihp
                       INNER JOIN product p ON p.id = ihp.product_id
                       INNER JOIN order_status os ON os.order_status_id = ihp.order_status
                       WHERE ihp.invoice_id = ?";
        
        $itemsResult = \Database::search($itemsQuery, [$orderId]);
        $items = [];
        
        while ($row = $itemsResult->fetch_assoc()) {
            $items[] = $row;
        }
        
        $invoice['items'] = $items;
        
        Response::success($invoice);
    }
    
    /**
     * Checkout and create order
     * POST /api/v1/orders/checkout
     */
    public function checkout(Request $request) {
        session_start();
        
        if (!isset($_SESSION['user'])) {
            Response::unauthorized();
        }
        
        $data = $request->all();
        $userEmail = $_SESSION['user']['email'];
        
        // Get cart items
        $cartResult = \Database::search(
            "SELECT c.*, p.price, p.qty, p.delivery_fee
             FROM cart c
             INNER JOIN product p ON p.id = c.product_id
             WHERE c.user_email = ? AND p.status_status_id = 1",
            [$userEmail]
        );
        
        if ($cartResult->num_rows === 0) {
            Response::error('Cart is empty', 400);
        }
        
        // Calculate total
        $total = 0;
        $cartItems = [];
        
        while ($row = $cartResult->fetch_assoc()) {
            // Check stock
            if ($row['qty'] < $row['qty']) {
                Response::error("Insufficient stock for product ID {$row['product_id']}", 400);
            }
            
            $itemTotal = ($row['price'] * $row['qty']) + $row['delivery_fee'];
            $total += $itemTotal;
            $cartItems[] = $row;
        }
        
        // Start transaction
        \Database::beginTransaction();
        
        try {
            // Create invoice
            $orderId = uniqid('ORD-');
            $invoiceResult = \Database::iud(
                "INSERT INTO invoice (order_id, date, total, user_email) VALUES (?, NOW(), ?, ?)",
                [$orderId, $total, $userEmail]
            );
            
            if (!$invoiceResult) {
                throw new \Exception('Failed to create invoice');
            }
            
            $invoiceId = \Database::getLastInsertId();
            
            // Add invoice items
            foreach ($cartItems as $item) {
                \Database::iud(
                    "INSERT INTO invoice_has_products (invoice_id, product_id, bought_qty, order_status) 
                     VALUES (?, ?, ?, 1)",
                    [$invoiceId, $item['product_id'], $item['qty']]
                );
                
                // Update product quantity
                \Database::iud(
                    "UPDATE product SET qty = qty - ? WHERE id = ?",
                    [$item['qty'], $item['product_id']]
                );
            }
            
            // Clear cart
            \Database::iud("DELETE FROM cart WHERE user_email = ?", [$userEmail]);
            
            // Commit transaction
            \Database::commit();
            
            Response::success([
                'order_id' => $orderId,
                'invoice_id' => $invoiceId,
                'total' => $total
            ], 'Order placed successfully', 201);
            
        } catch (\Exception $e) {
            \Database::rollback();
            Response::serverError('Failed to create order: ' . $e->getMessage());
        }
    }
    
    /**
     * Update order status (Seller)
     * PUT /api/v1/orders/{id}/status
     */
    public function updateStatus(Request $request, $params) {
        session_start();
        
        if (!isset($_SESSION['user'])) {
            Response::unauthorized();
        }
        
        $invoiceId = $params['id'] ?? null;
        $data = $request->all();
        
        // Validation
        $validator = new Validator($data);
        if (!$validator->validate([
            'product_id' => 'required|integer',
            'status' => 'required|integer|in:1,2,3,4'
        ])) {
            Response::validationError($validator->errors());
        }
        
        // Verify seller owns the product
        $userEmail = $_SESSION['user']['email'];
        $productResult = \Database::search(
            "SELECT * FROM product WHERE id = ? AND user_email = ?",
            [$data['product_id'], $userEmail]
        );
        
        if ($productResult->num_rows === 0) {
            Response::forbidden('You do not have permission to update this order');
        }
        
        // Update status
        $result = \Database::iud(
            "UPDATE invoice_has_products 
             SET order_status = ? 
             WHERE invoice_id = ? AND product_id = ?",
            [$data['status'], $invoiceId, $data['product_id']]
        );
        
        if ($result) {
            Response::success(null, 'Order status updated successfully');
        }
        
        Response::serverError('Failed to update order status');
    }
    
    /**
     * Get seller's orders
     * GET /api/v1/seller/orders
     */
    public function sellerOrders(Request $request) {
        session_start();
        
        if (!isset($_SESSION['user'])) {
            Response::unauthorized();
        }
        
        $userEmail = $_SESSION['user']['email'];
        $page = max(1, (int)$request->input('page', 1));
        $limit = min(100, max(1, (int)$request->input('limit', 20)));
        $offset = ($page - 1) * $limit;
        
        $query = "SELECT i.*, ihp.*, 
                         p.title as product_title,
                         u.fname as buyer_fname, u.lname as buyer_lname, u.mobile as buyer_mobile,
                         os.order_status_name,
                         (SELECT img_path FROM product_img WHERE product_id = p.id LIMIT 1) as image
                  FROM invoice_has_products ihp
                  INNER JOIN invoice i ON i.invoice_id = ihp.invoice_id
                  INNER JOIN product p ON p.id = ihp.product_id
                  INNER JOIN user u ON u.email = i.user_email
                  INNER JOIN order_status os ON os.order_status_id = ihp.order_status
                  WHERE p.user_email = ?
                  ORDER BY i.date DESC
                  LIMIT ? OFFSET ?";
        
        $result = \Database::search($query, [$userEmail, $limit, $offset]);
        $orders = [];
        
        while ($row = $result->fetch_assoc()) {
            $orders[] = $row;
        }
        
        // Get total count
        $countQuery = "SELECT COUNT(*) as count
                       FROM invoice_has_products ihp
                       INNER JOIN product p ON p.id = ihp.product_id
                       WHERE p.user_email = ?";
        
        $countResult = \Database::search($countQuery, [$userEmail]);
        $countRow = $countResult->fetch_assoc();
        $total = $countRow['count'];
        
        Response::paginated($orders, $total, $page, $limit);
    }
}
