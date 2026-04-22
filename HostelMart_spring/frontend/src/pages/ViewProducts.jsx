import { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import api from '../api';

function ViewProducts() {
    const [products, setProducts] = useState([]);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const navigate = useNavigate();
    const user = JSON.parse(localStorage.getItem('user'));

    useEffect(() => {
        if (!user) {
            navigate('/login');
            return;
        }
        loadProducts();
    }, [navigate, user]);

    const loadProducts = async () => {
        try {
            const res = await api.get('/products');
            setProducts(res.data);
        } catch (err) {
            setError('Failed to load products');
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm("Are you sure you want to delete this product?")) {
            try {
                await api.delete(`/products/${id}?sellerId=${user.id}`);
                setSuccess("Product deleted successfully");
                loadProducts();
            } catch (err) {
                setError(err.response?.data?.message || 'Failed to delete product');
            }
        }
    };

    const handleBuy = async (productId, quantityToBuy) => {
        try {
            await api.post('/orders/purchase', {
                productId: productId,
                buyerId: user.id,
                quantity: quantityToBuy
            });
            setSuccess("Purchase successful!");
            loadProducts();
        } catch (err) {
            setError(err.response?.data?.message || 'Failed to purchase product');
        }
    };

    return (
        <div className="container" style={{padding: '40px 20px'}}>
            <div style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>
                <h2>Products Marketplace</h2>
                <Link to="/add-product" className="btn btn-primary">Add New Product</Link>
            </div>

            {error && <div className="alert alert-error" style={{marginTop:'20px'}}>{error}</div>}
            {success && <div className="alert alert-success" style={{marginTop:'20px'}}>{success}</div>}

            <table className="product-table">
                <thead>
                    <tr>
                        <th>Product Name</th>
                        <th>Price</th>
                        <th>Available Quantity</th>
                        <th>Seller Name</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {products.map(p => (
                        <tr key={p.id}>
                            <td>{p.name}</td>
                            <td>₹{p.price}</td>
                            <td>{p.quantity}</td>
                            <td>{p.sellerName || 'Unknown'}</td>
                            <td>
                                {user.id === p.sellerId ? (
                                    <>
                                        <Link to={`/edit-product/${p.id}`} className="btn btn-primary btn-small">Edit</Link>
                                        <button onClick={() => handleDelete(p.id)} className="btn btn-danger btn-small">Delete</button>
                                    </>
                                ) : (
                                    p.quantity > 0 && <button onClick={() => handleBuy(p.id, 1)} className="btn btn-primary btn-small">Buy 1</button>
                                )}
                            </td>
                        </tr>
                    ))}
                    {products.length === 0 && (
                        <tr>
                            <td colSpan="5" style={{textAlign: 'center'}}>No products found.</td>
                        </tr>
                    )}
                </tbody>
            </table>
        </div>
    );
}

export default ViewProducts;
