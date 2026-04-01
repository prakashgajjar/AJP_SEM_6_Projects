<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - HostelMart</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .error-container {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 85vh;
            padding: 20px;
        }
        .error-box {
            text-align: center;
            background: #fff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
            border: 1px solid #e0e0e0;
        }
        .error-code {
            font-size: 3rem;
            color: #000;
            margin-bottom: 20px;
        }
        .error-message {
            font-size: 1.5rem;
            color: #333;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <!-- Navigation Bar -->
    <nav class="navbar">
        <div class="container">
            <div class="logo">HostelMart</div>
            <ul class="nav-links">
                <li><a href="index.jsp">Home</a></li>
                <li><a href="login.jsp">Login</a></li>
                <li><a href="signup.jsp">Sign Up</a></li>
            </ul>
        </div>
    </nav>

    <!-- Error Section -->
    <div class="error-container">
        <div class="error-box">
            <div class="error-code">⚠️</div>
            <div class="error-message">Oops! Something Went Wrong</div>
            <p style="color: #333; margin-bottom: 30px;">
                The page you're looking for might be unavailable or an error occurred.
            </p>
            <a href="index.jsp" class="btn btn-primary">Go to Home</a>
            <a href="javascript:history.back();" class="btn btn-secondary">Go Back</a>
        </div>
    </div>

    <!-- Footer -->
    <footer class="footer">
        <div class="container">
            <p>&copy; 2024 HostelMart. All Rights Reserved.</p>
        </div>
    </footer>
</body>
</html>
