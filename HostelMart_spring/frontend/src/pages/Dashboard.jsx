import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

function Dashboard() {
    const navigate = useNavigate();
    const [user, setUser] = useState(null);

    useEffect(() => {
        const userStr = localStorage.getItem('user');
        if (!userStr) {
            navigate('/login');
            return;
        }
        setUser(JSON.parse(userStr));
    }, [navigate]);

    if (!user) return null;

    return (
        <div className="container" style={{padding: '40px 20px'}}>
            <h2>Dashboard</h2>
            <p>Welcome back, {user.name}!</p>
            
            <div className="dashboard-grid">
                <div className="dashboard-card">
                    <h3>My Products</h3>
                    <p>Manage the products you are selling</p>
                    <div className="dashboard-links">
                        <Link to="/add-product" className="btn btn-primary btn-small">Add Product</Link>
                        <Link to="/products" className="btn btn-secondary btn-small">View My Products</Link>
                    </div>
                </div>

                <div className="dashboard-card">
                    <h3>Marketplace</h3>
                    <p>Browse and buy products from other students</p>
                    <div className="dashboard-links">
                        <Link to="/products" className="btn btn-primary btn-small">Browse Products</Link>
                    </div>
                </div>

                <div className="dashboard-card">
                    <h3>My Purchases</h3>
                    <p>View your purchase history</p>
                    <div className="dashboard-links">
                        <Link to="/my-purchases" className="btn btn-primary btn-small">View Orders</Link>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Dashboard;
