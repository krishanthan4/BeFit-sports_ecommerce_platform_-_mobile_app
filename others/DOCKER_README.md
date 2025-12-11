# BeFit Docker Setup

## Quick Start

### Prerequisites
- Docker Desktop installed
- Docker Compose installed
- At least 4GB RAM available

### Running the Application

1. **Start all services:**
```bash
docker-compose up -d
```

2. **Access the application:**
- Web Application: http://localhost
- phpMyAdmin: http://localhost:8080
- MySQL: localhost:3306

3. **View logs:**
```bash
docker-compose logs -f web
```

4. **Stop all services:**
```bash
docker-compose down
```

### Building Mobile APK

To build the Android APK:
```bash
docker-compose --profile build up mobile_build
```

The APK will be available in `befit_mobile/app/build/outputs/apk/debug/`

### Database Configuration

Default credentials:
- **Database Name:** befit_db
- **Username:** befit_user
- **Password:** befit_password
- **Root Password:** root

### Environment Variables

Copy `.env.docker` to `.env` and adjust as needed:
```bash
cd web
cp .env.docker .env
```

### Troubleshooting

**Database connection issues:**
```bash
docker-compose restart db
docker-compose logs db
```

**Permission issues:**
```bash
docker-compose exec web chown -R www-data:www-data /var/www/html/public/images
```

**Reset everything:**
```bash
docker-compose down -v
docker-compose up -d
```

### Development Workflow

1. Make code changes in `web/` or `befit_mobile/`
2. For web changes, they're reflected immediately (volume mounted)
3. For mobile changes, rebuild:
```bash
docker-compose --profile build up --build mobile_build
```

### Production Considerations

Before deploying to production:
1. Change all default passwords
2. Set `APP_ENV=production` in `.env`
3. Set `APP_DEBUG=false`
4. Configure proper SSL certificates
5. Update CORS settings
6. Configure email SMTP settings
