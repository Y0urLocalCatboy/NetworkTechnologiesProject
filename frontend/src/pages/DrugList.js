import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { toast } from 'react-toastify';
import {
  Typography,
  Box,
  Card,
  CardContent,
  TextField,
  Button,
  CircularProgress,
  Alert,
  Grid
} from '@mui/material';

const DrugDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [drug, setDrug] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [quantity, setQuantity] = useState(1);

  const [newDrug, setNewDrug] = useState({
    name: '',
    description: '',
    price: '',
    stock: ''
  });

  useEffect(() => {
    if (id === 'add') {
      setLoading(false);
      return;
    }

    const fetchDrug = async () => {
      try {
        const response = await axios.get(`/api/drugs/${id}`);
        setDrug(response.data);
        setLoading(false);
      } catch (err) {
        console.error('Error fetching drug details:', err);
        setError('Error during fetching drug details');
        setLoading(false);
      }
    };

    fetchDrug();
  }, [id]);

  const handleQuantityChange = (e) => {
    const value = parseInt(e.target.value, 10);
    if (value > 0 && drug && value <= drug.stock) {
      setQuantity(value);
    }
  };

  const handleBuyDrug = async () => {
    try {
      await axios.post(`/api/drugs/${id}/buy`, { quantity });
      toast.success('Drug purchased successfully!');
      const response = await axios.get(`/api/drugs/${id}`);
      setDrug(response.data);
    } catch (err) {
      toast.error('Error while buying drug: ' + (err.response?.data?.message || err.message));
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewDrug({
      ...newDrug,
      [name]: value
    });
  };

  const handleAddDrug = async (e) => {
    e.preventDefault();
    try {
      const drugData = {
        name: newDrug.name,
        description: newDrug.description,
        price: parseFloat(newDrug.price),
        stock: parseInt(newDrug.stock, 10)
      };

      const response = await axios.post('/api/drugs', drugData);

      toast.success('Drug added successfully!');
      navigate(`/drugs/${response.data.id}`);
    } catch (err) {
      toast.error('Error adding drug: ' + (err.response?.data?.message || err.message));
    }
  };

  if (loading) return <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}><CircularProgress /></Box>;

  if (id === 'add') {
    return (
        <Box sx={{ maxWidth: 800, mx: 'auto', p: 2 }}>
          <Card>
            <CardContent>
              <Typography variant="h4" gutterBottom>Add New Drug</Typography>
              <Box component="form" onSubmit={handleAddDrug} sx={{ mt: 2 }}>
                <Grid container spacing={2}>
                  <Grid item xs={12}>
                    <TextField
                        name="name"
                        label="Drug Name"
                        fullWidth
                        required
                        value={newDrug.name}
                        onChange={handleInputChange}
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                        name="description"
                        label="Description"
                        fullWidth
                        multiline
                        rows={4}
                        value={newDrug.description}
                        onChange={handleInputChange}
                    />
                  </Grid>
                  <Grid item xs={6}>
                    <TextField
                        name="price"
                        label="Price"
                        fullWidth
                        required
                        type="number"
                        inputProps={{ min: 0, step: "0.01" }}
                        value={newDrug.price}
                        onChange={handleInputChange}
                    />
                  </Grid>
                  <Grid item xs={6}>
                    <TextField
                        name="stock"
                        label="Stock Quantity"
                        fullWidth
                        required
                        type="number"
                        inputProps={{ min: 0 }}
                        value={newDrug.stock}
                        onChange={handleInputChange}
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <Button
                        type="submit"
                        variant="contained"
                        color="primary"
                        fullWidth
                        sx={{ mt: 2 }}
                    >
                      Add Drug
                    </Button>
                  </Grid>
                </Grid>
              </Box>
            </CardContent>
          </Card>
        </Box>
    );
  }

  if (error) return <Alert severity="error">{error}</Alert>;
  if (!drug) return <Alert severity="warning">Drug not found</Alert>;

  return (
      <Box sx={{ maxWidth: 800, mx: 'auto', p: 2 }}>
        <Card>
          <CardContent>
            <Typography variant="h4" gutterBottom>{drug.name}</Typography>

            <Box sx={{ my: 2 }}>
              <Typography variant="body1">{drug.description}</Typography>
              <Typography variant="h6" sx={{ mt: 2 }}>Price: ${drug.price}</Typography>
              <Typography variant="body2">Available: {drug.stock} pcs</Typography>
            </Box>

            <Box sx={{ mt: 4, borderTop: '1px solid #eee', pt: 2 }}>
              <Typography variant="h5" gutterBottom>Purchase Drug</Typography>

              <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                <Typography sx={{ mr: 2 }}>Quantity:</Typography>
                <TextField
                    type="number"
                    size="small"
                    value={quantity}
                    onChange={handleQuantityChange}
                    inputProps={{ min: 1, max: drug.stock }}
                    disabled={drug.stock <= 0}
                />
              </Box>

              <Typography variant="h6" sx={{ mb: 2 }}>
                Total price: ${(drug.price * quantity).toFixed(2)}
              </Typography>

              <Button
                  variant="contained"
                  color="primary"
                  onClick={handleBuyDrug}
                  disabled={drug.stock <= 0}
                  fullWidth
              >
                {drug.stock > 0 ? 'Buy Now' : 'Out of Stock'}
              </Button>
            </Box>
          </CardContent>
        </Card>
      </Box>
  );
};

export default DrugDetail;