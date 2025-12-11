# 🏋️ BeFit Sports E-Commerce Platform

A complete sports e-commerce platform with web frontend, REST API backend, and Android mobile app.

## 🚀 Quick Start with Docker

### Prerequisites
- Docker Desktop 20.10+
- Docker Compose 2.0+
- 4GB+ RAM available

### Start the Application

```bash
./start.sh
```

Or manually:
```bash
docker-compose up -d
```

**Access Points:**
- 🌐 **Web App:** http://localhost
- 🔧 **phpMyAdmin:** http://localhost:8080
- 📡 **REST API:** http://localhost/api/v1

### Stop the Application

```bash
docker-compose down
```

## 📁 Project Structure

```
BeFit-sports_ecommerce_platform/
├── web/                          # PHP Backend & Web Frontend
│   ├── api.php                   # REST API entry point
│   ├── config/                   # Configuration files
│   ├── src/
│   │   ├── Core/                # Framework (Router, Request, Response, Validator)
│   │   ├── Controllers/         # 7 API controllers
│   │   ├── Models/              # Data models
│   │   └── Middleware/          # Auth, CORS, JSON middleware
│   ├── routes/api.php           # 50+ REST endpoints
│   ├── public/
│   │   ├── js/
│   │   │   ├── api-client.js   # Unified API client
│   │   │   └── api-services.js # Service layer
│   │   ├── css/                 # Tailwind CSS
│   │   └── images/              # Product & profile images
│   └── guis/                    # PHP views
│
├── befit_mobile/                # Android Mobile App
│   └── app/src/main/java/com/sprinsec/mobile_v2/
│       ├── ui/                  # Activities & Fragments
│       │   ├── admin/          # Admin dashboard
│       │   ├── user/           # User interface
│       │   └── fragments/      # Reusable components
│       └── data/api/           # API services
│           ├── client/         # ApiClient, ApiResponse
│           └── ...Service.java # REST service classes
│
├── others/
│   └── database.sql            # Complete database schema
│
├── Dockerfile.web              # Apache + PHP 8.1 container
├── Dockerfile.mobile           # Gradle Android build
├── docker-compose.yml          # Multi-service orchestration
├── .github/workflows/ci.yml    # GitHub Actions CI/CD
└── start.sh                    # One-command startup script
```

## 🛠 Technology Stack

### Backend
- **Language:** PHP 8.1+
- **Database:** MySQL 8.0 (PDO with prepared statements)
- **Architecture:** MVC with REST API
- **Security:** bcrypt, session management, input validation
- **Framework:** Custom lightweight MVC

### Frontend
- **Web:** HTML5, Tailwind CSS, Vanilla JavaScript
- **Mobile:** Java (Android SDK 21+)
- **API Client:** Fetch API, OkHttp3

### DevOps
- **Containers:** Docker, Docker Compose
- **CI/CD:** GitHub Actions
- **Admin:** phpMyAdmin

## 📡 REST API Overview

### Base URL
```
http://localhost/api/v1
```

### Authentication
- **POST** `/auth/register` - Register new user
- **POST** `/auth/login` - Login
- **POST** `/auth/logout` - Logout
- **GET** `/auth/session` - Check session
- **POST** `/auth/verify` - Verify email code

### Products
- **GET** `/products` - List products (paginated)
- **GET** `/products/{id}` - Get product details
- **GET** `/products/search` - Search products
- **POST** `/products` - Create product (seller)
- **PUT** `/products/{id}` - Update product (seller)
- **GET** `/products/seller` - Seller's products

### Cart
- **GET** `/cart` - Get cart items
- **POST** `/cart` - Add to cart
- **PUT** `/cart/{id}` - Update quantity
- **DELETE** `/cart/{id}` - Remove item
- **DELETE** `/cart` - Clear cart

### Orders
- **GET** `/orders` - List orders
- **GET** `/orders/{id}` - Order details
- **POST** `/orders/checkout` - Place order
- **PUT** `/orders/{id}/status` - Update status (seller)

