import axios from 'axios';

const API_URL = '/api/drugs';

// Get all drugs with pagination and sorting
const getAllDrugs = async (page = 0, size = 10, sort = 'id', direction = 'asc') => {
  try {
    const response = await axios.get(`${API_URL}?page=${page}&size=${size}&sort=${sort}&direction=${direction}`);
    return response.data;
  } catch (error) {
    throw error.response?.data || { message: 'An error occurred while fetching drugs' };
  }
};

// Get a single drug by ID
const getDrugById = async (id) => {
  try {
    const response = await axios.get(`${API_URL}/${id}`);
    return response.data;
  } catch (error) {
    throw error.response?.data || { message: 'An error occurred while fetching the drug' };
  }
};

// Create a new drug (admin only)
const createDrug = async (drugData) => {
  try {
    const response = await axios.post(API_URL, drugData);
    return response.data;
  } catch (error) {
    throw error.response?.data || { message: 'An error occurred while creating the drug' };
  }
};

// Update a drug (admin only)
const updateDrug = async (id, drugData) => {
  try {
    const response = await axios.put(`${API_URL}/${id}`, drugData);
    return response.data;
  } catch (error) {
    throw error.response?.data || { message: 'An error occurred while updating the drug' };
  }
};

// Partially update a drug (admin only)
const partialUpdateDrug = async (id, drugData) => {
  try {
    const response = await axios.patch(`${API_URL}/${id}`, drugData);
    return response.data;
  } catch (error) {
    throw error.response?.data || { message: 'An error occurred while updating the drug' };
  }
};

// Delete a drug (admin only)
const deleteDrug = async (id) => {
  try {
    await axios.delete(`${API_URL}/${id}`);
    return true;
  } catch (error) {
    throw error.response?.data || { message: 'An error occurred while deleting the drug' };
  }
};

const drugService = {
  getAllDrugs,
  getDrugById,
  createDrug,
  updateDrug,
  partialUpdateDrug,
  deleteDrug
};

export default drugService;