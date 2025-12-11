<?php
namespace BeFit\Models;

/**
 * Product Model
 */
class Product extends BaseModel {
    
    protected $table = 'product';
    protected $primaryKey = 'id';
    
    /**
     * Get product with all details
     * 
     * @param int $id Product ID
     * @return array|null
     */
    public function getProductDetails($id) {
        $query = "SELECT p.*, 
                         c.cat_name, c.cat_img, c.cat_icon,
                         mhb.brand_brand_id, b.brand_name,
                         m.model_name,
                         con.condition_name,
                         u.fname as seller_fname, u.lname as seller_lname,
                         ssf.shipping_fee as standard_shipping_fee,
                         co.name as country_name,
                         GROUP_CONCAT(DISTINCT pi.img_path) as images,
                         GROUP_CONCAT(DISTINCT clr.clr_name) as colors
                  FROM product p
                  INNER JOIN category c ON c.cat_id = p.category_cat_id
                  INNER JOIN model_has_brand mhb ON mhb.id = p.model_has_brand_id
                  INNER JOIN brand b ON b.brand_id = mhb.brand_brand_id
                  INNER JOIN model m ON m.model_id = mhb.model_model_id
                  INNER JOIN `condition` con ON con.condition_id = p.condition_condition_id
                  INNER JOIN user u ON u.email = p.user_email
                  INNER JOIN standard_shipping_fee ssf ON ssf.id = p.standard_shipping_fee_id
                  INNER JOIN country co ON co.id = ssf.country_id
                  LEFT JOIN product_img pi ON pi.product_id = p.id
                  LEFT JOIN product_has_color phc ON phc.product_id = p.id
                  LEFT JOIN color clr ON clr.clr_id = phc.color_clr_id
                  WHERE p.id = ?
                  GROUP BY p.id";
        
        $result = $this->query($query, [$id]);
        
        if ($result->num_rows > 0) {
            $product = $result->fetch_assoc();
            $product['images'] = $product['images'] ? explode(',', $product['images']) : [];
            $product['colors'] = $product['colors'] ? explode(',', $product['colors']) : [];
            return $product;
        }
        
        return null;
    }
    
    /**
     * Get products with filters and pagination
     * 
     * @param array $filters Filter criteria
     * @param int $limit Limit
     * @param int $offset Offset
     * @return array
     */
    public function getProducts($filters = [], $limit = 20, $offset = 0) {
        $query = "SELECT p.*, 
                         c.cat_name,
                         b.brand_name,
                         m.model_name,
                         (SELECT img_path FROM product_img WHERE product_id = p.id LIMIT 1) as image
                  FROM product p
                  INNER JOIN category c ON c.cat_id = p.category_cat_id
                  INNER JOIN model_has_brand mhb ON mhb.id = p.model_has_brand_id
                  INNER JOIN brand b ON b.brand_id = mhb.brand_brand_id
                  INNER JOIN model m ON m.model_id = mhb.model_model_id
                  WHERE p.status_status_id = 1";
        
        $params = [];
        
        // Apply filters
        if (!empty($filters['category'])) {
            $query .= " AND p.category_cat_id = ?";
            $params[] = $filters['category'];
        }
        
        if (!empty($filters['brand'])) {
            $query .= " AND mhb.brand_brand_id = ?";
            $params[] = $filters['brand'];
        }
        
        if (!empty($filters['condition'])) {
            $query .= " AND p.condition_condition_id = ?";
            $params[] = $filters['condition'];
        }
        
        if (!empty($filters['min_price'])) {
            $query .= " AND p.price >= ?";
            $params[] = $filters['min_price'];
        }
        
        if (!empty($filters['max_price'])) {
            $query .= " AND p.price <= ?";
            $params[] = $filters['max_price'];
        }
        
        if (!empty($filters['search'])) {
            $query .= " AND (p.title LIKE ? OR p.description LIKE ? OR m.model_name LIKE ?)";
            $searchTerm = "%{$filters['search']}%";
            $params[] = $searchTerm;
            $params[] = $searchTerm;
            $params[] = $searchTerm;
        }
        
        // Sorting
        $sortBy = $filters['sort_by'] ?? 'newest';
        switch ($sortBy) {
            case 'price_low':
                $query .= " ORDER BY p.price ASC";
                break;
            case 'price_high':
                $query .= " ORDER BY p.price DESC";
                break;
            case 'oldest':
                $query .= " ORDER BY p.datetime_added ASC";
                break;
            case 'newest':
            default:
                $query .= " ORDER BY p.datetime_added DESC";
                break;
        }
        
        $query .= " LIMIT ? OFFSET ?";
        $params[] = $limit;
        $params[] = $offset;
        
        $result = $this->query($query, $params);
        return $this->fetchAll($result);
    }
    
