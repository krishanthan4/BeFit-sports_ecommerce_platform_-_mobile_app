<?php
namespace BeFit\Models;

/**
 * Cart Model
 */
class Cart extends BaseModel {
    
    protected $table = 'cart';
    protected $primaryKey = 'cart_id';
    
    /**
     * Get user's cart items
     * 
     * @param string $email User email
     * @return array
     */
    public function getCartItems($email) {
        $query = "SELECT c.*, 
                         p.title, p.price, p.qty as available_qty, p.delivery_fee,
                         b.brand_name,
                         m.model_name,
                         cat.cat_name,
                         (SELECT img_path FROM product_img WHERE product_id = p.id LIMIT 1) as image
                  FROM cart c
                  INNER JOIN product p ON p.id = c.product_id
                  INNER JOIN category cat ON cat.cat_id = p.category_cat_id
                  INNER JOIN model_has_brand mhb ON mhb.id = p.model_has_brand_id
                  INNER JOIN brand b ON b.brand_id = mhb.brand_brand_id
                  INNER JOIN model m ON m.model_id = mhb.model_model_id
                  WHERE c.user_email = ? AND p.status_status_id = 1
                  ORDER BY c.cart_id DESC";
        
        $result = $this->query($query, [$email]);
        return $this->fetchAll($result);
    }
    
    /**
     * Add item to cart
     * 
     * @param string $email User email
     * @param int $productId Product ID
     * @param int $qty Quantity
     * @return int|bool Cart ID or false
     */
    public function addItem($email, $productId, $qty = 1) {
        // Check if item already exists
        $existing = $this->query(
            "SELECT * FROM cart WHERE user_email = ? AND product_id = ?",
            [$email, $productId]
        );
        
        if ($existing->num_rows > 0) {
            // Update quantity
            $item = $existing->fetch_assoc();
            $newQty = $item['qty'] + $qty;
            return \Database::iud(
                "UPDATE cart SET qty = ? WHERE cart_id = ?",
                [$newQty, $item['cart_id']]
            );
        } else {
            // Insert new item
            return $this->create([
                'user_email' => $email,
                'product_id' => $productId,
                'qty' => $qty
            ]);
        }
    }
    
    /**
     * Update cart item quantity
     * 
     * @param int $cartId Cart ID
     * @param int $qty New quantity
     * @return bool
     */
    public function updateQuantity($cartId, $qty) {
        return \Database::iud(
            "UPDATE cart SET qty = ? WHERE cart_id = ?",
            [$qty, $cartId]
        );
    }
    
    /**
     * Remove item from cart
     * 
     * @param int $cartId Cart ID
     * @param string $email User email (for security)
     * @return bool
     */
    public function removeItem($cartId, $email) {
        return \Database::iud(
            "DELETE FROM cart WHERE cart_id = ? AND user_email = ?",
            [$cartId, $email]
        );
    }
    
    /**
     * Clear user's cart
     * 
     * @param string $email User email
     * @return bool
     */
    public function clearCart($email) {
        return \Database::iud(
            "DELETE FROM cart WHERE user_email = ?",
            [$email]
        );
    }
    
    /**
     * Get cart total
     * 
     * @param string $email User email
     * @return array
     */
    public function getCartTotal($email) {
        $query = "SELECT 
                    SUM(c.qty * p.price) as subtotal,
                    SUM(p.delivery_fee) as shipping,
                    SUM(c.qty * p.price + p.delivery_fee) as total
                  FROM cart c
                  INNER JOIN product p ON p.id = c.product_id
                  WHERE c.user_email = ? AND p.status_status_id = 1";
        
        $result = $this->query($query, [$email]);
        return $result->fetch_assoc();
    }
}
