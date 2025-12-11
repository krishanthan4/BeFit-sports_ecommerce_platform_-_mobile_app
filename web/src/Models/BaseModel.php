<?php
namespace BeFit\Models;

require_once __DIR__ . '/../../config/database.php';

/**
 * Base Model Class
 * Common database operations
 */
class BaseModel {
    
    protected $table;
    protected $primaryKey = 'id';
    
    /**
     * Find record by ID
     * 
     * @param int $id Record ID
     * @return array|null
     */
    public function find($id) {
        $result = \Database::search(
            "SELECT * FROM {$this->table} WHERE {$this->primaryKey} = ?",
            [$id]
        );
        
        return $result->num_rows > 0 ? $result->fetch_assoc() : null;
    }
    
    /**
     * Get all records
     * 
     * @param int $limit Limit
     * @param int $offset Offset
     * @return array
     */
    public function all($limit = null, $offset = 0) {
        $query = "SELECT * FROM {$this->table}";
        
        if ($limit) {
            $query .= " LIMIT $limit OFFSET $offset";
        }
        
        $result = \Database::search($query);
        return $this->fetchAll($result);
    }
    
    /**
     * Find records by condition
     * 
     * @param string $column Column name
     * @param mixed $value Column value
     * @return array
     */
    public function where($column, $value) {
        $result = \Database::search(
            "SELECT * FROM {$this->table} WHERE $column = ?",
            [$value]
        );
        
        return $this->fetchAll($result);
    }
    
    /**
     * Find first record by condition
     * 
     * @param string $column Column name
     * @param mixed $value Column value
     * @return array|null
     */
    public function whereFirst($column, $value) {
        $result = \Database::search(
            "SELECT * FROM {$this->table} WHERE $column = ? LIMIT 1",
            [$value]
        );
        
        return $result->num_rows > 0 ? $result->fetch_assoc() : null;
    }
    
    /**
     * Create new record
     * 
     * @param array $data Record data
     * @return int|bool Insert ID or false
     */
    public function create($data) {
        $columns = array_keys($data);
        $placeholders = array_fill(0, count($data), '?');
        
        $query = "INSERT INTO {$this->table} (" . implode(', ', $columns) . ") 
                  VALUES (" . implode(', ', $placeholders) . ")";
        
        $result = \Database::iud($query, array_values($data));
        
        return $result ? \Database::getLastInsertId() : false;
    }
    
    /**
     * Update record
     * 
     * @param int $id Record ID
     * @param array $data Update data
     * @return bool
     */
    public function update($id, $data) {
        $setPairs = [];
        $values = [];
        
        foreach ($data as $column => $value) {
            $setPairs[] = "$column = ?";
            $values[] = $value;
        }
        
        $values[] = $id;
        
        $query = "UPDATE {$this->table} SET " . implode(', ', $setPairs) . 
                 " WHERE {$this->primaryKey} = ?";
        
        return \Database::iud($query, $values);
    }
    
    /**
     * Delete record
     * 
     * @param int $id Record ID
     * @return bool
     */
    public function delete($id) {
        return \Database::iud(
            "DELETE FROM {$this->table} WHERE {$this->primaryKey} = ?",
            [$id]
        );
    }
    
    /**
     * Count records
     * 
     * @param string $column Optional column for WHERE clause
     * @param mixed $value Optional value for WHERE clause
     * @return int
     */
    public function count($column = null, $value = null) {
        if ($column && $value) {
            $result = \Database::search(
                "SELECT COUNT(*) as count FROM {$this->table} WHERE $column = ?",
                [$value]
            );
        } else {
            $result = \Database::search("SELECT COUNT(*) as count FROM {$this->table}");
        }
        
        $row = $result->fetch_assoc();
        return (int) $row['count'];
    }
    
    /**
     * Fetch all results as array
     * 
     * @param mysqli_result $result Query result
     * @return array
     */
    protected function fetchAll($result) {
        $data = [];
        while ($row = $result->fetch_assoc()) {
            $data[] = $row;
        }
        return $data;
    }
    
    /**
     * Execute custom query
     * 
     * @param string $query SQL query
     * @param array $params Query parameters
     * @return mysqli_result
     */
    protected function query($query, $params = []) {
        return \Database::search($query, $params);
    }
}
