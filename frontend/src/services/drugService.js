import axios from 'axios';

const getAuthConfig = () => {
   const token = localStorage.getItem('token');
   if (token) {
     return { headers: { Authorization: `Bearer ${token}` } };
   }
  return {};
};

const API_URL = '/api/drugs';

const getAllDrugs = async (page = 0, size = 10, sort = 'id', direction = 'asc') => {
  try {
    const response = await axios.get(`${API_URL}?page=${page}&size=${size}&sort=${sort}&direction=${direction}`, getAuthConfig());
    return response.data;
  } catch (error) {
    console.error("Error in getAllDrugs:", error.response?.data || error.message);
    throw error.response?.data || { message: 'An error occurred while fetching drugs' };
  }
};

const getDrugById = async (id) => {
  try {
    const response = await axios.get(`${API_URL}/${id}`, getAuthConfig());
    return response.data;
  } catch (error) {
    console.error("Error in getDrugById:", error.response?.data || error.message);
    throw error.response?.data || { message: 'An error occurred while fetching the drug' };
  }
};

const buyDrug = async (drugId, quantity) => {
  try {
    const response = await axios.post(`${API_URL}/buy`, { drugId, quantity }, getAuthConfig());
    return response.data;
  } catch (error) {
    console.error("Error in buyDrug:", error.response?.data || error.message);
    throw error.response?.data || { message: 'An error occurred while buying the drug' };
  }
};

const createDrug = async (drugData) => {
  try {
    const response = await axios.post(API_URL, drugData, getAuthConfig());
    return response.data;
  } catch (error) {
    console.error("Error in createDrug:", error.response?.data || error.message);
    const backendError = error.response?.data;
    if (backendError && Array.isArray(backendError.errors) && backendError.errors.length > 0) {
      const messages = backendError.errors.map(err => err.defaultMessage || err.message).join(', ');
      throw { message: messages || 'An error occurred while creating the drug' };
    }
    if (backendError && backendError.message) {
      throw { message: backendError.message };
    }
    throw { message: 'An error occurred while creating the drug' };
  }
};

const updateDrug = async (id, drugData) => {
  try {
    const response = await axios.put(`${API_URL}/${id}`, drugData, getAuthConfig());
    return response.data;
  } catch (error) {
    console.error("Error in updateDrug:", error.response?.data || error.message);
    throw error.response?.data || { message: 'An error occurred while updating the drug' };
  }
};

const partialUpdateDrug = async (id, drugData) => {
  try {
    const response = await axios.patch(`${API_URL}/${id}`, drugData, getAuthConfig());
    return response.data;
  } catch (error) {
    console.error("Error in partialUpdateDrug:", error.response?.data || error.message);
    throw error.response?.data || { message: 'An error occurred while updating the drug' };
  }
};

const deleteDrug = async (id) => {
  try {
    await axios.delete(`${API_URL}/${id}`, getAuthConfig());
    return true;
  } catch (error) {
    console.error("Error in deleteDrug:", error.response?.data || error.message);
    throw error.response?.data || { message: 'An error occurred while deleting the drug' };
  }
};

const drugService = {
  getAllDrugs,
  getDrugById,
  buyDrug,
  createDrug,
  updateDrug,
  partialUpdateDrug,
  deleteDrug
};

export default drugService;