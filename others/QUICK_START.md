# BeFit Platform - Quick Start Guide

## 🚀 Get Started in 5 Minutes

### Step 1: Database Setup (2 min)
```bash
cd web
php setup.php
```
Follow the prompts to configure your database connection. This will create:
- `config/database.php` - Database connection
- `config/.env` - Environment variables
- `DbConnection.php` - Backward compatibility file

### Step 2: Web Server Setup (1 min)

#### Using Apache
Ensure `.htaccess` is working and `mod_rewrite` is enabled:
```bash
sudo a2enmod rewrite
sudo systemctl restart apache2
```

#### Using PHP Built-in Server (Development Only)
```bash
cd web
php -S localhost:8000
```

### Step 3: Test the API (1 min)
```bash
# Test categories endpoint
curl http://localhost/api/v1/categories

# Expected response:
{
  "success": true,
  "message": "Success",
  "data": [...]
}
```

### Step 4: Mobile App Configuration (1 min)
Update `befit_mobile/app/src/main/java/com/sprinsec/mobile_v2/util/Config.java`:
```java
public static final String BACKEND_API_URL = "http://your-server-ip/api/v1/";
```

---

## 📱 Test Login Flow

### 1. Register a User
```bash
curl -X POST http://localhost/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "fname": "John",
    "lname": "Doe",
    "email": "john@example.com",
    "password": "password123",
    "mobile": "0712345678",
    "gender": 1
  }'
```

### 2. Login
```bash
curl -X POST http://localhost/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -c cookies.txt \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'
```

### 3. Get Products
```bash
curl http://localhost/api/v1/products?limit=5
```

### 4. Add to Cart (Authenticated)
```bash
curl -X POST http://localhost/api/v1/cart \
  -H "Content-Type: application/json" \
  -b cookies.txt \
  -d '{
    "product_id": 1,
    "qty": 2
  }'
```

---

## 📖 Key Files Reference

| File | Purpose |
|------|---------|
| `web/api.php` | Main API entry point |
| `web/routes/api.php` | All route definitions |
| `web/config/database.php` | Database configuration |
| `web/config/app.php` | Application settings |
| `web/.htaccess` | URL rewriting rules |
| `web/API_DOCUMENTATION.md` | Complete API docs |
| `web/MOBILE_APP_MIGRATION.md` | Mobile app migration guide |

---

## 🔧 Common Tasks

### Add a New Endpoint
1. Create controller method in `src/Controllers/`
2. Add route in `routes/api.php`
3. Test with cURL or Postman

Example:
```php
// In src/Controllers/ProductController.php
public function myNewMethod(Request $request) {
    Response::success(['message' => 'Hello World']);
}

// In routes/api.php
$router->get('/products/test', [ProductController::class, 'myNewMethod']);
```

### Enable Debug Mode
Edit `config/app.php`:
```php
'debug' => true,
'environment' => 'development',
```

### Check Logs
- PHP error log: Check Apache/PHP logs
- Database queries: Enable query logging in `config/database.php`

---

## 🐛 Troubleshooting

### Problem: 404 on all API routes
**Solution:** Check `.htaccess` and `mod_rewrite`
```bash
sudo a2enmod rewrite
sudo service apache2 restart
```

### Problem: Database connection failed
**Solution:** Run setup.php again or manually edit `config/database.php`

### Problem: CORS errors
**Solution:** CORS is enabled by default. Check `config/app.php` CORS settings.

### Problem: Session not persisting
**Solution:** Ensure cookies are enabled and `credentials: 'include'` in requests.

---

## 📚 Learning Path

### For Backend Development
1. Read `README.md` - Architecture overview
2. Study `src/Controllers/AuthController.php` - Controller example
3. Read `API_DOCUMENTATION.md` - All endpoints
4. Check `src/Core/` - Core framework classes

### For Mobile Development
1. Read `MOBILE_APP_MIGRATION.md` - Endpoint mapping
2. Update one service at a time
3. Test each feature thoroughly

### For Frontend Development
1. Use Fetch API or Axios
2. Set `credentials: 'include'` for cookies
3. Handle JSON responses properly

---

## 🎯 Next Steps

### Phase 1: Testing (Current)
- ✅ API structure created
- ⬜ Test all endpoints
- ⬜ Update mobile app
- ⬜ Update web frontend

### Phase 2: Migration
- ⬜ Rename old folders (`api` → `api_old`, `processes` → `processes_old`)
- ⬜ Update all frontend calls
- ⬜ Update mobile app services
- ⬜ Test thoroughly

### Phase 3: Cleanup
- ⬜ Remove old API folders
- ⬜ Remove duplicate code
- ⬜ Final testing
- ⬜ Deploy to production

---

## 💡 Tips

### Security
- Change default passwords
- Use HTTPS in production
- Enable rate limiting
- Regular security audits

### Performance
- Enable caching where appropriate
- Optimize database queries
- Use CDN for static assets
- Implement pagination

### Maintenance
- Keep documentation updated
- Write tests for critical flows
- Monitor error logs
- Regular backups

---

## 📞 Support Resources

- **API Documentation:** `API_DOCUMENTATION.md`
- **Architecture Guide:** `README.md`
- **Mobile Migration:** `MOBILE_APP_MIGRATION.md`
- **Database Schema:** `others/database.sql`

---

## ✅ Checklist Before Deployment

- [ ] Database configuration set
- [ ] Environment variables configured
- [ ] .htaccess working properly
- [ ] All endpoints tested
- [ ] Mobile app updated
- [ ] Frontend updated
- [ ] SSL certificate installed
- [ ] Secure cookie flags enabled
- [ ] Error reporting disabled
- [ ] Backups configured
- [ ] Monitoring set up

---

**Ready to start?** Run `php setup.php` now! 🚀
