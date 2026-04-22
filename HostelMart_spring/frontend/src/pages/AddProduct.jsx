import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api';

function AddProduct() {
    const [name, setName] = useState('');
    const [price, setPrice] = useState('');
    const [quantity, setQuantity] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const user = JSON.parse(localStorage.getItem('user'));

    useEffect(() => {
        if (!user) navigate('/login');
    }, [navigate, user]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const res = await api.post('/products', {
                name,
                price: parseFloat(price),
                quantity: parseInt(quantity),
                sellerId: user.id
            });
            if (res.data.success) {
                navigate('/products');
            }
        } catch (err) {
            setError('Failed to add product');
        }
    };

    return (
        <div className="form-container">
            <div className="form-box">
                <h2>Add New Product</h2>
                {error && <div className="alert alert-error">{error}</div>}
                
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label>Product Name</label>
                        <input type="text" value={name} onChange={e => setName(e.target.value)} required />
                    </div>
                    <div className="form-group">
                        <label>Price (₹)</label>
                        <input type="number" step="0.01" value={price} onChange={e => setPrice(e.target.value)} required />
                    </div>
                    <div className="form-group">
                        <label>Quantity</label>
                        <input type="number" value={quantity} onChange={e => setQuantity(e.target.value)} required />
                    </div>
                    <button type="submit" className="btn btn-primary" style={{width: '100%'}}>Add Product</button>
                </form>
            </div>
        </div>
    );
}

export default AddProduct;
