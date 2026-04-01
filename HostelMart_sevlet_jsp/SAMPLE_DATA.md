# HostelMart Sample Application Data

## Test Users for Login

Use these credentials to test the application after registering:

### Test Account 1
```
Email: test@hostelmart.com
Password: test123
Name: Test User
```

### Test Account 2
```
Email: admin@hostelmart.com
Password: admin123
Name: Admin User
```

### Test Account 3
```
Email: user@example.com
Password: user@12345
Name: John Doe
```

---

## Sample Products Data

After registration and login, you can add these sample products:

### Bedding Products
1. **Bed Sheets (Set of 2)**
   - Price: ₹299.99
   - Quantity: 50

2. **Pillow Case**
   - Price: ₹149.99
   - Quantity: 100

3. **Mattress Protector**
   - Price: ₹399.99
   - Quantity: 30

### Bath & Towels
4. **Towel (Bath)**
   - Price: ₹99.99
   - Quantity: 150

5. **Towel (Hand)**
   - Price: ₹49.99
   - Quantity: 200

### Blankets & Covers
6. **Double Blanket**
   - Price: ₹499.99
   - Quantity: 25

7. **Duvet Cover**
   - Price: ₹349.99
   - Quantity: 40

### Emergency Supplies
8. **First Aid Kit**
   - Price: ₹199.99
   - Quantity: 15

9. **Desk Lamp**
   - Price: ₹249.99
   - Quantity: 35

10. **Room Heater**
    - Price: ₹1299.99
    - Quantity: 10

---

## SQL Insert Commands for Sample Data

Execute these commands in MySQL to add sample data:

```sql
-- Use the database
USE hostelmart;

-- Insert sample users
INSERT INTO users (name, email, password) VALUES 
('Test User', 'test@hostelmart.com', 'test123'),
('Admin User', 'admin@hostelmart.com', 'admin123'),
('John Doe', 'user@example.com', 'user@12345');

-- Insert sample products
INSERT INTO products (name, price, quantity) VALUES 
('Bed Sheets (Set of 2)', 299.99, 50),
('Pillow Case', 149.99, 100),
('Mattress Protector', 399.99, 30),
('Towel (Bath)', 99.99, 150),
('Towel (Hand)', 49.99, 200),
('Double Blanket', 499.99, 25),
('Duvet Cover', 349.99, 40),
('First Aid Kit', 199.99, 15),
('Desk Lamp', 249.99, 35),
('Room Heater', 1299.99, 10);

-- Verify data
SELECT * FROM users;
SELECT * FROM products;
```

---

## Testing Workflow

### Step 1: User Registration
1. Click "Sign Up"
2. Fill details:
   - Name: Your Name
   - Email: your-unique-email@domain.com
   - Password: At least 4 characters
   - Confirm Password: Same as above
3. Click "Sign Up" button
4. Should redirect to login page with success message

### Step 2: User Login
1. Click "Login" 
2. Enter registered email and password
3. Click "Login" button
4. Should show dashboard with welcome message

### Step 3: View Products
1. From dashboard, click "View Products" or "Products" in navbar
2. Should show list of all products in table
3. Each product should show: ID, Name, Price, Quantity

### Step 4: Add Product
1. Click "Add New Product" button
2. Fill form:
   - Product Name: (e.g., Notebook)
   - Price: (e.g., 50.00)
   - Quantity: (e.g., 100)
3. Click "Add Product"
4. Should see success message and new product in list

### Step 5: Edit Product
1. From product list, click "Edit" button next to any product
2. Page should show pre-filled form
3. Modify any field
4. Click "Update Product"
5. Should show success message with updated values

### Step 6: Delete Product
1. From product list, click "Delete" button
2. Confirm deletion when prompted
3. Product should be removed from list
4. Success message should display

### Step 7: Logout
1. Click "Logout" link in navbar
2. Session should be destroyed
3. Should redirect to home page
4. Navbar should show "Login" and "Sign Up" again

---

## Validation Testing

### Test Form Validation

#### Signup Validation
- [ ] Empty Name → "Name is required!"
- [ ] Empty Email → "Email is required!"
- [ ] Invalid Email (no @) → "Invalid email format!"
- [ ] Empty Password → "Password is required!"
- [ ] Password < 4 chars → "Password must be at least 4 characters!"
- [ ] Passwords don't match → "Passwords do not match!"
- [ ] Duplicate Email → "Email already registered!"

#### Product Validation
- [ ] Empty Name → "Product name is required!"
- [ ] Empty Price → "Price is required!"
- [ ] Empty Quantity → "Quantity is required!"
- [ ] Negative Price → "Price must be greater than 0!"
- [ ] Negative Quantity → "Quantity cannot be negative!"

### Test Session Management
- [ ] Login and check session timeout (30 minutes)
- [ ] Try accessing products without login → Should redirect to login
- [ ] Logout and verify session is cleared
- [ ] Try back button after logout → Should not show protected page

---

## Performance Testing

### Load Test
```
Normal Workflow (Single User):
Register → Login → Add 10 products → Edit 5 products → Delete 2 products → Logout
Expected Time: < 2 seconds per page load
```

### Database Stress Test
```
Add 100 products and verify:
- Listing completes in < 1 second
- No performance degradation
- All records displayed correctly
```

---

## Error Scenario Testing

Test these error scenarios and verify proper handling:

1. Try login with wrong email
2. Try login with wrong password
3. Try registering with used email
4. Try adding product with invalid price
5. Try adding product with invalid quantity
6. Try accessing dashboard URL directly without login
7. Try accessing products page without login
8. Logout and try back navigation

---

## Browser Compatibility Testing

Test on:
- [ ] Chrome/Chromium (latest)
- [ ] Firefox (latest)
- [ ] Safari (latest)
- [ ] Edge (latest)
- [ ] Mobile browsers

Check:
- [ ] Font rendering correct
- [ ] Colors displaying properly
- [ ] Buttons clickable
- [ ] Forms submitting
- [ ] Responsive design working

---

## Documentation of Test Results

Use this template to record test results:

```
Test Date: ___________
Tester: ___________
Browser: ___________
OS: ___________

Feature: ___________
Result: [ ] Pass [ ] Fail
Notes: ___________

Time Taken: ___________
Issues Found: ___________
```

---

## Known Test Issues

*None reported yet. If you find issues, document them here.*

---

*Happy Testing! 🧪*
