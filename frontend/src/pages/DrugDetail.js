import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import drugService from '../services/drugService';
import { toast } from 'react-toastify';
import {
  Typography,
  Box,
  Card,
  CardContent,
  TextField,
  Button,
  CircularProgress,
  Alert
} from '@mui/material';

const DrugDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [drug, setDrug] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [purchaseQuantity, setPurchaseQuantity] = useState(1);

  useEffect(() => {
    if (!id) {
      setError('No drug ID provided.');
      setLoading(false);
      return;
    }

    const fetchDrug = async () => {
      setLoading(true);
      try {
        const data = await drugService.getDrugById(id);
        setDrug(data);
        setError(null);
      } catch (err) {
        console.error('Error fetching drug details:', err);
        setError('Error fetching drug details: ' + (err.message || 'Unknown error'));
        setDrug(null);
      } finally {
        setLoading(false);
      }
    };

    fetchDrug();
  }, [id]);

  const handleQuantityChange = (e) => {
    const value = parseInt(e.target.value, 10);
    if (drug && drug.quantity && value > 0 && value <= drug.quantity) {
      setPurchaseQuantity(value);
    } else if (value <= 0) {
      setPurchaseQuantity(1);
    }
  };

  const handleBuyDrug = async () => {
    if (!drug || !drug.id) {
      toast.error('Drug information is not available.');
      return;
    }
    try {
      const response = await drugService.buyDrug(drug.id, purchaseQuantity);
      if (response.success) {
        toast.success(response.message || 'Drug purchased successfully!');
        const updatedDrug = await drugService.getDrugById(id);
        setDrug(updatedDrug);
        setPurchaseQuantity(1);
      } else {
        toast.error(response.message || 'Failed to purchase drug.');
      }
    } catch (err) {
      toast.error('Error while buying drug: ' + (err.message || 'Unknown error'));
    }
  };

  if (loading) return <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}><CircularProgress /></Box>;
  if (error) return <Alert severity="error" sx={{ m: 2 }}>{error}</Alert>;
  if (!drug) return <Alert severity="warning" sx={{ m: 2 }}>Drug not found or ID is invalid.</Alert>;

  return (
      <Box sx={{ maxWidth: 800, mx: 'auto', p: 2 }}>
        <Card>
          <CardContent>
            <Typography variant="h4" gutterBottom>{drug.name}</Typography>
            {drug.manufacturer && <Typography variant="subtitle1" color="text.secondary" gutterBottom>Manufacturer: {drug.manufacturer}</Typography>}

            <Box sx={{ my: 2 }}>
              <Typography variant="body1">{drug.description || 'No description available.'}</Typography>
              <Typography variant="h6" sx={{ mt: 2 }}>Price: ${drug.price != null ? drug.price.toFixed(2) : 'N/A'}</Typography>
              <Typography variant="body2">Available: {drug.quantity != null ? drug.quantity : 'N/A'} pcs</Typography> {}
            </Box>

            {drug.quantity != null && drug.quantity > 0 ? (
                <Box sx={{ mt: 4, borderTop: '1px solid #eee', pt: 2 }}>
                  <Typography variant="h5" gutterBottom>Purchase Drug</Typography>

                  <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                    <Typography sx={{ mr: 2 }}>Quantity:</Typography>
                    <TextField
                        type="number"
                        size="small"
                        value={purchaseQuantity}
                        onChange={handleQuantityChange}
                        inputProps={{ min: 1, max: drug.quantity }}
                        disabled={drug.quantity <= 0}
                        sx={{ width: '100px' }}
                    />
                  </Box>

                  <Typography variant="h6" sx={{ mb: 2 }}>
                    Total price: ${(drug.price * purchaseQuantity).toFixed(2)}
                  </Typography>

                  <Button
                      variant="contained"
                      color="primary"
                      onClick={handleBuyDrug}
                      disabled={drug.quantity <= 0 || purchaseQuantity <= 0}
                      fullWidth
                  >
                    Buy Now
                  </Button>
                </Box>
            ) : (
                <Alert severity="info" sx={{ mt: 2 }}>This drug is currently out of stock.</Alert>
            )}
          </CardContent>
        </Card>
        <Button onClick={() => navigate('/drugs')} sx={{ mt: 2 }}>Back to Drug List</Button>
      </Box>
  );
};

export default DrugDetail;