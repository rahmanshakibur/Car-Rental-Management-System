# 🚗 Code Cafe — Car Rental Management System

A modern, desktop-based **Car Rental Management System** built with **Java 25**, **JavaFX 21**, **Maven**, and **MySQL**. Designed and developed with a modular, beginner-friendly architecture and clean UI styling.

![Java](https://img.shields.io/badge/Java-25-orange?logo=java)
![JavaFX](https://img.shields.io/badge/JavaFX-21-blue?logo=openjdk)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)
![Maven](https://img.shields.io/badge/Build-Maven-red?logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-green)

---

## 🌟 Key Features

- **🔐 Secure Role-Based Authentication:**
  - Database-backed authentication for system administrators.
  - Seamless login transition to the central dashboard.
- **📊 Real-Time Analytics Dashboard:**
  - Metric cards for Available Vehicles, Total Revenue, and Registered Customers.
  - Interactive Income BarChart and Customer Activity LineChart.
- **🚗 Complete Vehicle Management (CRUD):**
  - Add, Update, and Delete vehicle records.
  - Vehicle image upload and preview support.
  - Real-time search filter by Car ID, Brand, or Model.
- **📝 Automated Rental & Billing Engine:**
  - Dynamic vehicle selection showing only available cars.
  - Exact duration calculation in days using modern Java Time API.
  - Automatic rental price, payment, and change/balance calculation.
  - Auto-updates vehicle status to `Not Available` upon successful booking.
- **👥 Customer History & Booking Records:**
  - View all past and current rental transactions.
  - Search customer records by name, vehicle, or ID.
- **🎨 Modern Dark & Amber UI:**
  - Custom themed CSS inspired by the **Code Cafe** brand.

---

## 🏗️ System Architecture

The project follows a clean, modular structure with separation of concerns:

```text
com.codecafe.car_rental/
├── Launcher.java                 # Main application launcher
├── HelloApplication.java         # JavaFX lifecycle entry point
├── Database.java                 # Centralized JDBC connection factory
├── GetData.java                  # Global session data helper
├── CarData.java                  # Vehicle POJO model
├── CustomerData.java             # Customer rental record POJO model
├── RentalDatabaseHelper.java     # Database operations for rental transactions
├── RentCalculationHelper.java    # Day duration and price calculation logic
├── CarDatabaseHelper.java        # Database CRUD operations for vehicles
├── CustomerDatabaseHelper.java   # Database query helper for customer history
├── LoginController.java          # Login view controller
├── MainDashboardController.java  # Sidebar navigation shell controller
├── HomeController.java           # Dashboard analytics and charts controller
├── CarManagementController.java  # Vehicle inventory view controller
├── RentCarController.java        # Rental booking view controller
└── CustomerController.java       # Customer booking history view controller
```

---

## ⚙️ Prerequisites & Setup

### 1. Requirements
- **JDK:** Java 21 or Java 25 (e.g., Amazon Corretto 25 or Eclipse Temurin)
- **IDE:** IntelliJ IDEA (Recommended)
- **Database:** MySQL Server 8.0+ / XAMPP

### 2. Database Setup
1. Open your MySQL client (MySQL Workbench, phpMyAdmin, or Command Line).
2. Import the provided database script located in the project root:
   ```sql
   codecafe_rental_db.sql
   ```
3. Update connection credentials in `Database.java` if your local MySQL uses a different password:
   ```java
   String url = "jdbc:mysql://localhost:3306/codecafe_rental_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
   Connection con = DriverManager.getConnection(url, "root", "YOUR_PASSWORD");
   ```

### 3. Default Login Credentials
- **Username:** `sakib` (or `admin`)
- **Password:** `1234` (or `admin123`)

---

## 🚀 How to Run

### Via IntelliJ IDEA:
1. Open IntelliJ IDEA $\rightarrow$ `File` $\rightarrow$ `Open` $\rightarrow$ Select `Car Rental Project`.
2. Allow Maven to sync dependencies.
3. Navigate to:
   ```text
   src/main/java/com/codecafe/car_rental/Launcher.java
   ```
4. Right-click `Launcher.java` and select **Run 'Launcher.main()'**.

### Via Maven Command Line:
```bash
./mvnw clean compile
./mvnw javafx:run
```

---

## 👨‍💻 Author

**Sakibur Rahman**  
*Creator of Code Cafe by Sakib*  
Department of Computer Science & Engineering  
Southeast University (SEU)
