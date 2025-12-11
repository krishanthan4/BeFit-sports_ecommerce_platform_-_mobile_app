# BeFit - Fitness Equipment E-commerce Platform

BeFit is a comprehensive fitness equipment e-commerce platform consisting of an Android mobile application and a PHP web application, both powered by a shared backend server. The platform provides a seamless shopping experience for fitness enthusiasts to browse, purchase, and review fitness equipment across various categories.

## Project Components

### 1. Android Mobile Application (befit_mobile)
A native Java Android application providing a mobile-friendly e-commerce with Admin Dashboard.

### 2. Web Application
A responsive e-commerce website built with PHP, HTML, Tailwind CSS, and JavaScript.

### 3. Backend Server
A shared PHP backend that serves both the mobile app and web application through RESTful APIs.

## Features

### User Management
- User registration and authentication
- Profile management with personal information and profile picture
- Address management with province, district, and city information
- Role-based access control (admin, seller, and regular user)

### Product Management
- Product browsing by categories (Gym, Calisthenics, Swimming, etc.)
- Product filtering and searching
- Detailed product view with images, descriptions, and specifications
- Product reviews and ratings system

### Seller Features
- Seller dashboard for managing products
- Add new products with multiple images
- Monitor order status and selling history
- Track product performance

### Shopping Features
- Wishlist management
- Shopping cart functionality
- Secure checkout with PayHere payment integration
- Order tracking and history

### Admin Features
- Comprehensive admin dashboard with analytics
- User management (regular users and sellers)
- Product and category management
- Review and feedback moderation
- Sales reports and PDF export functionality
- Promotions and offers management

### Review System
- Product rating and written reviews
- Review moderation by admins
- Average star ratings display

## Technical Stack

### Mobile Application
- Java for Android
- OkHttp for network requests
- Gson for JSON parsing
- SQLite for local data storage
- PayHere SDK for payment processing

### Web Application
- PHP for server-side logic
- HTML, JavaScript, and Tailwind CSS for frontend
- jQuery for DOM manipulation
- PayHere JavaScript SDK for payment integration

### Server
- PHP for backend API implementation
- MySQL database for data storage
- RESTful API architecture
- JSON for data exchange

## Getting Started

### Prerequisites
- Android Studio for mobile app development
- PHP 7.4+ and MySQL for the server
- Web server (Apache/Nginx)
- PayHere merchant account for payment processing

### Server Setup
1. Clone the repository
2. Configure your web server to point to the `server` directory
3. Run the Server by using ngrok.
3. Import the database schema from `others/database.sql`
4. Copy `sampleConfig.php` to `config.php` and update database credentials and PayHere API keys
5. Ensure `resources` directory has write permissions for image uploads

### Mobile App Setup
1. Open the `befit_mobile` directory in Android Studio
2. Update the backend URL in `Config.java` to point to your server
3. Build and run the app on an emulator or physical device

## Security Features
- Password hashing and secure storage
- Session management
- Mobile authentication tokens
- Secure payment processing with PayHere
- Seller contact information protection

---

## Screenshots Of Platform

### 1. Android Mobile Application (befit_mobile)
A native Java Android application providing a mobile-friendly e-commerce with Admin Dashboard.

![Splash Screen 2](https://github.com/user-attachments/assets/bbdc7b06-c245-447a-91d2-64421f64861c)

![Main Splash Screen](https://github.com/user-attachments/assets/7c515999-af96-42ed-ac12-bfdf3582b655)

![Home Screen](https://github.com/user-attachments/assets/9dae2a16-87ef-4aa1-af30-c56943db87ce)

![User Management](https://github.com/user-attachments/assets/51512ebe-e5e8-4e01-b02a-b7fd37b4473c)


### 2. Web Application
A responsive e-commerce website built with PHP, HTML, Tailwind CSS, and JavaScript.

![Login Page](https://github.com/user-attachments/assets/e1d40de3-1c1d-4410-b035-aa3663574f72)

![Screenshot from 2025-05-19 16-29-18](https://github.com/user-attachments/assets/4c4a3678-1917-467c-84e7-b6319d5c8e43)

![Screenshot from 2025-05-19 16-30-19](https://github.com/user-attachments/assets/6edceb45-1ba0-46ad-9f52-c3f3917fd1cb)

![Screenshot from 2025-05-19 16-30-34](https://github.com/user-attachments/assets/446a79de-8c06-497f-865d-a3217a9b8249)

![Screenshot from 2025-05-19 16-30-56](https://github.com/user-attachments/assets/5976b342-ffd5-48d2-b894-57e6da8fda3c)


### Admin Dashboard - Web Application

![Admin Login Page](https://github.com/user-attachments/assets/ed892675-eb03-47b4-a63c-a8acf89be7f0)

![Screenshot from 2025-05-19 16-23-51](https://github.com/user-attachments/assets/45966347-bf1b-43f1-8da7-f8fb41dceab9)

![Screenshot from 2025-05-19 16-24-05](https://github.com/user-attachments/assets/858710f8-4099-4635-a372-92cc9635e231)

![Screenshot from 2025-05-19 16-24-24](https://github.com/user-attachments/assets/fccadccd-1369-4c66-ba72-caa27dc46e3a)

![Screenshot from 2025-05-19 16-24-58](https://github.com/user-attachments/assets/524fbd49-4f77-4a52-b1fc-1db0555d5675)

![Screenshot from 2025-05-19 16-25-16](https://github.com/user-attachments/assets/a45e041a-c8fc-4d70-92ad-d8322917b46f)

![Screenshot from 2025-05-19 16-25-59](https://github.com/user-attachments/assets/65ef187a-e71e-4dbf-8aa1-9d45d2f0ed62)

