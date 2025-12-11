#!/bin/bash

set -e

echo "======================================="
echo "   BeFit Docker Startup Script"
echo "======================================="
echo ""

check_docker() {
    if ! command -v docker &> /dev/null; then
        echo "❌ Docker is not installed. Please install Docker Desktop."
        exit 1
    fi
    echo "✓ Docker is installed"
}

check_docker_compose() {
    if ! command -v docker-compose &> /dev/null; then
        echo "❌ Docker Compose is not installed."
        exit 1
    fi
    echo "✓ Docker Compose is installed"
}

setup_env() {
    if [ ! -f web/.env ]; then
        cp web/.env.docker web/.env
        echo "✓ Created .env file"
    else
        echo "✓ .env file already exists"
    fi
}

create_directories() {
    mkdir -p web/public/images/products
    mkdir -p web/public/images/profiles
    mkdir -p web/logs
    touch web/public/images/products/.gitkeep
    touch web/public/images/profiles/.gitkeep
    echo "✓ Created necessary directories"
}

start_services() {
    echo ""
    echo "Starting Docker containers..."
    docker-compose up -d
    echo ""
}

wait_for_services() {
    echo "Waiting for services to be ready..."
    sleep 10
    
    if docker-compose ps | grep -q "Up"; then
        echo "✓ Services are running"
    else
        echo "❌ Some services failed to start"
        docker-compose logs
        exit 1
    fi
}

show_info() {
    echo ""
    echo "======================================="
    echo "   BeFit is now running!"
    echo "======================================="
    echo ""
    echo "Access your application at:"
    echo "  🌐 Web App:      http://localhost"
    echo "  🔧 phpMyAdmin:   http://localhost:8080"
    echo "  🗄️  MySQL:        localhost:3306"
    echo ""
    echo "Database Credentials:"
    echo "  Database: befit_db"
    echo "  Username: befit_user"
    echo "  Password: befit_password"
    echo ""
    echo "Useful commands:"
    echo "  View logs:       docker-compose logs -f"
    echo "  Stop services:   docker-compose down"
    echo "  Restart:         docker-compose restart"
    echo ""
    echo "API Documentation: web/API_DOCUMENTATION.md"
    echo "======================================="
    echo ""
}

main() {
    check_docker
    check_docker_compose
    setup_env
    create_directories
    start_services
    wait_for_services
    show_info
}

main
