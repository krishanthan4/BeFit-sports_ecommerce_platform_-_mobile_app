<?php
namespace BeFit\Controllers;

use BeFit\Core\Request;
use BeFit\Core\Response;
use BeFit\Core\Validator;
use BeFit\Models\User;

/**
 * Authentication Controller
 * Handle user authentication operations
 */
class AuthController {
    
    private $userModel;
    
    public function __construct() {
        $this->userModel = new User();
    }
    
    /**
     * User registration
     * POST /api/v1/auth/register
     */
    public function register(Request $request) {
        $data = $request->all();
        
        // Validation
        $validator = new Validator($data);
        if (!$validator->validate([
            'fname' => 'required',
            'lname' => 'required',
            'email' => 'required|email|unique:user,email',
            'password' => 'required|min:6',
            'mobile' => 'required|phone',
            'gender' => 'required|in:1,2'
        ])) {
            Response::validationError($validator->errors());
        }
        
        // Hash password
        $hashedPassword = password_hash($data['password'], PASSWORD_DEFAULT);
        
        // Generate verification code
        $verificationCode = uniqid();
        
        // Create user
        $result = \Database::iud(
            "INSERT INTO user (fname, lname, email, password, mobile, gender_gender_id, verification_code, joined_date, status_status_id) 
             VALUES (?, ?, ?, ?, ?, ?, ?, NOW(), 1)",
            [
                $data['fname'],
                $data['lname'],
                $data['email'],
                $hashedPassword,
                $data['mobile'],
                $data['gender'],
                $verificationCode
            ]
        );
        
        if ($result) {
            // TODO: Send verification email
            
            Response::success(
                ['email' => $data['email']],
                'Registration successful. Please verify your email.',
                201
            );
        } else {
            Response::serverError('Registration failed');
        }
    }
    
    /**
     * User login
     * POST /api/v1/auth/login
     */
    public function login(Request $request) {
        $data = $request->all();
        
        // Validation
        $validator = new Validator($data);
        if (!$validator->validate([
            'email' => 'required|email',
            'password' => 'required'
        ])) {
            Response::validationError($validator->errors());
        }
        
        // Find user
        $user = $this->userModel->getUserWithAddress($data['email']);
        
        if (!$user) {
            Response::error('Invalid email or password', 401);
        }
        
        // Verify password
        if (!password_verify($data['password'], $user['password'])) {
            Response::error('Invalid email or password', 401);
        }
        
        // Check if user is active
        if ($user['status_status_id'] != 1) {
            Response::error('Account is inactive. Please contact admin.', 403);
        }
        
        // Start session
        if (session_status() === PHP_SESSION_NONE) {
            session_start();
        }
        
        // Remove sensitive data
        unset($user['password']);
        unset($user['verification_code']);
        
        $_SESSION['user'] = $user;
        
        // Set cookie if remember me
        $rememberMe = $data['rememberMe'] ?? false;
        if ($rememberMe) {
            $sessionId = session_id();
            setcookie('BEFIT_SESSION', $sessionId, [
                'expires' => time() + (60 * 60 * 24 * 365),
                'path' => '/',
                'secure' => false,
                'httponly' => true,
                'samesite' => 'Lax'
            ]);
        }
        
        // Check if seller
        $isSeller = $this->userModel->isSeller($data['email']);
        
        Response::success([
            'user' => $user,
            'is_seller' => $isSeller,
            'session_id' => session_id()
        ], 'Login successful');
    }
    
    /**
     * User logout
     * POST /api/v1/auth/logout
     */
    public function logout(Request $request) {
        session_start();
        
        if (isset($_SESSION['user'])) {
            unset($_SESSION['user']);
            session_destroy();
            
            // Clear cookie
            setcookie('BEFIT_SESSION', '', time() - 3600, '/');
            
            Response::success(null, 'Logout successful');
        }
        
        Response::error('No active session', 400);
    }
    
    /**
     * Check session
     * GET /api/v1/auth/session
     */
    public function checkSession(Request $request) {
        session_start();
        
        if (isset($_SESSION['user'])) {
            $user = $_SESSION['user'];
            $isSeller = $this->userModel->isSeller($user['email']);
            
            Response::success([
                'user' => $user,
                'is_seller' => $isSeller,
                'authenticated' => true
            ]);
        }
        
        Response::success(['authenticated' => false]);
    }
    
    /**
     * Verify account
     * POST /api/v1/auth/verify
     */
    public function verify(Request $request) {
        $data = $request->all();
        
        $validator = new Validator($data);
        if (!$validator->validate([
            'email' => 'required|email',
            'code' => 'required'
        ])) {
            Response::validationError($validator->errors());
        }
        
        $user = $this->userModel->whereFirst('email', $data['email']);
        
        if (!$user) {
            Response::notFound('User not found');
        }
        
        if ($user['verification_code'] === $data['code']) {
            Response::success(null, 'Account verified successfully');
        }
        
        Response::error('Invalid verification code', 400);
    }
    
    /**
     * Request password reset
     * POST /api/v1/auth/forgot-password
     */
    public function forgotPassword(Request $request) {
        $data = $request->all();
        
        $validator = new Validator($data);
        if (!$validator->validate([
            'email' => 'required|email'
        ])) {
            Response::validationError($validator->errors());
        }
        
        $user = $this->userModel->whereFirst('email', $data['email']);
        
        if (!$user) {
            Response::notFound('Email not found');
        }
        
        // Generate reset code
        $resetCode = uniqid();
        
        $this->userModel->updateProfile($data['email'], [
            'verification_code' => $resetCode
        ]);
        
        // TODO: Send password reset email
        
        Response::success(
            ['email' => $data['email']],
            'Password reset code sent to your email'
        );
    }
    
    /**
     * Reset password
     * POST /api/v1/auth/reset-password
     */
    public function resetPassword(Request $request) {
        $data = $request->all();
        
        $validator = new Validator($data);
        if (!$validator->validate([
            'email' => 'required|email',
            'code' => 'required',
            'new_password' => 'required|min:6'
        ])) {
            Response::validationError($validator->errors());
        }
        
        $user = $this->userModel->whereFirst('email', $data['email']);
        
        if (!$user || $user['verification_code'] !== $data['code']) {
            Response::error('Invalid email or verification code', 400);
        }
        
        $hashedPassword = password_hash($data['new_password'], PASSWORD_DEFAULT);
        
        $this->userModel->updateProfile($data['email'], [
            'password' => $hashedPassword,
            'verification_code' => uniqid() // Generate new code
        ]);
        
        Response::success(null, 'Password reset successful');
    }
}
