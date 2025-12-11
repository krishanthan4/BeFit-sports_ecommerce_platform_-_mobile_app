<?php
namespace BeFit\Controllers;

use BeFit\Core\Request;
use BeFit\Core\Response;
use BeFit\Core\Validator;
use BeFit\Models\Product;

/**
 * Product Controller
 * Handle product operations
 */
class ProductController {
    
    private $productModel;
    
    public function __construct() {
        $this->productModel = new Product();
    }
    
    /**
     * Get all products with filters
     * GET /api/v1/products
     */
    public function index(Request $request) {
        $page = max(1, (int)$request->input('page', 1));
        $limit = min(100, max(1, (int)$request->input('limit', 20)));
        $offset = ($page - 1) * $limit;
        
        $filters = [
            'category' => $request->input('category'),
            'brand' => $request->input('brand'),
            'condition' => $request->input('condition'),
            'min_price' => $request->input('min_price'),
            'max_price' => $request->input('max_price'),
            'search' => $request->input('search'),
            'sort_by' => $request->input('sort_by', 'newest')
        ];
        
        $products = $this->productModel->getProducts($filters, $limit, $offset);
        $total = $this->productModel->getProductCount($filters);
        
        Response::paginated($products, $total, $page, $limit);
    }
    
    /**
     * Get single product details
     * GET /api/v1/products/{id}
     */
    public function show(Request $request, $params) {
        $id = $params['id'] ?? null;
        
        if (!$id) {
            Response::error('Product ID is required', 400);
        }
        
        $product = $this->productModel->getProductDetails($id);
        
        if (!$product) {
            Response::notFound('Product not found');
        }
        
        // Get reviews
        $reviews = $this->productModel->getReviews($id);
        $rating = $this->productModel->getAverageRating($id);
        
        $product['reviews'] = $reviews;
        $product['average_rating'] = $rating['avg_rating'] ?? 0;
        $product['review_count'] = $rating['review_count'] ?? 0;
        
        Response::success($product);
    }
    
    /**
     * Create new product
     * POST /api/v1/products
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
            'title' => 'required|max:100',
            'description' => 'required',
            'price' => 'required|numeric',
            'qty' => 'required|integer',
            'delivery_fee' => 'required|numeric',
            'category_id' => 'required|integer',
            'model_has_brand_id' => 'required|integer',
            'condition_id' => 'required|integer',
            'standard_shipping_fee_id' => 'required|integer'
        ])) {
            Response::validationError($validator->errors());
        }
        
        // Create product
        $productId = $this->productModel->create([
            'title' => $data['title'],
            'description' => $data['description'],
            'price' => $data['price'],
            'qty' => $data['qty'],
            'delivery_fee' => $data['delivery_fee'],
            'category_cat_id' => $data['category_id'],
            'model_has_brand_id' => $data['model_has_brand_id'],
            'condition_condition_id' => $data['condition_id'],
            'status_status_id' => 1, // Active
            'user_email' => $userEmail,
            'standard_shipping_fee_id' => $data['standard_shipping_fee_id'],
            'datetime_added' => date('Y-m-d H:i:s')
        ]);
        
        if ($productId) {
            // Handle image uploads
            if ($request->file('image1')) {
                $this->uploadProductImage($productId, $request->file('image1'), $data['title']);
            }
            if ($request->file('image2')) {
                $this->uploadProductImage($productId, $request->file('image2'), $data['title']);
            }
            if ($request->file('image3')) {
                $this->uploadProductImage($productId, $request->file('image3'), $data['title']);
            }
            
            // Handle colors
            if (!empty($data['colors']) && is_array($data['colors'])) {
                foreach ($data['colors'] as $colorId) {
                    \Database::iud(
                        "INSERT INTO product_has_color (product_id, color_clr_id) VALUES (?, ?)",
                        [$productId, $colorId]
                    );
                }
            }
            
            Response::success(
                ['product_id' => $productId],
                'Product created successfully',
                201
            );
        }
        
        Response::serverError('Failed to create product');
    }
    
    /**
     * Update product
     * PUT /api/v1/products/{id}
     */
    public function update(Request $request, $params) {
        session_start();
        
        if (!isset($_SESSION['user'])) {
            Response::unauthorized();
        }
        
        $productId = $params['id'] ?? null;
        $data = $request->all();
        $userEmail = $_SESSION['user']['email'];
        
        // Check ownership
        $product = $this->productModel->find($productId);
        if (!$product || $product['user_email'] !== $userEmail) {
            Response::forbidden('You do not have permission to update this product');
        }
        
        // Validation
        $validator = new Validator($data);
        if (!$validator->validate([
            'title' => 'required|max:100',
            'description' => 'required',
            'price' => 'required|numeric',
            'qty' => 'required|integer'
        ])) {
            Response::validationError($validator->errors());
        }
        
        // Update product
        $result = $this->productModel->update($productId, [
            'title' => $data['title'],
            'description' => $data['description'],
            'price' => $data['price'],
            'qty' => $data['qty'],
            'delivery_fee' => $data['delivery_fee'] ?? $product['delivery_fee']
        ]);
        
        if ($result) {
            Response::success(null, 'Product updated successfully');
        }
        
        Response::serverError('Failed to update product');
    }
    
    /**
     * Delete product
     * DELETE /api/v1/products/{id}
     */
    public function destroy(Request $request, $params) {
        session_start();
        
        if (!isset($_SESSION['user'])) {
            Response::unauthorized();
        }
        
        $productId = $params['id'] ?? null;
        $userEmail = $_SESSION['user']['email'];
        
        // Check ownership
        $product = $this->productModel->find($productId);
        if (!$product || $product['user_email'] !== $userEmail) {
            Response::forbidden('You do not have permission to delete this product');
        }
        
        // Soft delete by setting status to inactive
        $result = $this->productModel->update($productId, [
            'status_status_id' => 2
        ]);
        
        if ($result) {
            Response::success(null, 'Product deleted successfully');
        }
        
        Response::serverError('Failed to delete product');
    }
    
    /**
     * Get seller's products
     * GET /api/v1/products/seller
     */
    public function sellerProducts(Request $request) {
        session_start();
        
        if (!isset($_SESSION['user'])) {
            Response::unauthorized();
        }
        
        $userEmail = $_SESSION['user']['email'];
        $page = max(1, (int)$request->input('page', 1));
        $limit = min(100, max(1, (int)$request->input('limit', 20)));
        $offset = ($page - 1) * $limit;
        
        $products = $this->productModel->getSellerProducts($userEmail, $limit, $offset);
        $total = $this->productModel->count('user_email', $userEmail);
        
        Response::paginated($products, $total, $page, $limit);
    }
    
    /**
     * Upload product image
     */
    private function uploadProductImage($productId, $file, $productTitle) {
        $config = require __DIR__ . '/../../config/app.php';
        $uploadPath = $config['upload']['paths']['products'];
        
        if ($file['error'] === UPLOAD_ERR_OK) {
            $extension = pathinfo($file['name'], PATHINFO_EXTENSION);
            $filename = preg_replace('/[^a-zA-Z0-9]/', '', $productTitle) . uniqid() . '.' . $extension;
            $destination = $uploadPath . $filename;
            
            if (move_uploaded_file($file['tmp_name'], $destination)) {
                \Database::iud(
                    "INSERT INTO product_img (img_path, product_id) VALUES (?, ?)",
                    ['../public/images/product_images/' . $filename, $productId]
                );
            }
        }
    }
}
