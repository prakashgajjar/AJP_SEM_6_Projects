# HostelMart - Quick Start Guide

## ⚡ Quick Setup (5 minutes)

### Step 1: Database Setup
```bash
# Open MySQL command line
mysql -u root -p

# Paste commands from database_setup.sql:
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

### Step 2: IDE Setup (Using Eclipse)

1. **Create Dynamic Web Project**
   - File → New → Dynamic Web Project
   - Project name: HostelMart
   - Target runtime: Apache Tomcat v9.0

2. **Copy Source Code**
   - Copy `src/com/hostelmart/` to project's `src/` folder
   - Copy `webapp/` files (*.jsp, css/) to `WebContent/` folder
   - Copy `database_setup.sql` to project root

3. **Add MySQL JDBC Driver**
   - Download: `mysql-connector-java-8.0.x.jar`
   - Right-click Project → Build Path → Add External Archives
   - Select the JAR file
   - Alternative: Copy to `WebContent/WEB-INF/lib/`

4. **Update Database Configuration** (if needed)
   - Open: `src/com/hostelmart/util/DBUtil.java`
   - Update DB credentials:
     ```java
     private static final String DB_URL = "jdbc:mysql://localhost:3306/hostelmart?useSSL=false&serverTimezone=UTC";
     private static final String DB_USER = "root";
     private static final String DB_PASSWORD = "";  // Your MySQL password
     ```

5. **Run on Server**
   - Right-click Project → Run As → Run on Server
   - Select Apache Tomcat v9.0
   - Click Finish

### Step 3: Access Application
```
http://localhost:8080/HostelMart/index.jsp
```

## 📋 Test Credentials

After database setup, you can optionally add test data:

```sql
INSERT INTO users (name, email, password) VALUES 
('Test User', 'test@email.com', 'test123'),
('Admin User', 'admin@email.com', 'admin123');

INSERT INTO products (name, price, quantity) VALUES 
('Bed Sheet', 299.99, 50),
('Pillow', 149.99, 100),
('Mattress', 399.99, 30);
```

Then login with:
- Email: `test@email.com`
- Password: `test123`

## 🔧 Troubleshooting

### Error: "Unable to load authentication plugin"
```
Add parameter to DB_URL: 
?allowPublicKeyRetrieval=true&useSSL=false
```

### Error: "MySQL JDBC Driver not found"
1. Download: `mysql-connector-java-8.0.33.jar`
2. Copy to: `WebContent/WEB-INF/lib/`
3. Add to Build Path (Right-click → Build Path → Add...)

### Error: Connection Refused
1. Start MySQL: `mysql --version` to verify
2. Check credentials in `DBUtil.java`
3. Verify database name: `hostelmart`

### Port Already in Use
Change Tomcat port in Eclipse:
- Window → Preferences → Server → Runtime Environments
- Select Tomcat → Edit → Change port (e.g., 8081)

## 📁 File Organization

```
HostelMart (in Eclipse)
├── src/
│   └── com/hostelmart/
│       ├── servlet/ (4 servlets)
│       ├── dao/ (2 DAOs)
│       ├── model/ (2 models)
│       └── util/ (DBUtil)
├── WebContent/
│   ├── *.jsp (7 JSP files)
│   ├── css/
│   │   └── style.css
│   └── WEB-INF/
│       └── web.xml
└── Database files
```

## 🎯 Features Walkthrough

1. **Home Page** (`index.jsp`)
   - Shows welcome message
   - Links to Login/Signup

2. **Signup** (`signup.jsp` + `SignupServlet`)
   - Register new user
   - Validation: email, password

3. **Login** (`login.jsp` + `LoginServlet`)
   - User authentication
   - Session creation

4. **Dashboard** (`dashboard.jsp`)
   - Welcome message
   - Quick links to products

5. **Products** (`viewProducts.jsp` + `ProductServlet`)
   - List all products
   - Edit/Delete buttons

6. **Add Product** (`addProduct.jsp`)
   - Add new product form
   - Price, Quantity input

7. **Edit Product** (`editProduct.jsp`)
   - Update product details
   - Pre-filled values

## 🚀 Production Checklist

Before deploying to production:

- [ ] Change database password in `DBUtil.java`
- [ ] Use HTTPS for forms
- [ ] Implement password hashing (BCrypt)
- [ ] Add logging framework
- [ ] Enable input sanitization
- [ ] Add CSRF token to forms
- [ ] Review security.md

## 📚 Key Java Concepts Used

- **Servlets**: HTTP request/response handling
- **JSP**: Server-side templating
- **JDBC**: Database connectivity
- **MVC Pattern**: Separation of concerns
- **Session Management**: User tracking
- **PreparedStatement**: SQL injection prevention

## ✅ Verification Steps

1. ✓ Can access `http://localhost:8080/HostelMart/`
2. ✓ Can navigate to Signup page
3. ✓ Can create new user
4. ✓ Can login with created credentials
5. ✓ Can add a product
6. ✓ Can view all products
7. ✓ Can edit a product
8. ✓ Can delete a product
9. ✓ Can logout

---

**Need Help?**
- Check console for error messages
- Verify all files in correct folders
- Restart Tomcat after changes
- Clear browser cache (Ctrl+Shift+Delete)

