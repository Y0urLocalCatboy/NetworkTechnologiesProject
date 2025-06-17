import axios from 'axios';

const API_URL = '/api/users';

const getAllUsers = async (page = 0, size = 10, sort = 'id', direction = 'asc') => {
  try {
    const response = await axios.get(`${API_URL}?page=${page}&size=${size}&sort=${sort}&direction=${direction}`);
    return response.data;
  } catch (error) {
    throw error.response?.data || { message: 'An error occurred while fetching users' };
  }
};

const getCurrentUser = async () => {
  try {
    const response = await axios.get(`${API_URL}/me`);
    return response.data;
  } catch (error) {
    throw error.response?.data || { message: 'An error occurred while fetching user information' };
  }
};

const getUserById = async (id) => {
  try {
    const response = await axios.get(`${API_URL}/${id}`);
    return response.data;
  } catch (error) {
    throw error.response?.data || { message: 'An error occurred while fetching the user' };
  }
};

const registerUser = async (userData) => {
  try {
    const response = await axios.post(API_URL, userData);
    return response.data;
  } catch (error) {
    throw error.response?.data || { message: 'An error occurred during registration' };
  }
};

const updateUser = async (id, userData) => {
  try {
    const response = await axios.put(`${API_URL}/${id}`, userData);
    return response.data;
  } catch (error) {
    throw error.response?.data || { message: 'An error occurred while updating the user' };
  }
};

const partialUpdateUser = async (id, userData) => {
  try {
    const response = await axios.patch(`${API_URL}/${id}`, userData);
    return response.data;
  } catch (error) {
    throw error.response?.data || { message: 'An error occurred while updating the user' };
  }
};

const deleteUser = async (id) => {
  try {
    await axios.delete(`${API_URL}/${id}`);
    return true;
  } catch (error) {
    throw error.response?.data || { message: 'An error occurred while deleting the user' };
  }
};

const userService = {
  getAllUsers,
  getCurrentUser,
  getUserById,
  registerUser,
  updateUser,
  partialUpdateUser,
  deleteUser
};

export default userService;