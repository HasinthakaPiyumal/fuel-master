# Fuel Master

Fuel Master is a comprehensive fuel quota management system designed to streamline fuel distribution and administration. The system includes a **Spring Boot API**, a **React + Vite Admin Panel**, and a **Flutter Mobile App** for pumpers and users.

## Features

### Backend (Spring Boot API)
- User Authentication & Authorization (JWT)
- Role-based access control (Admin, Pumper, User)
- Fuel quota allocation and management
- Transaction logging and reporting
- RESTful API
- PostgreSQL database integration with JPA

### Frontend (React + Vite)
- Admin dashboard for fuel quota management
- User-friendly UI with ShadCN components
- Responsive and optimized performance

### Mobile App (Flutter)
- Pumper interface for fuel distribution tracking
- User profile and fuel quota details
- QR code scanning for authentication using mobile_scanner

## Tech Stack

### Backend:
- Java 21
- Spring Boot
- PostgreSQL/Mysql
- JPA for database handling
- JWT Authentication

### Frontend:
- React + Vite
- ShadCN UI
- Tailwind CSS
- Axios for API requests

### Mobile:
- Flutter (Dart)
- Dio for API calls
- mobile_scanner for QR Code scanning

## Repository & Structure
The entire project (backend, frontend, and mobile app) is contained in a single repository:
[Fuel Master Repository](https://github.com/HasinthakaPiyumal/fuel-master.git)

### Root Folder Structure:
- `fuel_master_backend/` - Spring Boot backend API
- `fuel_master_frontend/` - React + Vite admin panel
- `pumper_mobile_app/` - Flutter mobile application
- `.gitignore`
- `README.md`

## Installation & Setup

### Backend (Spring Boot API)
1. Clone the repository:
   ```sh
   git clone https://github.com/HasinthakaPiyumal/fuel-master.git
   cd fuel-master/fuel_master_backend
   ```
2. Configure the database in `application.properties`
3. Build and run the application:
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```

### Frontend (React + Vite Admin Panel)
1. Navigate to the frontend folder:
   ```sh
   cd fuel_master_frontend
   ```
2. Install dependencies:
   ```sh
   npm install
   ```
3. Run the development server:
   ```sh
   npm run dev
   ```

### Mobile (Flutter App)
1. Navigate to the mobile app folder:
   ```sh
   cd pumper_mobile_app
   ```
2. Install dependencies:
   ```sh
   flutter pub get
   ```
3. Run the app on an emulator or device:
   ```sh
   flutter run
   ```

## Contribution Guidelines
1. Fork the repository
2. Create a new branch (`feature-branch`)
3. Commit changes
4. Push to your fork and submit a PR

## License
This project is licensed under the MIT License.