### Wishlist
- **GET** `/wishlist` - Get wishlist
- **POST** `/wishlist` - Add item
- **DELETE** `/wishlist/{id}` - Remove item

### User
- **GET** `/user/profile` - Get profile
- **PUT** `/user/profile` - Update profile
- **PUT** `/user/address` - Update address
- **PUT** `/user/password` - Change password

### Categories
- **GET** `/categories` - List categories
- **GET** `/categories/brands` - List brands
- **GET** `/categories/models/{brand_id}` - List models

📖 **Full documentation:** `web/API_DOCUMENTATION.md`

## 🔧 Development

### Web Development

**Edit code:**
```bash
# Changes auto-reload (volume mounted)
vim web/src/Controllers/ProductController.php
vim web/public/js/api-services.js
```

**View logs:**
```bash
docker-compose logs -f web
```

**Access database:**
```bash
docker-compose exec db mysql -u befit_user -p
# Password: befit_password
```

### Mobile Development

**Update API base URL:**
```java
// befit_mobile/app/src/main/java/.../util/Config.java
public static final String BACKEND_API_URL = "http://10.0.2.2/api/v1/";
```

**Build APK:**
```bash
docker-compose --profile build up mobile_build
```

**Output:** `befit_mobile/app/build/outputs/apk/debug/app-debug.apk`

### Database Management

**phpMyAdmin:** http://localhost:8080
- Username: `root`
- Password: `root`

**Backup:**
```bash
docker-compose exec db mysqldump -u root -proot befit_db > backup_$(date +%Y%m%d).sql
```

**Restore:**
```bash
docker-compose exec -T db mysql -u root -proot befit_db < backup.sql
```

## 🧪 Testing

### Test API Endpoints

```bash
# Check health
curl http://localhost/api/v1/categories

# Login
curl -X POST http://localhost/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"Password123!","rememberMe":true}'

# Get products (with session cookie)
curl http://localhost/api/v1/products?page=1 \
  -H "Cookie: befit_session=YOUR_SESSION_ID"
```

### Run CI/CD Locally

