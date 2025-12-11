<?php
namespace BeFit\Models;

/**
 * User Model
 */
class User extends BaseModel {
    
    protected $table = 'user';
    protected $primaryKey = 'email';
    
    /**
     * Get user with address details
     * 
     * @param string $email User email
     * @return array|null
     */
    public function getUserWithAddress($email) {
        $query = "SELECT u.*, ua.*, c.city_name, d.district_name, p.province_name, co.name as country_name,
                         pi.path as profile_image
                  FROM user u
                  LEFT JOIN user_has_address ua ON ua.user_email = u.email
                  LEFT JOIN city c ON c.city_id = ua.city_city_id
                  LEFT JOIN district d ON d.district_id = c.district_district_id
                  LEFT JOIN province p ON p.province_id = d.province_province_id
                  LEFT JOIN country co ON co.id = p.country_id
                  LEFT JOIN profile_img pi ON pi.user_email = u.email
                  WHERE u.email = ? AND u.status_status_id = 1";
        
        $result = $this->query($query, [$email]);
        return $result->num_rows > 0 ? $result->fetch_assoc() : null;
    }
    
    /**
     * Check if user is a seller
     * 
     * @param string $email User email
     * @return bool
     */
    public function isSeller($email) {
        $result = $this->query(
            "SELECT COUNT(*) as count FROM product WHERE user_email = ?",
            [$email]
        );
        $row = $result->fetch_assoc();
        return $row['count'] > 0;
    }
    
    /**
     * Update user profile
     * 
     * @param string $email User email
     * @param array $data Update data
     * @return bool
     */
    public function updateProfile($email, $data) {
        $setPairs = [];
        $values = [];
        
        foreach ($data as $column => $value) {
            $setPairs[] = "$column = ?";
            $values[] = $value;
        }
        
        $values[] = $email;
        
        $query = "UPDATE user SET " . implode(', ', $setPairs) . " WHERE email = ?";
        return \Database::iud($query, $values);
    }
    
    /**
     * Get user's address
     * 
     * @param string $email User email
     * @return array|null
     */
    public function getAddress($email) {
        $query = "SELECT ua.*, c.city_name, d.district_name, p.province_name, co.name as country_name
                  FROM user_has_address ua
                  INNER JOIN city c ON c.city_id = ua.city_city_id
                  INNER JOIN district d ON d.district_id = c.district_district_id
                  INNER JOIN province p ON p.province_id = d.province_province_id
                  INNER JOIN country co ON co.id = p.country_id
                  WHERE ua.user_email = ?";
        
        $result = $this->query($query, [$email]);
        return $result->num_rows > 0 ? $result->fetch_assoc() : null;
    }
    
    /**
     * Update or create user address
     * 
     * @param string $email User email
     * @param array $addressData Address data
     * @return bool
     */
    public function updateOrCreateAddress($email, $addressData) {
        // Check if address exists
        $existing = $this->query(
            "SELECT * FROM user_has_address WHERE user_email = ?",
            [$email]
        );
        
        if ($existing->num_rows > 0) {
            // Update
            $setPairs = [];
            $values = [];
            
            foreach ($addressData as $column => $value) {
                $setPairs[] = "$column = ?";
                $values[] = $value;
            }
            
            $values[] = $email;
            
            $query = "UPDATE user_has_address SET " . implode(', ', $setPairs) . 
                     " WHERE user_email = ?";
            return \Database::iud($query, $values);
        } else {
            // Insert
            $addressData['user_email'] = $email;
            $columns = array_keys($addressData);
            $placeholders = array_fill(0, count($addressData), '?');
            
            $query = "INSERT INTO user_has_address (" . implode(', ', $columns) . ") 
                      VALUES (" . implode(', ', $placeholders) . ")";
            
            return \Database::iud($query, array_values($addressData));
        }
    }
    
    /**
     * Get all users with pagination
     * 
     * @param int $limit Limit
     * @param int $offset Offset
     * @param string $search Search query
     * @return array
     */
    public function getAllUsers($limit = 20, $offset = 0, $search = '') {
        $query = "SELECT u.*, pi.path as profile_image 
                  FROM user u
                  LEFT JOIN profile_img pi ON pi.user_email = u.email
                  WHERE 1=1";
        
        $params = [];
        
        if (!empty($search)) {
            $query .= " AND (u.fname LIKE ? OR u.lname LIKE ? OR u.email LIKE ? OR u.mobile LIKE ?)";
            $searchTerm = "%$search%";
            $params = [$searchTerm, $searchTerm, $searchTerm, $searchTerm];
        }
        
        $query .= " ORDER BY u.joined_date DESC LIMIT ? OFFSET ?";
        $params[] = $limit;
        $params[] = $offset;
        
        $result = $this->query($query, $params);
        return $this->fetchAll($result);
    }
}
