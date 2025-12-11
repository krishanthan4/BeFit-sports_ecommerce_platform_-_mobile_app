# 🚀 BeFit Quick Reference Card

## One-Command Operations

```bash
# Start everything
./start.sh

# Stop everything
docker-compose down

# View logs
docker-compose logs -f web

# Restart services
docker-compose restart

# Build mobile APK
docker-compose --profile build up mobile_build

# Remove everything (including volumes)
docker-compose down -v
```

## Access URLs

| Service | URL | Credentials |
|---------|-----|-------------|
| Web App | http://localhost | - |
| API | http://localhost/api/v1 | - |
| phpMyAdmin | http://localhost:8080 | root / root |
| MySQL | localhost:3306 | befit_user / befit_password |

## API Quick Test

```bash
# Get categories (no auth)
curl http://localhost/api/v1/categories

# Login
curl -X POST http://localhost/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"Pass123!","rememberMe":true}' \
  -c cookies.txt

# Get products (with session)
curl http://localhost/api/v1/products?page=1 -b cookies.txt
```

## Mobile App Config

```java
// befit_mobile/app/src/main/java/.../util/Config.java

// For Android Emulator
public static final String BACKEND_API_URL = "http://10.0.2.2/api/v1/";

// For Physical Device (replace with your IP)
public static final String BACKEND_API_URL = "http://192.168.1.100/api/v1/";
```

## Web Frontend Usage

```javascript
// Include in HTML
<script src="/public/js/api-client.js"></script>
<script src="/public/js/api-services.js"></script>

// Use in code
AuthService.login(email, password)
  .then(data => console.log('Logged in:', data))
  .catch(err => console.error('Error:', err));

ProductService.getProducts({ page: 1, category: 2 })
  .then(data => console.log('Products:', data));

CartService.addToCart(productId, quantity)
  .then(data => console.log('Added to cart:', data));
```

## Database Access

```bash
# MySQL CLI
docker-compose exec db mysql -u befit_user -p
# Password: befit_password

# Backup
docker-compose exec db mysqldump -u root -proot befit_db > backup.sql

# Restore
docker-compose exec -T db mysql -u root -proot befit_db < backup.sql
```

## Common Issues

### Port 80 already in use
```bash
# Edit docker-compose.yml, change:
ports:
  - "8000:80"  # Use port 8000 instead
```

### Permission errors
```bash
docker-compose exec web chown -R www-data:www-data /var/www/html
```

### Database not ready
```bash
docker-compose restart db
docker-compose logs db
```

### Mobile can't connect
- Emulator: Use `10.0.2.2` not `localhost`
- Device: Use your computer's actual IP

## File Locations

| What | Where |
|------|-------|
| REST API Code | `web/src/Controllers/` |
| API Routes | `web/routes/api.php` |
| Mobile Services | `befit_mobile/app/.../data/api/` |
| Web JS | `web/public/js/` |
| Database Config | `web/config/database.php` |
| Environment | `web/.env` |

## REST Endpoints Summary

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /auth/register | Register user |
| POST | /auth/login | Login |
| POST | /auth/logout | Logout |
| GET | /products | List products |
| GET | /products/{id} | Product details |
| POST | /cart | Add to cart |
| GET | /cart | View cart |
| POST | /orders/checkout | Place order |
| GET | /wishlist | View wishlist |
| GET | /user/profile | Get profile |

Full API docs: `web/API_DOCUMENTATION.md`

## GitHub Actions

```bash
# Install act (run CI locally)
# macOS
brew install act

# Run specific job
act -j test-backend
act -j build-mobile
act -j docker-build
```

## Production Deployment

1. Update `web/.env`:
   - `APP_ENV=production`
   - `APP_DEBUG=false`
   - Change passwords

2. Deploy:
   ```bash
   docker-compose -f docker-compose.prod.yml up -d
   ```

3. Set up SSL/TLS certificates

4. Configure backups

## Support

- 📖 Full Docs: `README.md`
- 🐳 Docker Guide: `DOCKER_README.md`
- 📡 API Reference: `web/API_DOCUMENTATION.md`
- 📱 Mobile Migration: `web/MOBILE_APP_MIGRATION.md`
- ✅ Completion Status: `COMPLETION_SUMMARY.md`

## Emergency Commands

```bash
# Nuclear option - reset everything
docker-compose down -v
rm -rf mysql_data
docker-compose up -d

# Check what's running
docker-compose ps

# Inspect container
docker-compose exec web bash

# Real-time logs
docker-compose logs -f --tail=100 web
```

---

**Quick Start:** `./start.sh` → Open http://localhost 🎉
