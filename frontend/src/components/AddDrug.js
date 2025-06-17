import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import drugService from '../services/drugService';
import { toast } from 'react-toastify';
import './AddDrug.css';

const AddDrug = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        name: '',
        description: '',
        price: '',
        stock: '',
        manufacturer: ''
    });
    const [loading, setLoading] = useState(false);
    const [errors, setErrors] = useState({});

    const validateForm = () => {
        const newErrors = {};
        if (!formData.name.trim()) newErrors.name = 'Name is required';
        else if (formData.name.trim().length < 2 || formData.name.trim().length > 100) newErrors.name = 'Name must be between 2 and 100 characters';

        if (!formData.price) newErrors.price = 'Price is required';
        else if (isNaN(formData.price) || Number(formData.price) < 0)
            newErrors.price = 'Price must be a non-negative number';

        if (!formData.stock) newErrors.stock = 'Quantity in stock is required';
        else if (isNaN(formData.stock) || !Number.isInteger(Number(formData.stock)) || Number(formData.stock) < 0)
            newErrors.stock = 'Quantity in stock must be a non-negative integer';

        if (formData.description && formData.description.length > 1000) {
            newErrors.description = 'Description cannot exceed 1000 characters';
        }

        if (formData.manufacturer && formData.manufacturer.trim().length > 100) {
            newErrors.manufacturer = 'Manufacturer name cannot exceed 100 characters';
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!validateForm()) return;

        setLoading(true);
        try {
            const dataToSend = {
                name: formData.name.trim(),
                description: formData.description.trim(),
                price: parseFloat(formData.price),
                stock: parseInt(formData.stock),
                manufacturer: formData.manufacturer.trim()
            };

            await drugService.createDrug(dataToSend);
            toast.success('Drug added successfully!');
            navigate('/drugs');
        } catch (error) {
            toast.error('Error while adding drug: ' + (error.message || 'Unknown error'));
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="add-drug-container">
            <h2>Add new Drug</h2>
            <form onSubmit={handleSubmit} className="drug-form">
                <div className="form-group">
                    <label htmlFor="name">Name*</label>
                    <input
                        type="text"
                        id="name"
                        name="name"
                        value={formData.name}
                        onChange={handleChange}
                        className={errors.name ? 'error' : ''}
                    />
                    {errors.name && <div className="error-message">{errors.name}</div>}
                </div>

                <div className="form-group">
                    <label htmlFor="description">Description</label>
                    <textarea
                        id="description"
                        name="description"
                        value={formData.description}
                        onChange={handleChange}
                        rows="4"
                        className={errors.description ? 'error' : ''}
                    />
                    {errors.description && <div className="error-message">{errors.description}</div>}
                </div>

                <div className="form-group">
                    <label htmlFor="price">Cena (zł)*</label>
                    <input
                        type="number"
                        id="price"
                        name="price"
                        value={formData.price}
                        onChange={handleChange}
                        step="0.01"
                        min="0"
                        className={errors.price ? 'error' : ''}
                    />
                    {errors.price && <div className="error-message">{errors.price}</div>}
                </div>

                <div className="form-group">
                    <label htmlFor="stock">Quantity*</label>
                    <input
                        type="number"
                        id="stock"
                        name="stock"
                        value={formData.stock}
                        onChange={handleChange}
                        min="0"
                        className={errors.stock ? 'error' : ''}
                    />
                    {errors.stock && <div className="error-message">{errors.stock}</div>}
                </div>


                <div className="form-group">
                    <label htmlFor="manufacturer">Manufacturer</label>
                    <input
                        type="text"
                        id="manufacturer"
                        name="manufacturer"
                        value={formData.manufacturer}
                        onChange={handleChange}
                        className={errors.manufacturer ? 'error' : ''}
                    />
                    {errors.manufacturer && <div className="error-message">{errors.manufacturer}</div>}
                </div>

                <div className="form-actions">
                    <button type="button" onClick={() => navigate('/drugs')} className="cancel-button">
                        Cancel
                    </button>
                    <button type="submit" className="submit-button" disabled={loading}>
                        {loading ? 'Adding...' : 'Add drug'}
                    </button>
                </div>
            </form>
        </div>
    );
};

export default AddDrug;