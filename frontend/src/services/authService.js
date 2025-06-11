import axios from 'axios';

const API_URL = '/api/auth';

const login = async (email, password) => {
  try {
    const response = await axios.post(`${API_URL}/login`, { email, password });
    if (response.data.token) {
      localStorage.setItem('token', response.data.token);
    }
    return response.data;
  } catch (error) {
    throw error.response?.data || { message: 'An error occurred during login' };
  }
};

const logout = () => {
  localStorage.removeItem('token');
};

const getCurrentUser = async () => {
  try {
    const token = localStorage.getItem('token');
    if (!token) {
      return null;
    }

    const response = await axios.get('/api/users/me', {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching current user:', error);
    return null;
  }
};

const register = async (userData) => {
  try {
    const response = await axios.post('/api/users/register', userData);
    return response.data;
  } catch (error) {
    throw error.response?.data || { message: error.toString() };
  }
};

const setAuthToken = (token) => {
  if (token) {
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
  } else {
    delete axios.defaults.headers.common['Authorization'];
  }
};

const initializeAuth = () => {
  const token = localStorage.getItem('token');
  if (token) {
    setAuthToken(token);
    return true;
  }
  return false;
};

const authService = {
  login,
  logout,
  getCurrentUser,
  register,
  setAuthToken,
  initializeAuth
};

export default authService;
