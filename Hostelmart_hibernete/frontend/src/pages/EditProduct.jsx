import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import api from '../api';

function EditProduct() {
    const { id } = useParams();
    const [name, setName] = useState('');
    const [price, setPrice] = useState('');
    const [quantity, setQuantity] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const [test , setTest] = useState(true)

    useEffect(() => {
        // 1. Read localStorage inside the effect so it doesn't run on every render
        const user = JSON.parse(localStorage.getItem('user'));
        
        if (!user) {
            navigate('/login');
            return;
        }

        // Fetch product
        api.get(`/products/${id}`).then(res => {
            setName(res.data.name);
            setPrice(res.data.price);
            setQuantity(res.data.quantity);
        }).catch(() => setError('Failed to load product'));
        
    // 2. Include 'id' because the fetch depends on it. 
    // 'user' is no longer a dependency because we moved it inside.
    }, [test]); 

    const handleSubmit = async (e) => {
        e.preventDefault();
        setTest(!test);
        try {
            // 3. Grab the user data at the exact moment of submission
            const user = JSON.parse(localStorage.getItem('user'));
            
            await api.put(`/products/${id}`, {
                name,
                price: parseFloat(price),
                quantity: parseInt(quantity),
                sellerId: user.id
            });
            navigate('/products');
        } catch (err) {
            setError(err.response?.data?.message || 'Failed to update product');
        }
    };

    return (
        <div className="form-container">
            <div className="form-box">
                <h2>Edit Product</h2>
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
                    <button type="submit" className="btn btn-primary" style={{width: '100%'}}>Update Product</button>
                </form>
            </div>
        </div>
    );
}

export default EditProduct;