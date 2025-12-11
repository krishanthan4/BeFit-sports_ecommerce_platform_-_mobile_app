<?php
namespace BeFit\Core;

/**
 * Router Class
 * Handle API routing with REST conventions
 */
class Router {
    
    private $routes = [];
    private $middlewares = [];
    
    /**
     * Register GET route
     * 
     * @param string $path Route path
     * @param callable $handler Route handler
     */
    public function get($path, $handler) {
        $this->addRoute('GET', $path, $handler);
    }
    
    /**
     * Register POST route
     * 
     * @param string $path Route path
     * @param callable $handler Route handler
     */
    public function post($path, $handler) {
        $this->addRoute('POST', $path, $handler);
    }
    
    /**
     * Register PUT route
     * 
     * @param string $path Route path
     * @param callable $handler Route handler
     */
    public function put($path, $handler) {
        $this->addRoute('PUT', $path, $handler);
    }
    
    /**
     * Register DELETE route
     * 
     * @param string $path Route path
     * @param callable $handler Route handler
     */
    public function delete($path, $handler) {
        $this->addRoute('DELETE', $path, $handler);
    }
    
    /**
     * Add route to collection
     * 
     * @param string $method HTTP method
     * @param string $path Route path
     * @param callable $handler Route handler
     */
    private function addRoute($method, $path, $handler) {
        // Convert route parameters to regex
        $pattern = preg_replace('/\{([a-zA-Z0-9_]+)\}/', '(?P<$1>[^/]+)', $path);
        $pattern = '#^' . $pattern . '$#';
        
        $this->routes[] = [
            'method' => $method,
            'path' => $path,
            'pattern' => $pattern,
            'handler' => $handler
        ];
    }
    
    /**
     * Add middleware
     * 
     * @param callable $middleware Middleware function
     */
    public function middleware($middleware) {
        $this->middlewares[] = $middleware;
    }
    
    /**
     * Dispatch route
     * 
     * @param Request $request Request object
     */
    public function dispatch($request) {
        $method = $request->method();
        $uri = $request->uri();
        
        // Remove /api/v1 prefix if exists
        $uri = preg_replace('#^/api/v1#', '', $uri);
        
        // Handle OPTIONS requests for CORS
        if ($method === 'OPTIONS') {
            http_response_code(200);
            exit;
        }
        
        // Execute middlewares
        foreach ($this->middlewares as $middleware) {
            $middleware($request);
        }
        
        // Find matching route
        foreach ($this->routes as $route) {
            if ($route['method'] === $method && preg_match($route['pattern'], $uri, $matches)) {
                // Extract parameters
                $params = array_filter($matches, 'is_string', ARRAY_FILTER_USE_KEY);
                
                // Call handler
                $handler = $route['handler'];
                
                if (is_callable($handler)) {
                    return call_user_func($handler, $request, $params);
                } elseif (is_array($handler) && count($handler) === 2) {
                    list($controller, $method) = $handler;
                    $controllerInstance = new $controller();
                    return $controllerInstance->$method($request, $params);
                }
            }
        }
        
        // No route found
        Response::notFound('Route not found');
    }
    
    /**
     * Group routes with common prefix
     * 
     * @param string $prefix Route prefix
     * @param callable $callback Callback function
     */
    public function group($prefix, $callback) {
        $previousRoutes = $this->routes;
        $this->routes = [];
        
        // Execute callback
        $callback($this);
        
        // Add prefix to new routes
        foreach ($this->routes as &$route) {
            $route['path'] = $prefix . $route['path'];
            $pattern = preg_replace('/\{([a-zA-Z0-9_]+)\}/', '(?P<$1>[^/]+)', $route['path']);
            $route['pattern'] = '#^' . $pattern . '$#';
        }
        
        // Merge with previous routes
        $this->routes = array_merge($previousRoutes, $this->routes);
    }
}
