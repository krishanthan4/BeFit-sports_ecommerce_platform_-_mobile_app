# BeFit E-Commerce Platform - Refactored REST API

## 🎯 Overview
This project has been completely refactored to follow modern REST API standards with proper separation of concerns, modular architecture, and clean code principles.

## 📁 New Project Structure

```
web/
├── api.php                    # Main API entry point
├── .htaccess                  # URL rewriting for clean routes
├── API_DOCUMENTATION.md       # Complete API documentation
├── config/
│   ├── app.php               # Application configuration
│   ├── database.php          # Database connection & queries
│   └── .env.example          # Environment variables template
├── src/
│   ├── Core/
│   │   ├── Request.php       # HTTP request handler
│   │   ├── Response.php      # Standardized JSON responses
│   │   ├── Router.php        # REST routing system
│   │   └── Validator.php     # Input validation
│   ├── Middleware/
│   │   ├── AuthMiddleware.php     # User authentication
│   │   ├── AdminMiddleware.php    # Admin authentication
│   │   ├── CorsMiddleware.php     # CORS handling
│   │   └── JsonMiddleware.php     # JSON response headers
│   ├── Models/
│   │   ├── BaseModel.php     # Base model with common queries
│   │   ├── User.php          # User model
│   │   ├── Product.php       # Product model
│   │   └── Cart.php          # Cart model
│   └── Controllers/
│       ├── AuthController.php      # Authentication logic
│       ├── ProductController.php   # Product operations
│       ├── CartController.php      # Cart operations
│       ├── UserController.php      # User profile
│       ├── OrderController.php     # Order management
│       ├── WishlistController.php  # Wishlist operations
│       └── CategoryController.php  # Categories & metadata
├── routes/
│   └── api.php               # All API routes defined here
├── public/
│   └── images/               # Public assets
└── resources/
    └── profile_images/       # User profile images
```

## 🚀 Key Improvements

### 1. **RESTful API Architecture**
- Proper HTTP methods (GET, POST, PUT, DELETE)
- Clean URL structure: `/api/v1/resource/{id}`
- Standardized JSON responses
- Proper status codes

### 2. **Modular Design**
- **Controllers**: Business logic separated by domain
- **Models**: Database operations with OOP
- **Middleware**: Reusable request/response filters
- **Routing**: Centralized route definitions

### 3. **Security**
- Password hashing with bcrypt
- Session-based authentication
- CORS support for cross-origin requests
- Input validation on all endpoints
- SQL injection prevention with prepared statements

### 4. **Code Quality**
- PSR-4 autoloading
- Namespaced classes
- DRY principles
- Single responsibility principle
- Comprehensive error handling

### 5. **Developer Experience**
- Complete API documentation
- Environment-based configuration
- Consistent code structure
- Easy to extend and maintain

## 🔧 Setup Instructions

### 1. Configure Database
```bash
cd web/config
cp ../sampleConnection.php database.php
# Edit database.php with your credentials
```

Update the database constants:
```php
private const DB_HOST = 'localhost';
private const DB_USER = 'your_username';
private const DB_PASS = 'your_password';
private const DB_NAME = 'befit_db';
```

### 2. Configure Environment
```bash
cp config/.env.example config/.env
# Edit .env with your settings
```

### 3. Configure Apache
Ensure mod_rewrite is enabled and .htaccess is working.

### 4. Set Permissions
```bash
chmod 755 public/images/product_images
chmod 755 resources/profile_images
```

### 5. Test API
```bash
# Test the API
curl http://localhost/api/v1/categories
```

## 📱 Mobile App Migration

The mobile app needs to update API endpoints. See `MOBILE_APP_MIGRATION.md` for the complete mapping.

### Quick Example:
**Old:**
```java
Config.BACKEND_API_URL + "signInProcess.php"
```

**New:**
```java
Config.BACKEND_API_URL + "auth/login"
```

## 🌐 API Endpoints

### Base URL
```
http://localhost/api/v1/
```

### Main Endpoints
- `POST /auth/login` - User login
- `POST /auth/register` - User registration
- `GET /products` - Get products with filters
- `GET /products/{id}` - Get product details
- `POST /cart` - Add to cart
- `GET /cart` - Get cart items
- `POST /orders/checkout` - Create order
- `GET /user/profile` - Get user profile

See `API_DOCUMENTATION.md` for complete documentation.

## 🔄 Differences from Old Structure

| Old Structure | New Structure | Improvement |
|--------------|---------------|-------------|
| `/api/signInProcess.php` | `POST /api/v1/auth/login` | REST convention |
| `/api/getHomeProductsProcess.php` | `GET /api/v1/products` | Clean URLs |
| `/processes/signInProcess.php` | Removed (Consolidated) | No duplication |
| Mixed PHP/HTML | Pure JSON API | Proper separation |
| Direct DB queries | Model layer | Better abstraction |
| No validation | Validator class | Input safety |
| Inconsistent responses | Standardized format | Predictable |

## 🧪 Testing

### Test Authentication
```bash
curl -X POST http://localhost/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"password123"}'
```

### Test Products
```bash
curl http://localhost/api/v1/products?category=1&limit=10
```

### Test with Session
```bash
curl http://localhost/api/v1/cart \
  -H "Cookie: BEFIT_SESSION=your_session_id"
```

## 📖 Code Examples

### Creating a New Controller
```php
<?php
namespace BeFit\Controllers;

use BeFit\Core\Request;
use BeFit\Core\Response;

class MyController {
    public function myMethod(Request $request) {
        $data = $request->all();
        // Your logic here
        Response::success($data);
    }
}
```

### Adding a New Route
```php
// In routes/api.php
$router->get('/my-endpoint', [MyController::class, 'myMethod']);
```

### Creating a New Model
```php
<?php
namespace BeFit\Models;

class MyModel extends BaseModel {
    protected $table = 'my_table';
    protected $primaryKey = 'id';
    
    // Custom methods here
}
```

## 🐛 Troubleshooting

### 404 Errors
- Check if mod_rewrite is enabled
- Verify .htaccess file exists
- Check Apache AllowOverride settings

### Database Connection Errors
- Verify credentials in `config/database.php`
- Check MySQL service is running
- Test connection with MySQL client

### CORS Errors
- Check CORS settings in `config/app.php`
- Verify headers in browser dev tools
- Update allowed origins if needed

### Session Not Working
- Check PHP session settings
- Verify cookies are enabled
- Check secure flag settings

## 📝 Contributing

When adding new features:
1. Create a new controller in `src/Controllers/`
2. Create model if needed in `src/Models/`
3. Add routes in `routes/api.php`
4. Update API documentation
5. Test thoroughly

## 🔐 Security Notes

- Never commit `.env` or `config/database.php`
- Use HTTPS in production
- Set secure cookie flags in production
- Implement rate limiting for production
- Regular security audits

## 📄 License

[Your License Here]

## 👥 Support

For issues or questions:
- Check API_DOCUMENTATION.md
- Review code examples
- Check troubleshooting section

---

**Note**: The old `/api/` and `/processes/` folders can be archived or removed after successful migration and testing.
