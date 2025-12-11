<?php
namespace BeFit\Controllers;

use BeFit\Core\Request;
use BeFit\Core\Response;
use BeFit\Core\Validator;
use BeFit\Models\User;

/**
 * User Controller
 * Handle user profile and settings operations
 */
class UserController {
    
    private $userModel;
    
    public function __construct() {
        $this->userModel = new User();
    }
    
    /**
     * Get user profile
     * GET /api/v1/user/profile
     */
    public function profile(Request $request) {
        session_start();
        
        if (!isset($_SESSION['user'])) {
            Response::unauthorized();
        }
        
        $userEmail = $_SESSION['user']['email'];
        $user = $this->userModel->getUserWithAddress($userEmail);
        
        if (!$user) {
            Response::notFound('User not found');
        }
        
        // Remove sensitive data
        unset($user['password']);
        unset($user['verification_code']);
        
        // Check if seller
        $isSeller = $this->userModel->isSeller($userEmail);
        $user['is_seller'] = $isSeller;
        
        Response::success($user);
    }
    
    /**
     * Update user profile
     * PUT /api/v1/user/profile
     */
    public function updateProfile(Request $request) {
        session_start();
        
        if (!isset($_SESSION['user'])) {
            Response::unauthorized();
        }
        
        $userEmail = $_SESSION['user']['email'];
        $data = $request->all();
        
        // Validation
        $validator = new Validator($data);
        if (!$validator->validate([
            'fname' => 'required',
            'lname' => 'required',
            'mobile' => 'required|phone'
        ])) {
            Response::validationError($validator->errors());
        }
        
        // Update user
        $userData = [
            'fname' => $data['fname'],
            'lname' => $data['lname'],
            'mobile' => $data['mobile']
        ];
        
        if (isset($data['gender']) && in_array($data['gender'], [1, 2])) {
            $userData['gender_gender_id'] = $data['gender'];
        }
        
        $result = $this->userModel->updateProfile($userEmail, $userData);
        
        // Update address if provided
        if (isset($data['city_id']) && !empty($data['city_id'])) {
            $addressData = [
                'city_city_id' => $data['city_id'],
                'line1' => $data['line1'] ?? '',
                'line2' => $data['line2'] ?? '',
                'postal_code' => $data['postal_code'] ?? null
            ];
            
            $this->userModel->updateOrCreateAddress($userEmail, $addressData);
        }
        
        // Handle profile image upload
        if ($request->file('profile_image')) {
            $this->uploadProfileImage($userEmail, $request->file('profile_image'), $data['fname']);
        }
        
        if ($result) {
            // Update session
            $_SESSION['user'] = $this->userModel->getUserWithAddress($userEmail);
            
            Response::success(null, 'Profile updated successfully');
        }
        
        Response::serverError('Failed to update profile');
    }
    
    /**
     * Get user's address
     * GET /api/v1/user/address
     */
    public function getAddress(Request $request) {
        session_start();
        
        if (!isset($_SESSION['user'])) {
            Response::unauthorized();
        }
        
        $userEmail = $_SESSION['user']['email'];
        $address = $this->userModel->getAddress($userEmail);
        
        Response::success($address);
    }
    
    /**
     * Update user's address
     * PUT /api/v1/user/address
     */
    public function updateAddress(Request $request) {
        session_start();
        
        if (!isset($_SESSION['user'])) {
            Response::unauthorized();
        }
        
        $userEmail = $_SESSION['user']['email'];
        $data = $request->all();
        
        // Validation
        $validator = new Validator($data);
        if (!$validator->validate([
            'city_id' => 'required|integer',
            'line1' => 'required',
            'line2' => 'required'
        ])) {
            Response::validationError($validator->errors());
        }
        
        $addressData = [
            'city_city_id' => $data['city_id'],
            'line1' => $data['line1'],
            'line2' => $data['line2'],
            'postal_code' => $data['postal_code'] ?? null
        ];
        
        $result = $this->userModel->updateOrCreateAddress($userEmail, $addressData);
        
        if ($result) {
            Response::success(null, 'Address updated successfully');
        }
        
        Response::serverError('Failed to update address');
    }
    
    /**
     * Change password
     * PUT /api/v1/user/change-password
     */
    public function changePassword(Request $request) {
        session_start();
        
        if (!isset($_SESSION['user'])) {
            Response::unauthorized();
        }
        
        $userEmail = $_SESSION['user']['email'];
        $data = $request->all();
        
        // Validation
        $validator = new Validator($data);
        if (!$validator->validate([
            'current_password' => 'required',
            'new_password' => 'required|min:6'
        ])) {
            Response::validationError($validator->errors());
        }
        
        // Verify current password
        $user = $this->userModel->whereFirst('email', $userEmail);
        
        if (!password_verify($data['current_password'], $user['password'])) {
            Response::error('Current password is incorrect', 400);
        }
        
        // Update password
        $hashedPassword = password_hash($data['new_password'], PASSWORD_DEFAULT);
        $result = $this->userModel->updateProfile($userEmail, [
            'password' => $hashedPassword
        ]);
        
        if ($result) {
            Response::success(null, 'Password changed successfully');
        }
        
        Response::serverError('Failed to change password');
    }
    
    /**
     * Upload profile image
     */
    private function uploadProfileImage($email, $file, $fname) {
        $config = require __DIR__ . '/../../config/app.php';
        $uploadPath = $config['upload']['paths']['profiles'];
        
        if ($file['error'] === UPLOAD_ERR_OK) {
            $extension = pathinfo($file['name'], PATHINFO_EXTENSION);
            $filename = preg_replace('/[^a-zA-Z0-9]/', '', $fname) . '_' . uniqid() . '.' . $extension;
            $destination = $uploadPath . $filename;
            
            if (move_uploaded_file($file['tmp_name'], $destination)) {
                // Delete old profile image
                \Database::iud("DELETE FROM profile_img WHERE user_email = ?", [$email]);
                
                // Insert new profile image
                \Database::iud(
                    "INSERT INTO profile_img (path, user_email) VALUES (?, ?)",
                    ['../resources/profile_images/' . $filename, $email]
                );
            }
        }
    }
}
