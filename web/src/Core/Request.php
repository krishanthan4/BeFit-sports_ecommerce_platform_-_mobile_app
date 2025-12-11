<?php
namespace BeFit\Core;

/**
 * HTTP Request Handler
 * Parse and validate incoming requests
 */
class Request {
    
    private $data;
    private $method;
    private $uri;
    private $headers;
    
    public function __construct() {
        $this->method = $_SERVER['REQUEST_METHOD'];
        $this->uri = parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH);
        $this->headers = getallheaders() ?: [];
        $this->parseData();
    }
    
    /**
     * Parse request data based on content type
     */
    private function parseData() {
        $contentType = $this->header('Content-Type') ?? '';
        
        if ($this->method === 'GET') {
            $this->data = $_GET;
        } elseif ($this->method === 'POST' || $this->method === 'PUT' || $this->method === 'DELETE') {
            if (strpos($contentType, 'application/json') !== false) {
                $json = file_get_contents('php://input');
                $this->data = json_decode($json, true) ?? [];
            } else {
                $this->data = array_merge($_POST, $_REQUEST);
            }
        } else {
            $this->data = [];
        }
    }
    
    /**
     * Get request method
     * 
     * @return string
     */
    public function method() {
        return $this->method;
    }
    
    /**
     * Get request URI
     * 
     * @return string
     */
    public function uri() {
        return $this->uri;
    }
    
    /**
     * Get all request data
     * 
     * @return array
     */
    public function all() {
        return $this->data;
    }
    
    /**
     * Get specific input value
     * 
     * @param string $key Input key
     * @param mixed $default Default value
     * @return mixed
     */
    public function input($key, $default = null) {
        return $this->data[$key] ?? $default;
    }
    
    /**
     * Check if input exists
     * 
     * @param string $key Input key
     * @return bool
     */
    public function has($key) {
        return isset($this->data[$key]);
    }
    
    /**
     * Get specific header
     * 
     * @param string $key Header name
     * @param mixed $default Default value
     * @return mixed
     */
    public function header($key, $default = null) {
        // Headers are case-insensitive
        $key = strtolower($key);
        foreach ($this->headers as $name => $value) {
            if (strtolower($name) === $key) {
                return $value;
            }
        }
        return $default;
    }
    
    /**
     * Get uploaded file
     * 
     * @param string $key File input name
     * @return array|null
     */
    public function file($key) {
        return $_FILES[$key] ?? null;
    }
    
    /**
     * Validate required fields
     * 
     * @param array $fields Required field names
     * @return array Validation errors
     */
    public function validate($fields) {
        $errors = [];
        
        foreach ($fields as $field) {
            if (!$this->has($field) || empty($this->input($field))) {
                $errors[$field] = ucfirst(str_replace('_', ' ', $field)) . ' is required';
            }
        }
        
        return $errors;
    }
    
    /**
     * Get client IP address
     * 
     * @return string
     */
    public function ip() {
        if (!empty($_SERVER['HTTP_CLIENT_IP'])) {
            return $_SERVER['HTTP_CLIENT_IP'];
        } elseif (!empty($_SERVER['HTTP_X_FORWARDED_FOR'])) {
            return $_SERVER['HTTP_X_FORWARDED_FOR'];
        } else {
            return $_SERVER['REMOTE_ADDR'];
        }
    }
    
    /**
     * Check if request is AJAX
     * 
     * @return bool
     */
    public function isAjax() {
        return !empty($_SERVER['HTTP_X_REQUESTED_WITH']) && 
               strtolower($_SERVER['HTTP_X_REQUESTED_WITH']) === 'xmlhttprequest';
    }
    
    /**
     * Check if request is JSON
     * 
     * @return bool
     */
    public function isJson() {
        $contentType = $this->header('Content-Type') ?? '';
        return strpos($contentType, 'application/json') !== false;
    }
}
