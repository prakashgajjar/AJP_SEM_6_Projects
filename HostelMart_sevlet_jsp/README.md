# HostelMart - Java Web Application

A simple yet comprehensive Java web application for hostel product management using Servlets, JSP, and MySQL.

## Features

✅ **User Authentication**
- User Registration (Signup)
- User Login with validation
- Session Management
- Logout functionality

✅ **Product Management (CRUD Operations)**
- Add new products
- View all products in table format
- Edit product details
- Delete products
- Form validation

✅ **Clean UI**
- Responsive design
- Navigation bar
- Dashboard
- Professional styling with CSS

✅ **Database**
- MySQL database integration
- JDBC with PreparedStatement (SQL injection prevention)
- Proper connection management

## Technology Stack

- **Backend**: Java (Servlets, JSP)
- **Database**: MySQL
- **Server**: Apache Tomcat
- **Frontend**: HTML, CSS
- **Database Access**: JDBC

## Project Structure

```
HostelMart/
├── src/
│   └── com/hostelmart/
│       ├── servlet/
│       │   ├── LoginServlet.java
│       │   ├── SignupServlet.java
│       │   ├── LogoutServlet.java
│       │   └── ProductServlet.java
│       ├── dao/
│       │   ├── UserDAO.java
│       │   └── ProductDAO.java
│       ├── model/
│       │   ├── User.java
│       │   └── Product.java
│       └── util/
│           └── DBUtil.java
├── webapp/
│   ├── index.jsp
│   ├── login.jsp
│   ├── signup.jsp
│   ├── dashboard.jsp
│   ├── addProduct.jsp
│   ├── viewProducts.jsp
│   ├── editProduct.jsp
│   ├── css/
│   │   └── style.css
│   └── WEB-INF/
│       └── web.xml
└── database_setup.sql
```

## Setup Instructions

### 1. Database Setup

1. Open MySQL command line or MySQL Workbench
2. Execute the `database_setup.sql` script:
   ```sql
   source database_setup.sql;
   ```
3. Or manually create the database:
   ```sql
   CREATE DATABASE hostelmart;
   USE hostelmart;
   
   CREATE TABLE users (
       id INT PRIMARY KEY AUTO_INCREMENT,
       name VARCHAR(100) NOT NULL,
       email VARCHAR(100) UNIQUE NOT NULL,
       password VARCHAR(100) NOT NULL,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
   );

   CREATE TABLE products (
       id INT PRIMARY KEY AUTO_INCREMENT,
       name VARCHAR(100) NOT NULL,
       price DECIMAL(10, 2) NOT NULL,
       quantity INT NOT NULL DEFAULT 0,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
   );
   ```

### 2. MySQL Configuration

Update database credentials in `src/com/hostelmart/util/DBUtil.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/hostelmart?useSSL=false&serverTimezone=UTC";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "";  // Set your MySQL password
```

### 3. Tomcat Setup

1. Download and install Apache Tomcat (version 9.0 or higher)
2. Download MySQL JDBC Driver (`mysql-connector-java-x.x.x.jar`)
3. Place the JDBC driver in `TOMCAT_HOME/lib/` folder

### 4. Project Deployment

#### Option A: Using Eclipse/IDE
1. Create a new Dynamic Web Project named "HostelMart"
2. Copy all Java files to `src/` folder
3. Copy all JSP and CSS files to `WebContent/` folder
4. Copy `web.xml` to `WebContent/WEB-INF/` folder
5. Configure Tomcat server in IDE
6. Deploy and run

#### Option B: Manual Compilation and Deployment
1. Compile Java files:
   ```bash
   javac -cp "mysql-connector-java-x.x.x.jar" src/com/hostelmart/*/*.java -d build/
   ```

2. Create WAR structure:
   ```
   HostelMart-war/
   ├── WEB-INF/
   │   ├── classes/ (compiled Java files)
   │   ├── lib/
   │   │   └── mysql-connector-java-x.x.x.jar
   │   └── web.xml
   └── (JSP files and CSS)
   ```

3. Deploy to Tomcat:
   ```bash
   # Copy the war file to TOMCAT_HOME/webapps/
   cp HostelMart.war TOMCAT_HOME/webapps/
   ```

### 5. Start the Application

1. Start Tomcat server:
   ```bash
   # Windows
   TOMCAT_HOME/bin/startup.bat
   
   # Linux/Mac
   TOMCAT_HOME/bin/startup.sh
   ```

2. Access the application:
   ```
   http://localhost:8080/HostelMart/index.jsp
   ```

## Usage

### User Registration
1. Click "Sign Up" on home page
2. Enter Name, Email, and Password
3. Password must be at least 4 characters
4. Email must be unique
5. Click "Sign Up" button

### User Login
1. Click "Login" on home page
2. Enter registered Email and Password
3. Click "Login" button
4. Session automatically created for 30 minutes

### Product Management
1. Login first to access products
2. Navigate to "Products" from dashboard
3. **Add Product**: Click "Add New Product", fill details, submit
4. **View Products**: See all products in table format
5. **Edit Product**: Click "Edit" button, update details, submit
6. **Delete Product**: Click "Delete" button and confirm

### Logout
1. Click "Logout" link in navigation bar
2. Session will be invalidated
3. Redirected to home page

## Database Queries

### Users Table
```sql
SELECT * FROM users;
SELECT * FROM users WHERE email = 'user@email.com';
INSERT INTO users (name, email, password) VALUES ('John', 'john@email.com', 'pass123');
UPDATE users SET name = 'Jane' WHERE id = 1;
DELETE FROM users WHERE id = 1;
```

### Products Table
```sql
SELECT * FROM products;
SELECT * FROM products WHERE price > 100;
INSERT INTO products (name, price, quantity) VALUES ('Product', 299.99, 50);
UPDATE products SET quantity = 100 WHERE id = 1;
DELETE FROM products WHERE id = 1;
```

## Security Features

✅ **SQL Injection Prevention**
- PreparedStatement used for all database queries
- Parameters passed separately from SQL

✅ **Session Management**
- Session timeout: 30 minutes
- HTTP-only cookies
- Automatic session invalidation on logout

✅ **Input Validation**
- Email format validation
- Password confirmation
- Password minimum length requirement
- Non-empty field validation

## Common Issues and Solutions

### Issue: "MySQL driver not found"
**Solution**: Ensure JDBC JAR is in classpath or Tomcat lib folder

### Issue: "Connection refused"
**Solution**: 
- Check MySQL is running
- Verify database credentials in DBUtil.java
- Ensure database name is "hostelmart"

### Issue: "Page not found" (404)
**Solution**: 
- Check Tomcat logs
- Verify web.xml configuration
- Ensure JSP files are in webapp folder

### Issue: "Session lost"
**Solution**: 
- Check session timeout in web.xml
- Verify cookies are enabled in browser

## Future Enhancements

- Password hashing using BCrypt
- Role-based access control (Admin/User)
- Advanced search and filtering
- CSV export functionality
- Email sending on signup
- Product categories
- Order management
- Unit tests using JUnit

## Notes

- This is a beginner-friendly project for learning Servlets and JSP
- Production deployment requires additional security measures
- Use HTTPS in production
- Implement proper exception handling and logging

## Author

Created as a learning project for Advanced Java

---

**Happy Coding! 🚀**
