import { Link } from 'react-router-dom';

function Home() {
    const userStr = localStorage.getItem('user');
    const user = userStr ? JSON.parse(userStr) : null;

    return (
        <>
            <div className="hero">
                <div className="hero-content">
                    <h1>Welcome to HostelMart</h1>
                    <p>Manage your hostel products efficiently</p>
                    {user ? (
                        <Link to="/dashboard" className="btn btn-primary">Go to Dashboard</Link>
                    ) : (
                        <>
                            <Link to="/login" className="btn btn-primary">Login to Start</Link>
                            <Link to="/signup" className="btn btn-secondary">Create Account</Link>
                        </>
                    )}
                </div>
            </div>

            <div className="features">
                <div className="container">
                    <h2>Features</h2>
                    <div className="feature-grid">
                        <div className="feature-card">
                            <h3>Easy Authentication</h3>
                            <p>Secure login and registration for users</p>
                        </div>
                        <div className="feature-card">
                            <h3>Product Management</h3>
                            <p>Add, edit, and delete products easily</p>
                        </div>
                        <div className="feature-card">
                            <h3>Real-time Updates</h3>
                            <p>Keep track of all products and inventory</p>
                        </div>
                    </div>
                </div>
            </div>

            <footer className="footer">
                <div className="container">
                    <p>&copy; 2024 HostelMart. All Rights Reserved.</p>
                </div>
            </footer>
        </>
    );
}

export default Home;
