<?php
namespace BeFit\Core;

/**
 * Validator Class
 * Validate input data
 */
class Validator {
    
    private $data;
    private $errors = [];
    
    public function __construct($data) {
        $this->data = $data;
    }
    
    /**
     * Validate data against rules
     * 
     * @param array $rules Validation rules
     * @return bool
     */
    public function validate($rules) {
        foreach ($rules as $field => $ruleSet) {
            $rules = explode('|', $ruleSet);
            $value = $this->data[$field] ?? null;
            
            foreach ($rules as $rule) {
                $this->applyRule($field, $value, $rule);
            }
        }
        
        return empty($this->errors);
    }
    
    /**
     * Apply validation rule
     * 
     * @param string $field Field name
     * @param mixed $value Field value
     * @param string $rule Validation rule
     */
    private function applyRule($field, $value, $rule) {
        $fieldName = $this->formatFieldName($field);
        
        // Parse rule with parameters
        if (strpos($rule, ':') !== false) {
            list($rule, $params) = explode(':', $rule, 2);
            $params = explode(',', $params);
        } else {
            $params = [];
        }
        
        switch ($rule) {
            case 'required':
                if (empty($value) && $value !== '0') {
                    $this->errors[$field][] = "$fieldName is required";
                }
                break;
                
            case 'email':
                if (!empty($value) && !filter_var($value, FILTER_VALIDATE_EMAIL)) {
                    $this->errors[$field][] = "$fieldName must be a valid email address";
                }
                break;
                
            case 'min':
                $min = $params[0] ?? 0;
                if (!empty($value) && strlen($value) < $min) {
                    $this->errors[$field][] = "$fieldName must be at least $min characters";
                }
                break;
                
            case 'max':
                $max = $params[0] ?? 0;
                if (!empty($value) && strlen($value) > $max) {
                    $this->errors[$field][] = "$fieldName must not exceed $max characters";
                }
                break;
                
            case 'numeric':
                if (!empty($value) && !is_numeric($value)) {
                    $this->errors[$field][] = "$fieldName must be a number";
                }
                break;
                
            case 'integer':
                if (!empty($value) && !filter_var($value, FILTER_VALIDATE_INT)) {
                    $this->errors[$field][] = "$fieldName must be an integer";
                }
                break;
                
            case 'alpha':
                if (!empty($value) && !ctype_alpha($value)) {
                    $this->errors[$field][] = "$fieldName must contain only letters";
                }
                break;
                
            case 'alphanumeric':
                if (!empty($value) && !ctype_alnum($value)) {
                    $this->errors[$field][] = "$fieldName must contain only letters and numbers";
                }
                break;
                
            case 'in':
                if (!empty($value) && !in_array($value, $params)) {
                    $allowed = implode(', ', $params);
                    $this->errors[$field][] = "$fieldName must be one of: $allowed";
                }
                break;
                
            case 'unique':
                // Format: unique:table,column
                if (count($params) >= 2) {
                    list($table, $column) = $params;
                    if (!empty($value)) {
                        require_once __DIR__ . '/../../config/database.php';
                        $result = \Database::search("SELECT * FROM $table WHERE $column = ?", [$value]);
                        if ($result->num_rows > 0) {
                            $this->errors[$field][] = "$fieldName already exists";
                        }
                    }
                }
                break;
                
            case 'exists':
                // Format: exists:table,column
                if (count($params) >= 2) {
                    list($table, $column) = $params;
                    if (!empty($value)) {
                        require_once __DIR__ . '/../../config/database.php';
                        $result = \Database::search("SELECT * FROM $table WHERE $column = ?", [$value]);
                        if ($result->num_rows === 0) {
                            $this->errors[$field][] = "$fieldName does not exist";
                        }
                    }
                }
                break;
                
            case 'phone':
                if (!empty($value) && !preg_match('/^[0-9]{10}$/', $value)) {
                    $this->errors[$field][] = "$fieldName must be a valid 10-digit phone number";
                }
                break;
                
            case 'url':
                if (!empty($value) && !filter_var($value, FILTER_VALIDATE_URL)) {
                    $this->errors[$field][] = "$fieldName must be a valid URL";
                }
                break;
        }
    }
    
    /**
     * Get validation errors
     * 
     * @return array
     */
    public function errors() {
        return $this->errors;
    }
    
    /**
     * Check if validation passed
     * 
     * @return bool
     */
    public function passes() {
        return empty($this->errors);
    }
    
    /**
     * Check if validation failed
     * 
     * @return bool
     */
    public function fails() {
        return !$this->passes();
    }
    
    /**
     * Format field name for error messages
     * 
     * @param string $field Field name
     * @return string
     */
    private function formatFieldName($field) {
        return ucfirst(str_replace('_', ' ', $field));
    }
}