Install [act](https://github.com/nektos/act):
```bash
act -j test-backend
act -j build-mobile
```

## 🎯 Features

### User Features
- ✅ User registration with email verification
- ✅ Secure login with session management
- ✅ Product browsing with pagination & filters
- ✅ Advanced search functionality
- ✅ Shopping cart management
- ✅ Wishlist/watchlist
- ✅ Order placement & tracking
- ✅ Order history
- ✅ Product reviews & ratings
- ✅ Profile management with image upload

### Seller Features
- ✅ Seller registration
- ✅ Product listing management
- ✅ Inventory management
- ✅ Order fulfillment
- ✅ Sales history & analytics
- ✅ Order status updates

### Admin Features
- ✅ User management
- ✅ Seller verification
- ✅ Category management
- ✅ Product moderation
- ✅ Order oversight
- ✅ Dashboard analytics
- ✅ Feedback & reviews management

## 🐛 Troubleshooting

### Database Connection Failed
```bash
docker-compose restart db
docker-compose logs db
```

### Port Already in Use
Edit `docker-compose.yml`:
```yaml
services:
  web:
    ports:
      - "8000:80"  # Change 80 to available port
```

### Permission Errors
```bash
docker-compose exec web chown -R www-data:www-data /var/www/html
docker-compose exec web chmod -R 755 /var/www/html
```

### Mobile App Can't Connect
- **Emulator:** Use `http://10.0.2.2/api/v1/`
- **Physical Device:** Use your computer's IP `http://192.168.x.x/api/v1/`
- Update `Config.java` with correct URL

### API Returns 404
```bash
# Check Apache rewrite module
docker-compose exec web a2enmod rewrite
docker-compose restart web
```

## 📦 Migration from Old Code

Old API folders have been preserved:
- `web/api` → `web/api_old` ✅
- `web/processes` → `web/processes_old` ✅

**After thorough testing, remove:**
```bash
rm -rf web/api_old web/processes_old
```

## 🚢 Production Deployment

### Pre-deployment Checklist

- [ ] Change all default passwords
- [ ] Update `web/.env`:
  - [ ] `APP_ENV=production`
  - [ ] `APP_DEBUG=false`
  - [ ] Set strong database password
- [ ] Configure SSL/TLS certificates
- [ ] Update CORS allowed origins
- [ ] Configure SMTP for emails
- [ ] Set up automated database backups
- [ ] Configure file upload limits
- [ ] Set up monitoring (e.g., New Relic, Datadog)
- [ ] Review security headers
- [ ] Set up CDN for static assets
- [ ] Configure rate limiting

### Docker Production

Create `docker-compose.prod.yml`:
```yaml
version: '3.8'
services:
  web:
    restart: always
    environment:
      APP_ENV: production
      APP_DEBUG: false
  db:
    restart: always
    volumes:
      - /opt/mysql-data:/var/lib/mysql
```

Deploy:
```bash
docker-compose -f docker-compose.prod.yml up -d
```

## 📊 Architecture Highlights

### Before (Old Structure)
- ❌ 110+ scattered PHP files
- ❌ Duplicated code (api/ and processes/)
- ❌ Mixed HTML/PHP output
- ❌ No input validation
- ❌ Direct SQL queries
- ❌ Inconsistent responses

### After (New Structure)
- ✅ 29 organized files (~7,200 LOC)
- ✅ Single source of truth (api/)
- ✅ Pure JSON responses
- ✅ Centralized validation
- ✅ Prepared statements (PDO)
- ✅ Standardized REST API

### Performance
- 🚀 50+ optimized endpoints
- 🚀 Pagination support
- 🚀 Efficient database queries
- 🚀 Session-based auth (fast)
- 🚀 Apache mod_rewrite

## 🤝 Contributing

1. Fork the repository
2. Create feature branch: `git checkout -b feature/amazing-feature`
3. Commit changes: `git commit -m 'Add amazing feature'`
4. Push to branch: `git push origin feature/amazing-feature`
5. Open pull request

CI/CD will automatically:
- ✅ Test PHP syntax
- ✅ Build Android APK
- ✅ Build Docker images
- ✅ Run code quality checks

## 📝 Documentation

- **API Reference:** [`web/API_DOCUMENTATION.md`](web/API_DOCUMENTATION.md)
- **Mobile Migration:** [`web/MOBILE_APP_MIGRATION.md`](web/MOBILE_APP_MIGRATION.md)
- **Quick Start:** [`web/QUICK_START.md`](web/QUICK_START.md)
- **Docker Guide:** [`DOCKER_README.md`](DOCKER_README.md)
- **Project Summary:** [`web/PROJECT_SUMMARY.md`](web/PROJECT_SUMMARY.md)

## 📄 License

Proprietary - All rights reserved © BeFit

## 👥 Contributors

- **Development Team:** BeFit Engineering
- **Architecture:** Modern REST API with MVC pattern
- **Version:** 2.0 (Refactored from legacy codebase)

---

## 💡 Getting Help

**Issues?**
1. Check [Troubleshooting](#-troubleshooting) section
2. Review documentation in `web/` folder
3. Inspect logs: `docker-compose logs -f`
4. Verify database connection in phpMyAdmin

**Success Metrics:**
- ✅ REST API with 50+ endpoints
- ✅ Mobile app with unified API client
- ✅ Web frontend with service layer
- ✅ Docker containerization
- ✅ CI/CD pipeline
- ✅ Comprehensive documentation

**Next Steps:**
1. Run `./start.sh` to launch platform
2. Access http://localhost to test web app
3. Test API with curl or Postman
4. Build mobile APK with Docker
5. Deploy to production following checklist

---

**Made with ❤️ by the BeFit Team**

