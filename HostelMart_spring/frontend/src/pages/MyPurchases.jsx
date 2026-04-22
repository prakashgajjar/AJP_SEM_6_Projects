import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api';

function MyPurchases() {
    const [orders, setOrders] = useState([]);
    const navigate = useNavigate();
    const user = JSON.parse(localStorage.getItem('user'));

    useEffect(() => {
        if (!user) {
            navigate('/login');
            return;
        }
        api.get(`/orders/buyer/${user.id}`).then(res => {
            setOrders(res.data);
        }).catch(err => {
            console.error('Failed to fetch orders', err);
        });
    }, [navigate, user]);

    return (
        <div className="container" style={{padding: '40px 20px'}}>
            <h2>My Purchases</h2>
            
            <table className="product-table">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Product Name</th>
                        <th>Seller Name</th>
                        <th>Quantity</th>
                        <th>Total Price</th>
                        <th>Status</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody>
                    {orders.map(o => (
                        <tr key={o.id}>
                            <td>#{o.id}</td>
                            <td>{o.productName}</td>
                            <td>{o.sellerName}</td>
                            <td>{o.quantity}</td>
                            <td>₹{o.price}</td>
                            <td><span style={{textTransform:'capitalize'}}>{o.status}</span></td>
                            <td>{new Date(o.createdAt).toLocaleDateString()}</td>
                        </tr>
                    ))}
                    {orders.length === 0 && (
                        <tr>
                            <td colSpan="7" style={{textAlign: 'center'}}>No purchases found.</td>
                        </tr>
                    )}
                </tbody>
            </table>
        </div>
    );
}

export default MyPurchases;