    /**
     * Get product count with filters
     * 
     * @param array $filters Filter criteria
     * @return int
     */
    public function getProductCount($filters = []) {
        $query = "SELECT COUNT(*) as count
                  FROM product p
                  INNER JOIN model_has_brand mhb ON mhb.id = p.model_has_brand_id
                  WHERE p.status_status_id = 1";
        
        $params = [];
        
        if (!empty($filters['category'])) {
            $query .= " AND p.category_cat_id = ?";
            $params[] = $filters['category'];
        }
        
        if (!empty($filters['brand'])) {
            $query .= " AND mhb.brand_brand_id = ?";
            $params[] = $filters['brand'];
        }
        
        if (!empty($filters['condition'])) {
            $query .= " AND p.condition_condition_id = ?";
            $params[] = $filters['condition'];
        }
        
        if (!empty($filters['min_price'])) {
            $query .= " AND p.price >= ?";
            $params[] = $filters['min_price'];
        }
        
        if (!empty($filters['max_price'])) {
            $query .= " AND p.price <= ?";
            $params[] = $filters['max_price'];
        }
        
        if (!empty($filters['search'])) {
            $query .= " AND (p.title LIKE ? OR p.description LIKE ?)";
            $searchTerm = "%{$filters['search']}%";
            $params[] = $searchTerm;
            $params[] = $searchTerm;
        }
        
        $result = $this->query($query, $params);
        $row = $result->fetch_assoc();
        return (int) $row['count'];
    }
    
    /**
     * Get products by seller
     * 
     * @param string $email Seller email
     * @param int $limit Limit
     * @param int $offset Offset
     * @return array
     */
    public function getSellerProducts($email, $limit = 20, $offset = 0) {
        $query = "SELECT p.*, 
                         c.cat_name,
                         b.brand_name,
                         m.model_name,
                         s.status as product_status,
                         (SELECT img_path FROM product_img WHERE product_id = p.id LIMIT 1) as image
                  FROM product p
                  INNER JOIN category c ON c.cat_id = p.category_cat_id
                  INNER JOIN model_has_brand mhb ON mhb.id = p.model_has_brand_id
                  INNER JOIN brand b ON b.brand_id = mhb.brand_brand_id
                  INNER JOIN model m ON m.model_id = mhb.model_model_id
                  INNER JOIN status s ON s.status_id = p.status_status_id
                  WHERE p.user_email = ?
                  ORDER BY p.datetime_added DESC
                  LIMIT ? OFFSET ?";
        
        $result = $this->query($query, [$email, $limit, $offset]);
        return $this->fetchAll($result);
    }
    
    /**
     * Get product reviews
     * 
     * @param int $productId Product ID
     * @return array
     */
    public function getReviews($productId) {
        $query = "SELECT f.*, u.fname, u.lname, pi.path as user_image
                  FROM feedback f
                  INNER JOIN user u ON u.email = f.user_email
                  LEFT JOIN profile_img pi ON pi.user_email = f.user_email
                  WHERE f.product_id = ?
                  ORDER BY f.date DESC";
        
        $result = $this->query($query, [$productId]);
        return $this->fetchAll($result);
    }
    
    /**
     * Get average rating
     * 
     * @param int $productId Product ID
     * @return float
     */
    public function getAverageRating($productId) {
        $query = "SELECT AVG(stars) as avg_rating, COUNT(*) as review_count
                  FROM feedback
                  WHERE product_id = ?";
        
        $result = $this->query($query, [$productId]);
        return $result->fetch_assoc();
    }
}
