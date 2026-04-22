import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080/api', // Spring MVC Backend
    headers: {
        'Content-Type': 'application/json'
    },
    withCredentials: true  // Enable sending cookies with requests
});

export default api;
