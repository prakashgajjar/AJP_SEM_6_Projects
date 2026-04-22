import { Link, useNavigate } from 'react-router-dom';

function Navbar() {
    const navigate = useNavigate();
    const userStr = localStorage.getItem('user');
    const user = userStr ? JSON.parse(userStr) : null;

    const handleLogout = () => {
        localStorage.removeItem('user');
        navigate('/');
    };

    return (
        <nav className="navbar">
            <div className="container" style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>
                <div className="logo"><Link style={{color:'#000', textDecoration:'none'}} to="/">HostelMart</Link></div>
                <ul className="nav-links">
                    <li><Link to="/">Home</Link></li>
                    {user ? (
                        <>
                            <li><Link to="/dashboard">Dashboard</Link></li>
                            <li><a href="#" onClick={handleLogout}>Logout</a></li>
                            <li><span className="user-name">Welcome, {user.name}</span></li>
                        </>
                    ) : (
                        <>
                            <li><Link to="/login">Login</Link></li>
                            <li><Link to="/signup">Sign Up</Link></li>
                        </>
                    )}
                </ul>
            </div>
        </nav>
    );
}

export default Navbar;
