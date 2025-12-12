<?php
class Database {
    private static $connection;
    private static $envLoaded = false;
    
    private static function loadEnv() {
        if (self::$envLoaded) {
            return;
        }
        
        $envFile = __DIR__ . '/../.env';
        if (file_exists($envFile)) {
            $lines = file($envFile, FILE_IGNORE_NEW_LINES | FILE_SKIP_EMPTY_LINES);
            foreach ($lines as $line) {
                if (strpos(trim($line), '#') === 0) {
                    continue;
                }
                
                if (strpos($line, '=') !== false) {
                    list($key, $value) = explode('=', $line, 2);
                    $key = trim($key);
                    $value = trim($value);
                    
                    if (!getenv($key)) {
                        putenv("$key=$value");
                        $_ENV[$key] = $value;
                        $_SERVER[$key] = $value;
                    }
                }
            }
        }
        
        self::$envLoaded = true;
    }
    
    private static function getDbHost() {
        self::loadEnv();
        return getenv('DB_HOST') ?: 'db';
    }
    
    private static function getDbUser() {
        self::loadEnv();
        return getenv('DB_USER') ?: 'root';
    }
    
    private static function getDbPass() {
        self::loadEnv();
        return getenv('DB_PASSWORD') ?: 'password';
    }
    
    private static function getDbName() {
        self::loadEnv();
        return getenv('DB_NAME') ?: 'befit_db';
    }
    
    private static function getDbPort() {
        self::loadEnv();
        return getenv('DB_PORT') ?: '3306';
    }
    
    public static function connect() {
        if (!isset(self::$connection)) {
            self::$connection = new mysqli(
                self::getDbHost(),
                self::getDbUser(),
                self::getDbPass(),
                self::getDbName(),
                self::getDbPort()
            );
            
            if (self::$connection->connect_error) {
                throw new Exception("Database connection failed: " . self::$connection->connect_error);
            }
            
            self::$connection->set_charset("utf8mb4");
        }
        
        return self::$connection;
    }
    
    /**
     * Execute SELECT query
     * 
     * @param string $query SQL query
     * @param array $params Query parameters
     * @return mysqli_result
     */
    public static function search($query, $params = []) {
        $conn = self::connect();
        $stmt = $conn->prepare($query);
        
        if ($stmt === false) {
            throw new Exception("Query preparation failed: " . $conn->error);
        }
        
        if (!empty($params)) {
            self::bindParams($stmt, $params);
        }
        
        $stmt->execute();
        $result = $stmt->get_result();
        $stmt->close();
        
        return $result;
    }
    
    /**
     * Execute INSERT, UPDATE, DELETE queries
     * 
     * @param string $query SQL query
     * @param array $params Query parameters
     * @return bool
     */
    public static function iud($query, $params = []) {
        $conn = self::connect();
        $stmt = $conn->prepare($query);
        
        if ($stmt === false) {
            throw new Exception("Query preparation failed: " . $conn->error);
        }
        
        if (!empty($params)) {
            self::bindParams($stmt, $params);
        }
        
        $result = $stmt->execute();
        $stmt->close();
        
        return $result;
    }
    
    /**
     * Get last inserted ID
     * 
     * @return int
     */
    public static function getLastInsertId() {
        return self::connect()->insert_id;
    }
    
    /**
     * Detect parameter type for binding
     * 
     * @param mixed $param Parameter value
     * @return string Type indicator (i, d, s)
     */
    private static function detectParamType($param) {
        switch (gettype($param)) {
            case 'integer':
                return 'i';
            case 'double':
                return 'd';
            case 'string':
            default:
                return 's';
        }
    }
    
    /**
     * Bind parameters to prepared statement
     * 
     * @param mysqli_stmt $stmt Prepared statement
     * @param array $params Parameters to bind
     */
    private static function bindParams($stmt, $params) {
        $types = '';
        $bindParams = [];
        
        foreach ($params as $param) {
            $types .= self::detectParamType($param);
            $bindParams[] = $param;
        }
        
        if (!empty($bindParams)) {
            $stmt->bind_param($types, ...$bindParams);
        }
    }
    
    /**
     * Begin transaction
     */
    public static function beginTransaction() {
        self::connect()->begin_transaction();
    }
    
    /**
     * Commit transaction
     */
    public static function commit() {
        self::connect()->commit();
    }
    
    /**
     * Rollback transaction
     */
    public static function rollback() {
        self::connect()->rollback();
    }
    
    /**
     * Close database connection
     */
    public static function close() {
        if (isset(self::$connection)) {
            self::$connection->close();
            self::$connection = null;
        }
    }
}
