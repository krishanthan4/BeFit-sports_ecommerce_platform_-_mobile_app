#!/bin/bash
set -e

echo "Starting BeFit Web Container..."

if [ ! -f /var/www/html/.env ]; then
    echo "Creating .env file from environment variables..."
    cat > /var/www/html/.env <<EOF
DB_HOST=${DB_HOST:-localhost}
DB_NAME=${DB_NAME:-befit_db}
DB_USER=${DB_USER:-root}
DB_PASSWORD=${DB_PASSWORD:-}
DB_CHARSET=utf8mb4

APP_ENV=${APP_ENV:-development}
APP_DEBUG=${APP_DEBUG:-true}
APP_URL=${APP_URL:-http://localhost}

CORS_ALLOWED_ORIGINS=${CORS_ALLOWED_ORIGINS:-*}
CORS_ALLOWED_METHODS=GET,POST,PUT,DELETE,OPTIONS
CORS_ALLOWED_HEADERS=Content-Type,Authorization,X-Requested-With

SESSION_LIFETIME=1440
SESSION_COOKIE_NAME=befit_session

UPLOAD_MAX_SIZE=10485760
UPLOAD_ALLOWED_TYPES=jpg,jpeg,png,gif,webp

EMAIL_SMTP_HOST=${EMAIL_SMTP_HOST:-smtp.gmail.com}
EMAIL_SMTP_PORT=${EMAIL_SMTP_PORT:-587}
EMAIL_SMTP_USERNAME=${EMAIL_SMTP_USERNAME:-}
EMAIL_SMTP_PASSWORD=${EMAIL_SMTP_PASSWORD:-}
EMAIL_FROM_ADDRESS=noreply@befit.com
EMAIL_FROM_NAME=BeFit
EOF
fi

if [ ! -f /var/www/html/config/database.php ]; then
    echo "Creating database.php from environment..."
    cat > /var/www/html/config/database.php <<'EOPHP'
<?php

class Database {
    private static $instance = null;
    private $connection;

    private function __construct() {
        $host = getenv('DB_HOST') ?: 'localhost';
        $dbname = getenv('DB_NAME') ?: 'befit_db';
        $username = getenv('DB_USER') ?: 'root';
        $password = getenv('DB_PASSWORD') ?: '';
        $charset = getenv('DB_CHARSET') ?: 'utf8mb4';

        $maxRetries = 30;
        $retryDelay = 2;

        for ($i = 0; $i < $maxRetries; $i++) {
            try {
                $dsn = "mysql:host=$host;dbname=$dbname;charset=$charset";
                $options = [
                    PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
                    PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
                    PDO::ATTR_EMULATE_PREPARES => false,
                ];
                $this->connection = new PDO($dsn, $username, $password, $options);
                error_log("Database connected successfully");
                break;
            } catch (PDOException $e) {
                if ($i < $maxRetries - 1) {
                    error_log("Database connection attempt " . ($i + 1) . " failed, retrying in ${retryDelay}s...");
                    sleep($retryDelay);
                } else {
                    error_log("Database connection error: " . $e->getMessage());
                    throw new Exception("Database connection failed after $maxRetries attempts");
                }
            }
        }
    }

    public static function getInstance() {
        if (self::$instance === null) {
            self::$instance = new self();
        }
        return self::$instance;
    }

    public function getConnection() {
        return $this->connection;
    }

    public function connect() {
        return $this->connection;
    }

    public function search($query, $params = []) {
        try {
            $stmt = $this->connection->prepare($query);
            $this->bindParams($stmt, $params);
            $stmt->execute();
            return $stmt;
        } catch (PDOException $e) {
            error_log("Query error: " . $e->getMessage());
            throw new Exception("Database query failed");
        }
    }

    public function iud($query, $params = []) {
        try {
            $stmt = $this->connection->prepare($query);
            $this->bindParams($stmt, $params);
            return $stmt->execute();
        } catch (PDOException $e) {
            error_log("Query error: " . $e->getMessage());
            throw new Exception("Database query failed");
        }
    }

    private function bindParams($stmt, $params) {
        foreach ($params as $key => $value) {
            if (is_int($key)) {
                $key = $key + 1;
            }
            
            $type = PDO::PARAM_STR;
            if (is_int($value)) {
                $type = PDO::PARAM_INT;
            } elseif (is_bool($value)) {
                $type = PDO::PARAM_BOOL;
            } elseif (is_null($value)) {
                $type = PDO::PARAM_NULL;
            }
            
            $stmt->bindValue($key, $value, $type);
        }
    }

    public function beginTransaction() {
        return $this->connection->beginTransaction();
    }

    public function commit() {
        return $this->connection->commit();
    }

    public function rollBack() {
        return $this->connection->rollBack();
    }

    public function lastInsertId() {
        return $this->connection->lastInsertId();
    }
}
EOPHP
fi

echo "Setting permissions..."
chown -R www-data:www-data /var/www/html/public/images
chmod -R 775 /var/www/html/public/images

echo "BeFit Web Container ready!"

exec apache2-foreground
